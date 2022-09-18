package com.rubber.at.tennis.invite.service;

import cn.hutool.core.util.IdUtil;
import com.rubber.at.tennis.invite.api.InviteInfoApplyApi;
import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;
import com.rubber.at.tennis.invite.api.enums.InviteInfoStateEnums;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.at.tennis.invite.service.component.InviteApplyComponent;
import com.rubber.at.tennis.invite.service.component.InviteQueryComponent;
import com.rubber.base.components.util.result.code.SysCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
        BeanUtils.copyProperties(dto,infoEntity);
        infoEntity.setInviteCode(creatInviteId());
        infoEntity.setStatus(InviteInfoStateEnums.INIT.getState());
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
        InviteInfoEntity infoEntity = inviteQueryComponent.getAndCheck(dto.getInviteCode(), dto.getUid());

        if (!InviteInfoStateEnums.isForUpdate(infoEntity.getStatus())){
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        // 数据转换
        initConvertForUpdate(infoEntity,dto);
        // 保存数据
        if(!inviteApplyComponent.updateInvite(infoEntity)){
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }
        return new InviteCodeResponse(infoEntity.getInviteCode());
    }

    /**
     * 发布邀请
     *
     * @param dto
     */
    @Override
    public InviteCodeResponse published(InviteInfoCodeReq dto) {
        InviteInfoEntity infoEntity = inviteQueryComponent.getAndCheck(dto.getInviteCode(), dto.getUid());
        // 数据转换
        if (!InviteInfoStateEnums.isForPublish(infoEntity.getStatus())){
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        return new InviteCodeResponse(infoEntity.getInviteCode());
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
    private void initConvertForUpdate(InviteInfoEntity oldDbEntity,InviteInfoDto dto){
        BeanUtils.copyProperties(dto,oldDbEntity,"status");
    }






    /**
     * 创建一个邀请id
     */
    private String creatInviteId(){
        return PREFIX + IdUtil.nanoId(16);
    }
}
