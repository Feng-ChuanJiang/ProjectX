		package com.cci.projectx.core.service;

		import com.cci.projectx.core.model.PhotoModel;
		import com.wlw.pylon.unit.BaseDbTest;
		import org.junit.Test;
		import org.springframework.beans.factory.annotation.Autowired;

		import java.util.Date;

		import static org.junit.Assert.assertEquals;
		import static org.junit.Assert.assertNull;

		public class PhotoServiceTest extends BaseDbTest{

            @Autowired
            private PhotoService photoService;

            @Test
            public void testCreate() throws Exception {
                PhotoModel photoModel = new PhotoModel();
                photoModel.setId(522148132L);
                photoModel.setName("eeebf90b-c64d-4ab9-8580-f62bebdebabe");
                photoModel.setPath("f1f28e5a-6775-44f4-bce9-653585ffb9ce");
                photoModel.setPhotoType(668169827L);
                photoModel.setCreateTime(new Date());
                photoModel.setContentType("dbc53895-d835-4eda-b601-b418f119211f");
                photoModel.setExtension("99d2a9a0-a103-4fe3-b24d-a4d4e68fee90");
                Long pkValue = photoModel.getId();
                saveModel(photoModel);

                PhotoModel findModel = photoService.findByPrimaryKey(pkValue);
                assertEquals(pkValue, findModel.getId());
                assertEquals(photoModel.getName(), findModel.getName());

                cleanModel(pkValue);
            }

            @Test
            public void testFindByPrimaryKey() throws Exception {
                PhotoModel photoModel = new PhotoModel();
                photoModel.setId(593639221L);
                photoModel.setName("4b289ed2-e1d7-4a5c-90c3-cfbb46a13a09");
                photoModel.setPath("96ffb2f0-e1e7-4ae6-96d1-bbe375aa9e5d");
                photoModel.setPhotoType(382732586L);
                photoModel.setCreateTime(new Date());
                photoModel.setContentType("b35ffd63-dd0d-4f5d-99b7-869d3bc8247e");
                photoModel.setExtension("4eeed593-fe57-4fe5-870a-12cdde302f0a");
                Long pkValue = photoModel.getId();
                saveModel(photoModel);

                PhotoModel findModel = photoService.findByPrimaryKey(pkValue);
                assertEquals(pkValue, findModel.getId());
                assertEquals(photoModel.getName(), findModel.getName());

                cleanModel(pkValue);
            }

            @Test
            public void testUpdateByPrimaryKey() throws Exception {
                PhotoModel photoModel = new PhotoModel();
                photoModel.setId(729463546L);
                photoModel.setName("9b3eaac6-6521-4086-8090-415cbe90961d");
                photoModel.setPath("ff42fb25-e2ab-42a3-99c5-8cc6e987316f");
                photoModel.setPhotoType(675939684L);
                photoModel.setCreateTime(new Date());
                photoModel.setContentType("8d59999f-0a1e-48b6-8337-c6868aa98d3a");
                photoModel.setExtension("53d1184c-db0c-45b3-a1e4-630b1b0a0901");
                Long pkValue = photoModel.getId();
                saveModel(photoModel);

                //PhotoModel updateModel = createModel();
                PhotoModel updateModel = new PhotoModel();
                updateModel.setId(286744526L);
                updateModel.setName("a4dbd800-d64c-464b-b7f5-3b9a10548fef");
                updateModel.setPath("9c14f965-3d97-43ba-b855-7c97d63da683");
                updateModel.setPhotoType(339193571L);
                updateModel.setCreateTime(new Date());
                updateModel.setContentType("316eaf9d-bfb2-4dd9-bf4f-4d120435cca8");
                updateModel.setExtension("3c7a1ab6-7100-4632-b182-51517c12865a");

                updateModel.setId(pkValue);
                Integer updateResult = photoService.updateByPrimaryKey(updateModel);
                assertEquals(new Integer(1), updateResult);
                PhotoModel findModel = photoService.findByPrimaryKey(pkValue);
                assertEquals(pkValue, findModel.getId());
                assertEquals(updateModel.getName(), findModel.getName());

                cleanModel(pkValue);
            }

            @Test
            public void testDeleteByPrimaryKey() throws Exception {
                PhotoModel photoModel = new PhotoModel();
                photoModel.setId(487855534L);
                photoModel.setName("1b3d6127-a39d-4dbf-b89e-5b97f563d5cd");
                photoModel.setPath("bec8140f-4158-4f82-a166-a508d7424fb4");
                photoModel.setPhotoType(561763740L);
                photoModel.setCreateTime(new Date());
                photoModel.setContentType("88178045-70d7-43e8-ad5e-e18b8e2718de");
                photoModel.setExtension("e82dd38d-68c3-4a61-a01f-4fca586c9147");
                Long pkValue = photoModel.getId();
                saveModel(photoModel);

                Integer deleteResult = photoService.deleteByPrimaryKey(pkValue);
                assertEquals(new Integer(1), deleteResult);
                PhotoModel findModel = photoService.findByPrimaryKey(pkValue);
                assertNull(findModel);
            }

            private void saveModel(PhotoModel photoModel) throws Exception {
                Integer createResult = photoService.create(photoModel);
                assertEquals(createResult, new Integer(1));
            }

            private void cleanModel(Long pk) throws Exception {
                Integer deleteResult = photoService.deleteByPrimaryKey(pk);
                assertEquals(deleteResult, new Integer(1));
            }

            @SuppressWarnings("unused")
            private PhotoModel createModel() {
                PhotoModel photoModel = new PhotoModel();
                photoModel.setId(922989891L);
                photoModel.setName("1e35383d-9aae-49c3-acba-b38aca1d6c46");
                photoModel.setPath("3667ad42-8300-4a66-8f90-2e4fd1e76545");
                photoModel.setPhotoType(786725990L);
                photoModel.setCreateTime(new Date());
                photoModel.setContentType("038e4590-6602-44f2-9ba0-79785e311677");
                photoModel.setExtension("1f1e60c4-0e38-4e16-ac56-b6dc1db408fc");
                return photoModel;
            }


        }
