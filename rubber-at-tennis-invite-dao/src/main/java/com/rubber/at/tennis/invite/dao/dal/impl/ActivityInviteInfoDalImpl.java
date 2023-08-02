package com.rubber.at.tennis.invite.dao.dal.impl;

import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.dao.mapper.ActivityInviteInfoMapper;
import com.rubber.at.tennis.invite.dao.dal.IActivityInviteInfoDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邀约表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Service
public class ActivityInviteInfoDalImpl extends BaseAdminService<ActivityInviteInfoMapper, ActivityInviteInfoEntity> implements IActivityInviteInfoDal {

}
