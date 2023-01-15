package com.rubber.at.tennis.invite.api;

import com.rubber.at.tennis.invite.api.dto.RecordTennisModel;
import com.rubber.at.tennis.invite.api.dto.UserModifyTennisDto;
import com.rubber.at.tennis.invite.api.dto.UserTennisDetail;
import com.rubber.at.tennis.invite.api.dto.req.UserTennisDateReq;
import com.rubber.base.components.util.session.BaseUserSession;

import java.util.List;

/**
 * @author luffyu
 * Created on 2022/11/26
 */
public interface UserTennisApi {


    /**
     * 查询用户的网球相关信息
     * @param userSession 当前的用户的信息
     * @return 返回用户的详情
     */
    UserTennisDetail getUserTennisInfo(BaseUserSession userSession);


    /**
     * 修改用户的基本信息
     * @param dto 当前的用户session
     * @return 返回用户是否成功
     */
    void updateUserTennis(UserModifyTennisDto dto);


    /**
     * 查询用户的日期信息
     * @param req 当前的用户的信息
     * @return 返回用户的详情
     */
    List<String> queryAtTennisDate(UserTennisDateReq req);


    /**
     * 记录网球记录信息
     * @param model
     */
    void recordTennis(RecordTennisModel model);


    /**
     * 取消活动信息
     */
    void cancelTennisRecord(BaseUserSession userSession,String bizId);
}
