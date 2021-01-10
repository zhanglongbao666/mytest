package com.itheima.health.exception;

/**
 * 包名:com.itheima.health.exception
 *  Description: 自定义异常
 *  区分系统与自定义的异常
 *  终止已经不符合业务逻辑的代码
 * @author ZhangLongBao
 * 日期2021-01-06  19:58
 */
public class HealthException extends RuntimeException{
    public HealthException(String message){
        super(message);
    }
}
