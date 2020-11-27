package com.dh.spike.result;

/**
 *
 * 通用结果集
 * Create by DiaoHao on 2020/10/18 15:29
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    public Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }

    public static <T>Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static <T>Result<T> error(CodeMsg cm) {
        return new Result<>(cm);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
