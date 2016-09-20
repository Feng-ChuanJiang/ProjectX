package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.InteractPermissionModel;
import com.cci.projectx.core.service.InteractPermissionService;
import com.cci.projectx.core.vo.InteractPermissionVO;
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
public class InteractPermissionRestApiController {

	private final Logger logger = LoggerFactory.getLogger(InteractPermissionRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private InteractPermissionService interactPermissionService;

	@GetMapping(value = "/interactPermission/{id}")
	public ResponseEnvelope<InteractPermissionVO> getInteractPermissionById(@PathVariable Long id){
		InteractPermissionModel interactPermissionModel = interactPermissionService.findByPrimaryKey(id);
		InteractPermissionVO interactPermissionVO =beanMapper.map(interactPermissionModel, InteractPermissionVO.class);
		ResponseEnvelope<InteractPermissionVO> responseEnv = new ResponseEnvelope<>(interactPermissionVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/interactPermission")
    public ResponseEnvelope<Page<InteractPermissionModel>> listInteractPermission(InteractPermissionVO interactPermissionVO, Pageable pageable){

		InteractPermissionModel param = beanMapper.map(interactPermissionVO, InteractPermissionModel.class);
        List<InteractPermissionModel> interactPermissionModelModels = interactPermissionService.selectPage(param,pageable);
        long count=interactPermissionService.selectCount(param);
        Page<InteractPermissionModel> page = new PageImpl<>(interactPermissionModelModels,pageable,count);
        ResponseEnvelope<Page<InteractPermissionModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/interactPermission")
	public ResponseEnvelope<Integer> createInteractPermission(@RequestBody InteractPermissionVO interactPermissionVO){
		InteractPermissionModel interactPermissionModel = beanMapper.map(interactPermissionVO, InteractPermissionModel.class);
		Integer result = interactPermissionService.create(interactPermissionModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/interactPermission/{id}")
	public ResponseEnvelope<Integer> deleteInteractPermissionByPrimaryKey(@PathVariable Long id){
		Integer result = interactPermissionService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/interactPermission/{id}")
	public ResponseEnvelope<Integer> updateInteractPermissionByPrimaryKeySelective(@PathVariable Long id,
																				   @RequestBody InteractPermissionVO interactPermissionVO){
		InteractPermissionModel interactPermissionModel = beanMapper.map(interactPermissionVO, InteractPermissionModel.class);
		interactPermissionModel.setId(id);
		Integer result = interactPermissionService.updateByPrimaryKeySelective(interactPermissionModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
