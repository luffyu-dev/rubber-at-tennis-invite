package com.rubber.at.tennis.invite.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.api.UserTennisApi;
import com.rubber.at.tennis.invite.api.dto.LevelMatrixDto;
import com.rubber.at.tennis.invite.api.dto.UserTennisDetail;
import com.rubber.at.tennis.invite.api.dto.UserTrainInfo;
import com.rubber.at.tennis.invite.api.dto.req.UserTennisDateReq;
import com.rubber.at.tennis.invite.dao.dal.IUserTennisInfoDal;
import com.rubber.at.tennis.invite.dao.entity.UserTennisInfoEntity;
import com.rubber.at.tennis.invite.service.model.RecordTennisModel;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author luffyu
 * Created on 2022/11/27
 */
@Slf4j
@Service
public class UserTennisService implements UserTennisApi {


    @Resource
    private IUserTennisInfoDal iUserTennisInfoDal;

    @Autowired
    private UserTennisRecordService userTennisRecordService;

    /**
     * 查询用户的网球相关信息
     *
     * @param userSession 当前的用户的信息
     * @return 返回用户的详情
     */
    @Override
    public UserTennisDetail getUserTennisInfo(BaseUserSession userSession) {
        UserTennisDetail userTennis = new UserTennisDetail();
        // 查询用户的基本信息
        UserTennisInfoEntity tennisInfo = getAndInit(userSession);
        convertTennisDetail(userTennis,tennisInfo);
        // 查询网球日记
        userTennis.setTennisDate(userTennisRecordService.queryRecordDate(userSession,null));
        return userTennis;
    }

    /**
     * 查询用户的日期信息
     *
     * @param req 当前的用户的信息
     * @return 返回用户的详情
     */
    @Override
    public List<String> queryAtTennisDate(UserTennisDateReq req) {
        return userTennisRecordService.queryRecordDate(req,req.getRecordDate());
    }


    /**
     * 记录网球的相关信息
     * @param model
     */
    public void recordTennis(RecordTennisModel model) {
        // 记录信息
        if(userTennisRecordService.recordTennis(model)){
            UserTennisInfoEntity  userTennisInfoEntity = getAndInit(model.getUserSession());
            Integer allTrainHours = userTennisInfoEntity.getAllTrainHours() + model.getRecordDuration();
            Integer weekTrainHours = userTennisInfoEntity.getWeekTrainHours() + model.getRecordDuration();
            userTennisInfoEntity.setAllTrainHours(allTrainHours);
            userTennisInfoEntity.setWeekTrainHours(weekTrainHours);
            userTennisInfoEntity.setUpdateTime(new Date());
            iUserTennisInfoDal.updateById(userTennisInfoEntity);
        }
    }


    /**
     * 查询网球的基本信息
     */
    private UserTennisInfoEntity getAndInit(BaseUserSession userSession){
        UserTennisInfoEntity userTennisInfoEntity = getTennisInfo(userSession);
        if (userTennisInfoEntity == null){
            userTennisInfoEntity = initUserTennis(userSession);
            iUserTennisInfoDal.save(userTennisInfoEntity);
        }
        return userTennisInfoEntity;
    }



    /**
     * 查询网球的基本信息
     */
    private UserTennisInfoEntity getTennisInfo(BaseUserSession userSession){
        LambdaQueryWrapper<UserTennisInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserTennisInfoEntity::getUid,userSession.getUid());
        return iUserTennisInfoDal.getOne(lqw);
    }


    /**
     * 数据对象
     */
    private void convertTennisDetail(UserTennisDetail userTennis,UserTennisInfoEntity tennisInfo){
        userTennis.setNtrp(tennisInfo.getNtrp());
        // 能力矩阵
        LevelMatrixDto levelMatrixDto = JSON.parseObject(tennisInfo.getLevelMatrix(),LevelMatrixDto.class);
        userTennis.setLevelMatrix(levelMatrixDto);
        Date startPlayDate = tennisInfo.getStartPlayDate();
        if (startPlayDate != null){
           long betweenMonth = DateUtil.betweenMonth(startPlayDate,new Date(),false);
           userTennis.setYearDate((int)betweenMonth / 12);
           userTennis.setMonthDate((int)betweenMonth % 12);
        }

        // 网球训练的信息
        UserTrainInfo userTrainInfo = new UserTrainInfo();
        userTrainInfo.setAllHours(tennisInfo.getAllTrainHours());
        userTrainInfo.setWeekHours(tennisInfo.getWeekTrainHours());
        userTennis.setUserTrainInfo(userTrainInfo);
    }


    /**
     * 初始化对象
     * @param userSession
     * @return
     */
    private UserTennisInfoEntity initUserTennis(BaseUserSession userSession){
        UserTennisInfoEntity entity = new UserTennisInfoEntity();
        entity.setUid(userSession.getUid());
        entity.setLevelMatrix(JSON.toJSONString(new LevelMatrixDto()));
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        entity.setWeekTrainHours(0);
        entity.setAllTrainHours(0);
        entity.setNtrp("2.5");
        return entity;
    }




}
