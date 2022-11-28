package com.rubber.at.tennis.invite.web;

import com.rubber.at.tennis.invite.api.UserTennisApi;
import com.rubber.at.tennis.invite.api.dto.UserTennisDetail;
import com.rubber.at.tennis.invite.service.UserTennisService;
import com.rubber.at.tennis.invite.service.common.constant.RecordTypeEnums;
import com.rubber.at.tennis.invite.service.model.RecordTennisModel;
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
    public UserTennisApi userTennisApi;

    @Autowired
    private UserTennisService tennisService;

    @Test
    public void doTest(){

        BaseUserSession baseUserSession = new BaseUserSession();
        baseUserSession.setUid(100000);
        UserTennisDetail userTennisInfo = userTennisApi.getUserTennisInfo(baseUserSession);
        System.out.println(userTennisInfo);

        RecordTennisModel model = new RecordTennisModel();
        model.setUserSession(baseUserSession);
        model.setRecordType(RecordTypeEnums.INVITE);
        model.setRecordDuration(120);
        model.setRecordTitle("邀约训练");


        tennisService.recordTennis(model);


        UserTennisDetail userTennisInfo2 = userTennisApi.getUserTennisInfo(baseUserSession);
        System.out.println(userTennisInfo);

    }

}
