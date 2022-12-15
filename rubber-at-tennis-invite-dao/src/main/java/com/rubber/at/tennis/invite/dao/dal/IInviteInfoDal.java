package com.rubber.at.tennis.invite.dao.dal;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.invite.dao.condition.InviteInfoCondition;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.base.components.mysql.plugins.admin.IBaseAdminService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 邀约详情表 服务类
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
public interface IInviteInfoDal extends IBaseAdminService<InviteInfoEntity> {


    /**
     * 通过code查询邀请的信息
     * @param code 当前的code
     * @return 返回邀请的信息
     */
    InviteInfoEntity getByCode(String code);


    /**
     *
     * @param page
     * @param condition
     * @return
     */
    Page<InviteInfoEntity> queryPageByJoin(Page<InviteInfoEntity> page, InviteInfoCondition condition);
}
