package com.rubber.at.tennis.invite.service;

import cn.hutool.core.date.DateUtil;
import com.rubber.at.tennis.invite.api.InviteInfoJoinApi;
import com.rubber.at.tennis.invite.api.UserTennisApi;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteJoinReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteCodeResponse;
import com.rubber.at.tennis.invite.api.enums.InviteInfoStateEnums;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.api.enums.RecordTypeEnums;
import com.rubber.at.tennis.invite.service.common.exception.ErrorCodeEnums;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.at.tennis.invite.service.component.InviteJoinComponent;
import com.rubber.at.tennis.invite.service.component.InviteQueryComponent;
import com.rubber.at.tennis.invite.service.model.InviteJoinModel;
import com.rubber.at.tennis.invite.api.dto.RecordTennisModel;
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
 * Created on 2022/9/3
 */
@Slf4j
@Service
public class InviteInfoJoinService implements InviteInfoJoinApi {

    @Autowired
    private InviteQueryComponent inviteQueryComponent;


    @Autowired
    private InviteJoinComponent inviteJoinComponent;

    @Autowired
    private UserTennisApi userTennisApi;

    /**
     * 邀约锁
     */
    private static final Map<String,ReentrantLock>  INVITE_LOCK = new ConcurrentHashMap<>();

    /**
     * 新增一个邀请
     *
     * @param req 当前的请求
     * @return 返回邀请信息
     */
    @Override
    public InviteCodeResponse join(InviteInfoCodeReq req) {
        ReentrantLock joinLock = buildInviteLock(req.getInviteCode());
        try {
            if(!joinLock.tryLock(500, TimeUnit.MILLISECONDS)){
                log.error("获取锁失败,req={}",req);
                return new InviteCodeResponse(req.getInviteCode());
            }
            // 获取邀请详情
            InviteInfoEntity infoEntity = inviteQueryComponent.getAndCheck(req.getInviteCode());
            // 校验是否已经完成
            this.doCheckInviteInfo(infoEntity);

            // 数据邀请对象
            InviteJoinModel joinModel = new InviteJoinModel(req,infoEntity);
            inviteJoinComponent.joinInvite(joinModel);

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
     * 参与人取消报名
     *
     * @param req
     * @return
     */
    @Override
    public InviteCodeResponse cancelJoin(InviteJoinReq req) {
        ReentrantLock joinLock = buildInviteLock(req.getInviteCode());
        try {
            if(!joinLock.tryLock(500, TimeUnit.MILLISECONDS)){
                log.error("获取锁失败,req={}",req);
                return new InviteCodeResponse(req.getInviteCode());
            }
            if (Integer.valueOf(1).equals(req.getCancelType())) {
                InviteInfoEntity userInvite = inviteQueryComponent.getBySponsor(req.getInviteCode(), req.getUid());
                // 校验是否可以编辑
//                if (InviteInfoStateEnums.CLOSE.getState().equals(userInvite.getStatus())
//                        || InviteInfoStateEnums.EXPIRED.getState().equals(userInvite.getStatus())) {
//                    throw new RubberServiceException(ErrorCodeEnums.INVITE_CLOSE);
//                }
                inviteJoinComponent.cancelJoinInvite(req.getJoinUid(), userInvite);
                return new InviteCodeResponse(req.getInviteCode());
            } else {
                // 取消自己参与的活动
                // 校验活动是否存在
                InviteInfoEntity inviteInfoEntity = inviteQueryComponent.getAndCheck(req.getInviteCode());
                // 校验是否可以编辑
                if (InviteInfoStateEnums.CLOSE.getState().equals(inviteInfoEntity.getStatus())
                        || InviteInfoStateEnums.EXPIRED.getState().equals(inviteInfoEntity.getStatus())) {
                    throw new RubberServiceException(ErrorCodeEnums.INVITE_CLOSE);
                }
                inviteJoinComponent.cancelJoinInvite(req.getUid(), inviteInfoEntity);

                // 取消关联的网球记录信息
                userTennisApi.cancelTennisRecord(req, inviteInfoEntity.getInviteCode());
                return new InviteCodeResponse(req.getInviteCode());
            }
        }catch (Exception e){
            log.error("参与邀约出现异常{}",e.getMessage());
            return new InviteCodeResponse(req.getInviteCode());
        }finally {
            joinLock.unlock();
        }
    }


    /**
     * 校验邀请事件是否可参与
     * @param infoEntity 当前的邀请对象
     */
    private void doCheckInviteInfo(InviteInfoEntity infoEntity){
        // 校验是否已经完成
        if (InviteInfoStateEnums.CLOSE.getState().equals(infoEntity.getStatus())){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_CLOSE);
        }
        // 校验是否已经完成
        if (InviteInfoStateEnums.FINISHED.getState().equals(infoEntity.getStatus())){
            throw new RubberServiceException(ErrorCodeEnums.INVITE_FINISHED);
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
    private void doHandlerTennisRecord(BaseUserSession userSession,InviteInfoEntity infoEntity){
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


    /**
     * 创建邀约锁
     * @param code
     * @return
     */
    private ReentrantLock buildInviteLock(String code){
        return INVITE_LOCK.getOrDefault(code, new ReentrantLock());
    }

}





