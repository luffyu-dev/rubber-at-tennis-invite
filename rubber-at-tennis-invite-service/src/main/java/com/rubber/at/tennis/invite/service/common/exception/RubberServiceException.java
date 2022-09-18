package com.rubber.at.tennis.invite.service.common.exception;

import com.rubber.base.components.util.result.IResultHandle;
import com.rubber.base.components.util.result.code.ICodeHandle;
import com.rubber.base.components.util.result.exception.BaseResultRunTimeException;

/**
 * @author luffyu
 * Created on 2022/8/14
 */
public class RubberServiceException  extends BaseResultRunTimeException {
    public RubberServiceException(String msg) {
        super(msg);
    }

    public RubberServiceException(IResultHandle handle) {
        super(handle);
    }

    public RubberServiceException(String code, String msg, Object data) {
        super(code, msg, data);
    }

    public RubberServiceException(ICodeHandle handle, Object data) {
        super(handle, data);
    }

    public RubberServiceException(ICodeHandle handle) {
        super(handle);
    }

    public RubberServiceException(ICodeHandle handle, String msg, Object... arguments) {
        super(handle, msg, arguments);
    }
}
