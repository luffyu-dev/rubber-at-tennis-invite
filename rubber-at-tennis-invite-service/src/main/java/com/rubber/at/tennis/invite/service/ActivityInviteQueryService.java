package com.rubber.at.tennis.invite.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.db.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rubber.at.tennis.invite.api.ActivityInviteQueryApi;
import com.rubber.at.tennis.invite.api.dto.*;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.service.component.ActivityInviteQueryComponent;
import com.rubber.base.components.util.result.page.ResultPage;
import com.rubber.base.components.util.session.BaseLbsUserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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




    /**
     * 查询单个活动详情接口
     *
     * @param req
     */
    @Override
    public ActivityInviteDetailDto getDetailInfo(InviteInfoCodeReq req) {
        // 查询活动基本信息
        ActivityInviteInfoEntity activityInviteInfoEntity = activityInviteQueryComponent.getByCode(req);

        JSONObject inviteConfigField = activityInviteQueryComponent.getInviteConfigField(req);


        List<InviteJoinUserDto>  joinUserDtos = activityInviteQueryComponent.queryJoinedUser(req);

        ActivityInviteDetailDto detailDto = new ActivityInviteDetailDto();
        BeanUtil.copyProperties(activityInviteInfoEntity,detailDto);
        detailDto.setConfigField(inviteConfigField);
        detailDto.setJoinedUserList(joinUserDtos);

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
    public ResultPage<ActivityInviteInfoDto> queryRecommendPage() {

        return new ResultPage<>();
    }

    /**
     * 查询加入的活动
     *
     * @param session
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryJoinPage(BaseLbsUserSession session) {
        return new ResultPage<>();
    }

    /**
     * 查询用户自己的创建的活动
     *
     * @param session
     */
    @Override
    public ResultPage<ActivityInviteInfoDto> queryUserInvite(BaseLbsUserSession session) {

//        ResultPage<InviteInfoDto> dtoResultPage = new ResultPage<>();
//        dtoResultPage.setCurrent(page.getCurrent());
//        dtoResultPage.setPages(page.getPages());
//        dtoResultPage.setSize(page.getSize());
//        dtoResultPage.setTotal(page.getTotal());
//        if (CollUtil.isNotEmpty(page.getRecords())){
//            dtoResultPage.setRecords(
//                    page.getRecords().stream().map(this::convertToDto).collect(Collectors.toList())
//            );
//        }
        return new ResultPage<>();
    }
}
