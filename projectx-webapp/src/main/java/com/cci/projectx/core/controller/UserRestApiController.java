package com.cci.projectx.core.controller;

import com.cci.projectx.core.RandomUtil;
import com.cci.projectx.core.annotation.IgnoreAuth;
import com.cci.projectx.core.entity.User;
import com.cci.projectx.core.model.FriendsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.UserService;
import com.cci.projectx.core.vo.FriendsVO;
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
import java.util.Map;

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
	@PostMapping(value = "/user/login")
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
	@GetMapping(value = "/user/{id}")
	public ResponseEnvelope<UserVO> getUserById(@PathVariable Long id){
		UserModel userModel = userService.findByPrimaryKey(id);
		UserVO userVO =beanMapper.map(userModel, UserVO.class);
		ResponseEnvelope<UserVO> responseEnv = new ResponseEnvelope<>(userVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/user")
    public ResponseEnvelope<Page<UserModel>> listUser(UserVO userVO,Pageable pageable){

		UserModel param = beanMapper.map(userVO, UserModel.class);
        List<UserModel> userModelModels = userService.selectPage(param,pageable);
        long count=userService.selectCount(param);
        Page<UserModel> page = new PageImpl<>(userModelModels,pageable,count);
        ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/user")
	public ResponseEnvelope<Integer> createUser(@RequestBody UserVO userVO){
		UserModel userModel = beanMapper.map(userVO, UserModel.class);
		Integer  result = userService.create(userModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/user/{id}")
	public ResponseEnvelope<Integer> deleteUserByPrimaryKey(@PathVariable Long id){
		Integer  result = userService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/user/{id}")
	public ResponseEnvelope<Integer> updateUserByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody UserVO userVO){
		UserModel userModel = beanMapper.map(userVO, UserModel.class);
		userModel.setId(id);
		Integer  result = userService.updateByPrimaryKeySelective(userModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

    @GetMapping(value = "/user/getUserShortById/{id}")
	  public ResponseEnvelope<Map> getUserShortById(@PathVariable Long id){
		Map<String, Object> map=userService.findUserShortById(id);
		ResponseEnvelope<Map> responseEnv = new ResponseEnvelope<Map>(map,true);
		return responseEnv;
	}

	@GetMapping(value = "/user/findUserFriendsById/{id}")
	public ResponseEnvelope<List<Map<String,Object>>> findUserFriendsById(@PathVariable Long id){
		List<Map<String,Object>> lisMap=userService.findUserFriendsById(id);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<List<Map<String,Object>>>(lisMap,true);
		return responseEnv;
	}

	@GetMapping(value = "/user/findUserFriendsNotId/{userId}/{friendsId}")
	public ResponseEnvelope<List<Map<String,Object>>> findUserFriendsNotId(@PathVariable Long userId,@PathVariable Long[] friendsId){
		List<Map<String,Object>> lisMap=userService.findUserFriendsNotId(userId, friendsId);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<List<Map<String,Object>>>(lisMap,true);
		return responseEnv;
	}
	@GetMapping(value = "/user/findApplyforFriends/{userId}")
	public ResponseEnvelope<List<Map<String,Object>>> findApplyforFriends(@PathVariable Long userId){
		List<Map<String,Object>> lisMap=userService.findApplyforFriends(userId);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<List<Map<String,Object>>>(lisMap,true);
		return responseEnv;
	}

	@GetMapping(value = "/user/findWaitingFriends/{userId}")
	public ResponseEnvelope<List<Map<String,Object>>> findWaitingFriends(@PathVariable Long userId){
		List<Map<String,Object>> lisMap=userService.findWaitingFriends(userId);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<List<Map<String,Object>>>(lisMap,true);
		return responseEnv;
	}

	@PostMapping(value = "/addFriend")
	public ResponseEnvelope<Integer> addFriends(@RequestBody FriendsVO friendsVO ){
		FriendsModel friendsModel = beanMapper.map(friendsVO, FriendsModel.class);
		Integer  result = userService.addFriends(friendsModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
		return responseEnv;
	}

	@PostMapping(value = "/deleteFriend")
	public ResponseEnvelope<Integer> deleteFriends(@RequestBody FriendsVO friendsVO ){
		FriendsModel friendsModel = beanMapper.map(friendsVO, FriendsModel.class);
		Integer  result = userService.deleteFriends(friendsModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
		return responseEnv;
	}

	@PostMapping(value = "/user/getUserByUserProfile")
	public ResponseEnvelope<List<User>> getUserByUserProfile(@RequestBody UserVO userVO){
		User user = beanMapper.map(userVO,User.class);
		List<User> listUser=userService.getUserByUserProfile(user);
		ResponseEnvelope<List<User>> responseEnv = new ResponseEnvelope<List<User>>(listUser,true);
		return responseEnv;
	}

}
