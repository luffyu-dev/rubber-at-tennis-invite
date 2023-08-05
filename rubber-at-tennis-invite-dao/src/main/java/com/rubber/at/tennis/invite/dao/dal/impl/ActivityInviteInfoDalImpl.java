package com.rubber.at.tennis.invite.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.dao.mapper.ActivityInviteInfoMapper;
import com.rubber.at.tennis.invite.dao.dal.IActivityInviteInfoDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 邀约表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Service
public class ActivityInviteInfoDalImpl extends BaseAdminService<ActivityInviteInfoMapper, ActivityInviteInfoEntity> implements IActivityInviteInfoDal {

    /**
     * @param code
     * @return
     */
    public ActivityInviteInfoEntity getByCode(String code) {
        LambdaQueryWrapper<ActivityInviteInfoEntity> lwq = new LambdaQueryWrapper<>();
        lwq.eq(ActivityInviteInfoEntity::getInviteCode,code);
        return getOne(lwq,true);
    }

    /**
     * @param code
     * @param managerUid
     * @return
     */
    @Override
    public ActivityInviteInfoEntity getByManagerCode(String code, Integer managerUid) {
        LambdaQueryWrapper<ActivityInviteInfoEntity> lwq = new LambdaQueryWrapper<>();
        lwq.eq(ActivityInviteInfoEntity::getInviteCode,code)
                .eq(ActivityInviteInfoEntity::getUid,managerUid);
        return getOne(lwq,true);
    }
}
