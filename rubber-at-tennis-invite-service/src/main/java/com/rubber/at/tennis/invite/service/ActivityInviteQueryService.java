package com.rubber.at.tennis.invite.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.at.tennis.invite.api.ActivityInviteQueryApi;
import com.rubber.at.tennis.invite.api.dto.*;
import com.rubber.at.tennis.invite.api.dto.req.ActivityInviteQueryReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteTemplateQueryReq;
import com.rubber.at.tennis.invite.api.enums.ActivityInviteJoinStateEnums;
import com.rubber.at.tennis.invite.api.enums.ActivityInviteStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserBasicInfoDal;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.UserBasicInfoEntity;
import com.rubber.at.tennis.invite.service.component.ActivityInviteQueryComponent;
import com.rubber.base.components.util.LbsUtils;
import com.rubber.base.components.util.annotation.NeedLogin;
import com.rubber.base.components.util.result.page.ResultPage;
import com.rubber.base.components.util.session.BaseLbsUserSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2023/8/2
 */
@Service("activityInviteQueryApi")
public class ActivityInviteQueryService implements ActivityInviteQueryApi {

    @Autowired
    private ActivityInviteQueryComponent activityInviteQueryComponent;

    @Autowired
    private IInviteUserBasicInfoDal iInviteUserBasicInfoDal;



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
        // 处理用户基本信息
        handlerUserInfo(detailDto);
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
        if (StrUtil.isEmpty(req.getCity())){
            req.setProvince("广东省");
            req.setCity("深圳市");
        }
        req.setStatus(ActivityInviteStateEnums.PUBLISHED.getState());
        IPage<ActivityInviteInfoEntity> page = activityInviteQueryComponent.queryPageInvite(req,false);
        return convertPageDto(page,req);
    }

    /**
     * 查询加入的活动
     *
     * @param req
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryUserJoinPage(ActivityInviteQueryReq req) {
        // TODO: 2023/8/7 需要修改
        IPage<ActivityInviteInfoEntity> page = activityInviteQueryComponent.queryJoinPageInvite(req);
        return convertPageDto(page,req);
    }

    /**
     * 查询用户自己的创建的活动
     *
     * @param req
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryUserInvite(ActivityInviteQueryReq req) {
        IPage<ActivityInviteInfoEntity> page = activityInviteQueryComponent.queryPageInvite(req,true);
        return convertPageDto(page,req);
    }

    /**
     * 查询用户的模版
     *
     * @param req
     * @return
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryUserInviteTemplate(InviteTemplateQueryReq req) {
        if (req.getUid() == null){
            return new ResultPage<>();
        }
        IPage<ActivityInviteInfoEntity> page;
        if (req.getUserTemplate() == 1){
            page = activityInviteQueryComponent.queryJoinPageInvite(req);
        }else {
            page = activityInviteQueryComponent.queryPageInvite(req,true);
        }
        return convertPageDto(page,req);
    }

    /**
     * 查询官方的模版
     *
     * @param req
     * @return
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryOfficialTemplate(ActivityInviteQueryReq req) {
        req.setPage(1);
        req.setSize(3);
        IPage<ActivityInviteInfoEntity> page = activityInviteQueryComponent.queryOfficeTemplateInvite(req);
        return convertPageDto(page,req);
    }


    private  ResultPage<ActivityInviteInfoDto> convertPageDto(IPage<ActivityInviteInfoEntity> page,ActivityInviteQueryReq req){
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
     * 头像信息
     * @param detailDto
     */
    private void handlerUserInfo(ActivityInviteDetailDto detailDto){
        UserBasicInfoEntity userBasicInfoEntity = iInviteUserBasicInfoDal.getByUid(detailDto.getUid());
        if (userBasicInfoEntity != null){
            InviteSponsorUserDto dto = new InviteSponsorUserDto();
            BeanUtils.copyProperties(userBasicInfoEntity,dto);
            detailDto.setSponsorInfo(dto);
        }
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
                    if (activityInviteInfoDto.getEndTime() != null && activityInviteInfoDto.getEndTime().getTime() < now){
                        joinStateEnums = ActivityInviteJoinStateEnums.END;
                    }
                    // 已满额
                    else if (activityInviteInfoDto.getJoinNumber() >= activityInviteInfoDto.getInviteNumber()){
                        joinStateEnums = ActivityInviteJoinStateEnums.FINISHED;
                    }
                    // 报名截止
                    else if (activityInviteInfoDto.getJoinDeadline() != null && now > activityInviteInfoDto.getJoinDeadline().getTime()){
                        joinStateEnums = ActivityInviteJoinStateEnums.TIME_LINE;
                    }
                }
                activityInviteInfoDto.setInviteStatusDesc(joinStateEnums.getDesc());
                activityInviteInfoDto.setJoinStatus(joinStateEnums.getState());

            default:
        }
    }


    private void handlerInviteTimeDesc(ActivityInviteInfoDto activityInviteInfoDto){
        if (activityInviteInfoDto.getStartTime() != null && activityInviteInfoDto.getEndTime() != null ){

            Date now = new Date();

            String beginTime = DateUtil.format(activityInviteInfoDto.getStartTime(),"MM/dd HH:mm");
            String endTime  = DateUtil.format(activityInviteInfoDto.getEndTime(),"HH:mm");
            // 相差的天数
//            long l = DateUtil.between(activityInviteInfoDto.getStartTime(), activityInviteInfoDto.getEndTime(),DateUnit.MINUTE);
//            BigDecimal bigDecimal = new BigDecimal(l);
//            bigDecimal = bigDecimal.divide(new BigDecimal(60),1,RoundingMode.UP);
//            double v =bigDecimal.doubleValue();
            String timeDesc = beginTime + "-"+ endTime ;
            activityInviteInfoDto.setInviteTimeDesc(timeDesc);

            String inviteTimeWeekDesc = "";
            // 当前日期的天数
            long x = DateUtil.betweenDay(now, activityInviteInfoDto.getStartTime(),true);
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
            int hour = DateUtil.hour(activityInviteInfoDto.getStartTime(), true);
            if (hour < 9){
                inviteTimeWeekDesc += "·早上";
            }else if (hour < 11){
                inviteTimeWeekDesc += "·上午";
            }else if (hour < 13){
                inviteTimeWeekDesc += "·中午";
            }else if (hour < 18){
                inviteTimeWeekDesc += "·下午";
            }else {
                inviteTimeWeekDesc += "·晚上";
            }
            activityInviteInfoDto.setInviteTimeWeekDesc(inviteTimeWeekDesc);
        }
        if (activityInviteInfoDto.getJoinDeadline() != null){
            activityInviteInfoDto.setJoinDeadlineDesc( DateUtil.format(activityInviteInfoDto.getJoinDeadline(),"MM/dd HH:mm"));
        }
    }

    /**
     * 处理lbs的位置距离信息
     */
    private void handlerLbs(ActivityInviteInfoDto activityInviteInfoDto,BaseLbsUserSession userLbs){
        if (StrUtil.isEmpty(userLbs.getLatitude()) || StrUtil.isEmpty(userLbs.getLongitude()) ||
                StrUtil.isEmpty(activityInviteInfoDto.getCourtLatitude()) || StrUtil.isEmpty(activityInviteInfoDto.getCourtLongitude())){
            return;
        }
        double mater = LbsUtils.getDistance(userLbs.getLatitude(),userLbs.getLongitude(),activityInviteInfoDto.getCourtLatitude(),activityInviteInfoDto.getCourtLongitude());
        BigDecimal bigDecimal = new BigDecimal(mater);
        bigDecimal = bigDecimal.divide(new BigDecimal(1000),1,RoundingMode.DOWN);
        double v = bigDecimal.doubleValue();
        v = Math.max(v,0.1);
        activityInviteInfoDto.setLbsDistance(v);
    }
}
