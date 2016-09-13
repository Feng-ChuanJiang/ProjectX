package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.MajorModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.MajorService;
import com.cci.projectx.core.vo.majorVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class majorRestApiController {

	private final Logger logger = LoggerFactory.getLogger(majorRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private MajorService majorService;

	@GetMapping(value = "/major/{id}")
	public ResponseEnvelope<majorVO> getmajorById(@PathVariable Long id){
		MajorModel majorModel = majorService.findByPrimaryKey(id);
		majorVO majorVO =beanMapper.map(majorModel, com.cci.projectx.core.vo.majorVO.class);
		ResponseEnvelope<com.cci.projectx.core.vo.majorVO> responseEnv = new ResponseEnvelope<>(majorVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/major")
    public ResponseEnvelope<Page<MajorModel>> listmajor(majorVO majorVO, Pageable pageable){

		MajorModel param = beanMapper.map(majorVO, MajorModel.class);
        List<MajorModel> majorModelModels = majorService.selectPage(param,pageable);
        long count=majorService.selectCount(param);
        Page<MajorModel> page = new PageImpl<>(majorModelModels,pageable,count);
        ResponseEnvelope<Page<MajorModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/major")
	public ResponseEnvelope<Integer> createmajor(@RequestBody majorVO majorVO){
		MajorModel majorModel = beanMapper.map(majorVO, MajorModel.class);
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
		MajorModel majorModel = beanMapper.map(majorVO, MajorModel.class);
		majorModel.setId(id);
		Integer result = majorService.updateByPrimaryKeySelective(majorModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}


	@GetMapping(value = "/major/like")
	public ResponseEnvelope<List<MajorModel>> getCommentInfo(majorVO mavo){
		MajorModel Model=beanMapper.map(mavo,MajorModel.class);
		List<MajorModel> tModelList=majorService.getMajor(Model);
		ResponseEnvelope<List<MajorModel>> responseEnvelope=new ResponseEnvelope<>(tModelList,true);
		return responseEnvelope;
	}

	@GetMapping(value = "/major/relation")
	public ResponseEnvelope<Map<String ,List<UserModel>>> getRelatMajor(Long userId, String name){
		Map<String ,List<UserModel>> map=new HashedMap();
		map.put("oneRelation",majorService.getOneRelatCompany(userId,name));
		map.put("twoRelation",majorService.getTwoRelatCompany(userId,name));
		ResponseEnvelope<Map<String ,List<UserModel>>> responseEnvelope=new ResponseEnvelope<>(map,true);
		return responseEnvelope;
	}
}
