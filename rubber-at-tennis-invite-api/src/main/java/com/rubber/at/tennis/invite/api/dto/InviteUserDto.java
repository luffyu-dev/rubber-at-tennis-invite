package com.rubber.at.tennis.invite.api.dto;

import com.rubber.base.components.util.session.BaseUserSession;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 邀约详情表
 * </p>
 *
 * @author rockyu
 * @since 2022-08-29
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InviteUserDto extends BaseUserSession {


    /**
     * 邀请code
     */
    private String inviteCode;

    /**
     * 参与人id
     */
    private Integer joinUid;

    /**
     * 关联的数据版本
     */
    private Integer dataVersion;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 状态 10表示报名成功 20表示退出
     */
    private Integer status;





}
