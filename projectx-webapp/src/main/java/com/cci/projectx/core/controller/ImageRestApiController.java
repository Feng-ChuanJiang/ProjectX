package com.cci.projectx.core.controller;

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

import com.cci.projectx.core.service.ImageService;
import com.cci.projectx.core.model.ImageModel;
import com.cci.projectx.core.vo.ImageVO;

import java.util.List;

@RestController
@RequestMapping("/projectx")
public class ImageRestApiController {

	private final Logger logger = LoggerFactory.getLogger(ImageRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private ImageService imageService;

	@GetMapping(value = "/core/image/{id}")
	public ResponseEnvelope<ImageVO> getImageById(@PathVariable Long id){
		ImageModel imageModel = imageService.findByPrimaryKey(id);
		ImageVO imageVO =beanMapper.map(imageModel, ImageVO.class);
		ResponseEnvelope<ImageVO> responseEnv = new ResponseEnvelope<>(imageVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/core/image")
    public ResponseEnvelope<Page<ImageModel>> listImage(ImageVO imageVO,Pageable pageable){

		ImageModel param = beanMapper.map(imageVO, ImageModel.class);
        List<ImageModel> imageModelModels = imageService.selectPage(param,pageable);
        long count=imageService.selectCount(param);
        Page<ImageModel> page = new PageImpl<>(imageModelModels,pageable,count);
        ResponseEnvelope<Page<ImageModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/core/image")
	public ResponseEnvelope<Integer> createImage(@RequestBody ImageVO imageVO){
		ImageModel imageModel = beanMapper.map(imageVO, ImageModel.class);
		Integer  result = imageService.create(imageModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/core/image/{id}")
	public ResponseEnvelope<Integer> deleteImageByPrimaryKey(@PathVariable Long id){
		Integer  result = imageService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/core/image/{id}")
	public ResponseEnvelope<Integer> updateImageByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody ImageVO imageVO){
		ImageModel imageModel = beanMapper.map(imageVO, ImageModel.class);
		imageModel.setId(id);
		Integer  result = imageService.updateByPrimaryKeySelective(imageModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
