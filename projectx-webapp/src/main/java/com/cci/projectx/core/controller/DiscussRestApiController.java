package com.cci.projectx.core.controller;

import com.cci.projectx.core.JPushPush;
import com.cci.projectx.core.ResponseEnvelope;
import com.cci.projectx.core.model.DiscussModel;
import com.cci.projectx.core.model.DiscussMyModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.DiscussService;
import com.cci.projectx.core.service.UserService;
import com.cci.projectx.core.vo.DiscussOnlyVO;
import com.cci.projectx.core.vo.DiscussTitleVO;
import com.cci.projectx.core.vo.DiscussVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class DiscussRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DiscussRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DiscussService discussService;

	@Autowired
	private UserService userService;

	@Autowired
	private JPushPush jPushPush;

	@GetMapping(value = "/discuss/{id}")
	public ResponseEnvelope<DiscussOnlyVO> getDiscussById(@PathVariable Long id){
		DiscussModel discussModel = discussService.findByPrimaryKey(id);
		DiscussOnlyVO discussVO =beanMapper.map(discussModel, DiscussOnlyVO.class);
		//查询这个研讨会用户信息
        List<DiscussMyModel> userModels=discussService.findUserByPrimary(discussVO.getUserId(),discussVO.getTitle());
		discussVO.setUsers(userModels);
		//设置用户信息
		UserModel user=userService.findUserShortById(discussVO.getUserId());
		discussVO.setMobilePhone(user.getMobilePhone());
		discussVO.setUserName(user.getName());
		discussVO.setUserPhoto(user.getPhotos());
		ResponseEnvelope<DiscussOnlyVO> responseEnv = new ResponseEnvelope<>(discussVO,true);
		return responseEnv;
	}

	@PostMapping(value= "/discuss/title")
	public ResponseEnvelope<DiscussOnlyVO> getDiscussByName(@RequestBody  DiscussTitleVO discussTitleVO){
		DiscussModel discussModel = discussService.findByPrimaryKey(discussTitleVO.getUserId(),discussTitleVO.getTitle());
		DiscussOnlyVO discussVO =beanMapper.map(discussModel, DiscussOnlyVO.class);
		//查询这个研讨会用户信息
		List<DiscussMyModel> userModels=discussService.findUserByPrimary(discussVO.getUserId(),discussVO.getTitle());
		discussVO.setUsers(userModels);
		//设置用户信息
		UserModel user=userService.findUserShortById(discussVO.getUserId());
		discussVO.setUserName(user.getName());
		discussVO.setUserPhoto(user.getPhotos());
		discussVO.setMobilePhone(user.getMobilePhone());
		ResponseEnvelope<DiscussOnlyVO> responseEnv = new ResponseEnvelope<>(discussVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/discuss")
    public ResponseEnvelope<List<DiscussMyModel>> listDiscuss(DiscussVO discussVO){
		Pageable pageable =new PageRequest(0,Integer.MAX_VALUE);
				DiscussModel param = beanMapper.map(discussVO, DiscussModel.class);
        List<DiscussMyModel> discussModelModels = discussService.selectPage(param,pageable);
		for (DiscussMyModel discussModelModel : discussModelModels) {
			//查询这个研讨会用户信息
			List<DiscussMyModel> userModels=discussService.findUserByPrimary(discussModelModel.getUserId(),discussModelModel.getTitle());
			discussModelModel.setUsers(userModels);
			//设置用户信息
			UserModel user=userService.findUserShortById(discussModelModel.getUserId());
			discussModelModel.setUserName(user.getName());
			discussModelModel.setUserPhoto(user.getPhotos());
			discussModelModel.setMobilePhone(user.getMobilePhone());
		}
        ResponseEnvelope<List<DiscussMyModel>> responseEnv = new ResponseEnvelope<>(discussModelModels,true);
        return responseEnv;
    }

	@GetMapping(value = "/discuss/all")
	public ResponseEnvelope<List<DiscussMyModel>> listDiscussAll(DiscussVO discussVO){
		List<DiscussMyModel> discussModelModels = discussService.selectDiscussAll(discussVO.getUserId());
		for (DiscussMyModel discussModelModel : discussModelModels) {
			//查询这个研讨会用户信息
			List<DiscussMyModel> userModels=discussService.findUserByPrimary(discussModelModel.getUserId(),discussModelModel.getTitle());
			discussModelModel.setUsers(userModels);
			//设置用户信息
			UserModel user=userService.findUserShortById(discussModelModel.getUserId());
			discussModelModel.setMobilePhone(user.getMobilePhone());
			discussModelModel.setUserName(user.getName());
			discussModelModel.setUserPhoto(user.getPhotos());
		}
		ResponseEnvelope<List<DiscussMyModel>> responseEnv = new ResponseEnvelope<>(discussModelModels,true);
		return responseEnv;
	}

	@PostMapping(value = "/discuss")
	public ResponseEnvelope<Long> createDiscuss(@RequestBody DiscussVO discussVO){
		DiscussModel discussModel = beanMapper.map(discussVO, DiscussModel.class);
		//推送
        if(CollectionUtils.isNotEmpty(discussVO.getInviteUserIds())){
			for (Long aLong : discussVO.getInviteUserIds()) {
				UserModel userModel=userService.findUserShortById(discussVO.getUserId());
				jPushPush.buildPushObject_all_alias_alert(aLong, userModel.getName() + JPushPush.SEMINAR_DISCUSS, jPushPush.convertBean(discussVO));
			}
		}
		Integer  result = discussService.create(discussModel);
		ResponseEnvelope<Long> responseEnv = new ResponseEnvelope<>(discussModel.getId(),true);
        return responseEnv;
	}

    @DeleteMapping(value = "/discuss/{id}")
	public ResponseEnvelope<Integer> deleteDiscussByPrimaryKey(@PathVariable Long id){
		Integer  result = discussService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/discuss/{id}")
	public ResponseEnvelope<Integer> updateDiscussByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody DiscussVO discussVO){
		DiscussModel discussModel = beanMapper.map(discussVO, DiscussModel.class);
		discussModel.setId(id);
		Integer  result = discussService.updateByPrimaryKeySelective(discussModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
