package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.UserCircleModel;
import com.cci.projectx.core.service.UserCircleService;
import com.cci.projectx.core.vo.UserCircleVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.cci.projectx.core.ResponseEnvelope;
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
public class UserCircleRestApiController {

	private final Logger logger = LoggerFactory.getLogger(UserCircleRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private UserCircleService userCircleService;

	@GetMapping(value = "/userCircle/{id}")
	public ResponseEnvelope<UserCircleVO> getUserCircleById(@PathVariable Long id){
		UserCircleModel userCircleModel = userCircleService.findByPrimaryKey(id);
		UserCircleVO userCircleVO =beanMapper.map(userCircleModel, UserCircleVO.class);
		ResponseEnvelope<UserCircleVO> responseEnv = new ResponseEnvelope<>(userCircleVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/userCircle")
    public ResponseEnvelope<Page<UserCircleModel>> listUserCircle(UserCircleVO userCircleVO, Pageable pageable){

		UserCircleModel param = beanMapper.map(userCircleVO, UserCircleModel.class);
        List<UserCircleModel> userCircleModelModels = userCircleService.selectPage(param,pageable);
        long count=userCircleService.selectCount(param);
        Page<UserCircleModel> page = new PageImpl<>(userCircleModelModels,pageable,count);
        ResponseEnvelope<Page<UserCircleModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/userCircle")
	public ResponseEnvelope<Integer> createUserCircle(@RequestBody UserCircleVO userCircleVO){
		UserCircleModel userCircleModel = beanMapper.map(userCircleVO, UserCircleModel.class);
		Integer result = userCircleService.create(userCircleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/userCircle/{id}")
	public ResponseEnvelope<Integer> deleteUserCircleByPrimaryKey(@PathVariable Long id){
		Integer result = userCircleService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/userCircle/{id}")
	public ResponseEnvelope<Integer> updateUserCircleByPrimaryKeySelective(@PathVariable Long id,
																		   @RequestBody UserCircleVO userCircleVO){
		UserCircleModel userCircleModel = beanMapper.map(userCircleVO, UserCircleModel.class);
		userCircleModel.setId(id);
		Integer result = userCircleService.updateByPrimaryKeySelective(userCircleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
