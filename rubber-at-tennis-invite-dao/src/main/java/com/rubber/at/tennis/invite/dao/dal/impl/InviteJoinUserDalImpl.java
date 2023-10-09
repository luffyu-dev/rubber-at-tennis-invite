package com.rubber.at.tennis.invite.dao.dal.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.dao.entity.InviteJoinUserEntity;
import com.rubber.at.tennis.invite.dao.mapper.InviteJoinUserMapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteJoinUserDal;
import com.rubber.base.components.mysql.plugins.admin.BaseAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 邀约人详情表 服务实现类
 * </p>
 *
 * @author rockyu
 * @since 2023-08-02
 */
@Service
public class InviteJoinUserDalImpl extends BaseAdminService<InviteJoinUserMapper, InviteJoinUserEntity> implements IInviteJoinUserDal {



    /**
     * 通过code查询参与人信息
     *
     * @param code
     * @return
     */
    @Override
    public List<InviteJoinUserEntity> queryJoinByCode(String code, Integer state) {
        LambdaQueryWrapper<InviteJoinUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteJoinUserEntity::getInviteCode,code);
        if (state != null){
            lqw.eq(InviteJoinUserEntity::getStatus,state);
        }
        lqw.orderByAsc(InviteJoinUserEntity::getUpdateTime);
        return list(lqw);
    }

    /**
     * 通过code查询参与人信息
     *
     * @param code
     * @param state
     * @param joinUid
     * @return
     */
    @Override
    public List<InviteJoinUserEntity> querySelfFriendJoinByCode(String code, Integer state, Integer joinUid) {
        LambdaQueryWrapper<InviteJoinUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteJoinUserEntity::getInviteCode,code)
                .eq(InviteJoinUserEntity::getStatus,state)
                .eq(InviteJoinUserEntity::getJoinUid,joinUid)
                .orderByAsc(InviteJoinUserEntity::getUpdateTime);
        return list(lqw);
    }

    /**
     * 通过code + uid 查询一个是是否参与
     *
     * @param code
     * @param uid
     */
    @Override
    public InviteJoinUserEntity getInviteJoinUser(String code, Integer uid) {
        LambdaQueryWrapper<InviteJoinUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteJoinUserEntity::getInviteCode,code)
                .eq(InviteJoinUserEntity::getJoinUid,uid)
                .eq(InviteJoinUserEntity::getDataVersion,uid);
        return getOne(lqw);
    }

    /**
     * 通过code + uid 查询一个是是否参与
     *
     * @param code
     * @param uid
     * @param ids
     */
    @Override
    public List<InviteJoinUserEntity> getInviteJoinUserByIds(String code, Integer uid, List<Integer> ids) {
        LambdaQueryWrapper<InviteJoinUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteJoinUserEntity::getInviteCode,code)
                .in(InviteJoinUserEntity::getId,ids);
        if (uid != null ){
            lqw.eq(InviteJoinUserEntity::getJoinUid,uid);
        }
        return list(lqw);
    }

}
