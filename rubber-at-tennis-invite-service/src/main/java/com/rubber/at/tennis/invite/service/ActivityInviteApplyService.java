package com.rubber.at.tennis.invite.service;

import cn.hutool.core.date.DateUtil;
import com.rubber.at.tennis.invite.api.ActivityInviteApplyApi;
import com.rubber.at.tennis.invite.api.UserTennisApi;
import com.rubber.at.tennis.invite.api.dto.ApplyInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.RecordTennisModel;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;
import com.rubber.at.tennis.invite.api.enums.ActivityInviteStateEnums;
import com.rubber.at.tennis.invite.api.enums.RecordTypeEnums;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.service.common.exception.ErrorCodeEnums;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.at.tennis.invite.service.component.ActivityInviteApplyComponent;
import com.rubber.at.tennis.invite.service.component.ActivityInviteQueryComponent;
import com.rubber.at.tennis.invite.service.component.InviteUserJoinComponent;
import com.rubber.at.tennis.invite.service.model.InviteJoinModel;
import com.rubber.base.components.util.result.code.SysCode;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author luffyu
 * Created on 2023/8/4
 */
@Slf4j
@Service("activityInviteApplyApi")
public class ActivityInviteApplyService  implements ActivityInviteApplyApi {

    @Autowired
    private ActivityInviteApplyComponent activityInviteApplyComponent;

    @Autowired
    private ActivityInviteQueryComponent inviteQueryComponent;

    @Autowired
    private InviteUserJoinComponent inviteUserJoinComponent;

    @Autowired
    private UserTennisApi userTennisApi;

    /**
     * 邀约锁
     */
    private static final Map<String,ReentrantLock> INVITE_LOCK = new ConcurrentHashMap<>();

    /**
     * 保存活动邀约信息
     *
     * @param dto
     */
    @Override
    public InviteCodeResponse saveActivityInviteInfo(ApplyInviteInfoDto dto) {
        // 数据校验和转换
        doCheckDto(dto);
        // 保存数据
        ActivityInviteInfoEntity entity = activityInviteApplyComponent.saveInvite(dto);

        return new InviteCodeResponse(entity.getInviteCode());
    }

    /**
     * 发布活动
     *
     * @param req
     */
    @Override
    public InviteCodeResponse publishInvite(InviteInfoCodeReq req) {
        ActivityInviteInfoEntity inviteInfoEntity = activityInviteApplyComponent.verifyAndGetManagerInvite(req);
        inviteInfoEntity.setStatus(ActivityInviteStateEnums.PUBLISHED.getState());
        activityInviteApplyComponent.updateInviteByCode(inviteInfoEntity,req);
        return new InviteCodeResponse(inviteInfoEntity.getInviteCode());
    }

    /**
     * 取消活动
     *
     * @param req
     */
    @Override
    public InviteCodeResponse closeInvite(InviteInfoCodeReq req) {
        ActivityInviteInfoEntity inviteInfoEntity = activityInviteApplyComponent.verifyAndGetManagerInvite(req);
        inviteInfoEntity.setStatus(ActivityInviteStateEnums.CLOSE.getState());
        activityInviteApplyComponent.updateInviteByCode(inviteInfoEntity,req);
        return new InviteCodeResponse(inviteInfoEntity.getInviteCode());
    }

    /**
     * 加入活动
     * 带多个人参加
     *
     * @param req
     */
    @Override
    public InviteCodeResponse joinInvite(InviteInfoCodeReq req) {
        ReentrantLock joinLock = buildInviteLock(req.getInviteCode());

        try {
            if(!joinLock.tryLock(500, TimeUnit.MILLISECONDS)){
                log.error("获取锁失败,req={}",req);
                return new InviteCodeResponse(req.getInviteCode());
            }
            // 获取邀请详情
            ActivityInviteInfoEntity infoEntity = inviteQueryComponent.getByCode(req);
            // 校验是否已经完成
            this.doCheckInviteInfo(infoEntity);
            // 数据邀请对象
            InviteJoinModel joinModel = new InviteJoinModel(req,infoEntity);
            inviteUserJoinComponent.joinInvite(joinModel);

            // 对接写入操作日期
            doHandlerTennisRecord(req,infoEntity);

            // 返回
            return new InviteCodeResponse(req.getInviteCode());
        }catch (Exception e){
            log.error("参与邀约出现异常{}",e.getMessage());
            return new InviteCodeResponse(req.getInviteCode());
        }finally {
            joinLock.unlock();
        }
    }

