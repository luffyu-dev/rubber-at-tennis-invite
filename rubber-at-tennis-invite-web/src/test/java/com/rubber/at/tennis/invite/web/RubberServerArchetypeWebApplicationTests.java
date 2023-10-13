package com.rubber.at.tennis.invite.web;

import com.alibaba.fastjson.JSONObject;
import com.rubber.at.tennis.invite.api.ActivityInviteApplyApi;
import com.rubber.at.tennis.invite.api.ActivityInviteQueryApi;
import com.rubber.at.tennis.invite.api.UserTennisApi;
import com.rubber.at.tennis.invite.api.dto.ActivityInviteDetailDto;
import com.rubber.at.tennis.invite.api.dto.ApplyInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.UserTennisDetail;
import com.rubber.at.tennis.invite.api.dto.req.ActivityInviteQueryReq;
import com.rubber.at.tennis.invite.api.dto.req.CancelJoinInviteReq;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.service.UserTennisService;
import com.rubber.at.tennis.invite.api.enums.RecordTypeEnums;
import com.rubber.at.tennis.invite.api.dto.RecordTennisModel;
import com.rubber.base.components.util.session.BaseUserSession;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;


@SpringBootTest
@ComponentScan("com.rubber.*")
@MapperScan("com.rubber.**.dao.mapper")
class RubberServerArchetypeWebApplicationTests {


    @Autowired
    private ActivityInviteApplyApi activityInviteApplyApi;


    @Autowired
    private ActivityInviteQueryApi activityInviteQueryApi;


    @Test
    public void doSave(){

        CancelJoinInviteReq dto = new CancelJoinInviteReq();
        dto.setInviteCode("AICDJ1iD9dKKotKUp4Q");
        dto.setUid(10000001);
//        dto.setName("这是一个测试");
//        dto.setInviteNumber(4);
//
//        dto.setAutoPublished(1);
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("k1","v1");
//        jsonObject.put("k2","v2");
//
//        dto.setConfigField(jsonObject);
//        activityInviteApplyApi.saveActivityInviteInfo(dto);

        activityInviteApplyApi.cancelJoinInvite(dto);

        //activityInviteApplyApi.closeInvite(dto);

        //activityInviteApplyApi.publishInvite(dto);



    }

    @Test
    public void doQuery(){
        ActivityInviteQueryReq req = new ActivityInviteQueryReq();
        req.setInviteCode("AICDJ1iD9dKKotKUp4Q");
        req.setUid(10000001);

        ActivityInviteDetailDto detailInfo = activityInviteQueryApi.getDetailInfo(req);

        activityInviteQueryApi.queryUserInvite(req);



    }
}
