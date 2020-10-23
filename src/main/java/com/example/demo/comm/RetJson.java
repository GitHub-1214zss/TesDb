package com.example.demo.comm;

import lombok.Data;

import java.io.Serializable;

/**************************************************************
 * 创建日期：2020/1/10 11:08
 * 作    者：lixuhong
 * 功能描述：返回数据
 **************************************************************/
@Data
public class RetJson implements Serializable {

    /** 状态码 */
    private int code;
    /** 消息 */
    private String msg;
    /** 数据 */
    private Object data;

    private Integer count;

    /**
     * 执行成功
     */
    public static Object ok(){
        return new RetJson();
    }

    /**
     * 执行成功
     * @param data 返回数据
     */
    public static Object ok(Object data){
        return new RetJson(data);
    }

    /**
     * 执行失败
     * @param code  返回码
     * @param msg   消息
     */
    public static Object err(int code, String msg){
        return new RetJson(code, msg);
    }

    /**
     * 执行失败
     * @param RetCode 返回码（枚举）
     */
    public static Object err(RetCode RetCode){
        return err(RetCode.getCode(), RetCode.getMsg());
    }

    /**
     * 执行失败
     * @param code  返回码
     * @param msg   消息
     * @param data   返回数据
     */
    public static Object err(int code, String msg, Object data){
        return new RetJson(code, msg, data);
    }

    public RetJson() {
        this.code = RetCode.SUCCESS.getCode();
        this.msg = RetCode.SUCCESS.getMsg();
    }

    public RetJson(Object data) {
        this.code = RetCode.SUCCESS.getCode();
        this.msg = RetCode.SUCCESS.getMsg();
        this.data = data;
    }

    public RetJson(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public RetJson(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
