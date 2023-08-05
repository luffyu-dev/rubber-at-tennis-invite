package com.rubber.at.tennis.invite.service.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.rubber.at.tennis.invite.api.dto.InviteJoinUserDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.enums.InviteJoinStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IActivityInviteInfoDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteConfigFieldDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteJoinUserDal;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteConfigFieldEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteJoinUserEntity;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.base.components.util.result.code.SysCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2023/8/5
 */
@Slf4j
@Component
public class ActivityInviteQueryComponent {


    @Resource
    private IActivityInviteInfoDal iActivityInviteInfoDal;


    @Resource
    private IInviteConfigFieldDal inviteConfigFieldDal;

    @Resource
    private IInviteJoinUserDal joinUserDal;


    public ActivityInviteInfoEntity getByCode(InviteInfoCodeReq req){
        ActivityInviteInfoEntity inviteInfoEntity = iActivityInviteInfoDal.getByCode(req.getInviteCode());
        if (inviteInfoEntity == null){
            log.error("邀约code不存在{}",req);
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        return inviteInfoEntity;
    }



    public JSONObject getInviteConfigField(InviteInfoCodeReq req){
        JSONObject config = new JSONObject();
        List<InviteConfigFieldEntity> inviteConfigFieldList = inviteConfigFieldDal.queryByCode(req.getInviteCode());
        if (CollUtil.isNotEmpty(inviteConfigFieldList)){
            for (InviteConfigFieldEntity configFieldEntity : inviteConfigFieldList) {
                config.put(configFieldEntity.getInviteField(),configFieldEntity.getInviteValue());
            }
        }
        return config;
    }


    /**
     * 查询参与的用户
     * @param req
     * @return
     */
    public List<InviteJoinUserDto> queryJoinedUser(InviteInfoCodeReq req){
        // 查询参与的用户
        List<InviteJoinUserEntity> inviteJoinUserEntities = joinUserDal.queryJoinByCode(req.getInviteCode(), InviteJoinStateEnums.SUCCESS.getState());
        if (CollUtil.isEmpty(inviteJoinUserEntities)){
            return new ArrayList<>();
        }
        return inviteJoinUserEntities.stream().map(i->{
            InviteJoinUserDto dto = new InviteJoinUserDto();
            BeanUtil.copyProperties(i,dto);
            return dto;
        }).collect(Collectors.toList());
    }


    /**
     * 查询用户创建的邀约活动
     */
    public void queryUserCreateList(){

    }


}
