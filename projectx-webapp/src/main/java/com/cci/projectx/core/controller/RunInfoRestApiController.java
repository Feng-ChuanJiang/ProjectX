package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.RunFriendsModel;
import com.cci.projectx.core.model.RunInfoModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.RunInfoService;
import com.cci.projectx.core.vo.RunFriendsVO;
import com.cci.projectx.core.vo.RunInfoVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.cci.projectx.core.ResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class RunInfoRestApiController {

	private final Logger logger = LoggerFactory.getLogger(RunInfoRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private RunInfoService runInfoService;

	@GetMapping(value = "/runInfo/{id}")
	public ResponseEnvelope<RunInfoVO> getRunInfoById(@PathVariable Long id){
		RunInfoModel runInfoModel = runInfoService.findByPrimaryKey(id);
		RunInfoVO runInfoVO =beanMapper.map(runInfoModel, RunInfoVO.class);
		ResponseEnvelope<RunInfoVO> responseEnv = new ResponseEnvelope<>(runInfoVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/runInfo")
    public ResponseEnvelope<Page<RunInfoModel>> listRunInfo(RunInfoVO runInfoVO, Pageable pageable){

		RunInfoModel param = beanMapper.map(runInfoVO, RunInfoModel.class);
        List<RunInfoModel> runInfoModelModels = runInfoService.selectPage(param,pageable);
        long count=runInfoService.selectCount(param);
        Page<RunInfoModel> page = new PageImpl<>(runInfoModelModels,pageable,count);
        ResponseEnvelope<Page<RunInfoModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/runInfo")
	public ResponseEnvelope<Integer> createRunInfo(@RequestBody RunInfoVO runInfoVO){
		RunInfoModel runInfoModel = beanMapper.map(runInfoVO, RunInfoModel.class);
		Integer result = runInfoService.create(runInfoModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/runInfo/{id}")
	public ResponseEnvelope<Integer> deleteRunInfoByPrimaryKey(@PathVariable Long id){
		Integer result = runInfoService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/runInfo/{id}")
	public ResponseEnvelope<Integer> updateRunInfoByPrimaryKeySelective(@PathVariable Long id,
																		@RequestBody RunInfoVO runInfoVO){
		RunInfoModel runInfoModel = beanMapper.map(runInfoVO, RunInfoModel.class);
		runInfoModel.setId(id);
		Integer result = runInfoService.updateByPrimaryKeySelective(runInfoModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}
	@PostMapping(value = "/runFriend")
	public ResponseEnvelope<Integer> addRunFriend(@RequestBody RunFriendsVO runFriendsVO){
		RunFriendsModel param = beanMapper.map(runFriendsVO, RunFriendsModel.class);
		Integer result = runInfoService.addFriends(param);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
		return responseEnv;
	}

	@PutMapping(value = "/runFriend")
	public ResponseEnvelope<Integer> updateRunFriend(@RequestBody RunFriendsVO runFriendsVO){
		RunFriendsModel param = beanMapper.map(runFriendsVO, RunFriendsModel.class);
		Integer result = runInfoService.updateFriends(param);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
		return responseEnv;
	}

	@GetMapping(value = "/runFriend/all/{id}")
	public ResponseEnvelope<Page<UserModel>> findUserRunFriendsById(@PathVariable Long id, Pageable pageable){
		Page<UserModel> page=runInfoService.findUserRunFriendsById(id,pageable);
		ResponseEnvelope<Page<UserModel>> responseEnv = new ResponseEnvelope<>(page,true);
		return responseEnv;
	}


	@GetMapping(value = "/runFriend/apply/{userId}")
	public ResponseEnvelope<List<Map<String,Object>>> findApplyforRunFriends(@PathVariable Long userId){
		List<Map<String,Object>> lisMap=runInfoService.findApplyforRunFriends(userId);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<List<Map<String,Object>>>(lisMap,true);
		return responseEnv;
	}

	@GetMapping(value = "/runFriend/waiting/{userId}")
	public ResponseEnvelope<List<Map<String,Object>>> findWaitingRunFriends(@PathVariable Long userId){
		List<Map<String,Object>> lisMap=runInfoService.findWaitingRunFriends(userId);
		ResponseEnvelope<List<Map<String,Object>>> responseEnv = new ResponseEnvelope<List<Map<String,Object>>>(lisMap,true);
		return responseEnv;
	}

	@GetMapping(value = "/runFriend/area/{userId}")
	public ResponseEnvelope<List<UserModel>> findAssignRunFriends(@PathVariable Long userId){
		List<UserModel> lisMap=runInfoService.findAssignRunFriends(userId,new PageRequest(0, Integer.MAX_VALUE));
		ResponseEnvelope<List<UserModel>> responseEnv = new ResponseEnvelope<>(lisMap,true);
		return responseEnv;
	}

}
