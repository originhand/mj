package com.example.hello.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T data) {
        return new Result<>(1, "success", data);
    }

    public static Result<?> success() {
        return success(null);
    }

    public static Result<?> error(String msg) {
        return new Result<>(0, msg, null);
    }
} 