package com.xj.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.demo.entity.RedPacketRecordEntity;
import com.xj.demo.service.RedPacketRecordService;

import java.util.List;


/**
 * 抢红包记录表
 *
 * @author yu
 * @email 
 * @date 2021-04-27 17:18:30
 */
@RestController
@RequestMapping("demo/redpacketrecord")
public class RedPacketRecordController {
    @Autowired
    private RedPacketRecordService redPacketRecordService;

    @GetMapping("list")
    public List<RedPacketRecordEntity> list() {
        return redPacketRecordService.getList();
    }

}
