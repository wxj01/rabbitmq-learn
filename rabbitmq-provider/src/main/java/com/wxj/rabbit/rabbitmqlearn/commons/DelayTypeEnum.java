package com.wxj.rabbit.rabbitmqlearn.commons;

/**
 * @author wxj
 * @version 1.0.0
 * @ClassName DelayTypeEnum.java
 * @Description TODO
 * @createTime 2021年09月22日 20:56:00
 */
public enum DelayTypeEnum {
    DELAY_10s(1, "延时10s"),
    DELAY_60s(2, "延时60s");

    private Integer code;
    private String desc;

    DelayTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DelayTypeEnum getDelayTypeEnumByValue(Integer code){
        for (DelayTypeEnum value : DelayTypeEnum.values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
