package com.rubber.at.tennis.invite.dao.dal.impl;

import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteUserMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邀约人详情表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@Service
public class InviteUserDalImpl extends BaseAdminService<InviteUserMapper, InviteUserEntity> implements IInviteUserDal {

}
