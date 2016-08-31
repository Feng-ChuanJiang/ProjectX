package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.majorModel;
import com.cci.projectx.core.vo.majorVO;
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
public class majorRestApiController {

	private final Logger logger = LoggerFactory.getLogger(majorRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private com.cci.projectx.core.service.majorService majorService;

	@GetMapping(value = "/major/{id}")
	public ResponseEnvelope<majorVO> getmajorById(@PathVariable Long id){
		majorModel majorModel = majorService.findByPrimaryKey(id);
		majorVO majorVO =beanMapper.map(majorModel, com.cci.projectx.core.vo.majorVO.class);
		ResponseEnvelope<com.cci.projectx.core.vo.majorVO> responseEnv = new ResponseEnvelope<>(majorVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/major")
    public ResponseEnvelope<Page<majorModel>> listmajor(majorVO majorVO, Pageable pageable){

		majorModel param = beanMapper.map(majorVO, majorModel.class);
        List<majorModel> majorModelModels = majorService.selectPage(param,pageable);
        long count=majorService.selectCount(param);
        Page<majorModel> page = new PageImpl<>(majorModelModels,pageable,count);
        ResponseEnvelope<Page<majorModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/major")
	public ResponseEnvelope<Integer> createmajor(@RequestBody majorVO majorVO){
		majorModel majorModel = beanMapper.map(majorVO, com.cci.projectx.core.model.majorModel.class);
		Integer result = majorService.create(majorModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/major/{id}")
	public ResponseEnvelope<Integer> deletemajorByPrimaryKey(@PathVariable Long id){
		Integer result = majorService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/major/{id}")
	public ResponseEnvelope<Integer> updatemajorByPrimaryKeySelective(@PathVariable Long id,
																	  @RequestBody majorVO majorVO){
		majorModel majorModel = beanMapper.map(majorVO, com.cci.projectx.core.model.majorModel.class);
		majorModel.setId(id);
		Integer result = majorService.updateByPrimaryKeySelective(majorModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}


	@GetMapping(value = "/major/like")
	public ResponseEnvelope<List<majorModel>> getCommentInfo(majorVO mavo){
		majorModel Model=beanMapper.map(mavo,majorModel.class);
		List<majorModel> tModelList=majorService.getMajor(Model);
		ResponseEnvelope<List<majorModel>> responseEnvelope=new ResponseEnvelope<>(tModelList,true);
		return responseEnvelope;
	}
}
