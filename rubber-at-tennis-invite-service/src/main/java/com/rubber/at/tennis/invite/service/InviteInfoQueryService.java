package com.rubber.at.tennis.invite.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rubber.at.tennis.invite.api.InviteInfoQueryApi;
import com.rubber.at.tennis.invite.api.dto.InviteUserDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.dto.InviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InvitePageReq;
import com.rubber.at.tennis.invite.api.dto.response.InviteInfoResponse;
import com.rubber.at.tennis.invite.api.enums.InviteInfoStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IInviteInfoDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserDal;
import com.rubber.at.tennis.invite.dao.entity.InviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteUserEntity;
import com.rubber.at.tennis.invite.service.component.InviteQueryComponent;
import com.rubber.base.components.util.result.page.ResultPage;
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
    private IInviteUserDal iInviteUserDal;

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
        Page<InviteInfoEntity> page = new Page<>();
        page.setSize(req.getSize());
        page.setCurrent(req.getPage());

        LambdaQueryWrapper<InviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(InviteInfoEntity::getUid,req.getUid());
        iInviteInfoDal.page(page,lqw);

        return convertDto(page);
    }




    /**
     * 对象转换
     */
    private InviteInfoDto convertToDto(InviteInfoEntity entity){
        InviteInfoDto dto = new InviteInfoDto();
        BeanUtils.copyProperties(entity,dto);
        if (dto.getStartTime() != null && dto.getEndTime() != null){
            String starStr = DateUtil.format(dto.getStartTime(),"yyyy/MM/dd HH:mm");
            String endStr = DateUtil.format(dto.getEndTime(),"HH:mm");
            dto.setStartEndTimeDesc(starStr + "-" + endStr);
        }

        InviteInfoStateEnums stateEnums = InviteInfoStateEnums.getState(entity.getStatus());
        if (entity.getJoinNumber() >= entity.getInviteNumber()){
            stateEnums = InviteInfoStateEnums.FINISHED;
        }else if (entity.getJoinDeadline() != null && entity.getJoinDeadline().getTime() <= (new Date()).getTime()){
            stateEnums = InviteInfoStateEnums.EXPIRED;
        }
        if (stateEnums != null){
            dto.setStatus(stateEnums.getState());
            dto.setStatusDesc(stateEnums.getDesc());
        }
        return dto;
    }


    private ResultPage<InviteInfoDto> convertDto(Page<InviteInfoEntity> page){
        ResultPage<InviteInfoDto> dtoResultPage = new ResultPage<>();
        dtoResultPage.setCurrent(page.getCurrent());
        dtoResultPage.setPages(page.getPages());
        dtoResultPage.setSize(page.getSize());
        dtoResultPage.setTotal(page.getTotal());
        if (CollUtil.isNotEmpty(page.getRecords())){
            dtoResultPage.setRecords(
                    page.getRecords().stream().map(i->{
                        InviteInfoDto dto = new InviteInfoDto();
                        BeanUtils.copyProperties(i,dto);
                        return dto;
                    }).collect(Collectors.toList())
            );
        }
        return dtoResultPage;
    }
}
