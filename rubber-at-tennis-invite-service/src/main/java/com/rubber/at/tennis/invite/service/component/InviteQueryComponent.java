package com.rubber.at.tennis.invite.service.component;

import cn.hutool.core.collection.CollUtil;
import com.rubber.at.tennis.invite.api.dto.InviteUserDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.enums.InviteJoinStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IInviteInfoDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.at.tennis.invite.dao.dal.IUserBasicInfoDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.dao.entity.UserBasicInfoEntity;
import com.rubber.at.tennis.invite.service.common.exception.ErrorCodeEnums;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.base.components.util.result.code.SysCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private IUserBasicInfoDal iUserBasicInfoDal;



    /**
     * 获取邀请对象信息
     */
    public InviteInfoEntity getAndCheck(String code){
        InviteInfoEntity entity = iInviteInfoDal.getByCode(code);
        if (entity == null){
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        return entity;
    }


    /**
     * 通过发起人的基本信息查询
     */
    public InviteInfoEntity getBySponsor(String code,Integer uid){
        InviteInfoEntity entity = this.getAndCheck(code);
        if (!entity.getUid().equals(uid)){
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        return entity;
    }



    /**
     * 校验用户的参与资格
     * @param req
     */
    public void checkUserQualifications(InviteInfoCodeReq req){
        InviteUserEntity joinUser = iInviteUserDal.getInviteJoinUser(req.getInviteCode(), req.getUid());
        if (joinUser != null){
            throw new RubberServiceException(ErrorCodeEnums.USER_IS_JOINED);
        }
    }


    /**
     * 获取发起人信息
     */
    public InviteUserDto getSponsorInfo(InviteInfoEntity infoEntity){
        UserBasicInfoEntity entity = iUserBasicInfoDal.getByUid(infoEntity.getUid());
        return convertUserToDto(entity);
    }


    /**
     * 查询参与用户的详细信息
     */
    public List<InviteUserDto> queryByJoinUser(InviteInfoEntity infoEntity){
        List<InviteUserEntity> inviteUserEntities = iInviteUserDal.queryJoinByCode(infoEntity.getInviteCode(), InviteJoinStateEnums.SUCCESS.getState());
        if (CollUtil.isEmpty(inviteUserEntities)){
            return new ArrayList<>();
        }
        List<Integer> joinUserList = inviteUserEntities.stream().map(InviteUserEntity::getJoinUid).collect(Collectors.toList());
        List<UserBasicInfoEntity> userBasicInfoList = iUserBasicInfoDal.queryByUid(joinUserList);

        return userBasicInfoList.stream().map(this::convertUserToDto).collect(Collectors.toList());
    }



    /**
     * 对象转换
     */
    private InviteUserDto convertUserToDto(UserBasicInfoEntity entity){
        if (entity == null){
            return new InviteUserDto();
        }
        InviteUserDto dto = new InviteUserDto();
        BeanUtils.copyProperties(entity,dto);
        return dto;
    }
}
