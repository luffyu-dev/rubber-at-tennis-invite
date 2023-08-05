package com.rubber.at.tennis.invite.dao.dal;

import com.rubber.at.tennis.invite.dao.entity.InviteConfigFieldEntity;
import com.rubber.base.components.mysql.plugins.admin.IBaseAdminService;

import java.util.List;

/**
 * <p>
 * 邀约详情配置表 服务类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
public interface IInviteConfigFieldDal extends IBaseAdminService<InviteConfigFieldEntity> {



    void removeAndSaveList(String inviteCode, List<InviteConfigFieldEntity> inviteConfigFieldEntityList);




    List<InviteConfigFieldEntity> queryByCode(String code);
}
