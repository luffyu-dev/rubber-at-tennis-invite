package com.rubber.at.tennis.invite.service.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.dao.dal.IInviteInfoDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@Slf4j
@Component
public class InviteApplyComponent {


    @Resource
    private IInviteInfoDal iInviteInfoDal;


    @Transactional(
            rollbackFor = Exception.class
    )
    public boolean saveInvite(InviteInfoEntity inviteInfoEntity){
        Date now = new Date();
        inviteInfoEntity.setCreateTime(now);
        inviteInfoEntity.setUpdateTime(now);
        inviteInfoEntity.setVersion(1);
        return iInviteInfoDal.save(inviteInfoEntity);
    }


    @Transactional(
            rollbackFor = Exception.class
    )
    public boolean updateInvite(InviteInfoEntity inviteInfoEntity){
        Date now = new Date();
        inviteInfoEntity.setUpdateTime(now);
        return iInviteInfoDal.updateById(inviteInfoEntity);
    }



    @Transactional(
            rollbackFor = Exception.class
    )
    public boolean updateInviteForJoin(InviteInfoEntity inviteInfoEntity,Integer oldJoinIndex,Integer newJoinIndex){
        Date now = new Date();
        inviteInfoEntity.setUpdateTime(now);
        inviteInfoEntity.setJoinNumber(newJoinIndex);
        LambdaQueryWrapper<InviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteInfoEntity::getId,inviteInfoEntity.getId())
                .eq(InviteInfoEntity::getJoinNumber,oldJoinIndex)
                .ge(InviteInfoEntity::getInviteNumber,newJoinIndex);
        return iInviteInfoDal.update(inviteInfoEntity,lqw);
    }


}
