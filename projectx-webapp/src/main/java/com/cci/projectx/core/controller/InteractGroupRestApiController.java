package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.InteractGroupModel;
import com.cci.projectx.core.service.InteractGroupService;
import com.cci.projectx.core.vo.InteractGroupVO;
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
@RequestMapping("/")
public class InteractGroupRestApiController {

	private final Logger logger = LoggerFactory.getLogger(InteractGroupRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private InteractGroupService interactGroupService;

	@GetMapping(value = "/interactGroup/{id}")
	public ResponseEnvelope<InteractGroupVO> getInteractGroupById(@PathVariable Long id){
		InteractGroupModel interactGroupModel = interactGroupService.findByPrimaryKey(id);
		InteractGroupVO interactGroupVO =beanMapper.map(interactGroupModel, InteractGroupVO.class);
		ResponseEnvelope<InteractGroupVO> responseEnv = new ResponseEnvelope<>(interactGroupVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/interactGroup")
    public ResponseEnvelope<Page<InteractGroupModel>> listInteractGroup(InteractGroupVO interactGroupVO, Pageable pageable){

		InteractGroupModel param = beanMapper.map(interactGroupVO, InteractGroupModel.class);
        List<InteractGroupModel> interactGroupModelModels = interactGroupService.selectPage(param,pageable);
        long count=interactGroupService.selectCount(param);
        Page<InteractGroupModel> page = new PageImpl<>(interactGroupModelModels,pageable,count);
        ResponseEnvelope<Page<InteractGroupModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/interactGroup")
	public ResponseEnvelope<Integer> createInteractGroup(@RequestBody InteractGroupVO interactGroupVO){
		InteractGroupModel interactGroupModel = beanMapper.map(interactGroupVO, InteractGroupModel.class);
		Integer result = interactGroupService.create(interactGroupModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/interactGroup/{id}")
	public ResponseEnvelope<Integer> deleteInteractGroupByPrimaryKey(@PathVariable Long id){
		Integer result = interactGroupService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/interactGroup/{id}")
	public ResponseEnvelope<Integer> updateInteractGroupByPrimaryKeySelective(@PathVariable Long id,
																			  @RequestBody InteractGroupVO interactGroupVO){
		InteractGroupModel interactGroupModel = beanMapper.map(interactGroupVO, InteractGroupModel.class);
		interactGroupModel.setId(id);
		Integer result = interactGroupService.updateByPrimaryKeySelective(interactGroupModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
