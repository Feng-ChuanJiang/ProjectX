package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.CompanyModel;
import com.cci.projectx.core.service.CompanyService;
import com.cci.projectx.core.vo.CompanyVO;
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
public class CompanyRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CompanyRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CompanyService companyService;

	@GetMapping(value = "/company/{id}")
	public ResponseEnvelope<CompanyVO> getCompanyById(@PathVariable Long id){
		CompanyModel companyModel = companyService.findByPrimaryKey(id);
		CompanyVO companyVO =beanMapper.map(companyModel, CompanyVO.class);
		ResponseEnvelope<CompanyVO> responseEnv = new ResponseEnvelope<>(companyVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/company")
    public ResponseEnvelope<Page<CompanyModel>> listCompany(CompanyVO companyVO, Pageable pageable){

		CompanyModel param = beanMapper.map(companyVO, CompanyModel.class);
        List<CompanyModel> companyModelModels = companyService.selectPage(param,pageable);
        long count=companyService.selectCount(param);
        Page<CompanyModel> page = new PageImpl<>(companyModelModels,pageable,count);
        ResponseEnvelope<Page<CompanyModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/company")
	public ResponseEnvelope<Integer> createCompany(@RequestBody CompanyVO companyVO){
		CompanyModel companyModel = beanMapper.map(companyVO, CompanyModel.class);
		Integer result = companyService.create(companyModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/company/{id}")
	public ResponseEnvelope<Integer> deleteCompanyByPrimaryKey(@PathVariable Long id){
		Integer result = companyService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/company/{id}")
	public ResponseEnvelope<Integer> updateCompanyByPrimaryKeySelective(@PathVariable Long id,
																		@RequestBody CompanyVO companyVO){
		CompanyModel companyModel = beanMapper.map(companyVO, CompanyModel.class);
		companyModel.setId(id);
		Integer result = companyService.updateByPrimaryKeySelective(companyModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

	@GetMapping(value = "/company/like")
	public ResponseEnvelope<List<CompanyModel>> getCommentInfo(CompanyVO commentVO){
		CompanyModel commentModel=beanMapper.map(commentVO,CompanyModel.class);
		List<CompanyModel> tModelList=companyService.getCompany(commentModel);
		ResponseEnvelope<List<CompanyModel>> responseEnvelope=new ResponseEnvelope<>(tModelList,true);
		return responseEnvelope;
	}

}
