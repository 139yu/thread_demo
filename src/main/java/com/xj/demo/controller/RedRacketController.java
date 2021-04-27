package com.xj.demo.controller;

import com.power.common.model.CommonResult;
import com.xj.demo.vo.CreateRedPacket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xj.demo.entity.RedRacketEntity;
import com.xj.demo.service.RedRacketService;

import java.util.List;


/**
 * 红包信息表
 *
 * @author yu
 * @email 
 * @date 2021-04-27 17:18:30
 */
@RestController
@RequestMapping("demo/redracket")
public class RedRacketController {
    @Autowired
    private RedRacketService redRacketService;

    /**
     * 创建红包
     * @param createRedPacket
     * @return
     */
    @PostMapping("/create")
    public CommonResult create(@RequestBody CreateRedPacket createRedPacket){
        redRacketService.create(createRedPacket);
        return CommonResult.ok();
    }

    @PostMapping("startTwoSeckil")
    public CommonResult startTwoSeckil(@RequestParam("redPacketId") String redPacketId){
        redRacketService.startTwoSeckil(redPacketId);
        return CommonResult.ok();
    }
}
