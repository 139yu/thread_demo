package com.xj.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 红包信息表
 * 
 * @author yu
 * @email 
 * @date 2021-04-27 17:18:30
 */
@Data
@TableName("red_racket")
@Accessors(chain = true)
public class RedRacketEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
		@TableId(value = "id",type = IdType.AUTO)
	private Long id;
	/**
	 * 红包唯一ID
	 */
	private String redPacketId;
	/**
	 * 红包金额单位分
	 */
	private Integer totalAmount;
	/**
	 * 红包个数
	 */
	private Integer totalPacket;
	/**
	 * 红包类型
	 */
	private Integer type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 版本号
	 */
	private Integer version;

}
