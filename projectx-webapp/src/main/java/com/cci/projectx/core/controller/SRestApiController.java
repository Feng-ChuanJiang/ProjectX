package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.SModel;
import com.cci.projectx.core.service.SService;
import com.cci.projectx.core.vo.SVO;
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
public class SRestApiController {

	private final Logger logger = LoggerFactory.getLogger(SRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private SService sService;

	@GetMapping(value = "/s/{id}")
	public ResponseEnvelope<SVO> getSById(@PathVariable Long id){
		SModel sModel = sService.findByPrimaryKey(id);
		SVO sVO =beanMapper.map(sModel, SVO.class);
		ResponseEnvelope<SVO> responseEnv = new ResponseEnvelope<>(sVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/s")
    public ResponseEnvelope<Page<SModel>> listS(SVO sVO,Pageable pageable){

		SModel param = beanMapper.map(sVO, SModel.class);
        List<SModel> sModelModels = sService.selectPage(param,pageable);
        long count=sService.selectCount(param);
        Page<SModel> page = new PageImpl<>(sModelModels,pageable,count);
        ResponseEnvelope<Page<SModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/s")
	public ResponseEnvelope<Integer> createS(@RequestBody SVO sVO){
		SModel sModel = beanMapper.map(sVO, SModel.class);
		Integer  result = sService.create(sModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/s/{id}")
	public ResponseEnvelope<Integer> deleteSByPrimaryKey(@PathVariable Long id){
		Integer  result = sService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/s/{id}")
	public ResponseEnvelope<Integer> updateSByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody SVO sVO){
		SModel sModel = beanMapper.map(sVO, SModel.class);
		sModel.setId(id);
		Integer  result = sService.updateByPrimaryKeySelective(sModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
