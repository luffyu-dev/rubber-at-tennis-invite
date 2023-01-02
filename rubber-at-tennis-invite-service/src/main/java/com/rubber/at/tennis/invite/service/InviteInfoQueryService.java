package com.rubber.at.tennis.invite.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.invite.api.InviteInfoQueryApi;
import com.rubber.at.tennis.invite.api.dto.InviteUserDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InvitePageReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteInfoResponse;
import com.rubber.at.tennis.invite.api.enums.InviteInfoStateEnums;
import com.rubber.at.tennis.invite.dao.condition.InviteInfoCondition;
import com.rubber.at.tennis.invite.dao.dal.IInviteInfoDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.service.component.InviteQueryComponent;
import com.rubber.at.tennis.invite.service.model.InviteCostInfo;
import com.rubber.base.components.mysql.utils.ReflectionUtils;
import com.rubber.base.components.util.LbsUtils;
import com.rubber.base.components.util.result.page.ResultPage;
import com.rubber.base.components.util.session.BaseLbsUserSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2022/9/1
 */
@Service("inviteInfoQueryApi")
public class InviteInfoQueryService implements InviteInfoQueryApi {

    @Autowired
    private InviteQueryComponent inviteQueryComponent;


    @Autowired
    private IInviteInfoDal iInviteInfoDal;


    /**
     * 获取一个邀请详情
     *
     * @param req 当前的请求如此
     * @return 返回一个邀请的dto
     */
    @Override
    public InviteInfoResponse getInviteInfo(InviteInfoCodeReq req) {
        // 查询邀请的基本信息
        InviteInfoEntity entity = inviteQueryComponent.getAndCheck(req.getInviteCode());
        InviteInfoResponse response = new InviteInfoResponse();
        BeanUtils.copyProperties(convertToDto(entity),response);
        // 填充发起人信息
        response.setSponsorInfo(inviteQueryComponent.getSponsorInfo(entity));
        // 填充用户信息
        response.setJoinUserList(inviteQueryComponent.queryByJoinUser(entity));
        // 填充距离
        response.setLbsDistance(handlerSetLbs(req,entity));
        return response;
    }

    /**
     * 查询当前用户的登录session
     *
     * @param req 当前的session请求
     * @return 返回是否登录
     */
    @Override
    public ResultPage<InviteInfoDto> listPage(InvitePageReq req) {
        if (req.getQueryUid() == null){
            req.setQueryUid(req.getUid());
        }
        Page<InviteInfoEntity> page;
        if (Integer.valueOf(1).equals(req.getSearchType())){
            page = queryUserJoin(req);
        }else {
            page = queryUserCreateInvite(req);
        }
        return convertPageDto(page);
    }


    /**
     * 查询自己创建的邀约活动
     * @param req 当前请求
     * @return 返回也没信息
     */
    private Page<InviteInfoEntity> queryUserCreateInvite(InvitePageReq req){
        Page<InviteInfoEntity> page = new Page<>();
        page.setSize(req.getSize());
        page.setCurrent(req.getPage());
        LambdaQueryWrapper<InviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteInfoEntity::getUid,req.getQueryUid());
        if (StrUtil.isNotEmpty(req.getSearchValue())){
            lqw.like(InviteInfoEntity::getInviteTitle,req.getSearchValue());
        }
        lqw.orderByDesc(InviteInfoEntity::getCreateTime);
        iInviteInfoDal.page(page,lqw);
        return page;
    }

    /**
     * 查询自己参与的
     * @param req
     * @return
     */
    private Page<InviteInfoEntity> queryUserJoin(InvitePageReq req){
        Page<InviteInfoEntity> page = new Page<>();
        page.setSize(req.getSize());
        page.setCurrent(req.getPage());

        InviteInfoCondition condition = new InviteInfoCondition();
        condition.setJoinUid(req.getUid());
        condition.setSearchValue(req.getSearchValue());
        iInviteInfoDal.queryPageByJoin(page,condition);
        return page;
    }

    /**
     * 对象分页转换
     * @param page
     * @return
     */
    private ResultPage<InviteInfoDto> convertPageDto(Page<InviteInfoEntity> page){
        ResultPage<InviteInfoDto> dtoResultPage = new ResultPage<>();
        dtoResultPage.setCurrent(page.getCurrent());
        dtoResultPage.setPages(page.getPages());
        dtoResultPage.setSize(page.getSize());
        dtoResultPage.setTotal(page.getTotal());
        if (CollUtil.isNotEmpty(page.getRecords())){
            dtoResultPage.setRecords(
                    page.getRecords().stream().map(this::convertToDto).collect(Collectors.toList())
            );
        }
        return dtoResultPage;
    }


    /**
     * 对象转换
     */
    private InviteInfoDto convertToDto(InviteInfoEntity entity){
        InviteInfoDto dto = new InviteInfoDto();
        BeanUtils.copyProperties(entity,dto);
        if (dto.getJoinNumber() == null){
            dto.setJoinNumber(0);
        }
        if (dto.getStartTime() != null && dto.getEndTime() != null){
            String starStr = DateUtil.format(dto.getStartTime(),"yyyy/MM/dd HH:mm");
            String endStr = DateUtil.format(dto.getEndTime(),"HH:mm");
            dto.setStartEndTimeDesc(starStr + "-" + endStr);
        }
        InviteInfoStateEnums stateEnums = InviteInfoStateEnums.getState(entity.getStatus());
        if (dto.getJoinNumber() >= dto.getInviteNumber()){
            stateEnums = InviteInfoStateEnums.FINISHED;
        }
        if (entity.getJoinDeadline() != null && entity.getJoinDeadline().getTime() <= (new Date()).getTime()){
            stateEnums = InviteInfoStateEnums.EXPIRED;
        }
        if (stateEnums != null){
            dto.setStatus(stateEnums.getState());
            dto.setStatusDesc(stateEnums.getDesc());
        }

        if (StrUtil.isNotEmpty(entity.getCostInfo())){
            InviteCostInfo  costInfo = JSON.parseObject(entity.getCostInfo(),InviteCostInfo.class);
            dto.setCostType(costInfo.getCostType());
            dto.setPeopleCost(costInfo.getPeopleCost());
        }
        return dto;
    }


    /**
     * 距离lbs的距离
     * @param lbsUserSession
     * @param entity
     * @return
     */
    private int handlerSetLbs(BaseLbsUserSession lbsUserSession,InviteInfoEntity entity){
        if (StrUtil.isNotEmpty(lbsUserSession.getLongitude())
                && StrUtil.isNotEmpty(lbsUserSession.getLatitude())
                && StrUtil.isNotEmpty(entity.getCourtLongitude())
                && StrUtil.isNotEmpty(entity.getCourtLatitude())) {
            return (int)LbsUtils.getDistance(lbsUserSession.getLatitude(),lbsUserSession.getLongitude(),
                    entity.getCourtLatitude(),entity.getCourtLongitude());
        }
        return 0;
    }



}
