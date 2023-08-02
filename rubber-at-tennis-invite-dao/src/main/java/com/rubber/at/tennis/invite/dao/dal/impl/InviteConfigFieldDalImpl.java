package com.rubber.at.tennis.invite.dao.dal.impl;

import com.rubber.at.tennis.invite.dao.entity.InviteConfigFieldEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteConfigFieldMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteConfigFieldDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邀约详情配置表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Service
public class InviteConfigFieldDalImpl extends BaseAdminService<InviteConfigFieldMapper, InviteConfigFieldEntity> implements IInviteConfigFieldDal {

}
