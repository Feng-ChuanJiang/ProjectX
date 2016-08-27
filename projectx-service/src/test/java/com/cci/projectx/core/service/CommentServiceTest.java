		package com.cci.projectx.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wlw.pylon.unit.BaseDbTest;
import com.cci.projectx.core.service.CommentService;
import com.cci.projectx.core.model.CommentModel;

import java.util.Date;

public class CommentServiceTest extends BaseDbTest{

	@Autowired
	private CommentService commentService;

	@Test
	public void testCreate() throws Exception {
		CommentModel commentModel = new CommentModel();
		commentModel.setId(256765734L);
		commentModel.setUserId(447322370L);
		commentModel.setInteractId(230184789L);
		commentModel.setCreatTime(new Date());
		commentModel.setComment("13655794-da15-4865-89ea-816b02ff2470");
		commentModel.setType(55884160);
		Long pkValue = commentModel.getId();
		saveModel(commentModel);

		CommentModel findModel = commentService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(commentModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		CommentModel commentModel = new CommentModel();
		commentModel.setId(706134587L);
		commentModel.setUserId(929846901L);
		commentModel.setInteractId(968637991L);
		commentModel.setCreatTime(new Date());
		commentModel.setComment("d7e35813-fab5-44cf-9a7d-7d94c61aceb9");
		commentModel.setType(53849744);
		Long pkValue = commentModel.getId();
		saveModel(commentModel);

		CommentModel findModel = commentService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(commentModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		CommentModel commentModel = new CommentModel();
		commentModel.setId(827306155L);
		commentModel.setUserId(347892469L);
		commentModel.setInteractId(547576217L);
		commentModel.setCreatTime(new Date());
		commentModel.setComment("52f408b5-5ffc-415d-9275-ac59f6450184");
		commentModel.setType(33612279);
		Long pkValue = commentModel.getId();
		saveModel(commentModel);

		//CommentModel updateModel = createModel();
		CommentModel updateModel = new CommentModel();
		updateModel.setId(246117252L);
		updateModel.setUserId(316995028L);
		updateModel.setInteractId(161813370L);
		updateModel.setCreatTime(new Date());
		updateModel.setComment("fb446f82-fe55-4ed0-beb0-2c3d097c91c1");
		updateModel.setType(17551682);
		
		updateModel.setId(pkValue);
		Integer updateResult = commentService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		CommentModel findModel = commentService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		CommentModel commentModel = new CommentModel();
		commentModel.setId(503021417L);
		commentModel.setUserId(443719789L);
		commentModel.setInteractId(524892046L);
		commentModel.setCreatTime(new Date());
		commentModel.setComment("1a854658-9ebe-45fd-a1d0-3de14c536670");
		commentModel.setType(80151173);
		Long pkValue = commentModel.getId();
		saveModel(commentModel);
	
		Integer deleteResult = commentService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		CommentModel findModel = commentService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(CommentModel commentModel) throws Exception {
		Integer createResult = commentService.create(commentModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = commentService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private CommentModel createModel() {
		CommentModel commentModel = new CommentModel();
		commentModel.setId(912188695L);
		commentModel.setUserId(450512539L);
		commentModel.setInteractId(915605967L);
		commentModel.setCreatTime(new Date());
		commentModel.setComment("f1b1ded8-7e73-4b21-a358-2769471e0b28");
		commentModel.setType(90317164);
		return commentModel;
	}


}
