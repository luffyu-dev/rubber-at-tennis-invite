package com.rubber.at.tennis.invite.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.rubber.at.tennis.invite.api.InviteInfoApplyApi;
import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;
import com.rubber.at.tennis.invite.api.enums.InviteInfoStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IInviteInfoDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.service.common.exception.ErrorCodeEnums;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.at.tennis.invite.service.component.InviteApplyComponent;
import com.rubber.at.tennis.invite.service.component.InviteQueryComponent;
import com.rubber.at.tennis.invite.service.model.InviteCostInfo;
import com.rubber.base.components.util.result.code.SysCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @author luffyu
 * Created on 2022/8/29
 */
@Slf4j
@Service("inviteInfoApplyApi")
public class InviteInfoApplyService implements InviteInfoApplyApi {


    @Autowired
    private InviteQueryComponent inviteQueryComponent;

    @Autowired
    private InviteApplyComponent inviteApplyComponent;

    @Autowired
    private IInviteInfoDal iInviteInfoDal;



    private static final String PREFIX = "IC";

    /**
     * 新增一个邀请
     *
     * @param dto 当前的请求
     * @return 返回邀请信息
     */
    @Override
    @Transactional(
            rollbackFor = Exception.class
    )
    public InviteCodeResponse addInviteInfo(InviteInfoDto dto) {
        // 数据校验
        doCheckDto(dto);
        // 数据初始化
        InviteInfoEntity infoEntity = new InviteInfoEntity();
        initConvert(infoEntity,dto);
        infoEntity.setInviteCode(creatInviteId());
        infoEntity.setStatus(InviteInfoStateEnums.INIT.getState());
        infoEntity.setJoinNumber(0);
        // 保存数据
        if(!inviteApplyComponent.saveInvite(infoEntity)){
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }
        return new InviteCodeResponse(infoEntity.getInviteCode());
    }

    /**
     * 修改一个邀请
     *
     * @param dto 当前的请求
     * @return 返回邀请信息
     */
    @Override
    @Transactional(
            rollbackFor = Exception.class
    )
    public InviteCodeResponse editInviteInfo(InviteInfoDto dto) {
        InviteInfoEntity infoDB = inviteQueryComponent.getBySponsor(dto.getInviteCode(), dto.getUid());
        if (dto.getInviteNumber() < infoDB.getJoinNumber()){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_NUMBER_CHANGE_ERROR);
        }
        // 数据转换
        initConvert(infoDB,dto);
        // 保存数据
        if(!inviteApplyComponent.updateInvite(infoDB)){
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }
        return new InviteCodeResponse(infoDB.getInviteCode());
    }

    /**
     * 发布邀请
     *
     * @param dto
     */
    @Override
    public InviteCodeResponse published(InviteInfoCodeReq dto) {
        InviteInfoEntity infoEntity = inviteQueryComponent.getBySponsor(dto.getInviteCode(), dto.getUid());
        // 数据转换
        if (!InviteInfoStateEnums.isForPublish(infoEntity.getStatus())){
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        return new InviteCodeResponse(infoEntity.getInviteCode());
    }

    /**
     * 关闭邀请
     *
     * @param dto
     */
    @Override
    public InviteCodeResponse closeInvite(InviteInfoCodeReq dto) {
        InviteInfoEntity infoEntity = inviteQueryComponent.getBySponsor(dto.getInviteCode(), dto.getUid());
        infoEntity.setStatus(InviteInfoStateEnums.CLOSE.getState());
        infoEntity.setUpdateTime(new Date());
        if(!iInviteInfoDal.updateById(infoEntity)){
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }
        return new InviteCodeResponse(dto.getInviteCode());
    }


    /**
     * 数据逻辑校验
     */
    private void doCheckDto(InviteInfoDto dto){
        if (dto.getInviteNumber() <= 0){
            log.error("邀请人数量至少有1人");
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
    }


    /**
     * 编辑时候的 初始化并转换
     */
    private void initConvert(InviteInfoEntity oldDbEntity,InviteInfoDto dto){
        BeanUtils.copyProperties(dto,oldDbEntity,"status");
        InviteCostInfo costInfo = new InviteCostInfo();
        costInfo.setCostType(dto.getCostType());
        costInfo.setPeopleCost(dto.getPeopleCost());
        oldDbEntity.setCostInfo(JSON.toJSONString(costInfo));
    }



    /**
     * 创建一个邀请id
     */
    private String creatInviteId(){
        return PREFIX + IdUtil.nanoId(16);
    }
}
