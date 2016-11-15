package com.cci.projectx.core.controller;

import com.cci.projectx.core.ResponseEnvelope;
import com.cci.projectx.core.model.DepartmentModel;
import com.cci.projectx.core.model.UserContactsModel;
import com.cci.projectx.core.service.DepartmentService;
import com.cci.projectx.core.vo.DepartmentVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
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
public class DepartmentRestApiController {

	private final Logger logger = LoggerFactory.getLogger(DepartmentRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private DepartmentService departmentService;

	@GetMapping(value = "/department/{id}")
	public ResponseEnvelope<DepartmentVO> getDepartmentById(@PathVariable Long id){
		DepartmentModel departmentModel = departmentService.findByPrimaryKey(id);
		DepartmentVO departmentVO =beanMapper.map(departmentModel, DepartmentVO.class);
		ResponseEnvelope<DepartmentVO> responseEnv = new ResponseEnvelope<>(departmentVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/department")
    public ResponseEnvelope<Page<DepartmentModel>> listDepartment(DepartmentVO departmentVO, Pageable pageable){

		DepartmentModel param = beanMapper.map(departmentVO, DepartmentModel.class);
        List<DepartmentModel> departmentModelModels = departmentService.selectPage(param,pageable);
        long count=departmentService.selectCount(param);
        Page<DepartmentModel> page = new PageImpl<>(departmentModelModels,pageable,count);
        ResponseEnvelope<Page<DepartmentModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/department")
	public ResponseEnvelope<Integer> createDepartment(@RequestBody DepartmentVO departmentVO){
		DepartmentModel departmentModel = beanMapper.map(departmentVO, DepartmentModel.class);
		Integer result = departmentService.create(departmentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/department/{id}")
	public ResponseEnvelope<Integer> deleteDepartmentByPrimaryKey(@PathVariable Long id){
		Integer result = departmentService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/department/{id}")
	public ResponseEnvelope<Integer> updateDepartmentByPrimaryKeySelective(@PathVariable Long id,
																		   @RequestBody DepartmentVO departmentVO){
		DepartmentModel departmentModel = beanMapper.map(departmentVO, DepartmentModel.class);
		departmentModel.setId(id);
		Integer result = departmentService.updateByPrimaryKeySelective(departmentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}


	@GetMapping(value = "/department/like")
	public ResponseEnvelope<List<DepartmentModel>> getCommentInfo(DepartmentVO commentVO){
		DepartmentModel commentModel=beanMapper.map(commentVO,DepartmentModel.class);
		List<DepartmentModel> tModelList=departmentService.getDepartment(commentModel);
		ResponseEnvelope<List<DepartmentModel>> responseEnvelope=new ResponseEnvelope<>(tModelList,true);
		return responseEnvelope;
	}

	@GetMapping(value = "/department/relation")
	public ResponseEnvelope<List<UserContactsModel>> getRelatDepartment(Long userId, String name){
		List<UserContactsModel> userContactsModels=departmentService.getTwoRelatCompany(userId,name);
		ResponseEnvelope<List<UserContactsModel>> responseEnvelope=new ResponseEnvelope<>(userContactsModels,true);
		return responseEnvelope;
	}

}
