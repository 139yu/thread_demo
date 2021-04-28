package com.xj.demo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 抢红包记录表
 * 
 * @author yu
 * @email 
 * @date 2021-04-27 17:18:30
 */
@Data
@TableName("red_packet_record")
@Accessors(chain = true)
public class RedPacketRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
		@TableId(value = "id",type = IdType.AUTO)
	private Long id;
	/**
	 * 抢到红包的金额
	 */
	private Integer amount;
	/**
	 * 红包ID
	 */
	private String redPacketId;
	/**
	 * 抢到红包用户的用户标识
	 */
	private Long uid;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
