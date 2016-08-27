		package com.cci.projectx.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wlw.pylon.unit.BaseDbTest;
import com.cci.projectx.core.service.ImageService;
import com.cci.projectx.core.model.ImageModel;


public class ImageServiceTest extends BaseDbTest{

	@Autowired
	private ImageService imageService;

	@Test
	public void testCreate() throws Exception {
		ImageModel imageModel = new ImageModel();
		imageModel.setId(675838228L);
		imageModel.setName("22ead6db-b3ad-4c05-8509-1a7619906a62");
		imageModel.setFileName("8666692e-7b95-40e5-badb-a57b1156ccf8");
		imageModel.setAddress("44657fec-8e58-4a00-a26d-a23ecca16898");
		Long pkValue = imageModel.getId();
		saveModel(imageModel);

		ImageModel findModel = imageService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(imageModel.getName(), findModel.getName());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		ImageModel imageModel = new ImageModel();
		imageModel.setId(488455395L);
		imageModel.setName("0560a82f-c7eb-4048-8bab-41c574b57cdf");
		imageModel.setFileName("7060ade7-1de1-47bf-848d-bf676b2750d3");
		imageModel.setAddress("3dff2216-90cd-4f05-8f3f-0042ec20e9c1");
		Long pkValue = imageModel.getId();
		saveModel(imageModel);

		ImageModel findModel = imageService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(imageModel.getName(), findModel.getName());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		ImageModel imageModel = new ImageModel();
		imageModel.setId(746948279L);
		imageModel.setName("6516c1b8-27fe-413f-a643-47975aec6bc4");
		imageModel.setFileName("6ec3d0ad-cb50-40da-8117-169c4fa40eec");
		imageModel.setAddress("dc0a516b-c667-4d21-aef7-cd920b3a90e9");
		Long pkValue = imageModel.getId();
		saveModel(imageModel);

		//ImageModel updateModel = createModel();
		ImageModel updateModel = new ImageModel();
		updateModel.setId(396614415L);
		updateModel.setName("85980381-bb3a-441f-8245-36fb83e120ea");
		updateModel.setFileName("5abc61ea-c607-4e2f-a474-da3fa62b1122");
		updateModel.setAddress("9602055e-16fc-4812-91ff-5c2151db6a8a");
		
		updateModel.setId(pkValue);
		Integer updateResult = imageService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		ImageModel findModel = imageService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getName(), findModel.getName());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		ImageModel imageModel = new ImageModel();
		imageModel.setId(656098064L);
		imageModel.setName("acfb4f9e-77a7-4901-a484-a26c7da6d5b8");
		imageModel.setFileName("fc0f9151-1eae-4934-9a17-68db1a85af9a");
		imageModel.setAddress("8b209d10-fb51-4850-aef0-73ba4ac3f962");
		Long pkValue = imageModel.getId();
		saveModel(imageModel);
	
		Integer deleteResult = imageService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		ImageModel findModel = imageService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(ImageModel imageModel) throws Exception {
		Integer createResult = imageService.create(imageModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = imageService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private ImageModel createModel() {
		ImageModel imageModel = new ImageModel();
		imageModel.setId(895864984L);
		imageModel.setName("56bd02e2-98dc-4d14-b2fb-2fff7806a977");
		imageModel.setFileName("0f6bddd7-864f-4888-9ac1-c252feb2fd73");
		imageModel.setAddress("5c8b13d1-e2e5-483d-823a-7b4db3b57107");
		return imageModel;
	}


}
