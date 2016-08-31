package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.LndustryModel;
import com.cci.projectx.core.service.LndustryService;
import com.cci.projectx.core.vo.LndustryVO;
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
public class LndustryRestApiController {

	private final Logger logger = LoggerFactory.getLogger(LndustryRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private LndustryService lndustryService;

	@GetMapping(value = "/lndustry/{id}")
	public ResponseEnvelope<LndustryVO> getLndustryById(@PathVariable Long id){
		LndustryModel lndustryModel = lndustryService.findByPrimaryKey(id);
		LndustryVO lndustryVO =beanMapper.map(lndustryModel, LndustryVO.class);
		ResponseEnvelope<LndustryVO> responseEnv = new ResponseEnvelope<>(lndustryVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/lndustry")
    public ResponseEnvelope<Page<LndustryModel>> listLndustry(LndustryVO lndustryVO, Pageable pageable){

		LndustryModel param = beanMapper.map(lndustryVO, LndustryModel.class);
        List<LndustryModel> lndustryModelModels = lndustryService.selectPage(param,pageable);
        long count=lndustryService.selectCount(param);
        Page<LndustryModel> page = new PageImpl<>(lndustryModelModels,pageable,count);
        ResponseEnvelope<Page<LndustryModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/lndustry")
	public ResponseEnvelope<Integer> createLndustry(@RequestBody LndustryVO lndustryVO){
		LndustryModel lndustryModel = beanMapper.map(lndustryVO, LndustryModel.class);
		Integer result = lndustryService.create(lndustryModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/lndustry/{id}")
	public ResponseEnvelope<Integer> deleteLndustryByPrimaryKey(@PathVariable Long id){
		Integer result = lndustryService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/lndustry/{id}")
	public ResponseEnvelope<Integer> updateLndustryByPrimaryKeySelective(@PathVariable Long id,
																		 @RequestBody LndustryVO lndustryVO){
		LndustryModel lndustryModel = beanMapper.map(lndustryVO, LndustryModel.class);
		lndustryModel.setId(id);
		Integer result = lndustryService.updateByPrimaryKeySelective(lndustryModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

	@GetMapping(value = "/lndustry/like")
	public ResponseEnvelope<List<LndustryModel>> getIndeustry(LndustryVO lndustryVO){
		LndustryModel lndustryModel=beanMapper.map(lndustryVO,LndustryModel.class);
		List<LndustryModel> lndustryModelList=lndustryService.getLndustry(lndustryModel);
		ResponseEnvelope<List<LndustryModel>> responseEnvelope=new ResponseEnvelope<>(lndustryModelList,true);
		return  responseEnvelope;
	}

}
