package com.rubber.at.tennis.invite.service.component;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rubber.at.tennis.invite.api.dto.ApplyInviteInfoDto;
import com.rubber.at.tennis.invite.api.dto.req.InviteInfoCodeReq;
import com.rubber.at.tennis.invite.api.enums.ActivityInviteStateEnums;
import com.rubber.at.tennis.invite.dao.dal.IActivityInviteInfoDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteConfigFieldDal;
import com.rubber.at.tennis.invite.dao.dal.IInviteUserBasicInfoDal;
import com.rubber.at.tennis.invite.dao.entity.ActivityInviteInfoEntity;
import com.rubber.at.tennis.invite.dao.entity.InviteConfigFieldEntity;
import com.rubber.at.tennis.invite.service.common.exception.RubberServiceException;
import com.rubber.base.components.util.result.code.SysCode;
import com.rubber.base.components.util.session.BaseLbsUserSession;
import com.rubber.base.components.util.session.BaseUserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author luffyu
 * Created on 2022/9/3
 */
@Slf4j
@Component
public class ActivityInviteApplyComponent {


    @Resource
    private IActivityInviteInfoDal iActivityInviteInfoDal;


    @Resource
    private IInviteConfigFieldDal inviteConfigFieldDal;

    @Resource
    private IInviteUserBasicInfoDal iInviteUserBasicInfoDal;


    /**
     * 活动的前缀
     */
    private static final String PREFIX = "AIC";


    /**
     * 创建一个邀请id
     */
    private String creatInviteId(){
        return PREFIX + IdUtil.nanoId(16);
    }



    @Transactional(
            rollbackFor = Exception.class
    )
    public ActivityInviteInfoEntity saveInvite(ApplyInviteInfoDto dto){
        ActivityInviteInfoEntity inviteInfoEntity ;

        if (StrUtil.isEmpty(dto.getInviteCode())){
            // 新增
            inviteInfoEntity = new ActivityInviteInfoEntity();
            BeanUtil.copyProperties(dto,inviteInfoEntity);
            inviteInfoEntity.setInviteCode(creatInviteId());
            Date now = new Date();
            inviteInfoEntity.setCreateTime(now);
            inviteInfoEntity.setUpdateTime(now);
            inviteInfoEntity.setVersion(1);
            inviteInfoEntity.setJoinNumber(0);
            // 设置是否自动发布
            if (Integer.valueOf(1).equals(dto.getAutoPublished())){
                inviteInfoEntity.setStatus(ActivityInviteStateEnums.PUBLISHED.getState());
            }else {
                inviteInfoEntity.setStatus(ActivityInviteStateEnums.INIT.getState());
            }
            // 设置报名状态
            inviteInfoEntity.setJoinStatus(10);
            if(!iActivityInviteInfoDal.save(inviteInfoEntity)){
                throw new RubberServiceException(SysCode.SYSTEM_BUS);
            }
            saveConfigField(inviteInfoEntity,dto.getConfigField());
            // 保存联系信息
            saveOrUpdateUserContact(dto,dto.getConfigField());
            //保存扩展参数
            return inviteInfoEntity;
        }else {
            // 修改
            inviteInfoEntity = this.verifyAndGetManagerInvite(dto);
            if (Integer.valueOf(1).equals(dto.getAutoPublished())){
                inviteInfoEntity.setStatus(ActivityInviteStateEnums.PUBLISHED.getState());
            }
            BeanUtil.copyProperties(dto,inviteInfoEntity,"joinNumber","joinStatus");
            this.updateInviteByCode(inviteInfoEntity,dto);
            // 保存拓展参数
            saveConfigField(inviteInfoEntity,dto.getConfigField());
            // 保存联系信息
            saveOrUpdateUserContact(dto,dto.getConfigField());
            return inviteInfoEntity;
        }
    }


    /**
     * 保存基本信息
     * @param inviteInfoEntity
     * @param jsonObject
     */

    private void saveConfigField(ActivityInviteInfoEntity inviteInfoEntity, JSONObject jsonObject){
        List<InviteConfigFieldEntity> list = new ArrayList<>();
        for (String k:jsonObject.keySet()){
            InviteConfigFieldEntity configFieldEntity = new InviteConfigFieldEntity();
            configFieldEntity.setInviteCode(inviteInfoEntity.getInviteCode());
            configFieldEntity.setInviteField(k);
            configFieldEntity.setInviteValue(jsonObject.getString(k));
            configFieldEntity.setCreateTime(inviteInfoEntity.getUpdateTime());
            configFieldEntity.setUpdateTime(inviteInfoEntity.getUpdateTime());
            configFieldEntity.setStatus(10);
            configFieldEntity.setVersion(1);
            list.add(configFieldEntity);
        }
        inviteConfigFieldDal.removeAndSaveList(inviteInfoEntity.getInviteCode(),list);
    }

    // 保存用户手机号
    private void saveOrUpdateUserContact(BaseLbsUserSession baseLbsUserSession,JSONObject configData){
        String userWx = configData.getString("sponsorContactWx");
        String userPhone = configData.getString("sponsorContactPhone");
        iInviteUserBasicInfoDal.updateContact(baseLbsUserSession.getUid(),userPhone,userWx);
    }



    @Transactional(
            rollbackFor = Exception.class
    )
    public boolean updateInviteForJoin(ActivityInviteInfoEntity inviteInfoEntity,Integer oldJoinIndex,Integer newJoinIndex){
        Date now = new Date();
        inviteInfoEntity.setUpdateTime(now);
        inviteInfoEntity.setJoinNumber(newJoinIndex);
        LambdaQueryWrapper<ActivityInviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ActivityInviteInfoEntity::getId,inviteInfoEntity.getId())
                .eq(ActivityInviteInfoEntity::getJoinNumber,oldJoinIndex)
                .ge(ActivityInviteInfoEntity::getInviteNumber,newJoinIndex);
        return iActivityInviteInfoDal.update(inviteInfoEntity,lqw);
    }


    /**
     * 保存code信息
     * @param req 当前的邀约code
     * @return 返回邀约主体信息
     */
    public ActivityInviteInfoEntity verifyAndGetManagerInvite(InviteInfoCodeReq req){
        ActivityInviteInfoEntity inviteInfoEntity = iActivityInviteInfoDal.getByManagerCode(req.getInviteCode(),req.getUid());
        if (inviteInfoEntity == null){
            log.error("邀约code不存在{}",req);
            throw new RubberServiceException(SysCode.PARAM_ERROR);
        }
        return inviteInfoEntity;
    }


    /**
     * 更新状态
     * @param inviteInfoEntity
     */
    public void updateInviteByCode(ActivityInviteInfoEntity inviteInfoEntity, BaseUserSession baseLbsUserSession){
        LambdaQueryWrapper<ActivityInviteInfoEntity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(ActivityInviteInfoEntity::getUid,baseLbsUserSession.getUid())
                .eq(ActivityInviteInfoEntity::getInviteCode,inviteInfoEntity.getInviteCode())
                .eq(ActivityInviteInfoEntity::getId,inviteInfoEntity.getId());
        Date now = new Date();
        inviteInfoEntity.setUpdateTime(now);
        if(!iActivityInviteInfoDal.update(inviteInfoEntity,lqw)){
            throw new RubberServiceException(SysCode.SYSTEM_BUS);
        }
    }


}
