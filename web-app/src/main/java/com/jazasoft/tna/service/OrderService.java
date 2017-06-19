package com.jazasoft.tna.service;

import com.jazasoft.tna.dto.OrderDto;
import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.entity.Order;
import com.jazasoft.tna.respository.OrderRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Mojahid Hussain on 17-Jun-17.
 */
@Service
@Transactional(readOnly = true)
public class OrderService {
    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired OrderRepository orderRepository;

    @Autowired Mapper mapper;

    public Order findOne(Long id){
        logger.debug("findOne(): id = {}",id);
        return orderRepository.findOne(id);
    }



    public OrderDto findAllByLabel(Label label){
        return mapper.map(orderRepository.findByLabel(label),OrderDto.class);
    }

    public List<OrderDto> findAll() {
        logger.debug("findAll()");

        return orderRepository.findAll().stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return orderRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return orderRepository.count();
    }

    @Transactional
    public Order Save(Order order){
        logger.debug("save()");
        return orderRepository.save(order);
    }
    @Transactional
    public Order update(Order order){
        logger.debug("update()");
        Order order1 = orderRepository.findOne(order.getId());
        mapper.map(order,order1);
        return order1;
    }
    @Transactional
    public void delete(Long id){
        logger.debug("delete()");
        orderRepository.delete(id);
    }
}
