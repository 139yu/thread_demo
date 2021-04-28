package com.xj.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xj.demo.entity.RedPacketRecordEntity;
import com.xj.demo.service.RedPacketRecordService;
import com.xj.demo.vo.CreateRedPacket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xj.demo.dao.RedRacketDao;
import com.xj.demo.entity.RedRacketEntity;
import com.xj.demo.service.RedRacketService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class RedRacketServiceImpl extends ServiceImpl<RedRacketDao, RedRacketEntity> implements RedRacketService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedPacketRecordService redPacketRecordService;
    private ReentrantLock lock = new ReentrantLock();

    Random random = new Random();

    @Override
    public List<RedRacketEntity> getList() {
        return this.baseMapper.selectList(new QueryWrapper<RedRacketEntity>());
    }

    /**
     * 创建红包，将红包添加进redis
     *
     * @param createRedPacket
     */
    @Override
    public String create(CreateRedPacket createRedPacket) {
        RedRacketEntity redRacketEntity = new RedRacketEntity();
        int totalAmount = createRedPacket.getTotalAmount().multiply(new BigDecimal(100)).intValue();
        String redPacketId = UUID.randomUUID().toString();
        redRacketEntity
                .setVersion(1)
                .setRedPacketId(redPacketId)
                .setTotalAmount(totalAmount)
                .setTotalPacket(createRedPacket.getTotalPacket())
                .setCreateTime(new Date())
                .setType(createRedPacket.getType());
        redisTemplate.opsForValue().set(redPacketId + ":balance", createRedPacket.getTotalAmount(), 24, TimeUnit.HOURS);
        //记录红包个数
        redisTemplate.opsForValue().set(redPacketId + ":restPeople", createRedPacket.getTotalPacket());
        this.save(redRacketEntity);
        return redPacketId;
    }

    public Integer getBalance(String redPacketId) {
        return (Integer) redisTemplate.opsForValue().get(redPacketId + ":balance");
    }
    public void setBalance(String key,int balance){
        redisTemplate.opsForValue().set(key,balance);
    }
    /**
     * 抢红包
     *
     * @param redPacketId
     */
    @Transactional
    @Override
    public void startTwoSeckil(String redPacketId) {
        String key = redPacketId + ":restPeople";
        //剩余红包个数
        int restPeople = (Integer) redisTemplate.opsForValue().get(key);
        int balance = getBalance(redPacketId);
            if (restPeople > 0 && balance > 0) {
                int money = 0;
                if (restPeople == 1) {
                    money = balance;
                } else {
                    money = random.nextInt(balance / restPeople * 2) + 1;
                }
                /**
                 * 使用lua脚本
                 * 确认当前剩余红包数量与剩余金额未被修改后，红包个数减1，剩余红包金额更新
                 */
                String script = "if (redis.call('get',KEYS[1]) == ARGV[1] and redis.call('get',KEYS[2]) == ARGV[2]) " +
                        "then " +
                        "redis.call('set',KEYS[1],ARGV[1] -1) " +
                        "redis.call('set',KEYS[2],ARGV[3]) " +
                        "return 0 " +
                        "else return -1 " +
                        "end";
                List<String> keys = new ArrayList<>();
                keys.add(key);
                keys.add(redPacketId + ":balance");
                long res = (long) redisTemplate.execute(new DefaultRedisScript(script,Long.class), keys, restPeople,balance,balance - money);
                if (res != -1) {

                    RedPacketRecordEntity redPacketRecordEntity = new RedPacketRecordEntity();
                    redPacketRecordEntity
                            .setAmount(money)
                            .setRedPacketId(redPacketId)
                            .setUid(Thread.currentThread().getId())
                            .setCreateTime(new Date());
                    redPacketRecordService.save(redPacketRecordEntity);
                    log.info("线程{}抢到{}元，剩余红包{}个", Thread.currentThread().getId(), new BigDecimal(money).divide(new BigDecimal(100)), redisTemplate.opsForValue().get(redPacketId + ":restPeople"));
                } else {//失败后自旋
                    startTwoSeckil(redPacketId);
                }

            } else {
                log.info("线程{}抢红包失败", Thread.currentThread().getId());
            }



    }
}