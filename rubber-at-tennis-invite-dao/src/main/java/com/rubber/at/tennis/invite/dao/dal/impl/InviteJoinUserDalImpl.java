package com.rubber.at.tennis.invite.dao.dal.impl;

import com.rubber.at.tennis.invite.dao.entity.InviteJoinUserEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteJoinUserMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteJoinUserDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邀约人详情表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Service
public class InviteJoinUserDalImpl extends BaseAdminService<InviteJoinUserMapper, InviteJoinUserEntity> implements IInviteJoinUserDal {

}
