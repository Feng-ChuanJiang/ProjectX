package com.cci.projectx.core.controller;

import com.cci.projectx.core.HRErrorCode;
import com.cci.projectx.core.RandomUtil;
import com.cci.projectx.core.annotation.IgnoreAuth;
import com.cci.projectx.core.model.FriendsModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.UserService;
import com.cci.projectx.core.vo.*;
import com.google.common.cache.Cache;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.core.exception.BusinessException;
import com.cci.projectx.core.ResponseEnvelope;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

	@Value("${sms.serverUrl}")
	private String serverUrl;
	@Value("${sms.appKey}")
	private String appKey;
	@Value("${sms.appSecret}")
	private String appSecret;
	@Value("${sms.templateCode}")
	private String templateCode;
	@IgnoreAuth
	@GetMapping(value = "/user/verifica/{phone}")
	public ResponseEnvelope<String> userVerifica(@PathVariable String phone, HttpSession httpSession) {
		//验证码
		String vali= RandomUtil.createRandom(true,6);
		httpSession.setAttribute(phone,vali);
		TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName("未拓空间");
		req.setSmsParamString(  "{number:'"+vali+"'}"  );
		req.setRecNum(phone);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
		} catch (ApiException e) {
			throw new BusinessException("500","短信发送失败");
		}
		ResponseEnvelope<String> responseEnv = new ResponseEnvelope<>(rsp.getBody(), true);
		return responseEnv;
	}

	@IgnoreAuth
	@PostMapping(value = "/user/register")
	public ResponseEnvelope<String> userRegister(@RequestBody RegisterVO registerVO, HttpSession httpSession) {
		Object vali =httpSession.getAttribute(registerVO.getMobilePhone());
		if(null==vali||!vali.toString().equals(registerVO.getCaptcha())){
			HRErrorCode.throwBusinessException(HRErrorCode.CAPTCHA_INCORRECT);
		}
		UserModel userModel = beanMapper.map(registerVO, UserModel.class);
		userService.register(userModel,registerVO.getCaptcha());
		ResponseEnvelope<String> responseEnv = new ResponseEnvelope<>("ok",true);
		return responseEnv;
	}
	@IgnoreAuth
	@GetMapping(value = "/user/existed/{phone}")
	public ResponseEnvelope<String> findUserByAccount(@PathVariable String phone) {
		UserModel existedUser=userService.findUserByAccount(phone);
		if (null != existedUser) {
			HRErrorCode.throwBusinessException(HRErrorCode.USER_HAVE_EXISTED);
		}
		ResponseEnvelope<String> responseEnv = new ResponseEnvelope<>("ok",true);
		return responseEnv;
	}
	@IgnoreAuth
	@PostMapping(value = "/user/login")
	public ResponseEnvelope<UserInfoVO> userLogin(@RequestBody LoginVO loginVO) {
		UserModel userModel = beanMapper.map(loginVO, UserModel.class);
		UserModel existedUser = userService.login(userModel);
		UserModel user=userService.findByPrimaryKey(existedUser.getId());
		UserInfoVO userInfoVO = beanMapper.map(user, UserInfoVO.class);
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
		Integer  result = userService.updateByPrimaryKey(userModel);
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
    //得到朋友信息，朋友信息不等于【单个或多个朋友编号】
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
	@PutMapping(value = "/friend")
	public ResponseEnvelope<Integer> updateFriends(@RequestBody FriendsVO friendsVO ){
		FriendsModel friendsModel = beanMapper.map(friendsVO, FriendsModel.class);
		Integer  result = userService.updateFriends(friendsModel);
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
	//共同同事
	@GetMapping(value = "/user/like/{name}")
	public ResponseEnvelope<List<UserModel>> findUserByLikeName(@PathVariable String name){
		List<UserModel> page=userService.findUserByLikeName(name);
		ResponseEnvelope<List<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}

	//共同同事
	@GetMapping(value = "/user/like/{userId}/{name}")
	public ResponseEnvelope<List<UserModel>> findFriendUserByLikeName(@PathVariable Long userId,@PathVariable String name){
		List<UserModel> page=userService.findFriendUserByLikeName(userId,name);
		ResponseEnvelope<List<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}


	///////////////////////////////////////////////////
    //好友总数
	@GetMapping(value = "/friends/total/{id}")
	public ResponseEnvelope<Integer> findfriendsCount(@PathVariable Long id){
		int userIds=userService.findfriendsCount(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(userIds,true);
		return responseEnv;
	}
     //共同好友总数
	@GetMapping(value = "/friends/common/total/{userIdOne}/{userIdTwo}")
	public ResponseEnvelope<Integer> findCommonFriendsCount(@PathVariable Long userIdOne,@PathVariable Long userIdTwo){
		int userIds=userService.findCommonFriendsCount(userIdOne,userIdTwo);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(userIds,true);
		return responseEnv;
	}
   //共同校友总数
	@GetMapping(value = "/friends/school/total/{educationId}")
	public ResponseEnvelope<Integer> findSchoolfellowCount(@PathVariable Long educationId){
		int educationIds=userService.findSchoolfellowCount(educationId);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(educationIds,true);
		return responseEnv;
	}
   //共同同事总数
	@GetMapping(value = "/friends/colleague/total/{workingId}")
	public ResponseEnvelope<Integer> findColleagueCount(@PathVariable Long workingId){
		int workingIds=userService.findColleagueCount(workingId);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(workingIds,true);
		return responseEnv;
	}
   //共同好友总数
	@GetMapping(value = "/friends/common/{userIdOne}/{userIdTwo}")
	public ResponseEnvelope<Page<UserModel>> findCommonFriends(@PathVariable Long userIdOne,@PathVariable Long userIdTwo, Pageable pageable){
		Page<UserModel> page=userService.findCommonFriends(userIdOne,userIdTwo,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}
   //共同校友
	@GetMapping(value = "/friends/school/{educationId}")
	public ResponseEnvelope<Page<UserModel>> findSchoolfellow(@PathVariable Long educationId, Pageable pageable){
		Page<UserModel> page=userService.findSchoolfellow(educationId,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}
   //共同同事
	@GetMapping(value = "/friends/colleague/{workingId}")
	public ResponseEnvelope<Page<UserModel>> findColleague(@PathVariable Long workingId, Pageable pageable){
		Page<UserModel> page=userService.findColleague(workingId,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}
	//根据电话得到朋友信息
	@PostMapping(value = "/friends/relation")
	public ResponseEnvelope<Map<String,Object>> findRelation(@RequestBody FriendPhoneVO phones){
		Map<String,Object> page=userService.findRelation(phones.getUserId(),phones.getPhones());
		ResponseEnvelope<Map<String,Object>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}


}
