package com.rubber.at.tennis.invite.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.dao.dal.IUserTennisRecordDal;
import com.rubber.at.tennis.invite.dao.entity.UserTennisRecordEntity;
import com.rubber.at.tennis.invite.service.model.RecordTennisModel;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author luffyu
 * Created on 2022/11/27
 */
@Slf4j
@Service
public class UserTennisRecordService {

    @Autowired
    private IUserTennisRecordDal iUserTennisRecordDal;


    /**
     * 查询有记录的list
     * @param baseUserSession 当前的请求参数
     * @return
     */
    public List<String> queryRecordDate(BaseUserSession baseUserSession,String targetMon){
        if (targetMon == null){
            targetMon = DateUtil.format(new Date(),"yyyy/MM");
        }
        LambdaQueryWrapper<UserTennisRecordEntity> lqw = new LambdaQueryWrapper<>();
        lqw.select(UserTennisRecordEntity::getRecordDate)
                .eq(UserTennisRecordEntity::getUid,baseUserSession.getUid())
                .likeRight(UserTennisRecordEntity::getRecordDate,targetMon)
                .eq(UserTennisRecordEntity::getStatus,1)
                .groupBy(UserTennisRecordEntity::getRecordDate);
        List<UserTennisRecordEntity> recordList = iUserTennisRecordDal.list(lqw);
        if (CollUtil.isEmpty(recordList)){
            return null;
        }
        return recordList.stream().map(UserTennisRecordEntity::getRecordDate).collect(Collectors.toList());
    }



    public boolean recordTennis(RecordTennisModel model){
        UserTennisRecordEntity recordEntity = new UserTennisRecordEntity();
        BeanUtils.copyProperties(model,recordEntity);
        recordEntity.setUid(model.getUserSession().getUid());
        recordEntity.setRecordTitle(model.getRecordType().toString());
        recordEntity.setRecordDate(DateUtil.format(new Date(),"yyyy/MM/dd"));
        return iUserTennisRecordDal.save(recordEntity);
    }

}
