package com.pinting.manage.queue.consumer.autoack.bind;

import com.pinting.rabbit.queue.consumer.BasicListenner;
import com.pinting.rabbit.queue.core.service.RabbitDispatcherService;
import com.pinting.rabbit.queue.core.util.QueueConstant;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 微信消息发送队列监听者绑定
 * Created by babyshark on 2018/6/6.
 */
@Service(QueueConstant.BIZ_WECHAT_MSG_TEMPLATE_QUEUE)
public class WechatMsgSendListener extends BasicListenner {

    @Autowired
    private RabbitDispatcherService dispatcherService;

    @Override
    public void onMessage(Message message) {
        super.onMessage(dispatcherService, message);
    }
}
