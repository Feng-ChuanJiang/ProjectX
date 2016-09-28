package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.DiscussPermissionModel;
import com.cci.projectx.core.service.DiscussPermissionService;
import com.cci.projectx.core.vo.DiscussPermissionVO;
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
public class DiscussPermissionRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DiscussPermissionRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DiscussPermissionService discussPermissionService;

	@GetMapping(value = "/discussPermission/{id}")
	public ResponseEnvelope<DiscussPermissionVO> getDiscussPermissionById(@PathVariable Long id){
		DiscussPermissionModel discussPermissionModel = discussPermissionService.findByPrimaryKey(id);
		DiscussPermissionVO discussPermissionVO =beanMapper.map(discussPermissionModel, DiscussPermissionVO.class);
		ResponseEnvelope<DiscussPermissionVO> responseEnv = new ResponseEnvelope<>(discussPermissionVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/discussPermission")
    public ResponseEnvelope<Page<DiscussPermissionModel>> listDiscussPermission(DiscussPermissionVO discussPermissionVO, Pageable pageable){

		DiscussPermissionModel param = beanMapper.map(discussPermissionVO, DiscussPermissionModel.class);
        List<DiscussPermissionModel> discussPermissionModelModels = discussPermissionService.selectPage(param,pageable);
        long count=discussPermissionService.selectCount(param);
        Page<DiscussPermissionModel> page = new PageImpl<>(discussPermissionModelModels,pageable,count);
        ResponseEnvelope<Page<DiscussPermissionModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/discussPermission")
	public ResponseEnvelope<Integer> createDiscussPermission(@RequestBody DiscussPermissionVO discussPermissionVO){
		DiscussPermissionModel discussPermissionModel = beanMapper.map(discussPermissionVO, DiscussPermissionModel.class);
		Integer  result = discussPermissionService.create(discussPermissionModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/discussPermission/{id}")
	public ResponseEnvelope<Integer> deleteDiscussPermissionByPrimaryKey(@PathVariable Long id){
		Integer  result = discussPermissionService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/discussPermission/{id}")
	public ResponseEnvelope<Integer> updateDiscussPermissionByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody DiscussPermissionVO discussPermissionVO){
		DiscussPermissionModel discussPermissionModel = beanMapper.map(discussPermissionVO, DiscussPermissionModel.class);
		discussPermissionModel.setId(id);
		Integer  result = discussPermissionService.updateByPrimaryKeySelective(discussPermissionModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
