package com.rubber.at.tennis.invite.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.at.tennis.invite.api.ActivityInviteQueryApi;
import com.rubber.at.tennis.invite.api.dto.*;
import com.rubber.at.tennis.invite.api.dto.req.ActivityInviteQueryReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.enums.ActivityInviteJoinStateEnums;
import com.rubber.at.tennis.invite.api.enums.ActivityInviteStateEnums;
import com.rubber.at.tennis.invite.api.enums.InviteJoinStateEnums;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.service.component.ActivityInviteQueryComponent;
import com.rubber.base.components.util.LbsUtils;
import com.rubber.base.components.util.result.page.ResultPage;
import com.rubber.base.components.util.session.BaseLbsUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2023/8/2
 */
@Service("activityInviteQueryApi")
public class ActivityInviteQueryService implements ActivityInviteQueryApi {

    @Autowired
    private ActivityInviteQueryComponent activityInviteQueryComponent;




    /**
     * 查询单个活动详情接口
     *
     * @param req
     */
    @Override
    public ActivityInviteDetailDto getDetailInfo(InviteInfoCodeReq req) {
        // 查询活动基本信息
        ActivityInviteInfoEntity activityInviteInfoEntity = activityInviteQueryComponent.getByCode(req);
        // 查询拓展字段
        JSONObject inviteConfigField = activityInviteQueryComponent.getInviteConfigField(req);
        // 查询参与人
        List<InviteJoinUserDto>  joinUserDtos = activityInviteQueryComponent.queryJoinedUser(req);
        // 对象组装
        ActivityInviteDetailDto detailDto = new ActivityInviteDetailDto();
        BeanUtil.copyProperties(activityInviteInfoEntity,detailDto);
        detailDto.setConfigField(inviteConfigField);
        detailDto.setJoinedUserList(joinUserDtos);
        // 处理状态
        handlerDesc(detailDto);
        // 计算地理位置
        handlerLbs(detailDto,req);
        // 计算时间
        handlerInviteTimeDesc(detailDto);
        return detailDto;
    }

    /**
     * 查询单个活动的邀请详情
     *
     * @param req
     */
    @Override
    public List<InviteJoinUserDto>  getInviteJoinList(InviteInfoCodeReq req) {
        // 校验活动
        activityInviteQueryComponent.getByCode(req);
        // 查询参与人
        return activityInviteQueryComponent.queryJoinedUser(req);
    }

    /**
     * 查询推荐的活动信息
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryRecommendPage(ActivityInviteQueryReq req) {
        req.setStatus(ActivityInviteStateEnums.PUBLISHED.getState());
        IPage<ActivityInviteInfoEntity> page = activityInviteQueryComponent.queryPageInvite(req,false);
        ResultPage<ActivityInviteInfoDto> dtoResultPage = new ResultPage<>();
        dtoResultPage.setCurrent(page.getCurrent());
        dtoResultPage.setPages(page.getPages());
        dtoResultPage.setSize(page.getSize());
        dtoResultPage.setTotal(page.getTotal());
        if (CollUtil.isNotEmpty(page.getRecords())){
            dtoResultPage.setRecords(
                    page.getRecords().stream().map(i->{
                        ActivityInviteInfoDto dto = new ActivityInviteInfoDto();
                        BeanUtil.copyProperties(i,dto);
                        // 处理状态
                        handlerDesc(dto);
                        // 计算地理位置
                        handlerLbs(dto,req);
                        // 计算时间
                        handlerInviteTimeDesc(dto);
                        return dto;
                    }).collect(Collectors.toList())
            );
        }
        return dtoResultPage;
    }

    /**
     * 查询加入的活动
     *
     * @param req
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryJoinPage(ActivityInviteQueryReq req) {
        // TODO: 2023/8/7 需要修改
        IPage<ActivityInviteInfoEntity> page = activityInviteQueryComponent.queryJoinPageInvite(req,false);
        ResultPage<ActivityInviteInfoDto> dtoResultPage = new ResultPage<>();
        dtoResultPage.setCurrent(page.getCurrent());
        dtoResultPage.setPages(page.getPages());
        dtoResultPage.setSize(page.getSize());
        dtoResultPage.setTotal(page.getTotal());
        if (CollUtil.isNotEmpty(page.getRecords())){
            dtoResultPage.setRecords(
                    page.getRecords().stream().map(i->{
                        ActivityInviteInfoDto dto = new ActivityInviteInfoDto();
                        BeanUtil.copyProperties(i,dto);
                        // 处理状态
                        handlerDesc(dto);
                        // 计算地理位置
                        handlerLbs(dto,req);
                        // 计算时间
                        handlerInviteTimeDesc(dto);
                        return dto;
                    }).collect(Collectors.toList())
            );
        }
        return dtoResultPage;
    }

    /**
     * 查询用户自己的创建的活动
     *
     * @param req
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryUserInvite(ActivityInviteQueryReq req) {
        IPage<ActivityInviteInfoEntity> page = activityInviteQueryComponent.queryPageInvite(req,true);
        ResultPage<ActivityInviteInfoDto> dtoResultPage = new ResultPage<>();
        dtoResultPage.setCurrent(page.getCurrent());
        dtoResultPage.setPages(page.getPages());
        dtoResultPage.setSize(page.getSize());
        dtoResultPage.setTotal(page.getTotal());
        if (CollUtil.isNotEmpty(page.getRecords())){
            dtoResultPage.setRecords(
                    page.getRecords().stream().map(i->{
                        ActivityInviteInfoDto dto = new ActivityInviteInfoDto();
                        BeanUtil.copyProperties(i,dto);
                        // 处理状态
                        handlerDesc(dto);
                        // 计算地理位置
                        handlerLbs(dto,req);
                        // 计算时间
                        handlerInviteTimeDesc(dto);
                        return dto;
                    }).collect(Collectors.toList())
            );
        }
        return dtoResultPage;
    }


    /**
     * 处理描述文案信息
     */
    private void handlerDesc(ActivityInviteInfoDto activityInviteInfoDto){
        ActivityInviteStateEnums inviteStateEnums = ActivityInviteStateEnums.getState(activityInviteInfoDto.getStatus());
        if (inviteStateEnums == null){
            activityInviteInfoDto.setInviteStatusDesc("已结束");
            return;
        }
        switch (inviteStateEnums){
            case INIT:
            case CLOSE:
                activityInviteInfoDto.setInviteStatusDesc(inviteStateEnums.getDesc());
                break;
            case PUBLISHED:
                ActivityInviteJoinStateEnums joinStateEnums = ActivityInviteJoinStateEnums.getState(activityInviteInfoDto.getJoinStatus());
                if (joinStateEnums == null){
                    joinStateEnums = ActivityInviteJoinStateEnums.INVITING;
                }
                activityInviteInfoDto.setInviteStatusDesc(joinStateEnums.getDesc());
                if (ActivityInviteJoinStateEnums.INVITING.equals(joinStateEnums)){
                    long now = System.currentTimeMillis();
                    // 已结束
                    if (activityInviteInfoDto.getEndTime() != null && activityInviteInfoDto.getEndTime().getTime() > now){
                        activityInviteInfoDto.setInviteStatusDesc(ActivityInviteJoinStateEnums.END.getDesc());
                        return;
                    }
                    // 已满额
                    if (activityInviteInfoDto.getJoinNumber() >= activityInviteInfoDto.getInviteNumber()){
                        activityInviteInfoDto.setInviteStatusDesc(ActivityInviteJoinStateEnums.FINISHED.getDesc());
                        return;
                    }
                    // 报名截止
                    if (activityInviteInfoDto.getJoinDeadline() != null && now > activityInviteInfoDto.getJoinDeadline().getTime()){
                        activityInviteInfoDto.setInviteStatusDesc(ActivityInviteJoinStateEnums.TIME_LINE.getDesc());
                        return;
                    }
                }
            default:
        }
    }


