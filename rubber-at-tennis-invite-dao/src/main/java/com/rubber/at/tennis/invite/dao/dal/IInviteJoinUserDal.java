package com.rubber.at.tennis.invite.dao.dal;

import com.rubber.at.tennis.invite.dao.entity.InviteJoinUserEntity;
import com.rubber.base.components.mysql.plugins.admin.IBaseAdminService;

import java.util.List;

/**
 * <p>
 * 邀约人详情表 服务类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
public interface IInviteJoinUserDal extends IBaseAdminService<InviteJoinUserEntity> {



    /**
     * 通过code查询参与人信息
     *
     * @param code
     * @return
     */
    List<InviteJoinUserEntity> queryJoinByCode(String code, Integer state);


    /**
     * 通过code + uid 查询一个是是否参与
     */
    InviteJoinUserEntity getInviteJoinUser(String code, Integer uid);

}
