		package com.cci.projectx.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wlw.pylon.unit.BaseDbTest;
import com.cci.projectx.core.service.WorkingExperienceService;
import com.cci.projectx.core.model.WorkingExperienceModel;

import java.util.Date;

public class WorkingExperienceServiceTest extends BaseDbTest{

	@Autowired
	private WorkingExperienceService workingExperienceService;

	@Test
	public void testCreate() throws Exception {
		WorkingExperienceModel workingExperienceModel = new WorkingExperienceModel();
		workingExperienceModel.setId(335168508L);
		workingExperienceModel.setUserId(940496507L);
		workingExperienceModel.setCompanyId(275406839L);
		workingExperienceModel.setCompanyForShort("819d1f2b");
		workingExperienceModel.setDepartment("e6f1d191-45c3-4724-8faf-0087e007");
		workingExperienceModel.setDepartmentForShort("2c4f6a33");
		workingExperienceModel.setTitle("a49c5dca-f88a-4e56-a14b-5d0f287d2823");
		workingExperienceModel.setTitleForShort("139551db");
		workingExperienceModel.setStartTime(new Date());
		workingExperienceModel.setEndTime(new Date());
		workingExperienceModel.setSort(98389554);
		Long pkValue = workingExperienceModel.getId();
		saveModel(workingExperienceModel);

		WorkingExperienceModel findModel = workingExperienceService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(workingExperienceModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		WorkingExperienceModel workingExperienceModel = new WorkingExperienceModel();
		workingExperienceModel.setId(766151126L);
		workingExperienceModel.setUserId(765679428L);
		workingExperienceModel.setCompanyId(440506751L);
		workingExperienceModel.setCompanyForShort("dc7ab488");
		workingExperienceModel.setDepartment("4db05a54-d2f1-47ac-b2dd-b452b9ae");
		workingExperienceModel.setDepartmentForShort("4b4bcca3");
		workingExperienceModel.setTitle("89eea4ba-fba2-4c40-914b-a63d8fb35523");
		workingExperienceModel.setTitleForShort("26c6a2e7");
		workingExperienceModel.setStartTime(new Date());
		workingExperienceModel.setEndTime(new Date());
		workingExperienceModel.setSort(18488959);
		Long pkValue = workingExperienceModel.getId();
		saveModel(workingExperienceModel);

		WorkingExperienceModel findModel = workingExperienceService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(workingExperienceModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		WorkingExperienceModel workingExperienceModel = new WorkingExperienceModel();
		workingExperienceModel.setId(522175730L);
		workingExperienceModel.setUserId(290869896L);
		workingExperienceModel.setCompanyId(447570143L);
		workingExperienceModel.setCompanyForShort("220eb57a");
		workingExperienceModel.setDepartment("d0089643-1930-4652-bca2-b492e16e");
		workingExperienceModel.setDepartmentForShort("eabe72f5");
		workingExperienceModel.setTitle("20aeedf3-eeeb-4e17-93d2-c63667a4f747");
		workingExperienceModel.setTitleForShort("9566e425");
		workingExperienceModel.setStartTime(new Date());
		workingExperienceModel.setEndTime(new Date());
		workingExperienceModel.setSort(94329294);
		Long pkValue = workingExperienceModel.getId();
		saveModel(workingExperienceModel);

		//WorkingExperienceModel updateModel = createModel();
		WorkingExperienceModel updateModel = new WorkingExperienceModel();
		updateModel.setId(734358725L);
		updateModel.setUserId(986644509L);
		updateModel.setCompanyId(662617388L);
		updateModel.setCompanyForShort("f100d8f0");
		updateModel.setDepartment("ea55a20f-dd0f-4141-bcc1-22881f4d");
		updateModel.setDepartmentForShort("27b18578");
		updateModel.setTitle("fa0ecaf2-3e69-4cac-96e8-46b1bc96f8b8");
		updateModel.setTitleForShort("ebdfa595");
		updateModel.setStartTime(new Date());
		updateModel.setEndTime(new Date());
		updateModel.setSort(10738833);
		
		updateModel.setId(pkValue);
		Integer updateResult = workingExperienceService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		WorkingExperienceModel findModel = workingExperienceService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		WorkingExperienceModel workingExperienceModel = new WorkingExperienceModel();
		workingExperienceModel.setId(326454427L);
		workingExperienceModel.setUserId(381144581L);
		workingExperienceModel.setCompanyId(432409218L);
		workingExperienceModel.setCompanyForShort("97c29055");
		workingExperienceModel.setDepartment("44ae8ae2-c4cf-4ff5-bb2c-5207e7ec");
		workingExperienceModel.setDepartmentForShort("1cd959f8");
		workingExperienceModel.setTitle("d3cd2f38-49d6-43db-be84-c959f4336148");
		workingExperienceModel.setTitleForShort("dc0c83b6");
		workingExperienceModel.setStartTime(new Date());
		workingExperienceModel.setEndTime(new Date());
		workingExperienceModel.setSort(22657380);
		Long pkValue = workingExperienceModel.getId();
		saveModel(workingExperienceModel);
	
		Integer deleteResult = workingExperienceService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		WorkingExperienceModel findModel = workingExperienceService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(WorkingExperienceModel workingExperienceModel) throws Exception {
		Integer createResult = workingExperienceService.create(workingExperienceModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = workingExperienceService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private WorkingExperienceModel createModel() {
		WorkingExperienceModel workingExperienceModel = new WorkingExperienceModel();
		workingExperienceModel.setId(771952485L);
		workingExperienceModel.setUserId(271844707L);
		workingExperienceModel.setCompanyId(527715819L);
		workingExperienceModel.setCompanyForShort("49670b9f");
		workingExperienceModel.setDepartment("88e4b61b-d857-460f-9172-8478488b");
		workingExperienceModel.setDepartmentForShort("e26ab2f6");
		workingExperienceModel.setTitle("3e1edd95-0e1a-420b-aad8-9d892f110d40");
		workingExperienceModel.setTitleForShort("aae5008a");
		workingExperienceModel.setStartTime(new Date());
		workingExperienceModel.setEndTime(new Date());
		workingExperienceModel.setSort(95934317);
		return workingExperienceModel;
	}


}
