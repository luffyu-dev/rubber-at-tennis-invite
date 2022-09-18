package com.rubber.at.tennis.invite.service.component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.dao.dal.IInviteInfoDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.base.components.util.result.code.SysCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@Slf4j
@Component
public class InviteQueryComponent {


    @Resource
    private IInviteInfoDal iInviteInfoDal;

    @Resource
    private IInviteUserDal iInviteUserDal;




    /**
     * 获取邀请对象信息
     */
    public InviteInfoEntity getAndCheck(String code){
        LambdaQueryWrapper<InviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteInfoEntity::getInviteCode,code);
        InviteInfoEntity entity = iInviteInfoDal.getOne(lqw);
        if (entity == null){
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        return entity;
    }



    /**
     * 获取邀请对象信息
     */
    public InviteInfoEntity getAndCheck(String code,Integer uid){
        LambdaQueryWrapper<InviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteInfoEntity::getInviteCode,code)
                .eq(InviteInfoEntity::getUid,uid);
        InviteInfoEntity entity = iInviteInfoDal.getOne(lqw);
        if (entity == null){
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        return entity;
    }


    /**
     * 获取用户的邀请数据
     */
    public InviteUserEntity getInviteJoinUser(String code,Integer uid){
        LambdaQueryWrapper<InviteUserEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteUserEntity::getInviteCode,code)
                .eq(InviteUserEntity::getJoinUid,uid);
        return iInviteUserDal.getOne(lqw);
    }

}
