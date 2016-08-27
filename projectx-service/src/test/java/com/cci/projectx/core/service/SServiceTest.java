		package com.cci.projectx.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.wlw.pylon.unit.BaseDbTest;
import com.cci.projectx.core.service.SService;
import com.cci.projectx.core.model.SModel;

import java.util.Date;

public class SServiceTest extends BaseDbTest{

	@Autowired
	private SService sService;

	@Test
	public void testCreate() throws Exception {
		SModel sModel = new SModel();
		sModel.setId(288762175L);
		sModel.setUserId(948338314L);
		sModel.setCreatTime(new Date());
		sModel.setU(0.0953301005385977);
		sModel.setV(0.09090821461413501);
		sModel.setM(0.3929126331222169);
		sModel.setN(0.05290756976823041);
		sModel.setA(0.5976405527476252);
		sModel.setB(0.8244690877861363);
		sModel.setC(0.11943529967035071);
		sModel.setD(0.7294544548167137);
		Long pkValue = sModel.getId();
		saveModel(sModel);

		SModel findModel = sService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(sModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testFindByPrimaryKey() throws Exception {
		SModel sModel = new SModel();
		sModel.setId(704311890L);
		sModel.setUserId(548803873L);
		sModel.setCreatTime(new Date());
		sModel.setU(0.473193002109902);
		sModel.setV(0.7832517585929708);
		sModel.setM(0.3392027546378311);
		sModel.setN(0.4728007800278291);
		sModel.setA(0.813808260917621);
		sModel.setB(0.5634987894098088);
		sModel.setC(0.43448063885114296);
		sModel.setD(0.0038358975350454294);
		Long pkValue = sModel.getId();
		saveModel(sModel);

		SModel findModel = sService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(sModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testUpdateByPrimaryKey() throws Exception {
		SModel sModel = new SModel();
		sModel.setId(914419158L);
		sModel.setUserId(290285432L);
		sModel.setCreatTime(new Date());
		sModel.setU(0.9090814486350786);
		sModel.setV(0.7793594727531269);
		sModel.setM(0.0045862693473708305);
		sModel.setN(0.4272757445140386);
		sModel.setA(0.4807048724514683);
		sModel.setB(0.3018232853519177);
		sModel.setC(0.27711871430536805);
		sModel.setD(0.1300270974437573);
		Long pkValue = sModel.getId();
		saveModel(sModel);

		//SModel updateModel = createModel();
		SModel updateModel = new SModel();
		updateModel.setId(161242651L);
		updateModel.setUserId(876877932L);
		updateModel.setCreatTime(new Date());
		updateModel.setU(0.7918991871737957);
		updateModel.setV(0.3332421793523722);
		updateModel.setM(0.9484924679073601);
		updateModel.setN(0.3226885018590716);
		updateModel.setA(0.9684871179528527);
		updateModel.setB(0.051144220486695535);
		updateModel.setC(0.21496524648984883);
		updateModel.setD(0.9687595722267227);
		
		updateModel.setId(pkValue);
		Integer updateResult = sService.updateByPrimaryKey(updateModel);
		assertEquals(new Integer(1), updateResult);
		SModel findModel = sService.findByPrimaryKey(pkValue);
		assertEquals(pkValue, findModel.getId());
		assertEquals(updateModel.getUserId(), findModel.getUserId());

		cleanModel(pkValue);
	}

	@Test
	public void testDeleteByPrimaryKey() throws Exception{
		SModel sModel = new SModel();
		sModel.setId(561016436L);
		sModel.setUserId(433824809L);
		sModel.setCreatTime(new Date());
		sModel.setU(0.7051563528750598);
		sModel.setV(0.3021407584166481);
		sModel.setM(0.6026021277642755);
		sModel.setN(0.8119434387781596);
		sModel.setA(0.007760567950050867);
		sModel.setB(0.6029191212608348);
		sModel.setC(0.5993549253070447);
		sModel.setD(0.005051524694212994);
		Long pkValue = sModel.getId();
		saveModel(sModel);
	
		Integer deleteResult = sService.deleteByPrimaryKey(pkValue);
		assertEquals(new Integer(1), deleteResult);
		SModel findModel = sService.findByPrimaryKey(pkValue);
		assertNull(findModel);
	}
	
	private void saveModel(SModel sModel) throws Exception {
		Integer createResult = sService.create(sModel);
		assertEquals(createResult, new Integer(1));
	}

	private void cleanModel(Long pk) throws Exception {
		Integer deleteResult = sService.deleteByPrimaryKey(pk);
		assertEquals(deleteResult, new Integer(1));
	}

	@SuppressWarnings("unused")
	private SModel createModel() {
		SModel sModel = new SModel();
		sModel.setId(739051232L);
		sModel.setUserId(555369363L);
		sModel.setCreatTime(new Date());
		sModel.setU(0.04089062448786884);
		sModel.setV(0.08773634815084264);
		sModel.setM(0.06439860946746545);
		sModel.setN(0.4533739026518965);
		sModel.setA(0.4711960926431922);
		sModel.setB(0.5665979147660193);
		sModel.setC(0.24296880026457246);
		sModel.setD(0.8236252388492062);
		return sModel;
	}


}
