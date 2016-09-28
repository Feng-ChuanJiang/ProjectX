package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.DiscussInviteModel;
import com.cci.projectx.core.service.DiscussInviteService;
import com.cci.projectx.core.vo.DiscussInviteVO;
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
public class DiscussInviteRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DiscussInviteRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DiscussInviteService discussInviteService;

	@GetMapping(value = "/discussInvite/{id}")
	public ResponseEnvelope<DiscussInviteVO> getDiscussInviteById(@PathVariable Long id){
		DiscussInviteModel discussInviteModel = discussInviteService.findByPrimaryKey(id);
		DiscussInviteVO discussInviteVO =beanMapper.map(discussInviteModel, DiscussInviteVO.class);
		ResponseEnvelope<DiscussInviteVO> responseEnv = new ResponseEnvelope<>(discussInviteVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/discussInvite")
    public ResponseEnvelope<Page<DiscussInviteModel>> listDiscussInvite(DiscussInviteVO discussInviteVO, Pageable pageable){

		DiscussInviteModel param = beanMapper.map(discussInviteVO, DiscussInviteModel.class);
        List<DiscussInviteModel> discussInviteModelModels = discussInviteService.selectPage(param,pageable);
        long count=discussInviteService.selectCount(param);
        Page<DiscussInviteModel> page = new PageImpl<>(discussInviteModelModels,pageable,count);
        ResponseEnvelope<Page<DiscussInviteModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/discussInvite")
	public ResponseEnvelope<Integer> createDiscussInvite(@RequestBody DiscussInviteVO discussInviteVO){
		DiscussInviteModel discussInviteModel = beanMapper.map(discussInviteVO, DiscussInviteModel.class);
		Integer  result = discussInviteService.create(discussInviteModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/discussInvite/{id}")
	public ResponseEnvelope<Integer> deleteDiscussInviteByPrimaryKey(@PathVariable Long id){
		Integer  result = discussInviteService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/discussInvite/{id}")
	public ResponseEnvelope<Integer> updateDiscussInviteByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody DiscussInviteVO discussInviteVO){
		DiscussInviteModel discussInviteModel = beanMapper.map(discussInviteVO, DiscussInviteModel.class);
		discussInviteModel.setId(id);
		Integer  result = discussInviteService.updateByPrimaryKeySelective(discussInviteModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