    /**
     * 取消加入活动
     *
     * @param req
     */
    @Override
    public InviteCodeResponse cancelJoinInvite(InviteInfoCodeReq req) {
        ReentrantLock joinLock = buildInviteLock(req.getInviteCode());
        try {
            if(!joinLock.tryLock(500, TimeUnit.MILLISECONDS)){
                log.error("获取锁失败,req={}",req);
                return new InviteCodeResponse(req.getInviteCode());
            }
            // 取消自己参与的活动
            // 校验活动是否存在
            ActivityInviteInfoEntity infoEntity = inviteQueryComponent.getByCode(req);
            // 校验是否可以编辑
            if (ActivityInviteStateEnums.CLOSE.getState().equals(infoEntity.getStatus())) {
                throw new RubberServiceException(ErrorCodeEnums.INVITE_CLOSE);
            }
            inviteUserJoinComponent.cancelJoinInvite(req.getUid(), infoEntity);
            // 取消关联的网球记录信息
            userTennisApi.cancelTennisRecord(req, infoEntity.getInviteCode());

            return new InviteCodeResponse(req.getInviteCode());

        }catch (Exception e){
            log.error("参与邀约出现异常{}",e.getMessage());
            return new InviteCodeResponse(req.getInviteCode());
        }finally {
            joinLock.unlock();
        }
    }


    /**
     * 数据逻辑校验
     */
    private void doCheckDto(ApplyInviteInfoDto dto){
        if (dto.getInviteNumber() <= 0){
            log.error("邀请人数量至少有1人");
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        if (dto.getStartTime() == null || dto.getEndTime() == null || DateUtil.compare(dto.getStartTime(),dto.getEndTime()) > 0){
            log.error("请设置正常的开始结束时间");
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }

    }



    /**
     * 创建邀约锁
     * @param code
     * @return
     */
    private ReentrantLock buildInviteLock(String code){
        return INVITE_LOCK.getOrDefault(code, new ReentrantLock());
    }



    /**
     * 校验邀请事件是否可参与
     * @param infoEntity 当前的邀请对象
     */
    private void doCheckInviteInfo(ActivityInviteInfoEntity infoEntity){
        // 校验是否已经完成
        if (!ActivityInviteStateEnums.PUBLISHED.getState().equals(infoEntity.getStatus())){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_CLOSE);
        }
        if (infoEntity.getJoinNumber() >= infoEntity.getInviteNumber()){
            // 邀请人员已完成
            throw new RubberServiceException(ErrorCodeEnums.USER_IS_FULL);
        }
        Date now = new Date();
        if (infoEntity.getJoinDeadline() != null && now.getTime() > infoEntity.getJoinDeadline().getTime()){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_TIME_JOIN_DEADLINE);
        }

    }



    /**
     * 写入tennis的记录
     */
    private void doHandlerTennisRecord(BaseUserSession userSession, ActivityInviteInfoEntity infoEntity){
        RecordTennisModel recordTennisModel = new RecordTennisModel();
        recordTennisModel.setRecordTitle(infoEntity.getInviteTitle());
        recordTennisModel.setRecordType(RecordTypeEnums.INVITE);
        recordTennisModel.setUserSession(userSession);
        recordTennisModel.setBizId(infoEntity.getInviteCode());
        if (infoEntity.getStartTime() != null && infoEntity.getEndTime() != null){
            recordTennisModel.setRecordDate(DateUtil.format(infoEntity.getStartTime(),"yyyy/MM/dd"));
            recordTennisModel.setRecordStart(infoEntity.getStartTime());
            recordTennisModel.setRecordEnd(infoEntity.getEndTime());
            Long time  = (infoEntity.getEndTime().getTime() - infoEntity.getStartTime().getTime()) / 1000 / 60;
            recordTennisModel.setRecordDuration(time < 0 ? 60 : time.intValue());
        }else {
            recordTennisModel.setRecordDate(DateUtil.format(infoEntity.getJoinDeadline(),"yyyy/MM/dd"));
            recordTennisModel.setRecordDuration(60);
        }
        userTennisApi.recordTennis(recordTennisModel);
    }


}
