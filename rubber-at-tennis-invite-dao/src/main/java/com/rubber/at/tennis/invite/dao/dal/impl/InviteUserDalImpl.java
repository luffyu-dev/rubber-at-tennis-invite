package com.rubber.at.tennis.invite.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteUserMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 邀约人详情表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@Service
public class InviteUserDalImpl extends BaseAdminService<InviteUserMapper, InviteUserEntity> implements IInviteUserDal {

    /**
     * 通过code查询参与人信息
     *
     * @param code
     * @return
     */
    @Override
    public List<InviteUserEntity> queryJoinByCode(String code,Integer state) {
        LambdaQueryWrapper<InviteUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteUserEntity::getInviteCode,code);
        if (state != null){
            lqw.eq(InviteUserEntity::getStatus,state);
        }
        return list(lqw);
    }

    /**
     * 通过code + uid 查询一个是是否参与
     *
     * @param code
     * @param uid
     */
    @Override
    public InviteUserEntity getInviteJoinUser(String code, Integer uid) {
        LambdaQueryWrapper<InviteUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteUserEntity::getInviteCode,code)
                .eq(InviteUserEntity::getJoinUid,uid);
        return getOne(lqw);
    }
}
