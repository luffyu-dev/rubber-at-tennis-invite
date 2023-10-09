package com.rubber.at.tennis.invite.dao.dal;

import com.rubber.at.tennis.invite.dao.entity.UserBasicInfoEntity;
import com.rubber.base.components.mysql.plugins.admin.IBaseAdminService;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 用户基础信息表 服务类
 * </p>
 *
 * @author rockyu
 * @since 2022-10-16
 */
public interface IInviteUserBasicInfoDal extends IBaseAdminService<UserBasicInfoEntity> {

    /**
     * 通过uid 查询用户的基础信息
     * @param uid 当前的uid
     * @return 返回用户的基础信息
     */
    UserBasicInfoEntity getByUid(Integer uid);


    /**
     * 联系信息
     * @param uid
     * @param userPhone
     * @param userWx
     * @return
     */
    boolean updateContact(Integer uid,String userPhone,String userWx);

    /**
     * 查询用户的基础信息列表
     * @param uids 当前的uid集合
     * @return 返回符合要求的用户信息
     */
    List<UserBasicInfoEntity> queryByUid(Collection<Integer> uids);

}
