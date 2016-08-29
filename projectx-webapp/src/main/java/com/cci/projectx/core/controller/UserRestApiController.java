package com.cci.projectx.core.controller;

import com.cci.projectx.core.RandomUtil;
import com.cci.projectx.core.annotation.IgnoreAuth;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.UserService;
import com.cci.projectx.core.vo.UserInfoVO;
import com.cci.projectx.core.vo.UserVO;
import com.google.common.cache.Cache;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projectx")
public class UserRestApiController {

	private final Logger logger = LoggerFactory.getLogger(UserRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private Cache<String, Long> sessionCache;

	@IgnoreAuth
	@PostMapping(value = "/core/user/login")
	public ResponseEnvelope<UserInfoVO> userLogin(@RequestBody UserVO userVO) {
		UserModel userModel = beanMapper.map(userVO, UserModel.class);
		UserModel existedUser = userService.login(userModel);
		UserInfoVO userInfoVO = beanMapper.map(existedUser, UserInfoVO.class);
		String sessionId = RandomUtil.generateAuthToken();
		sessionCache.put(sessionId, existedUser.getId());
		userInfoVO.setSessionId(sessionId);
		ResponseEnvelope<UserInfoVO> responseEnv = new ResponseEnvelope<>(userInfoVO, true);
		return responseEnv;
	}
	@GetMapping(value = "/core/user/{id}")
	public ResponseEnvelope<UserVO> getUserById(@PathVariable Long id){
		UserModel userModel = userService.findByPrimaryKey(id);
		UserVO userVO =beanMapper.map(userModel, UserVO.class);
		ResponseEnvelope<UserVO> responseEnv = new ResponseEnvelope<>(userVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/user")
    public ResponseEnvelope<Page<UserModel>> listUser(UserVO userVO,Pageable pageable){

		UserModel param = beanMapper.map(userVO, UserModel.class);
        List<UserModel> userModelModels = userService.selectPage(param,pageable);
        long count=userService.selectCount(param);
        Page<UserModel> page = new PageImpl<>(userModelModels,pageable,count);
        ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/user")
	public ResponseEnvelope<Integer> createUser(@RequestBody UserVO userVO){
		UserModel userModel = beanMapper.map(userVO, UserModel.class);
		Integer  result = userService.create(userModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/user/{id}")
	public ResponseEnvelope<Integer> deleteUserByPrimaryKey(@PathVariable Long id){
		Integer  result = userService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/user/{id}")
	public ResponseEnvelope<Integer> updateUserByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody UserVO userVO){
		UserModel userModel = beanMapper.map(userVO, UserModel.class);
		userModel.setId(id);
		Integer  result = userService.updateByPrimaryKeySelective(userModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
