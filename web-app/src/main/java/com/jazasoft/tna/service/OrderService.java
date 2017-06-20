package com.jazasoft.tna.service;

import com.jazasoft.tna.dto.OrderDto;
import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.entity.Order;
import com.jazasoft.tna.respository.LabelRepository;
import com.jazasoft.tna.respository.OrderRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Autowired
    LabelRepository labelRepository;

    @Autowired Mapper mapper;

    public Order findOne(Long id){
        logger.debug("findOne(): id = {}",id);
        return orderRepository.findOne(id);
    }



    public OrderDto findByLabel(Label label){
        return mapper.map(orderRepository.findByLabel(label),OrderDto.class);
    }

    public List<OrderDto> findAllByLabel(Label label) {
        logger.debug("findByLabel()");

        return orderRepository.findByLabel(label).stream()
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
    public OrderDto Save(OrderDto orderDto){
        logger.debug("save()");
        //Label label = labelRepository.findOne(orderDto.getLabel().getId());
        //System.out.println("lebel is////////"+label);
        Order order = mapper.map(orderDto, Order.class );
        //order.setLabel(orderDto.getLabel());
        order.setOrderAt(new Date());
        order=orderRepository.save(order);
        return mapper.map(order,OrderDto.class);
    }
    @Transactional
    public OrderDto update(OrderDto orderDto){
        logger.debug("update()");
        Order order1 = orderRepository.findOne(orderDto.getId());
        Order order2 = mapper.map(orderDto,Order.class);
        order2.setOrderAt(new Date());
        mapper.map(order2,order1);
        return mapper.map(order1,OrderDto.class);
    }
    @Transactional
    public void delete(Long id){
        logger.debug("delete()");
        orderRepository.delete(id);
    }
}
