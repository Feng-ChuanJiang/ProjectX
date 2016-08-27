		package com.cci.projectx.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wlw.pylon.unit.BaseDbTest;
import com.cci.projectx.core.service.EducationService;
import com.cci.projectx.core.model.EducationModel;

import java.util.Date;

public class EducationServiceTest extends BaseDbTest{

	@Autowired
	private EducationService educationService;

	@Test
	public void testCreate() throws Exception {
		EducationModel educationModel = new EducationModel();
		educationModel.setId(422722193L);
		educationModel.setUserId(664129740L);
		educationModel.setUniversity("759e1e2c-164a-4a9f-8c3f-8a59fb24");
		educationModel.setDegree("b8c952ec-c04d-430a-9c2d-6e2788f6");
		educationModel.setMajorx("9b3b770d-575e-4160-9566-c6671e87");
		educationModel.setMajorxForShort("f95187d8");
		educationModel.setMajory("36935c61-f353-4a3a-9cc1-c4c8bced");
		educationModel.setMajoryForShort("8654c16b");
		educationModel.setStartTime(new Date());
		educationModel.setEndTime(new Date());
		educationModel.setSort(58891472);
		Long pkValue = educationModel.getId();
		saveModel(educationModel);

		EducationModel findModel = educationService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(educationModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		EducationModel educationModel = new EducationModel();
		educationModel.setId(436989893L);
		educationModel.setUserId(940992878L);
		educationModel.setUniversity("0dcedcf5-bb75-4bb2-8551-ae446a5b");
		educationModel.setDegree("38cc60a0-b454-4ef8-b6a3-81451be6");
		educationModel.setMajorx("907af4bd-bff8-40bd-bee2-352b2f59");
		educationModel.setMajorxForShort("5babc738");
		educationModel.setMajory("1a800559-f1dd-49df-8464-b1518d41");
		educationModel.setMajoryForShort("d02c5300");
		educationModel.setStartTime(new Date());
		educationModel.setEndTime(new Date());
		educationModel.setSort(92369449);
		Long pkValue = educationModel.getId();
		saveModel(educationModel);

		EducationModel findModel = educationService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(educationModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		EducationModel educationModel = new EducationModel();
		educationModel.setId(360960498L);
		educationModel.setUserId(439483382L);
		educationModel.setUniversity("b0485138-6ebb-4687-8ff7-a6105c68");
		educationModel.setDegree("b744ef02-603f-48ad-a08b-f3e9b90e");
		educationModel.setMajorx("e13412f6-e2ff-4498-bf7d-892056de");
		educationModel.setMajorxForShort("1f76e6b8");
		educationModel.setMajory("db0eb945-ba49-43c7-b5c0-fddd6f88");
		educationModel.setMajoryForShort("4b4f525f");
		educationModel.setStartTime(new Date());
		educationModel.setEndTime(new Date());
		educationModel.setSort(71213111);
		Long pkValue = educationModel.getId();
		saveModel(educationModel);

		//EducationModel updateModel = createModel();
		EducationModel updateModel = new EducationModel();
		updateModel.setId(885310314L);
		updateModel.setUserId(883039719L);
		updateModel.setUniversity("b9c6a383-ae94-463f-aa8d-545a917d");
		updateModel.setDegree("3eaf864b-8fcc-459d-be03-35d76695");
		updateModel.setMajorx("6b730245-8b69-4728-99b9-b57fe838");
		updateModel.setMajorxForShort("00e6c8ab");
		updateModel.setMajory("ba81b92c-9cfb-445b-b57c-23492732");
		updateModel.setMajoryForShort("5aa589e8");
		updateModel.setStartTime(new Date());
		updateModel.setEndTime(new Date());
		updateModel.setSort(40518138);
		
		updateModel.setId(pkValue);
		Integer updateResult = educationService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		EducationModel findModel = educationService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		EducationModel educationModel = new EducationModel();
		educationModel.setId(397661152L);
		educationModel.setUserId(668670701L);
		educationModel.setUniversity("c749bf9c-ee6f-4a75-99fa-30677a82");
		educationModel.setDegree("f027b014-6059-42d1-af6d-cbe3ba9b");
		educationModel.setMajorx("4fcc0ea6-e881-42a2-813e-0fffa2ba");
		educationModel.setMajorxForShort("eb96fbed");
		educationModel.setMajory("33972817-317d-432a-bb5f-22854fc1");
		educationModel.setMajoryForShort("dcc61983");
		educationModel.setStartTime(new Date());
		educationModel.setEndTime(new Date());
		educationModel.setSort(34286315);
		Long pkValue = educationModel.getId();
		saveModel(educationModel);
	
		Integer deleteResult = educationService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		EducationModel findModel = educationService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(EducationModel educationModel) throws Exception {
		Integer createResult = educationService.create(educationModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = educationService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private EducationModel createModel() {
		EducationModel educationModel = new EducationModel();
		educationModel.setId(971399271L);
		educationModel.setUserId(663776119L);
		educationModel.setUniversity("27a7e433-e70e-44f0-8c1e-64e48277");
		educationModel.setDegree("0b180287-3659-4f0c-8859-08b218d9");
		educationModel.setMajorx("158c4140-e40b-473d-a8cb-cad20b85");
		educationModel.setMajorxForShort("863b49f7");
		educationModel.setMajory("4c023951-581e-4387-bde9-be16cb39");
		educationModel.setMajoryForShort("3705ac08");
		educationModel.setStartTime(new Date());
		educationModel.setEndTime(new Date());
		educationModel.setSort(99542880);
		return educationModel;
	}


}
