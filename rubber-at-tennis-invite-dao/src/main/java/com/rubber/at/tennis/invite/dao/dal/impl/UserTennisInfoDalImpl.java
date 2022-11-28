package com.rubber.at.tennis.invite.dao.dal.impl;

import com.rubber.at.tennis.invite.dao.entity.UserTennisInfoEntity;
import com.rubber.at.tennis.invite.dao.mapper.UserTennisInfoMapper;
import com.rubber.at.tennis.invite.dao.dal.IUserTennisInfoDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户网球训练基础表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-11-26
 */
@Service
public class UserTennisInfoDalImpl extends BaseAdminService<UserTennisInfoMapper, UserTennisInfoEntity> implements IUserTennisInfoDal {

}
