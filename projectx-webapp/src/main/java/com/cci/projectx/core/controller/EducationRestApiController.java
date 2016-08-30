package com.cci.projectx.core.controller;

import com.cci.projectx.core.entity.Education;
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

import com.cci.projectx.core.service.EducationService;
import com.cci.projectx.core.model.EducationModel;
import com.cci.projectx.core.vo.EducationVO;

import java.util.List;

@RestController
@RequestMapping("/projectx")
public class EducationRestApiController {

	private final Logger logger = LoggerFactory.getLogger(EducationRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private EducationService educationService;

	@GetMapping(value = "/education/{id}")
	public ResponseEnvelope<EducationVO> getEducationById(@PathVariable Long id){
		EducationModel educationModel = educationService.findByPrimaryKey(id);
		EducationVO educationVO =beanMapper.map(educationModel, EducationVO.class);
		ResponseEnvelope<EducationVO> responseEnv = new ResponseEnvelope<>(educationVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/education")
    public ResponseEnvelope<Page<EducationModel>> listEducation(EducationVO educationVO,Pageable pageable){

		EducationModel param = beanMapper.map(educationVO, EducationModel.class);
        List<EducationModel> educationModelModels = educationService.selectPage(param,pageable);
        long count=educationService.selectCount(param);
        Page<EducationModel> page = new PageImpl<>(educationModelModels,pageable,count);
        ResponseEnvelope<Page<EducationModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/education")
	public ResponseEnvelope<Integer> createEducation(@RequestBody EducationVO educationVO){
		EducationModel educationModel = beanMapper.map(educationVO, EducationModel.class);
		Integer  result = educationService.create(educationModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/education/{id}")
	public ResponseEnvelope<Integer> deleteEducationByPrimaryKey(@PathVariable Long id){
		Integer  result = educationService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/education/{id}")
	public ResponseEnvelope<Integer> updateEducationByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody EducationVO educationVO){
		EducationModel educationModel = beanMapper.map(educationVO, EducationModel.class);
		educationModel.setId(id);
		Integer  result = educationService.updateByPrimaryKeySelective(educationModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}
	@PostMapping(value = "/education/getEducationByEducationInfo")
	public ResponseEnvelope<List<Education>> getEducationByEducationInfo(@RequestBody EducationVO educationVO){
		Education education= beanMapper.map(educationVO,Education.class);
		List<Education> educationList= educationService.getEducationByEducationInfo(education);
		ResponseEnvelope<List<Education>> envelope =new ResponseEnvelope<List<Education>>(educationList,true);
		return envelope;
	}
}
