package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.WorkingExperienceModel;
import com.cci.projectx.core.service.WorkingExperienceService;
import com.cci.projectx.core.vo.WorkingExperienceVO;
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
@RequestMapping("/projectx")
public class WorkingExperienceRestApiController {

	private final Logger logger = LoggerFactory.getLogger(WorkingExperienceRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private WorkingExperienceService workingExperienceService;

	@GetMapping(value = "/workingExperience/{id}")
	public ResponseEnvelope<WorkingExperienceVO> getWorkingExperienceById(@PathVariable Long id){
		WorkingExperienceModel workingExperienceModel = workingExperienceService.findByPrimaryKey(id);
		WorkingExperienceVO workingExperienceVO =beanMapper.map(workingExperienceModel, WorkingExperienceVO.class);
		ResponseEnvelope<WorkingExperienceVO> responseEnv = new ResponseEnvelope<>(workingExperienceVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/workingExperience")
    public ResponseEnvelope<Page<WorkingExperienceModel>> listWorkingExperience(WorkingExperienceVO workingExperienceVO,Pageable pageable){

		WorkingExperienceModel param = beanMapper.map(workingExperienceVO, WorkingExperienceModel.class);
        List<WorkingExperienceModel> workingExperienceModelModels = workingExperienceService.selectPage(param,pageable);
        long count=workingExperienceService.selectCount(param);
        Page<WorkingExperienceModel> page = new PageImpl<>(workingExperienceModelModels,pageable,count);
        ResponseEnvelope<Page<WorkingExperienceModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/workingExperience")
	public ResponseEnvelope<Integer> createWorkingExperience(@RequestBody WorkingExperienceVO workingExperienceVO){
		WorkingExperienceModel workingExperienceModel = beanMapper.map(workingExperienceVO, WorkingExperienceModel.class);
		Integer  result = workingExperienceService.create(workingExperienceModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/workingExperience/{id}")
	public ResponseEnvelope<Integer> deleteWorkingExperienceByPrimaryKey(@PathVariable Long id){
		Integer  result = workingExperienceService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/workingExperience/{id}")
	public ResponseEnvelope<Integer> updateWorkingExperienceByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody WorkingExperienceVO workingExperienceVO){
		WorkingExperienceModel workingExperienceModel = beanMapper.map(workingExperienceVO, WorkingExperienceModel.class);
		workingExperienceModel.setId(id);
		Integer  result = workingExperienceService.updateByPrimaryKeySelective(workingExperienceModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}
	@GetMapping(value = "/workingExperience/like")
	public ResponseEnvelope<List<WorkingExperienceModel>> getWorkingByWorkInfo(@RequestBody WorkingExperienceVO workingExperienceVO){
		WorkingExperienceModel workingExperience=beanMapper.map(workingExperienceVO,WorkingExperienceModel.class);
		List<WorkingExperienceModel> workingExperienceList=workingExperienceService.getWorkingByWorkInfo(workingExperience);
		ResponseEnvelope<List<WorkingExperienceModel>> responseEnvelope=new ResponseEnvelope<>(workingExperienceList,true);

		return  responseEnvelope;
	}

}
