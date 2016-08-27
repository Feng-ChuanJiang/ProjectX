		package com.cci.projectx.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wlw.pylon.unit.BaseDbTest;
import com.cci.projectx.core.service.CompanyService;
import com.cci.projectx.core.model.CompanyModel;


public class CompanyServiceTest extends BaseDbTest{

	@Autowired
	private CompanyService companyService;

	@Test
	public void testCreate() throws Exception {
		CompanyModel companyModel = new CompanyModel();
		companyModel.setId(739022445L);
		companyModel.setCompanyName("c51385aa-c9ca-433e-97e3-fd7cf77f0e79");
		companyModel.setCompanyLogo("93812e39-4a3c-44c1-8dda-5cf9ec95c170");
		Long pkValue = companyModel.getId();
		saveModel(companyModel);

		CompanyModel findModel = companyService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(companyModel.getCompanyName(), findModel.getCompanyName());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		CompanyModel companyModel = new CompanyModel();
		companyModel.setId(455603179L);
		companyModel.setCompanyName("de851884-8b14-4cad-8fb6-edd570a8d584");
		companyModel.setCompanyLogo("4d9ffd0b-eff0-4144-87c5-2af00d450b2a");
		Long pkValue = companyModel.getId();
		saveModel(companyModel);

		CompanyModel findModel = companyService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(companyModel.getCompanyName(), findModel.getCompanyName());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		CompanyModel companyModel = new CompanyModel();
		companyModel.setId(254437202L);
		companyModel.setCompanyName("ffa2a57d-12fc-4ea1-ba21-147880ba1816");
		companyModel.setCompanyLogo("f051aa70-ffed-4aff-a8c2-63eb587545d7");
		Long pkValue = companyModel.getId();
		saveModel(companyModel);

		//CompanyModel updateModel = createModel();
		CompanyModel updateModel = new CompanyModel();
		updateModel.setId(359599243L);
		updateModel.setCompanyName("70adc876-9efe-45ef-bfed-160bc1dfdf05");
		updateModel.setCompanyLogo("e9c23cee-75d5-49ab-b9bf-683fd9b7f563");
		
		updateModel.setId(pkValue);
		Integer updateResult = companyService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		CompanyModel findModel = companyService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getCompanyName(), findModel.getCompanyName());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		CompanyModel companyModel = new CompanyModel();
		companyModel.setId(752066073L);
		companyModel.setCompanyName("cd1c0d2f-8067-48a0-91ef-aad919e229ea");
		companyModel.setCompanyLogo("0b59ac4b-ae4f-43aa-a28e-ab143ab3ff5d");
		Long pkValue = companyModel.getId();
		saveModel(companyModel);
	
		Integer deleteResult = companyService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		CompanyModel findModel = companyService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(CompanyModel companyModel) throws Exception {
		Integer createResult = companyService.create(companyModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = companyService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private CompanyModel createModel() {
		CompanyModel companyModel = new CompanyModel();
		companyModel.setId(583878449L);
		companyModel.setCompanyName("bdc927a0-f9a1-440c-b9c1-8908d2daeb76");
		companyModel.setCompanyLogo("ee9e8af5-acf7-4f36-9333-63e2cbc6245b");
		return companyModel;
	}


}
