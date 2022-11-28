package com.rubber.at.tennis.invite.dao.dal.impl;

import com.rubber.at.tennis.invite.dao.entity.UserTennisRecordEntity;
import com.rubber.at.tennis.invite.dao.mapper.UserTennisRecordMapper;
import com.rubber.at.tennis.invite.dao.dal.IUserTennisRecordDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户网球训练记录 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-11-25
 */
@Service
public class UserTennisRecordDalImpl extends BaseAdminService<UserTennisRecordMapper, UserTennisRecordEntity> implements IUserTennisRecordDal {

}
