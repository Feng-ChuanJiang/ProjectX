package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.CircleModel;
import com.cci.projectx.core.service.CircleService;
import com.cci.projectx.core.vo.CircleVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.cci.projectx.core.ResponseEnvelope;
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
public class CircleRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CircleRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CircleService circleService;

	@GetMapping(value = "/circle/{id}")
	public ResponseEnvelope<CircleVO> getCircleById(@PathVariable Long id){
		CircleModel circleModel = circleService.findByPrimaryKey(id);
		CircleVO circleVO =beanMapper.map(circleModel, CircleVO.class);
		ResponseEnvelope<CircleVO> responseEnv = new ResponseEnvelope<>(circleVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/circle/user/{userId}")
	public ResponseEnvelope<List<CircleModel>> getCircleByUserId(@PathVariable Long userId){
		List<CircleModel> circleModel = circleService.findByUserId(userId);
		ResponseEnvelope<List<CircleModel>> responseEnv = new ResponseEnvelope<>(circleModel,true);
		return responseEnv;
	}

	@GetMapping(value = "/circle")
    public ResponseEnvelope<Page<CircleModel>> listCircle(CircleVO circleVO, Pageable pageable){

		CircleModel param = beanMapper.map(circleVO, CircleModel.class);
        List<CircleModel> circleModelModels = circleService.selectPage(param,pageable);
        long count=circleService.selectCount(param);
        Page<CircleModel> page = new PageImpl<>(circleModelModels,pageable,count);
        ResponseEnvelope<Page<CircleModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/circle")
	public ResponseEnvelope<Integer> createCircle(@RequestBody CircleVO circleVO){
		CircleModel circleModel = beanMapper.map(circleVO, CircleModel.class);
		Integer result = circleService.create(circleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/circle/{id}")
	public ResponseEnvelope<Integer> deleteCircleByPrimaryKey(@PathVariable Long id){
		Integer result = circleService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/circle/{id}")
	public ResponseEnvelope<Integer> updateCircleByPrimaryKeySelective(@PathVariable Long id,
																	   @RequestBody CircleVO circleVO){
		CircleModel circleModel = beanMapper.map(circleVO, CircleModel.class);
		circleModel.setId(id);
		Integer result = circleService.updateByPrimaryKeySelective(circleModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
