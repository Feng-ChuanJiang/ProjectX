		package com.cci.projectx.core.service;

import com.cci.projectx.core.model.InteractModel;
import com.wlw.pylon.unit.BaseDbTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InteractServiceTest extends BaseDbTest{

	@Autowired
	private InteractService interactService;

	@Test
	public void testCreate() throws Exception {
		InteractModel interactModel = new InteractModel();
		interactModel.setId(199044327L);
		interactModel.setContent("5a03c9ca-76a2-46b2-878c-b3cb9503638a");
		interactModel.setUserId(812802053L);
		interactModel.setGroupId(710291951L);
		interactModel.setCreateTime(new Date());
		interactModel.setPrivacyPermission(91539988);
		interactModel.setTag(21873929);
		interactModel.setLongitude(new BigDecimal(0.5555555));
		interactModel.setLatitude(new BigDecimal(0.5555555));
		interactModel.setAddressDescribe("d2cc3549-34ed-4bd3-b101-cb982bd194c7");
		interactModel.setPicture("917c5c80-2e26-4406-baf0-6c221c549770");
		Long pkValue = interactModel.getId();
		saveModel(interactModel);

		InteractModel findModel = interactService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(interactModel.getContent(), findModel.getContent());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		InteractModel interactModel = new InteractModel();
		interactModel.setId(996043366L);
		interactModel.setContent("90aa4522-8fdb-47bd-88a0-7b363ec5cb76");
		interactModel.setUserId(289725296L);
		interactModel.setGroupId(559838148L);
		interactModel.setCreateTime(new Date());
		interactModel.setPrivacyPermission(62911212);
		interactModel.setTag(37385593);
		interactModel.setLongitude(new BigDecimal(0.5555555));
		interactModel.setLatitude(new BigDecimal(0.5555555));
		interactModel.setAddressDescribe("35c82143-0282-4bed-a02a-d35cb5cc3c47");
		interactModel.setPicture("96f36212-7202-45be-bc13-9da75b2fc477");
		Long pkValue = interactModel.getId();
		saveModel(interactModel);

		InteractModel findModel = interactService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(interactModel.getContent(), findModel.getContent());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		InteractModel interactModel = new InteractModel();
		interactModel.setId(633106766L);
		interactModel.setContent("fe63677e-93e4-418e-b863-ea3c345a872d");
		interactModel.setUserId(341693400L);
		interactModel.setGroupId(770170077L);
		interactModel.setCreateTime(new Date());
		interactModel.setPrivacyPermission(45651582);
		interactModel.setTag(17699580);
		interactModel.setLongitude(new BigDecimal(0.5555555));
		interactModel.setLatitude(new BigDecimal(0.5555555));
		interactModel.setAddressDescribe("33bc3738-9e3c-4636-beee-dc470eacc1c7");
		interactModel.setPicture("3910fc25-9916-4ae6-9015-d8caf41ef1d4");
		Long pkValue = interactModel.getId();
		saveModel(interactModel);

		//InteractModel updateModel = createModel();
		InteractModel updateModel = new InteractModel();
		updateModel.setId(832567254L);
		updateModel.setContent("6000406f-97ea-4588-8718-817e92c426f2");
		updateModel.setUserId(286729458L);
		updateModel.setGroupId(468267207L);
		updateModel.setCreateTime(new Date());
		updateModel.setPrivacyPermission(66113830);
		updateModel.setTag(79023585);
		updateModel.setLongitude(new BigDecimal(0.5555555));
		updateModel.setLatitude(new BigDecimal(0.5555555));
		updateModel.setAddressDescribe("45810d1e-085b-4562-b868-ea757e90962b");
		updateModel.setPicture("2b31897d-6b37-49f6-898d-1a53fffb0858");
		
		updateModel.setId(pkValue);
		Integer updateResult = interactService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		InteractModel findModel = interactService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getContent(), findModel.getContent());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		InteractModel interactModel = new InteractModel();
		interactModel.setId(706215912L);
		interactModel.setContent("67f3466f-f778-4284-9acf-bc10ee6ffa22");
		interactModel.setUserId(141281548L);
		interactModel.setGroupId(601657576L);
		interactModel.setCreateTime(new Date());
		interactModel.setPrivacyPermission(37469119);
		interactModel.setTag(32152550);
		interactModel.setLongitude(new BigDecimal(0.5555555));
		interactModel.setLatitude(new BigDecimal(0.5555555));
		interactModel.setAddressDescribe("6ba0569e-3f5d-41aa-9e2a-fab74ac5b9a3");
		interactModel.setPicture("b375d25b-9498-4719-8c0c-d43c0109fc9d");
		Long pkValue = interactModel.getId();
		saveModel(interactModel);
	
		Integer deleteResult = interactService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		InteractModel findModel = interactService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(InteractModel interactModel) throws Exception {
		Integer createResult = interactService.create(interactModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = interactService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private InteractModel createModel() {
		InteractModel interactModel = new InteractModel();
		interactModel.setId(369354335L);
		interactModel.setContent("4a7c0c6e-b0e4-4164-a0ff-c86bc6d6f43c");
		interactModel.setUserId(223078605L);
		interactModel.setGroupId(358318173L);
		interactModel.setCreateTime(new Date());
		interactModel.setPrivacyPermission(42423784);
		interactModel.setTag(19254014);
		interactModel.setLongitude(new BigDecimal(0.5555555));
		interactModel.setLatitude(new BigDecimal(0.5555555));
		interactModel.setAddressDescribe("fd40150c-bb7e-4b7d-b5d3-5bc38d0f1b5f");
		interactModel.setPicture("5cdda865-6578-42c6-a74f-3f4b451e42b1");
		return interactModel;
	}


}
