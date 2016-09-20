package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.UserInteractCircleModel;
import com.cci.projectx.core.service.UserInteractCircleService;
import com.cci.projectx.core.vo.UserInteractCircleVO;
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
public class UserInteractCircleRestApiController {

	private final Logger logger = LoggerFactory.getLogger(UserInteractCircleRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private UserInteractCircleService userInteractCircleService;

	@GetMapping(value = "/userInteractCircle/{id}")
	public ResponseEnvelope<UserInteractCircleVO> getUserInteractCircleById(@PathVariable Long id){
		UserInteractCircleModel userInteractCircleModel = userInteractCircleService.findByPrimaryKey(id);
		UserInteractCircleVO userInteractCircleVO =beanMapper.map(userInteractCircleModel, UserInteractCircleVO.class);
		ResponseEnvelope<UserInteractCircleVO> responseEnv = new ResponseEnvelope<>(userInteractCircleVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/userInteractCircle")
    public ResponseEnvelope<Page<UserInteractCircleModel>> listUserInteractCircle(UserInteractCircleVO userInteractCircleVO, Pageable pageable){

		UserInteractCircleModel param = beanMapper.map(userInteractCircleVO, UserInteractCircleModel.class);
        List<UserInteractCircleModel> userInteractCircleModelModels = userInteractCircleService.selectPage(param,pageable);
        long count=userInteractCircleService.selectCount(param);
        Page<UserInteractCircleModel> page = new PageImpl<>(userInteractCircleModelModels,pageable,count);
        ResponseEnvelope<Page<UserInteractCircleModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/userInteractCircle")
	public ResponseEnvelope<Integer> createUserInteractCircle(@RequestBody UserInteractCircleVO userInteractCircleVO){
		UserInteractCircleModel userInteractCircleModel = beanMapper.map(userInteractCircleVO, UserInteractCircleModel.class);
		Integer result = userInteractCircleService.create(userInteractCircleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/userInteractCircle/{id}")
	public ResponseEnvelope<Integer> deleteUserInteractCircleByPrimaryKey(@PathVariable Long id){
		Integer result = userInteractCircleService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/userInteractCircle/{id}")
	public ResponseEnvelope<Integer> updateUserInteractCircleByPrimaryKeySelective(@PathVariable Long id,
																				   @RequestBody UserInteractCircleVO userInteractCircleVO){
		UserInteractCircleModel userInteractCircleModel = beanMapper.map(userInteractCircleVO, UserInteractCircleModel.class);
		userInteractCircleModel.setId(id);
		Integer result = userInteractCircleService.updateByPrimaryKeySelective(userInteractCircleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
