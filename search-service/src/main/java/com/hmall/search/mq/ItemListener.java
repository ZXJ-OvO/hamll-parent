package com.hmall.search.mq;

import com.hmall.search.service.ISearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ItemListener {

    @Resource
    private ISearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "up.queue"),
            exchange = @Exchange(name = "item.exchange", type = ExchangeTypes.TOPIC),
            key = "item.status.up"
    ))
    public void upConsumer(Long id) {
        searchService.saveItemById(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "down.queue"),
            exchange = @Exchange(name = "item.exchange", type = ExchangeTypes.TOPIC),
            key = "item.status.down"
    ))
    public void downConsumer(Long id) {
        searchService.deleteItemById(id);
    }
}
