package com.xj.demo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 */
@Data
public class CreateRedPacket {
    /**
     * 红包金额
     */
    public BigDecimal totalAmount;
    /**
     * 红包个数
     */
    public Integer totalPacket;
    /**
     * 红包类型
     */
    public Integer type;
}
