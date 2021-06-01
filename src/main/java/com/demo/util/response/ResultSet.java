package com.demo.util.response;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 自定义返回
 *
 * @author zhang
 * @since 2018-12-31
 */
@ApiModel(value = "自定义返回结果集")
@Data
public class ResultSet<T> implements Serializable {

    private static final Long serialVersionUID = -2883559330470105863L;
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private Integer status;
    /**
     * 消息
     */
    @ApiModelProperty(value = "消息")
    private String message = null;
    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    private Long timestamp = System.currentTimeMillis();
    /**
     * 结果集
     */
    @ApiModelProperty(value = "结果集")
    public T result;

    /**
     * 成功
     *
     * @param <T> 啥都没有
     * @return OK对象
     */
    public static <T> ResultSet<T> ok() {
        return ok(200, "success");
    }

    /**
     * 成功无消息
     *
     * @param result 结果集
     * @param <T>    啥都没有
     * @return OK对象
     */
    public static <T> ResultSet<T> ok(T result) {
        return ok(200, "success", result);
    }

    /**
     * 成功有消息
     *
     * @param message 消息
     * @param result  结果级
     * @return OK对象
     */
    public static <T> ResultSet<T> ok(String message, T result) {
        return ok(200, message, result);
    }

    /**
     * 失败消息
     *
     * @param <T> 啥都没有
     * @return ERROR对象
     */
    public static <T> ResultSet<T> error() {
        return error(406, "error");
    }

    /**
     * 失败
     *
     * @param <T> 啥都没有
     * @return ERROR对象
     */
    public static <T> ResultSet<T> error(String message) {
        return error(406, message);
    }


    /**
     * 自定义成功消息
     *
     * @param status  状态码
     * @param message 信息
     * @param result  结果集
     * @param <T>     啥都没有
     * @return OK对象
     */
    @SafeVarargs
    public static <T> ResultSet<T> ok(int status, String message, T... result) {
        ResultSet<T> set = new ResultSet<>();
        set.status = status;
        if (message != null && !"".equals(message)) {
            set.message = message;
        } else {
            set.message = "success";
        }
        set.result = result != null && result.length != 0 ? result[0] : null;
        return set;
    }

    /**
     * 自定义失败消息
     *
     * @param message 消息
     * @param <T>     啥都没有
     * @return ERROR对象
     */
    public static <T> ResultSet<T> error(int status, String message) {
        ResultSet<T> set = new ResultSet<>();
        set.status = status;
        if (message != null && !"".equals(message)) {
            set.message = message;
        } else {
            set.message = "error";
        }
        return set;
    }

    /**
     * 转成JSON
     *
     * @return ResultSet JSON
     */
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
