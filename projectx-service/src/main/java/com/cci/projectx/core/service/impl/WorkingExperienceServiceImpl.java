package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.ElasticSearchHelp;
import com.cci.projectx.core.domain.CompanyNeo;
import com.cci.projectx.core.domain.DepartmentNeo;
import com.cci.projectx.core.domain.PostNeo;
import com.cci.projectx.core.entity.WorkingExperience;
import com.cci.projectx.core.model.CompanyModel;
import com.cci.projectx.core.model.DepartmentModel;
import com.cci.projectx.core.model.PostModel;
import com.cci.projectx.core.model.WorkingExperienceModel;
import com.cci.projectx.core.neorepository.CompanyNeoRepository;
import com.cci.projectx.core.neorepository.DepartmentNeoRepository;
import com.cci.projectx.core.neorepository.PostNeoRepository;
import com.cci.projectx.core.repository.WorkingExperienceRepository;
import com.cci.projectx.core.service.CompanyService;
import com.cci.projectx.core.service.DepartmentService;
import com.cci.projectx.core.service.PostService;
import com.cci.projectx.core.service.WorkingExperienceService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkingExperienceServiceImpl implements WorkingExperienceService {

	@Autowired
	private BeanMapper beanMapper;

	@Autowired
	private WorkingExperienceRepository workingExperienceRepo;

	@Autowired
	private ElasticSearchHelp elasticSearchBase;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyNeoRepository companyNeoRepository;

	@Autowired
	private DepartmentService departmentService;

	@Autowired
	private DepartmentNeoRepository departmentNeoRepository;

	@Autowired
	private PostService postService;

	@Autowired
	private PostNeoRepository postNeoRepository;

	@Transactional
	@Override
	public int create(WorkingExperienceModel workingExperienceModel) {
		return createSelective(workingExperienceModel);
	}

	@Transactional
	@Override
	public int createSelective(WorkingExperienceModel workingExperienceModel) {
		WorkingExperience workingExperience = beanMapper.map(workingExperienceModel, WorkingExperience.class);
		int id = workingExperienceRepo.insertSelective(workingExperience);
		if (workingExperience.getId()!=null) {
			elasticSearchBase.mergeES(workingExperience, String.valueOf(workingExperience.getId()));
		}
		//添加公司关系
		if (!StringUtils.isEmpty(workingExperience.getCompany())) {
			//查询mysql里面是否有
			if (companyService.findCountByName(workingExperience.getCompany())==0) {
				
				CompanyModel companyModel = new CompanyModel();
				companyModel.setName(workingExperience.getCompany());
				companyService.create(companyModel);
			}
			Long ids=companyService.findIdByName(workingExperience.getCompany());
			//添加关系
			if(ids!=null) {
				CompanyNeo companyNeo = companyNeoRepository.findByCompanyId(ids);
				companyNeoRepository.deleteCompanyRelat(workingExperience.getUserId(), companyNeo.getCompanyId());
				companyNeoRepository.addCompanyRelat(workingExperience.getUserId(), companyNeo.getCompanyId());
			}
		}

		//添加部门关系
		if (!StringUtils.isEmpty(workingExperience.getDepartment())) {
			//查询mysql里面是否有
			if (departmentService.findCountByName(workingExperience.getDepartment())==0) {

				DepartmentModel departmentModel = new DepartmentModel();
				departmentModel.setName(workingExperience.getDepartment());
				departmentService.create(departmentModel);
			}
			Long ids=departmentService.findIdByName(workingExperience.getDepartment());
			//添加关系
			if(ids!=null) {
				DepartmentNeo departmentNeo = departmentNeoRepository.findByDepartmentId(ids);
				departmentNeoRepository.deleteDepartmentRelat(workingExperience.getUserId(), departmentNeo.getDepartmentId());
				departmentNeoRepository.addDepartmentRelat(workingExperience.getUserId(), departmentNeo.getDepartmentId());
			}
		}

		//添加职位关系
		if (!StringUtils.isEmpty(workingExperience.getPosition())) {
			//查询mysql里面是否有
			if (postService.findCountByName(workingExperience.getPosition())==0) {

				PostModel postModel = new PostModel();
				postModel.setName(workingExperience.getPosition());
				postService.create(postModel);
			}
			Long ids=postService.findIdByName(workingExperience.getPosition());
			//添加关系
			if(ids!=null) {
				PostNeo postNeo = postNeoRepository.findByPostId(ids);
				postNeoRepository.deletePostRelat(workingExperience.getUserId(), postNeo.getPostId());
				postNeoRepository.addPostRelat(workingExperience.getUserId(), postNeo.getPostId());
			}
		}

		return id;
	}

	@Transactional
	@Override
	public int deleteByPrimaryKey(Long id) {
		WorkingExperienceModel workingExperience = findByPrimaryKey(id); 
		//删除公司关系
		if (!StringUtils.isEmpty(workingExperience.getCompany())) {
			//删除关系
			Long ids=companyService.findIdByName(workingExperience.getCompany());
			if(ids!=null){
				CompanyNeo companyNeo = companyNeoRepository.findByCompanyId(ids);
				if (companyNeo != null) {
					companyNeoRepository.deleteCompanyRelat(workingExperience.getUserId(), companyNeo.getCompanyId());
				}
			}

		}

		//删除部门关系
		if (!StringUtils.isEmpty(workingExperience.getDepartment())) {
			//删除关系
			Long ids=departmentService.findIdByName(workingExperience.getDepartment());
			if(ids!=null){
				DepartmentNeo departmentNeo = departmentNeoRepository.findByDepartmentId(ids);
				if (departmentNeo != null) {
					departmentNeoRepository.deleteDepartmentRelat(workingExperience.getUserId(), departmentNeo.getDepartmentId());
				}
			}

		}

		//删除职位关系
		if (!StringUtils.isEmpty(workingExperience.getPosition())) {
			//删除关系
			Long ids=postService.findIdByName(workingExperience.getPosition());
			if(ids!=null){
				PostNeo postNeo = postNeoRepository.findByPostId(ids);
				if (postNeo != null) {
					postNeoRepository.deletePostRelat(workingExperience.getUserId(), postNeo.getPostId());
				}
			}

		}

		int wid = workingExperienceRepo.deleteByPrimaryKey(id);
		if (wid > 0) {
			elasticSearchBase.deleteES(WorkingExperience.class, id);
		}
		return wid;
	}

	@Transactional(readOnly = true)
	@Override
	public WorkingExperienceModel findByPrimaryKey(Long id) {
		WorkingExperience workingExperience = workingExperienceRepo.selectByPrimaryKey(id);
		return beanMapper.map(workingExperience, WorkingExperienceModel.class);
	}

	@Transactional(readOnly = true)
	@Override
	public long selectCount(WorkingExperienceModel workingExperienceModel) {
		return workingExperienceRepo.selectCount(beanMapper.map(workingExperienceModel, WorkingExperience.class));
	}

	@Transactional(readOnly = true)
	@Override
	public List<WorkingExperienceModel> selectPage(WorkingExperienceModel workingExperienceModel,Pageable pageable) {
		WorkingExperience workingExperience = beanMapper.map(workingExperienceModel, WorkingExperience.class);
		return beanMapper.mapAsList(workingExperienceRepo.selectPage(workingExperience,pageable),WorkingExperienceModel.class);
	}

	@Transactional
	@Override
	public int updateByPrimaryKey(WorkingExperienceModel workingExperienceModel) {

		WorkingExperienceModel workingExperience = findByPrimaryKey(workingExperienceModel.getId());
		//更新公司关系
		if (!StringUtils.equals(workingExperience.getCompany(),workingExperienceModel.getCompany())) {
			//查询mysql里面是否有
			if (companyService.findCountByName(workingExperienceModel.getCompany())==0) {
				CompanyModel companyModel = new CompanyModel();
				companyModel.setName(workingExperienceModel.getCompany());
				companyService.create(companyModel);
			}
			Long id=companyService.findIdByName(workingExperience.getCompany());
			Long idM=companyService.findIdByName(workingExperienceModel.getCompany());
			//修改关系
			if(id!=null&&idM!=null) {
				CompanyNeo companyNeo = companyNeoRepository.findByCompanyId(id);
				CompanyNeo companyNeoM = companyNeoRepository.findByCompanyId(idM);
				companyNeoRepository.deleteCompanyRelat(workingExperience.getUserId(), companyNeo.getCompanyId());
				companyNeoRepository.addCompanyRelat(workingExperience.getUserId(), companyNeoM.getCompanyId());
			}

		}
		//更新部门关系
		if (!StringUtils.equals(workingExperience.getDepartment(),workingExperienceModel.getDepartment())) {
			//查询mysql里面是否有
			if (departmentService.findCountByName(workingExperienceModel.getDepartment())==0) {
				DepartmentModel departmentModel = new DepartmentModel();
				departmentModel.setName(workingExperienceModel.getDepartment());
				departmentService.create(departmentModel);
			}
			Long id=departmentService.findIdByName(workingExperience.getDepartment());
			Long idM=departmentService.findIdByName(workingExperienceModel.getDepartment());
			//修改关系
			if(id!=null&&idM!=null) {
				DepartmentNeo departmentNeo = departmentNeoRepository.findByDepartmentId(id);
				DepartmentNeo departmentNeoM = departmentNeoRepository.findByDepartmentId(idM);
				departmentNeoRepository.deleteDepartmentRelat(workingExperience.getUserId(), departmentNeo.getDepartmentId());
				departmentNeoRepository.addDepartmentRelat(workingExperience.getUserId(), departmentNeoM.getDepartmentId());
			}

		}
        //更新职位关系
		if (!StringUtils.equals(workingExperience.getPosition(),workingExperienceModel.getPosition())) {
			//查询mysql里面是否有
			if (postService.findCountByName(workingExperienceModel.getPosition())==0) {
				PostModel postModel = new PostModel();
				postModel.setName(workingExperienceModel.getPosition());
				postService.create(postModel);
			}
			Long id=postService.findIdByName(workingExperience.getPosition());
			Long idM=postService.findIdByName(workingExperienceModel.getPosition());
			//修改关系
			if(id!=null&&idM!=null) {
				PostNeo postNeo = postNeoRepository.findByPostId(id);
				PostNeo postNeoM = postNeoRepository.findByPostId(idM);
				postNeoRepository.deletePostRelat(workingExperience.getUserId(), postNeo.getPostId());
				postNeoRepository.addPostRelat(workingExperience.getUserId(), postNeoM.getPostId());
			}

		}

		return updateByPrimaryKeySelective(workingExperienceModel);
	}
	
	@Transactional
	@Override
	public int updateByPrimaryKeySelective(WorkingExperienceModel workingExperienceModel) {
		WorkingExperience workingExperience = beanMapper.map(workingExperienceModel, WorkingExperience.class);
		int id = workingExperienceRepo.updateByPrimaryKeySelective(workingExperience);
		if (workingExperience.getId()!=null) {
			elasticSearchBase.mergeES(workingExperience, workingExperience.getId().toString());
		}
		return id;
	}

	/**
	 * 通过对象模糊查询教育背景
	 * @param workingExperienceModel
	 * @return
	 */
	@Transactional
	@Override
	public List<WorkingExperienceModel> getWorkingByWorkInfo(WorkingExperienceModel workingExperienceModel){
		WorkingExperience workingExperience=beanMapper.map(workingExperienceModel,WorkingExperience.class);
		List<WorkingExperienceModel> workingExperiences = elasticSearchBase.findESForList(workingExperience);
		return workingExperiences;
	}

}
