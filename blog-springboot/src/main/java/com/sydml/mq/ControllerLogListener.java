package com.sydml.mq;


import com.sydml.config.RabbitMqConfig;
import com.sydml.dao.mongo.ControllerLogDAO;
import com.sydml.model.entity.ControllerLog;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mqpearh
 */
@Component
@RabbitListener(queues = RabbitMqConfig.CONTROLLER_LOG_QUEUE)
public class ControllerLogListener {

    @Autowired
    ControllerLogDAO controllerLogDAO;

    @RabbitHandler
    public void save(ControllerLog log) {
        controllerLogDAO.saveControllerLog(log);
    }
}
