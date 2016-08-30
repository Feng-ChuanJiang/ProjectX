package com.cci.projectx.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import com.wlw.pylon.web.rest.annotation.RestApiController;

import com.cci.projectx.core.service.InteractService;
import com.cci.projectx.core.model.InteractModel;
import com.cci.projectx.core.vo.InteractVO;

import java.util.List;

@RestController
@RequestMapping("/projectx")
public class InteractRestApiController {

	private final Logger logger = LoggerFactory.getLogger(InteractRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private InteractService interactService;

	@GetMapping(value = "/interact/{id}")
	public ResponseEnvelope<InteractVO> getInteractById(@PathVariable Long id){
		InteractModel interactModel = interactService.findByPrimaryKey(id);
		InteractVO interactVO =beanMapper.map(interactModel, InteractVO.class);
		ResponseEnvelope<InteractVO> responseEnv = new ResponseEnvelope<>(interactVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/interact")
    public ResponseEnvelope<Page<InteractModel>> listInteract(InteractVO interactVO,Pageable pageable){

		InteractModel param = beanMapper.map(interactVO, InteractModel.class);
        List<InteractModel> interactModelModels = interactService.selectPage(param,pageable);
        long count=interactService.selectCount(param);
        Page<InteractModel> page = new PageImpl<>(interactModelModels,pageable,count);
        ResponseEnvelope<Page<InteractModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/interact")
	public ResponseEnvelope<Integer> createInteract(@RequestBody InteractVO interactVO){
		InteractModel interactModel = beanMapper.map(interactVO, InteractModel.class);
		Integer  result = interactService.create(interactModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/interact/{id}")
	public ResponseEnvelope<Integer> deleteInteractByPrimaryKey(@PathVariable Long id){
		Integer  result = interactService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/interact/{id}")
	public ResponseEnvelope<Integer> updateInteractByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody InteractVO interactVO){
		InteractModel interactModel = beanMapper.map(interactVO, InteractModel.class);
		interactModel.setId(id);
		Integer  result = interactService.updateByPrimaryKeySelective(interactModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
