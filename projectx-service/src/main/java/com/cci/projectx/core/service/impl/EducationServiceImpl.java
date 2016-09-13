package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.domain.MajorNeo;
import com.cci.projectx.core.domain.SchoolNeo;
import com.cci.projectx.core.entity.Education;
import com.cci.projectx.core.model.*;
import com.cci.projectx.core.neorepository.MajorNeoRepository;
import com.cci.projectx.core.neorepository.SchoolNeoRepository;
import com.cci.projectx.core.repository.EducationRepository;
import com.cci.projectx.core.service.EducationService;
import com.cci.projectx.core.service.MajorService;
import com.cci.projectx.core.service.SchoolService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EducationServiceImpl implements EducationService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private EducationRepository educationRepo;

	@Autowired
	private ElasticSearchHelp elasticSearchBase;

	@Autowired
	private SchoolNeoRepository schoolNeoRepository;

	@Autowired
	private MajorNeoRepository majorNeoRepository;

	@Autowired
	private SchoolService schoolService;

	@Autowired
	private MajorService majorService;
	@Transactional
	@Override
	public int create(EducationModel educationModel) {
		return createSelective(educationModel);
	}

	@Transactional
	@Override
	public int createSelective(EducationModel educationModel) {
		Education education = beanMapper.map(educationModel, Education.class);
		int nid = educationRepo.insertSelective(education);
		if (education.getId()!=null) {
			elasticSearchBase.mergeES(education, String.valueOf(education.getId()));
		}

		//添加学校关系
		if (!StringUtils.isEmpty(education.getUniversity())) {
			//查询mysql里面是否有
			if (schoolService.findCountByName(education.getUniversity())==0) {
				
				SchoolModel schoolModel = new SchoolModel();
				schoolModel.setName(education.getUniversity());
				schoolService.create(schoolModel);
			}
			Long ids=schoolService.findIdByName(education.getUniversity());
			//添加关系
			if(ids!=null) { 
				SchoolNeo schoolNeo = schoolNeoRepository.findBySchoolId(ids);
				schoolNeoRepository.deleteSchoolRelat(education.getUserId(), schoolNeo.getSchoolId());
				schoolNeoRepository.addSchoolRelat(education.getUserId(), schoolNeo.getSchoolId());
			}
		}

		//添加专业X关系
		if (!StringUtils.isEmpty(education.getMajorx())) {
			//查询mysql里面是否有
			if (majorService.findCountByName(education.getMajorx())==0) {
				MajorModel majorModel = new MajorModel();
				majorModel.setName(education.getMajorx());
				majorService.create(majorModel);
			}
			Long ids=majorService.findIdByName(education.getMajorx());
			//添加关系
			if(ids!=null) {
				MajorNeo majorNeo = majorNeoRepository.findByMajorId(ids);
				majorNeoRepository.deleteMajorRelat(education.getUserId(), majorNeo.getMajorId());
				majorNeoRepository.addMajorRelat(education.getUserId(), majorNeo.getMajorId());
			}
		}

		//添加专业Y关系
		if (!StringUtils.isEmpty(education.getMajory())) {
			//查询mysql里面是否有
			if (majorService.findCountByName(education.getMajory())==0) {
				MajorModel majorModel = new MajorModel();
				majorModel.setName(education.getMajory());
				majorService.create(majorModel);
			}
			Long ids=majorService.findIdByName(education.getMajory());
			//添加关系
			if(ids!=null) {
				MajorNeo majorNeo = majorNeoRepository.findByMajorId(ids);
				majorNeoRepository.deleteMajorRelat(education.getUserId(), majorNeo.getMajorId());
				majorNeoRepository.addMajorRelat(education.getUserId(), majorNeo.getMajorId());
			}
		}


		return nid;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {

		EducationModel education = findByPrimaryKey(id);
		//删除学校关系
		if (!StringUtils.isEmpty(education.getUniversity())) {
			//删除关系
			Long ids=schoolService.findIdByName(education.getUniversity());
			if(ids!=null){
				SchoolNeo schoolNeo = schoolNeoRepository.findBySchoolId(ids);
				if (schoolNeo != null) {
					schoolNeoRepository.deleteSchoolRelat(education.getUserId(), schoolNeo.getSchoolId());
				}
			}

		}


		//删除专业X关系
		if (StringUtils.isNotEmpty(education.getMajorx())) {
			//删除关系
			Long ids=majorService.findIdByName(education.getMajorx());
			if(ids!=null){
				MajorNeo majorNeo = majorNeoRepository.findByMajorId(ids);
				if (majorNeo != null) {
					majorNeoRepository.deleteMajorRelat(education.getUserId(), majorNeo.getMajorId());
				}
			}

		}
		//删除专业Y关系
		if (StringUtils.isNotEmpty(education.getMajory())) {
			//删除关系
			Long ids=majorService.findIdByName(education.getMajory());
			if(ids!=null){
				MajorNeo majorNeo = majorNeoRepository.findByMajorId(ids);
				if (majorNeo != null) {
					majorNeoRepository.deleteMajorRelat(education.getUserId(), majorNeo.getMajorId());
				}
			}

		}


		int eid = educationRepo.deleteByPrimaryKey(id);
		if (eid > 0) {
			elasticSearchBase.deleteES(Education.class, id);
		}
		return eid;
	}

	@Transactional(readOnly = true)
	@Override
	public EducationModel findByPrimaryKey(Long id) {
		Education education = educationRepo.selectByPrimaryKey(id);
		return beanMapper.map(education, EducationModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(EducationModel educationModel) {
		return educationRepo.selectCount(beanMapper.map(educationModel, Education.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<EducationModel> selectPage(EducationModel educationModel,Pageable pageable) {
		Education education = beanMapper.map(educationModel, Education.class);
		return beanMapper.mapAsList(educationRepo.selectPage(education,pageable),EducationModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(EducationModel educationModel) {
		//更新学校关系
		EducationModel education = findByPrimaryKey(educationModel.getId());
		if (!StringUtils.equals(education.getUniversity(),educationModel.getUniversity())) {
			//查询mysql里面是否有
			if (schoolService.findCountByName(educationModel.getUniversity())==0) {
				SchoolModel schoolModel = new SchoolModel();
				schoolModel.setName(educationModel.getUniversity());
				schoolService.create(schoolModel);
			}
			Long id=schoolService.findIdByName(education.getUniversity());
			Long idM=schoolService.findIdByName(educationModel.getUniversity());
			//修改关系
			if(id!=null&&idM!=null) {
				SchoolNeo schoolNeo = schoolNeoRepository.findBySchoolId(id);
				SchoolNeo schoolNeoM = schoolNeoRepository.findBySchoolId(idM);
				schoolNeoRepository.deleteSchoolRelat(education.getUserId(), schoolNeo.getSchoolId());
				schoolNeoRepository.addSchoolRelat(education.getUserId(), schoolNeoM.getSchoolId());
			}

		}


		//更新专业X关系
		if (!StringUtils.equals(education.getMajorx(),educationModel.getMajorx())) {
			//查询mysql里面是否有
			if (majorService.findCountByName(educationModel.getMajorx())==0) {
				MajorModel majorModel = new MajorModel();
				majorModel.setName(educationModel.getMajorx());
				majorService.create(majorModel);
			}
			Long ids=majorService.findIdByName(education.getMajorx());
			Long idsM=majorService.findIdByName(educationModel.getMajorx());
			//修改关系
			if(ids!=null&&idsM!=null) {
				MajorNeo majorNeo = majorNeoRepository.findByMajorId(ids);
				MajorNeo majorNeoM = majorNeoRepository.findByMajorId(idsM);
				majorNeoRepository.deleteMajorRelat(education.getUserId(), majorNeo.getMajorId());
				majorNeoRepository.addMajorRelat(education.getUserId(), majorNeoM.getMajorId());
			}

		}

		//更新专业Y关系
		if (!StringUtils.equals(education.getMajory(),educationModel.getMajory())) {
			//查询mysql里面是否有
			if (majorService.findCountByName(educationModel.getMajory())==0) {
				MajorModel majorModel = new MajorModel();
				majorModel.setName(educationModel.getMajory());
				majorService.create(majorModel);
			}
			Long id=majorService.findIdByName(education.getMajory());
			Long idsM=majorService.findIdByName(educationModel.getMajory());
			//修改关系
			if(id!=null&&idsM!=null) {
				MajorNeo majorNeo = majorNeoRepository.findByMajorId(id);
				MajorNeo majorNeoM = majorNeoRepository.findByMajorId(idsM);
				majorNeoRepository.deleteMajorRelat(education.getUserId(), majorNeo.getMajorId());
				majorNeoRepository.addMajorRelat(education.getUserId(), majorNeoM.getMajorId());
			}

		}
		return updateByPrimaryKeySelective(educationModel);
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(EducationModel educationModel) {
		Education education = beanMapper.map(educationModel, Education.class);
		int id = educationRepo.updateByPrimaryKeySelective(education);
		if (id > 0) {
			elasticSearchBase.mergeES(education,education.getId().toString());
		}
		return id;
	}

	/**
	 * 通过对象模糊查询教育背景
	 * @param educationModel
	 * @return
	 */
	@Transactional
	@Override
	public List<EducationModel> getEducationByEducationInfo(EducationModel educationModel){
		Education education=beanMapper.map(educationModel,Education.class);
		List<EducationModel> educations = elasticSearchBase.findESForList(education);
		return educations;
	}

}
