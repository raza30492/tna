package com.jazasoft.tna.service;

import com.jazasoft.tna.entity.OrderDetail;
import com.jazasoft.tna.respository.OrderDetailRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Mojahid Hussain on 17-Jun-17.
 */
@Service
@Transactional(readOnly = true)
public class OrderDetailService {

    private final Logger logger = LoggerFactory.getLogger(OrderDetailService.class);
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    Mapper mapper;

    public OrderDetail findOne(Long id){
        logger.debug("findOne(): id = {}",id);
        return orderDetailRepository.findOne(id);
    }

    public List<OrderDetail> findAll(){
        logger.debug("findAll()");
        return  orderDetailRepository.findAll();
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return orderDetailRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return orderDetailRepository.count();
    }

    @Transactional
    public OrderDetail Save(OrderDetail orderDetail){
        logger.debug("save()");
        return orderDetailRepository.save(orderDetail);
    }

    @Transactional
    public void updateBatch(List<OrderDetail> orderDetails){
        logger.debug("update()");
        OrderDetail orderDetail = null;
        for (OrderDetail od: orderDetails){
            orderDetail = orderDetailRepository.findOne(od.getId());
            if (od.getCompletedAt() != null) orderDetail.setCompletedAt(od.getCompletedAt());
            if (od.getRemarks() != null) orderDetail.setRemarks(od.getRemarks());
        }
    }

    @Transactional
    public OrderDetail update(OrderDetail orderDetail){
        logger.debug("update()");
        OrderDetail orderDetail1 = orderDetailRepository.findOne(orderDetail.getId());
        mapper.map(orderDetail,orderDetail1);
        return orderDetail1;
    }
    @Transactional
    public void delete(Long id){
        logger.debug("delete()");
        orderDetailRepository.delete(id);
    }
}
