package com.rubber.at.tennis.invite.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.dao.dal.IUserTennisRecordDal;
import com.rubber.at.tennis.invite.dao.entity.UserTennisRecordEntity;
import com.rubber.at.tennis.invite.api.dto.RecordTennisModel;
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

    @Autowired
    private UserTennisService userTennisService;

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


    /**
     * 记录网球信息
     * @param model
     * @return
     */
    public boolean recordTennis(RecordTennisModel model){
        UserTennisRecordEntity recordEntity = new UserTennisRecordEntity();
        BeanUtils.copyProperties(model,recordEntity);
        recordEntity.setUid(model.getUserSession().getUid());
        recordEntity.setRecordTitle(model.getRecordType().toString());
        if (recordEntity.getRecordDate() == null){
            recordEntity.setRecordDate(DateUtil.format(new Date(),"yyyy/MM/dd"));
        }
        return iUserTennisRecordDal.save(recordEntity);
    }

    /**
     * 取消获得信息
     * @param bizId
     */
    public int cancelRecordTennis(String bizId,BaseUserSession userSession){
        LambdaQueryWrapper<UserTennisRecordEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserTennisRecordEntity::getBizId,bizId)
                .eq(UserTennisRecordEntity::getUid,userSession.getUid())
                .eq(UserTennisRecordEntity::getStatus,1)
                .last(" limit 1");
        UserTennisRecordEntity recordEntity = iUserTennisRecordDal.getOne(lqw);
        if (recordEntity != null){
            UserTennisRecordEntity up = new UserTennisRecordEntity();
            up.setId(recordEntity.getId());
            up.setStatus(0);
            if(iUserTennisRecordDal.update(recordEntity,lqw)){
                return recordEntity.getRecordDuration();
            }
        }
        return 0;
    }

}