    private void handlerInviteTimeDesc(ActivityInviteInfoDto activityInviteInfoDto){
        if (activityInviteInfoDto.getStartTime() != null && activityInviteInfoDto.getEndTime() != null ){

            String beginTime = DateUtil.format(activityInviteInfoDto.getStartTime(),"MM-YY hh:mm");
            String endTime = DateUtil.format(activityInviteInfoDto.getEndTime(),"hh:mm");
            // 相差的天数
            long l = DateUtil.betweenDay(activityInviteInfoDto.getStartTime(), activityInviteInfoDto.getEndTime(),true);
            String timeDesc = beginTime + "-"+ endTime;
            if (l > 0){
                timeDesc += "[+"+ l +"]";
            }
            activityInviteInfoDto.setInviteTimeDesc(timeDesc);

            String inviteTimeWeekDesc = "";
            // 当前日期的天数
            long x = DateUtil.betweenDay(new Date(), activityInviteInfoDto.getStartTime(),true);
            if (x  >= 0 ){
                switch ((int) x){
                    case 0:
                        inviteTimeWeekDesc ="今天";
                        break;
                    case 1:
                        inviteTimeWeekDesc ="明天";
                        break;
                    case 2:
                        inviteTimeWeekDesc ="后天";
                        break;
                    default:
                        int week = DateUtil.dayOfWeek(activityInviteInfoDto.getStartTime());
                        switch (week){
                            case 1:
                                inviteTimeWeekDesc ="周日";
                                break;
                            case 2:
                                inviteTimeWeekDesc ="周一";
                                break;
                            case 3:
                                inviteTimeWeekDesc ="周二";
                                break;
                            case 4:
                                inviteTimeWeekDesc ="周三";
                                break;
                            case 5:
                                inviteTimeWeekDesc ="周四";
                                break;
                            case 6:
                                inviteTimeWeekDesc ="周五";
                                break;
                            case 7:
                                inviteTimeWeekDesc ="周六";
                                break;
                            default:
                        }

                }
            }
            activityInviteInfoDto.setInviteTimeWeekDesc(inviteTimeWeekDesc);
        }
    }

    /**
     * 处理lbs的位置距离信息
     */
    private void handlerLbs(ActivityInviteInfoDto activityInviteInfoDto,BaseLbsUserSession userLbs){
        if (StrUtil.isEmpty(userLbs.getLatitude()) || StrUtil.isEmpty(userLbs.getLongitude()) ||
                StrUtil.isEmpty(activityInviteInfoDto.getLatitude()) || StrUtil.isEmpty(activityInviteInfoDto.getLongitude())){
            return;
        }
        double meter1 = LbsUtils.getDistance(userLbs.getLatitude(),userLbs.getLongitude(),activityInviteInfoDto.getLatitude(),activityInviteInfoDto.getLongitude());
        activityInviteInfoDto.setLbsDistance((int)meter1);
    }
}
