package com.rubber.at.tennis.invite.dao.dal;

import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.base.components.mysql.plugins.admin.IBaseAdminService;

import java.util.List;

/**
 * <p>
 * 邀约人详情表 服务类
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
public interface IInviteUserDal extends IBaseAdminService<InviteUserEntity> {

    /**
     * 通过code查询参与人信息
     *
     * @param code
     * @return
     */
    List<InviteUserEntity> queryJoinByCode(String code,Integer state);


    /**
     * 通过code + uid 查询一个是是否参与
     */
    InviteUserEntity getInviteJoinUser(String code, Integer uid);
}
