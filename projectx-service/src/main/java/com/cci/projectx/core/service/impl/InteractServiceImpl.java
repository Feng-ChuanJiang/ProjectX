package com.cci.projectx.core.service.impl;

import com.cci.projectx.core.entity.Interact;
import com.cci.projectx.core.model.InteractModel;
import com.cci.projectx.core.repository.InteractRepository;
import com.cci.projectx.core.service.CommentService;
import com.cci.projectx.core.service.InteractPermissionService;
import com.cci.projectx.core.service.InteractService;
import com.cci.projectx.core.service.UserInteractCircleService;
import com.wlw.pylon.core.beans.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InteractServiceImpl implements InteractService {

    @Autowired
    private BeanMapper beanMapper;

    @Autowired
    private InteractRepository interactRepo;

    @Autowired
    private UserInteractCircleService userInteractCircleService;

    @Autowired
    private InteractPermissionService interactPermissionService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CommentService commentService;

    @Transactional
    @Override
    public int create(InteractModel interactModel) {
        return createSelective(interactModel);
    }

    @Transactional
    @Override
    public int createSelective(InteractModel interactModel) {
        Interact interact = beanMapper.map(interactModel, Interact.class);
        int tag = interactRepo.insertSelective(interact);
        interactModel.setId(interact.getId());
        return tag;
    }

    @Transactional
    @Override
    public int deleteByPrimaryKey(Long id) {
        InteractModel interactModel = findByPrimaryKey(id);
        interactPermissionService.deleteByUserIdandInteractId(interactModel.getUserId(), id);
        userInteractCircleService.deleteByUserIdandInteractId(interactModel.getUserId(), id);
        return interactRepo.deleteByPrimaryKey(id);
    }

    @Transactional(readOnly = true)
    @Override
    public InteractModel findByPrimaryKey(Long id) {
        Interact interact = interactRepo.selectByPrimaryKey(id);
        return beanMapper.map(interact, InteractModel.class);
    }

    @Transactional(readOnly = true)
    @Override
    public long selectCount(InteractModel interactModel) {
        return interactRepo.selectCount(beanMapper.map(interactModel, Interact.class));
    }

    @Transactional(readOnly = true)
    @Override
    public List<InteractModel> selectPage(InteractModel interactModel, Pageable pageable,Long userId) {
        Interact interact = beanMapper.map(interactModel, Interact.class);
        List<InteractModel> interactModels=beanMapper.mapAsList(interactRepo.selectPage(interact, pageable), InteractModel.class);
        if(userId==interact.getUserId()){
            interactModels=commentService.setAllCommentByInteract(interactModels);
        }else{
            interactModels=commentService.setFriendCommentByInteract(interactModels,userId);
        }


        return interactModels;
    }

    @Transactional
    @Override
    public int updateByPrimaryKey(InteractModel interactModel) {
        return interactRepo.updateByPrimaryKey(beanMapper.map(interactModel, Interact.class));
    }

    @Transactional
    @Override
    public int updateByPrimaryKeySelective(InteractModel interactModel) {
        return interactRepo.updateByPrimaryKeySelective(beanMapper.map(interactModel, Interact.class));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<InteractModel> selectPageByCircleId(Long circleId, Pageable pageable,Long userId) {
        String sql = "SELECT A.* FROM INTERACT A,USER_INTERACT_CIRCLE B WHERE A.ID=B.INTERACT_ID AND B.CIRCLE_ID=? ORDER BY A.ID DESC LIMIT ? , ?";
        String sqlCount = "SELECT COUNT(1) FROM INTERACT A,USER_INTERACT_CIRCLE B WHERE A.ID=B.INTERACT_ID AND B.CIRCLE_ID=? ORDER BY A.ID DESC";
        List<InteractModel> interactModels = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(InteractModel.class), circleId, pageable.getOffset(), pageable.getPageSize());
        interactModels=commentService.setFriendCommentByInteract(interactModels,userId);
        int count = jdbcTemplate.queryForObject(sqlCount, Integer.class, circleId);
        Page<InteractModel> page = new PageImpl<>(interactModels, pageable, count);
        return page;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<InteractModel> selectPageByFriend(Long userId, Pageable pageable) {
        String sql = "  SELECT C.* FROM(\n" +
                " SELECT A.* FROM INTERACT A,FRIENDS B WHERE  A.USER_ID=B.USER_ID AND B.FRIEND_ID=? AND B.STATE=1 AND A.PRIVACY_PERMISSION=1\n" +
                " UNION ALL\n" +
                " SELECT A.* FROM INTERACT A,FRIENDS B WHERE  A.USER_ID=B.FRIEND_ID AND B.USER_ID=? AND B.STATE=1 AND  A.PRIVACY_PERMISSION=1\n" +
                "UNION ALL\n" +
                "SELECT A.* FROM INTERACT A,INTERACT_PERMISSION B WHERE A.ID=B.INTERACT_ID AND B.FRIEND_ID=? AND  A.PRIVACY_PERMISSION=2\n" +
                "UNION ALL\n" +
                "SELECT  A.* FROM INTERACT A WHERE A.USER_ID=?\n" +
                ")C WHERE C.USER_ID NOT IN (SELECT Z.ID FROM(\n" +
                "SELECT FRIEND_ID ID FROM FRIEND_SETING WHERE USER_ID=? AND HIS_INTERACT=2 AND BLACKLIST=1\n" +
                "UNION\n" +
                "SELECT USER_ID ID FROM FRIEND_SETING WHERE FRIEND_ID=? AND MY_INTERACT=2 AND BLACKLIST=1\n" +
                "UNION\n" +
                "SELECT FRIEND_ID ID FROM FRIEND_SETING WHERE USER_ID=? AND BLACKLIST=2\n" +
                ")Z ) \n" +
                "ORDER BY C.ID DESC LIMIT ? , ?";

        String sqlCount = "  SELECT COUNT(1) FROM(\n" +
                " SELECT A.* FROM INTERACT A,FRIENDS B WHERE  A.USER_ID=B.USER_ID AND B.FRIEND_ID=? AND B.STATE=1 AND  A.PRIVACY_PERMISSION=1\n" +
                " UNION ALL\n" +
                " SELECT A.* FROM INTERACT A,FRIENDS B WHERE  A.USER_ID=B.FRIEND_ID AND B.USER_ID=? AND B.STATE=1 AND  A.PRIVACY_PERMISSION=1\n" +
                "UNION ALL\n" +
                "SELECT A.* FROM INTERACT A,INTERACT_PERMISSION B WHERE A.ID=B.INTERACT_ID AND B.FRIEND_ID=? AND A.PRIVACY_PERMISSION=2\n" +
                "UNION ALL\n" +
                "SELECT  A.* FROM INTERACT A WHERE A.USER_ID=?\n" +
                ")C WHERE C.USER_ID NOT IN (SELECT Z.ID FROM(\n" +
                "SELECT FRIEND_ID ID FROM FRIEND_SETING WHERE USER_ID=? AND HIS_INTERACT=2 AND BLACKLIST=1\n" +
                "UNION\n" +
                "SELECT USER_ID ID FROM FRIEND_SETING WHERE FRIEND_ID=? AND MY_INTERACT=2 AND BLACKLIST=1\n" +
                "UNION\n" +
                "SELECT FRIEND_ID ID FROM FRIEND_SETING WHERE USER_ID=? AND BLACKLIST=2\n" +
                ")Z ) \n" +
                "ORDER BY C.ID DESC";

        List<InteractModel> interactModels = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(InteractModel.class), userId, userId, userId, userId, userId, userId, userId, pageable.getOffset(), pageable.getPageSize());
        interactModels=commentService.setFriendCommentByInteract(interactModels,userId);
        int count = jdbcTemplate.queryForObject(sqlCount, Integer.class, userId, userId, userId, userId, userId, userId, userId);
        Page<InteractModel> page = new PageImpl<>(interactModels, pageable, count);
        return page;
    }

}
