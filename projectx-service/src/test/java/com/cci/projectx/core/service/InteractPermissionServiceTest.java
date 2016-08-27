		package com.cci.projectx.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wlw.pylon.unit.BaseDbTest;
import com.cci.projectx.core.service.InteractPermissionService;
import com.cci.projectx.core.model.InteractPermissionModel;


public class InteractPermissionServiceTest extends BaseDbTest{

	@Autowired
	private InteractPermissionService interactPermissionService;

	@Test
	public void testCreate() throws Exception {
		InteractPermissionModel interactPermissionModel = new InteractPermissionModel();
		interactPermissionModel.setId(568524130L);
		interactPermissionModel.setInteractId(846370285L);
		interactPermissionModel.setUserId(186396943L);
		interactPermissionModel.setTag(87820920);
		Long pkValue = interactPermissionModel.getId();
		saveModel(interactPermissionModel);

		InteractPermissionModel findModel = interactPermissionService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(interactPermissionModel.getInteractId(), findModel.getInteractId());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		InteractPermissionModel interactPermissionModel = new InteractPermissionModel();
		interactPermissionModel.setId(772506338L);
		interactPermissionModel.setInteractId(413041972L);
		interactPermissionModel.setUserId(358232375L);
		interactPermissionModel.setTag(52224182);
		Long pkValue = interactPermissionModel.getId();
		saveModel(interactPermissionModel);

		InteractPermissionModel findModel = interactPermissionService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(interactPermissionModel.getInteractId(), findModel.getInteractId());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		InteractPermissionModel interactPermissionModel = new InteractPermissionModel();
		interactPermissionModel.setId(787135301L);
		interactPermissionModel.setInteractId(270941838L);
		interactPermissionModel.setUserId(822412838L);
		interactPermissionModel.setTag(97352640);
		Long pkValue = interactPermissionModel.getId();
		saveModel(interactPermissionModel);

		//InteractPermissionModel updateModel = createModel();
		InteractPermissionModel updateModel = new InteractPermissionModel();
		updateModel.setId(358291098L);
		updateModel.setInteractId(922548165L);
		updateModel.setUserId(923981633L);
		updateModel.setTag(34812502);
		
		updateModel.setId(pkValue);
		Integer updateResult = interactPermissionService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		InteractPermissionModel findModel = interactPermissionService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getInteractId(), findModel.getInteractId());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		InteractPermissionModel interactPermissionModel = new InteractPermissionModel();
		interactPermissionModel.setId(958367788L);
		interactPermissionModel.setInteractId(926750803L);
		interactPermissionModel.setUserId(167548290L);
		interactPermissionModel.setTag(99414698);
		Long pkValue = interactPermissionModel.getId();
		saveModel(interactPermissionModel);
	
		Integer deleteResult = interactPermissionService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		InteractPermissionModel findModel = interactPermissionService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(InteractPermissionModel interactPermissionModel) throws Exception {
		Integer createResult = interactPermissionService.create(interactPermissionModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = interactPermissionService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private InteractPermissionModel createModel() {
		InteractPermissionModel interactPermissionModel = new InteractPermissionModel();
		interactPermissionModel.setId(235841693L);
		interactPermissionModel.setInteractId(187449568L);
		interactPermissionModel.setUserId(849092875L);
		interactPermissionModel.setTag(75742783);
		return interactPermissionModel;
	}


}
