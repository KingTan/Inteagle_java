package com.inteagle.common.mqtt.queue;

import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 1.建Event类（数据对象）
 * 2.建立一个生产数据的工厂类，EventFactory，用于生产数据；
 * 3.监听事件类（处理Event数据）
 * 4.实例化Disruptor，配置参数，绑定事件；
 * 5.建存放数据的核心 RingBuffer，生产的数据放入 RungBuffer。
 */
@Slf4j
public class ObjectEventConsumer implements EventHandler<ObjectEvent> {
    @Override
    public void onEvent(ObjectEvent objectEvent, long l, boolean b) throws Exception {
//        log.info("Disruptor===事件消费者:" + JSON.toJSONString(objectEvent.getEvent()));
        Thread.sleep(10000);
    }
}
