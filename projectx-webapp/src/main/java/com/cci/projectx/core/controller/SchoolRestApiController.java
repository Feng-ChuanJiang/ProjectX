package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.SchoolModel;
import com.cci.projectx.core.service.SchoolService;
import com.cci.projectx.core.vo.SchoolVO;
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
public class SchoolRestApiController {

	private final Logger logger = LoggerFactory.getLogger(SchoolRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SchoolService schoolService;

	@GetMapping(value = "/school/{id}")
	public ResponseEnvelope<SchoolVO> getSchoolById(@PathVariable Long id){
		SchoolModel schoolModel = schoolService.findByPrimaryKey(id);
		SchoolVO schoolVO =beanMapper.map(schoolModel, SchoolVO.class);
		ResponseEnvelope<SchoolVO> responseEnv = new ResponseEnvelope<>(schoolVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/school")
    public ResponseEnvelope<Page<SchoolModel>> listSchool(SchoolVO schoolVO, Pageable pageable){

		SchoolModel param = beanMapper.map(schoolVO, SchoolModel.class);
        List<SchoolModel> schoolModelModels = schoolService.selectPage(param,pageable);
        long count=schoolService.selectCount(param);
        Page<SchoolModel> page = new PageImpl<>(schoolModelModels,pageable,count);
        ResponseEnvelope<Page<SchoolModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/school")
	public ResponseEnvelope<Integer> createSchool(@RequestBody SchoolVO schoolVO){
		SchoolModel schoolModel = beanMapper.map(schoolVO, SchoolModel.class);
		Integer result = schoolService.create(schoolModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/school/{id}")
	public ResponseEnvelope<Integer> deleteSchoolByPrimaryKey(@PathVariable Long id){
		Integer result = schoolService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/school/{id}")
	public ResponseEnvelope<Integer> updateSchoolByPrimaryKeySelective(@PathVariable Long id,
																	   @RequestBody SchoolVO schoolVO){
		SchoolModel schoolModel = beanMapper.map(schoolVO, SchoolModel.class);
		schoolModel.setId(id);
		Integer result = schoolService.updateByPrimaryKeySelective(schoolModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}
	@GetMapping(value = "/school/like")
	public  ResponseEnvelope<List<SchoolModel>> getSchool(SchoolVO schoolVO){
		SchoolModel schoolModel=beanMapper.map(schoolVO,SchoolModel.class);
		List<SchoolModel> schoolModelList=schoolService.getSchool(schoolModel);
		ResponseEnvelope<List<SchoolModel>> responseEnvelope=new ResponseEnvelope<>(schoolModelList,true);
		return responseEnvelope;
	}

}
