package com.rubber.at.tennis.invite.dao.dal;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.invite.dao.condition.InviteInfoCondition;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.base.components.mysql.plugins.admin.IBaseAdminService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 邀约表 服务类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
public interface IActivityInviteInfoDal extends IBaseAdminService<ActivityInviteInfoEntity> {



    ActivityInviteInfoEntity getByCode(String code);


    ActivityInviteInfoEntity getByManagerCode(String code, Integer managerUid);


    IPage<ActivityInviteInfoEntity> pageJoinInvite(IPage<ActivityInviteInfoEntity> page, @Param("condition") InviteInfoCondition condition);

}
