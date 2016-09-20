package com.cci.projectx.core.controller;

import com.cci.projectx.core.InteractPermissionType;
import com.cci.projectx.core.model.InteractModel;
import com.cci.projectx.core.model.InteractPermissionModel;
import com.cci.projectx.core.model.UserInteractCircleModel;
import com.cci.projectx.core.service.InteractPermissionService;
import com.cci.projectx.core.service.InteractService;
import com.cci.projectx.core.service.UserInteractCircleService;
import com.cci.projectx.core.vo.InteractPermissionVO;
import com.cci.projectx.core.vo.InteractVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.cci.projectx.core.ResponseEnvelope;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class InteractRestApiController {

	private final Logger logger = LoggerFactory.getLogger(InteractRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private InteractService interactService;

	@Autowired
	private UserInteractCircleService userInteractCircleService;

	@Autowired
	private InteractPermissionService interactPermissionService;

	@GetMapping(value = "/interact/{id}")
	public ResponseEnvelope<InteractVO> getInteractById(@PathVariable Long id){
		InteractModel interactModel = interactService.findByPrimaryKey(id);
		InteractVO interactVO =beanMapper.map(interactModel, InteractVO.class);
		ResponseEnvelope<InteractVO> responseEnv = new ResponseEnvelope<>(interactVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/interact")
    public ResponseEnvelope<Page<InteractModel>> listInteract(InteractVO interactVO,Pageable pageable){

		InteractModel param = beanMapper.map(interactVO, InteractModel.class);
        List<InteractModel> interactModelModels = interactService.selectPage(param,pageable);
        long count=interactService.selectCount(param);
        Page<InteractModel> page = new PageImpl<>(interactModelModels,pageable,count);
        ResponseEnvelope<Page<InteractModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/interact")
	public ResponseEnvelope<Integer> createInteract(@RequestBody InteractPermissionVO interactVO){
		InteractModel interactModel = beanMapper.map(interactVO, InteractModel.class);
		Integer  result = interactService.create(interactModel);

		List<Long> circleIds=interactVO.getCircleIds();
		List<Long> friendIds=interactVO.getFriendIds();
		//添加圈子
		if(CollectionUtils.isNotEmpty(circleIds)){
			for (Long circleId : circleIds) {
				UserInteractCircleModel uic=new UserInteractCircleModel();
				uic.setUserId(interactVO.getUserId());
				uic.setCircleId(circleId);
				uic.setInteractId(interactModel.getId());
				userInteractCircleService.createSelective(uic);
			}
		}
		//添加不能查看此动态的人员
		if(interactVO.getPrivacyPermission().intValue() == InteractPermissionType.ASSIGNPERMISSION.getType()){
         if(CollectionUtils.isNotEmpty(friendIds)){
			 for (Long friendId : friendIds) {
				 InteractPermissionModel ipm=new InteractPermissionModel();
				 ipm.setInteractId(interactModel.getId());
				 ipm.setUserId(interactModel.getUserId());
				 ipm.setFriendId(friendId);
				 interactPermissionService.createSelective(ipm);
			 }
		 }
		}
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/interact/{id}")
	public ResponseEnvelope<Integer> deleteInteractByPrimaryKey(@PathVariable Long id){
		Integer  result = interactService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/interact/{id}")
	public ResponseEnvelope<Integer> updateInteractByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody InteractVO interactVO){
		InteractModel interactModel = beanMapper.map(interactVO, InteractModel.class);
		interactModel.setId(id);
		Integer  result = interactService.updateByPrimaryKeySelective(interactModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

	@GetMapping(value = "/interact/circle/{circleId}")
	public ResponseEnvelope<Page<InteractModel>> selectPageByCircleId(@PathVariable Long circleId,Pageable pageable){
		Page<InteractModel> page = interactService.selectPageByCircleId(circleId,pageable);
		ResponseEnvelope<Page<InteractModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	@GetMapping(value = "/interact/friend/{userId}")
	public ResponseEnvelope<Page<InteractModel>> selectPageByFriend(@PathVariable Long userId,Pageable pageable){
		Page<InteractModel> page = interactService.selectPageByFriend(userId,pageable);
		ResponseEnvelope<Page<InteractModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

}
