		package com.cci.projectx.core.service;

import com.cci.projectx.core.model.UserModel;
import com.wlw.pylon.unit.BaseDbTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserServiceTest extends BaseDbTest{

	@Autowired
	private UserService userService;

	@Test
	public void testCreate() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setId(773039614L);
		userModel.setName("dcf19bda-c858-4e3a-a65f-abd871368227");
		userModel.setPassword("41094eab-6d25-4c36-bf24-a8f5380ec2ed");
		userModel.setPhotos("d8014e86-9a84-44b8-bf0c-f9c1d306bad0");
		userModel.setMobilePhone("21ad97c2-55a2-4b54-bcf5-");
		userModel.setGender("9");
		userModel.setConstellation("7df6335f");
		userModel.setAge(99381705);
		userModel.setLndustry("9fac7587-6666-470c-a9ab-dfb97df2a5e2");
		userModel.setGroupId(910111949L);
		userModel.setLabels("1f350478-1e5b-4edc-b09a-000c10265ed7");
		userModel.setLongitude(new BigDecimal(0.5555555));
		userModel.setLatitude(new BigDecimal(0.5555555));
		Long pkValue = userModel.getId();
		saveModel(userModel);

		UserModel findModel = userService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(userModel.getName(), findModel.getName());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setId(112348736L);
		userModel.setName("9ff60867-6694-4f3e-a8e7-cb7f51cc7ce0");
		userModel.setPassword("be3ede50-7bb7-4c9e-9142-79511d07a9ad");
		userModel.setPhotos("a96dd2cf-2aee-4641-aa29-c120991e3dfe");
		userModel.setMobilePhone("a20adc77-d4a0-4026-b43e-");
		userModel.setGender("2");
		userModel.setConstellation("3fd8057e");
		userModel.setAge(64052742);
		userModel.setLndustry("28a126fb-42b6-4c16-b34b-53e6671178e3");
		userModel.setGroupId(738981979L);
		userModel.setLabels("79f00bab-cb41-40c7-91a8-53fdaf34f4fb");
		userModel.setLongitude(new BigDecimal(0.5555555));
		userModel.setLatitude(new BigDecimal(0.5555555));
		Long pkValue = userModel.getId();
		saveModel(userModel);

		UserModel findModel = userService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(userModel.getName(), findModel.getName());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		UserModel userModel = new UserModel();
		userModel.setId(895266327L);
		userModel.setName("f5017d15-ccaf-4c6b-85af-e9be96067f97");
		userModel.setPassword("cc417901-376b-468d-ad1b-d941e08eee1a");
		userModel.setPhotos("cadbc3fe-1342-425e-9066-e32caafe12a5");
		userModel.setMobilePhone("fc1bdb0a-be62-4de5-b827-");
		userModel.setGender("9");
		userModel.setConstellation("731eaeb1");
		userModel.setAge(24596478);
		userModel.setLndustry("8432dc1f-e633-46c0-ab44-5de336e91c9e");
		userModel.setGroupId(145644403L);
		userModel.setLabels("eea1c356-1f6c-480b-98bd-a4dc9b8edab2");
		userModel.setLongitude(new BigDecimal(0.5555555));
		userModel.setLatitude(new BigDecimal(0.5555555));
		Long pkValue = userModel.getId();
		saveModel(userModel);

		//UserModel updateModel = createModel();
		UserModel updateModel = new UserModel();
		updateModel.setId(513159612L);
		updateModel.setName("fd520bb5-1966-4a42-91fb-8962bf0883ee");
		updateModel.setPassword("5d01f54b-ffad-4562-9c48-726b54edcbe3");
		updateModel.setPhotos("2a60e536-d598-494d-9633-ffded9342f20");
		updateModel.setMobilePhone("23bec7d6-798d-4543-af24-");
		updateModel.setGender("5");
		updateModel.setConstellation("429b417f");
		updateModel.setAge(67704554);
		updateModel.setLndustry("f5bffbab-4520-48ee-b071-d3169e931175");
		updateModel.setGroupId(494227363L);
		updateModel.setLabels("9ae92acf-b5f5-4546-96a8-0d7f2e8d4e00");
		updateModel.setLongitude(new BigDecimal(0.5555555));
		updateModel.setLatitude(new BigDecimal(0.5555555));
		
		updateModel.setId(pkValue);
		Integer updateResult = userService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		UserModel findModel = userService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getName(), findModel.getName());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		UserModel userModel = new UserModel();
		userModel.setId(882101607L);
		userModel.setName("167525a4-b2fd-44f5-b82b-ccb6d2519d9f");
		userModel.setPassword("8667ca2a-9698-4add-89b0-542b50a3e549");
		userModel.setPhotos("796900bb-70e9-48d3-946b-978d676321af");
		userModel.setMobilePhone("414c1fb7-4731-4d7c-97a6-");
		userModel.setGender("3");
		userModel.setConstellation("888f0fee");
		userModel.setAge(43624432);
		userModel.setLndustry("76b87e4b-7285-45ae-80a8-75fb86de914b");
		userModel.setGroupId(342983514L);
		userModel.setLabels("0b05d9f5-5469-4804-b7f5-86bcef004654");
		userModel.setLongitude(new BigDecimal(0.5555555));
		userModel.setLatitude(new BigDecimal(0.5555555));
		Long pkValue = userModel.getId();
		saveModel(userModel);
	
		Integer deleteResult = userService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		UserModel findModel = userService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(UserModel userModel) throws Exception {
		Integer createResult = userService.create(userModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = userService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private UserModel createModel() {
		UserModel userModel = new UserModel();
		userModel.setId(490284421L);
		userModel.setName("f973da8e-7f30-4cb0-b55c-68a9455ed6f1");
		userModel.setPassword("a58d01b6-cb45-4458-a2fa-1c3cb1218f01");
		userModel.setPhotos("2b3f7a05-79af-4d92-bea1-45f94397fe03");
		userModel.setMobilePhone("94630884-41d4-4d57-a4f2-");
		userModel.setGender("8");
		userModel.setConstellation("015601e9");
		userModel.setAge(78888690);
		userModel.setLndustry("220b351c-8f92-4e24-ab16-fcb49e0b9223");
		userModel.setGroupId(210746172L);
		userModel.setLabels("1eae012e-cc52-4b58-82bd-09fd1301b99e");
		userModel.setLongitude(new BigDecimal(0.5555555));
		userModel.setLatitude(new BigDecimal(0.5555555));
		return userModel;
	}


}
