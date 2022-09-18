package com.rubber.at.tennis.invite.dao.dal.impl;

import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteInfoMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteInfoDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邀约详情表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@Service
public class InviteInfoDalImpl extends BaseAdminService<InviteInfoMapper, InviteInfoEntity> implements IInviteInfoDal {

}
