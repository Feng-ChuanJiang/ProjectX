package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.FriendSetingModel;
import com.cci.projectx.core.service.FriendSetingService;
import com.cci.projectx.core.vo.FriendSetingVO;
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
public class FriendSetingRestApiController {

	private final Logger logger = LoggerFactory.getLogger(FriendSetingRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private FriendSetingService friendSetingService;

	@GetMapping(value = "/friendSeting/{id}")
	public ResponseEnvelope<FriendSetingVO> getFriendSetingById(@PathVariable Long id){
		FriendSetingModel friendSetingModel = friendSetingService.findByPrimaryKey(id);
		FriendSetingVO friendSetingVO =beanMapper.map(friendSetingModel, FriendSetingVO.class);
		ResponseEnvelope<FriendSetingVO> responseEnv = new ResponseEnvelope<>(friendSetingVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/friendSeting")
    public ResponseEnvelope<Page<FriendSetingModel>> listFriendSeting(FriendSetingVO friendSetingVO, Pageable pageable){

		FriendSetingModel param = beanMapper.map(friendSetingVO, FriendSetingModel.class);
        List<FriendSetingModel> friendSetingModelModels = friendSetingService.selectPage(param,pageable);
        long count=friendSetingService.selectCount(param);
        Page<FriendSetingModel> page = new PageImpl<>(friendSetingModelModels,pageable,count);
        ResponseEnvelope<Page<FriendSetingModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/friendSeting")
	public ResponseEnvelope<Integer> createFriendSeting(@RequestBody FriendSetingVO friendSetingVO){
		FriendSetingModel friendSetingModel = beanMapper.map(friendSetingVO, FriendSetingModel.class);
		Integer result = friendSetingService.create(friendSetingModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/friendSeting/{id}")
	public ResponseEnvelope<Integer> deleteFriendSetingByPrimaryKey(@PathVariable Long id){
		Integer result = friendSetingService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/friendSeting/{id}")
	public ResponseEnvelope<Integer> updateFriendSetingByPrimaryKeySelective(@PathVariable Long id,
																			 @RequestBody FriendSetingVO friendSetingVO){
		FriendSetingModel friendSetingModel = beanMapper.map(friendSetingVO, FriendSetingModel.class);
		friendSetingModel.setId(id);
		Integer result = friendSetingService.updateByPrimaryKeySelective(friendSetingModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
