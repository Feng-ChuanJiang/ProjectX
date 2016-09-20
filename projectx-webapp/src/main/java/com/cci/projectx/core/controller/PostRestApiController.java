package com.cci.projectx.core.controller;

import com.cci.projectx.core.model.PostModel;
import com.cci.projectx.core.model.UserModel;
import com.cci.projectx.core.service.PostService;
import com.cci.projectx.core.vo.PostVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.cci.projectx.core.ResponseEnvelope;
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
public class PostRestApiController {

	private final Logger logger = LoggerFactory.getLogger(PostRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private PostService postService;

	@GetMapping(value = "/post/{id}")
	public ResponseEnvelope<PostVO> getPostById(@PathVariable Long id){
		PostModel postModel = postService.findByPrimaryKey(id);
		PostVO postVO =beanMapper.map(postModel, PostVO.class);
		ResponseEnvelope<PostVO> responseEnv = new ResponseEnvelope<>(postVO,true);
		return responseEnv;
	}

	@GetMapping(value = "/post")
    public ResponseEnvelope<Page<PostModel>> listPost(PostVO postVO, Pageable pageable){

		PostModel param = beanMapper.map(postVO, PostModel.class);
        List<PostModel> postModelModels = postService.selectPage(param,pageable);
        long count=postService.selectCount(param);
        Page<PostModel> page = new PageImpl<>(postModelModels,pageable,count);
        ResponseEnvelope<Page<PostModel>> responseEnv = new ResponseEnvelope<>(page,true);
        return responseEnv;
    }

	@PostMapping(value = "/post")
	public ResponseEnvelope<Integer> createPost(@RequestBody PostVO postVO){
		PostModel postModel = beanMapper.map(postVO, PostModel.class);
		Integer result = postService.create(postModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/post/{id}")
	public ResponseEnvelope<Integer> deletePostByPrimaryKey(@PathVariable Long id){
		Integer result = postService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/post/{id}")
	public ResponseEnvelope<Integer> updatePostByPrimaryKeySelective(@PathVariable Long id,
																	 @RequestBody PostVO postVO){
		PostModel postModel = beanMapper.map(postVO, PostModel.class);
		postModel.setId(id);
		Integer result = postService.updateByPrimaryKeySelective(postModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}

	@GetMapping(value = "/post/like")
	public ResponseEnvelope<List<PostModel>> getCommentInfo(PostVO postVO){
		PostModel Model=beanMapper.map(postVO,PostModel.class);
		List<PostModel> tModelList=postService.getPost(Model);
		ResponseEnvelope<List<PostModel>> responseEnvelope=new ResponseEnvelope<>(tModelList,true);
		return responseEnvelope;
	}

	@GetMapping(value = "/post/relation")
	public ResponseEnvelope<Map<String ,List<UserModel>>> getRelatPost(Long userId, String name){
		Map<String ,List<UserModel>> map=new HashedMap();
		map.put("oneRelation",postService.getOneRelatCompany(userId,name));
		map.put("twoRelation",postService.getTwoRelatCompany(userId,name));
		ResponseEnvelope<Map<String ,List<UserModel>>> responseEnvelope=new ResponseEnvelope<>(map,true);
		return responseEnvelope;
	}

}
