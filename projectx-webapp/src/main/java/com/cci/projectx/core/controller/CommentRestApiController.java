package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.CommentModel;
import com.cci.projectx.core.service.CommentService;
import com.cci.projectx.core.vo.CommentVO;
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
public class CommentRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CommentRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private CommentService commentService;

	@GetMapping(value = "/comment/{id}")
	public ResponseEnvelope<CommentVO> getCommentById(@PathVariable Long id){
		CommentModel commentModel = commentService.findByPrimaryKey(id);
		CommentVO commentVO =beanMapper.map(commentModel, CommentVO.class);
		ResponseEnvelope<CommentVO> responseEnv = new ResponseEnvelope<>(commentVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/comment")
    public ResponseEnvelope<Page<CommentModel>> listComment(CommentVO commentVO, Pageable pageable){

		CommentModel param = beanMapper.map(commentVO, CommentModel.class);
        List<CommentModel> commentModelModels = commentService.selectPage(param,pageable);
        long count=commentService.selectCount(param);
        Page<CommentModel> page = new PageImpl<>(commentModelModels,pageable,count);
        ResponseEnvelope<Page<CommentModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/comment")
	public ResponseEnvelope<Integer> createComment(@RequestBody CommentVO commentVO){
		CommentModel commentModel = beanMapper.map(commentVO, CommentModel.class);
		Integer result = commentService.create(commentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/comment/{id}")
	public ResponseEnvelope<Integer> deleteCommentByPrimaryKey(@PathVariable Long id){
		Integer result = commentService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/comment/{id}")
	public ResponseEnvelope<Integer> updateCommentByPrimaryKeySelective(@PathVariable Long id,
																		@RequestBody CommentVO commentVO){
		CommentModel commentModel = beanMapper.map(commentVO, CommentModel.class);
		commentModel.setId(id);
		Integer result = commentService.updateByPrimaryKeySelective(commentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

}
