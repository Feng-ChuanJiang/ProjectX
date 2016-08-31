package com.cci.projectx.core.controller;

import com.cci.projectx.core.HRErrorCode;
import com.cci.projectx.core.RandomUtil;
import com.cci.projectx.core.annotation.IgnoreAuth;
import com.cci.projectx.core.model.FriendsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.UserService;
import com.cci.projectx.core.vo.FriendsVO;
import com.cci.projectx.core.vo.UpdatePassVO;
import com.cci.projectx.core.vo.UserInfoVO;
import com.cci.projectx.core.vo.UserVO;
import com.google.common.cache.Cache;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
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
	@PutMapping(value = "/user/passrest")
	public ResponseEnvelope<Integer> passrest(@RequestAttribute Long userId,
											  @RequestBody UpdatePassVO updatePassVO) {
		UserModel userModel = userService.findByPrimaryKey(userId);
		if (!DigestUtils.md5Hex(updatePassVO.getOldPassword()).equals(userModel.getPassword())) {
			HRErrorCode.throwBusinessException(HRErrorCode.OLD_PASSWORD_INCORRECT);
		}
		UserModel param = new UserModel();
		param.setId(userId);
		param.setPassword(DigestUtils.md5Hex(updatePassVO.getNewPassword()));
		Integer result = userService.updateByPrimaryKeySelective(param);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result, true);
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

    @GetMapping(value = "/user/short/{id}")
	  public ResponseEnvelope<UserModel> getUserShortById(@PathVariable Long id){
		UserModel userModel=userService.findUserShortById(id);
		ResponseEnvelope<UserModel> responseEnv = new ResponseEnvelope<>(userModel,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/{id}")
	public ResponseEnvelope<Page<UserModel>> findUserFriendsById(@PathVariable Long id, Pageable pageable){
		Page<UserModel> page=userService.findUserFriendsById(id,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/{userId}/{friendsId}")
	public ResponseEnvelope<Page<UserModel>> findUserFriendsNotId(@PathVariable Long userId,@PathVariable Long[] friendsId,Pageable pageable){
		Page<UserModel> page=userService.findUserFriendsNotId(userId,friendsId,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}
	@GetMapping(value = "/friends/{userId}/apply")
	public ResponseEnvelope<List<Map<String,Object>>> findApplyforFriends(@PathVariable Long userId){
		List<Map<String,Object>> lisMap=userService.findApplyforFriends(userId);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<List<Map<String,Object>>>(lisMap,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/{userId}/waiting")
	public ResponseEnvelope<List<Map<String,Object>>> findWaitingFriends(@PathVariable Long userId){
		List<Map<String,Object>> lisMap=userService.findWaitingFriends(userId);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<List<Map<String,Object>>>(lisMap,true);
		return responseEnv;
	}

	@PostMapping(value = "/friend")
	public ResponseEnvelope<Integer> addFriends(@RequestBody FriendsVO friendsVO ){
		FriendsModel friendsModel = beanMapper.map(friendsVO, FriendsModel.class);
		Integer  result = userService.addFriends(friendsModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
		return responseEnv;
	}

	@DeleteMapping (value = "/friend/{userId}/{friendsId}")
	public ResponseEnvelope<Integer> deleteFriends(@PathVariable Long userId,@PathVariable Long friendsId ){
		FriendsModel friendsModel = new FriendsModel();
		friendsModel.setUserId(userId);
		friendsModel.setFriendId(friendsId);
		Integer  result = userService.deleteFriends(friendsModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
		return responseEnv;
	}

	@GetMapping(value = "/user/like")
	public ResponseEnvelope<List<UserModel>> getUserByUserProfile(UserVO userVO) throws UnsupportedEncodingException {
		UserModel user = beanMapper.map(userVO,UserModel.class);
		List<UserModel> listUser=userService.getUserByUserProfile(user);
		ResponseEnvelope<List<UserModel>> responseEnv = new ResponseEnvelope<>(listUser,true);
		return responseEnv;
	}

	///////////////////////////////////////////////////

	@GetMapping(value = "/friends/total/{id}")
	public ResponseEnvelope<Integer> findfriendsCount(@PathVariable Long id){
		int userIds=userService.findfriendsCount(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(userIds,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/common/total/{userIdOne}/{userIdTwo}")
	public ResponseEnvelope<Integer> findCommonFriendsCount(@PathVariable Long userIdOne,@PathVariable Long userIdTwo){
		int userIds=userService.findCommonFriendsCount(userIdOne,userIdTwo);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(userIds,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/school/total/{educationId}")
	public ResponseEnvelope<Integer> findSchoolfellowCount(@PathVariable Long educationId){
		int educationIds=userService.findSchoolfellowCount(educationId);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(educationIds,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/colleague/total/{workingId}")
	public ResponseEnvelope<Integer> findColleagueCount(@PathVariable Long workingId){
		int workingIds=userService.findColleagueCount(workingId);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(workingIds,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/common/{userIdOne}/{userIdTwo}")
	public ResponseEnvelope<Page<UserModel>> findCommonFriends(@PathVariable Long userIdOne,@PathVariable Long userIdTwo, Pageable pageable){
		Page<UserModel> page=userService.findCommonFriends(userIdOne,userIdTwo,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/school/{educationId}")
	public ResponseEnvelope<Page<UserModel>> findSchoolfellow(@PathVariable Long educationId, Pageable pageable){
		Page<UserModel> page=userService.findSchoolfellow(educationId,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	@GetMapping(value = "/friends/colleague/{workingId}")
	public ResponseEnvelope<Page<UserModel>> findColleague(@PathVariable Long workingId, Pageable pageable){
		Page<UserModel> page=userService.findColleague(workingId,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}
}
