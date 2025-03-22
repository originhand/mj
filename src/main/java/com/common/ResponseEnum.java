package com.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 哈喽沃德
 * @version 1.0
 * @date 2025/03/17 下午 19:23:40
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {
    SUCCESS(0,"success"),
    PARAMETER_IS_ABNORMAL(-1000,"参数异常"),
    LOGIN_INFORMATION_IS_INCORRECT(1001, "登录信息异常");

    private final Integer code;
    private final String msg;

}
