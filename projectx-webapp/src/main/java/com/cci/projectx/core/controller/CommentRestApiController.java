package com.cci.projectx.core.controller;

import com.cci.projectx.core.entity.User;
import com.cci.projectx.core.model.CommentModel;
import com.cci.projectx.core.service.CommentService;
import com.cci.projectx.core.vo.CommentVO;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import com.wlw.pylon.web.rest.ResponseEnvelope;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;

@RestController
@RequestMapping("/projectx")
public class CommentRestApiController {

	private final Logger logger = LoggerFactory.getLogger(CommentRestApiController.class);

	@Autowired
	private BeanMapper beanMapper;
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	Neo4jOperations neo4jTemplate;
	@Autowired
	private CommentService commentService;

	@GetMapping(value = "/comment/{id}")
	public ResponseEnvelope<CommentVO> getCommentById(@PathVariable Long id){
		User u=new User();
		u.setPhotos("D");
		IndexQuery indexQuery = new IndexQuery();
		indexQuery.setId("3000");
		indexQuery.setObject(u);
		elasticsearchTemplate.putMapping(u.getClass());
		String str = elasticsearchTemplate.index(indexQuery);
		elasticsearchTemplate.refresh(u.getClass(), true);
		CommentModel commentModel = commentService.findByPrimaryKey(id);



		CommentVO commentVO =beanMapper.map(commentModel, CommentVO.class);
		ResponseEnvelope<CommentVO> responseEnv = new ResponseEnvelope<>(commentVO,true);
		return responseEnv;
	}

	/**
	 * 拼接es查询sql
	 *
	 * @param o
	 * @return
	 */
	public BoolQueryBuilder getESSql(User o) {
		BoolQueryBuilder builder = boolQuery();
		// 获取类中的所有定义字段
		Field[] fields = o.getClass().getDeclaredFields();
		String str = "";
		// 循环遍历字段，获取字段对应的属性值
		for (Field field : fields) {
			// 如果不为空，设置可见性，然后返回
			field.setAccessible(true);
			try {
				// 设置字段可见，即可用get方法获取属性值。
				if (field.get(o) != null && !field.get(o).toString().equals("")) {
					//判断是否是集合
					if (!field.getType().isInstance(new ArrayList<>())) {
						builder.must(QueryBuilders.queryStringQuery(field.get(o).toString()).defaultField(field.getName()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

			}

		}
		return builder;

	}

	@GetMapping(value = "/comment")
    public ResponseEnvelope<Page<CommentModel>> listComment(CommentVO commentVO,Pageable pageable){

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
		Integer  result = commentService.create(commentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}

    @DeleteMapping(value = "/comment/{id}")
	public ResponseEnvelope<Integer> deleteCommentByPrimaryKey(@PathVariable Long id){
		Integer  result = commentService.deleteByPrimaryKey(id);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<>(result,true);
        return responseEnv;
	}


    @PutMapping(value = "/comment/{id}")
	public ResponseEnvelope<Integer> updateCommentByPrimaryKeySelective(@PathVariable Long id,
					@RequestBody CommentVO commentVO){
		CommentModel commentModel = beanMapper.map(commentVO, CommentModel.class);
		commentModel.setId(id);
		Integer  result = commentService.updateByPrimaryKeySelective(commentModel);
		ResponseEnvelope<Integer> responseEnv = new ResponseEnvelope<Integer>(result,true);
        return responseEnv;
	}


	@GetMapping(value = "/comment/like")
	public ResponseEnvelope<List<CommentModel>> getCommentInfo(CommentVO commentVO){
		CommentModel commentModel=beanMapper.map(commentVO,CommentModel.class);
		List<CommentModel> commentModelList=commentService.getComment(commentModel);
		ResponseEnvelope<List<CommentModel>> responseEnvelope=new ResponseEnvelope<>(commentModelList,true);
		return responseEnvelope;
	}
}
