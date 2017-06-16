package com.jazasoft.tna.service;

import com.jazasoft.tna.entity.Buyer;
import com.jazasoft.tna.respository.BuyerRepository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mtalam on 6/14/2017.
 */
@Service
@Transactional(readOnly = true)
public class BuyerService
{

    private final Logger logger = LoggerFactory.getLogger(BuyerService.class);
    @Autowired BuyerRepository buyerRepository;

    @Autowired Mapper mapper;

    public Buyer findOne(Long id){
        logger.debug("findOne(): id = {}",id);
        return buyerRepository.findOne(id);
    }

    public List<Buyer> findAll(){
        logger.debug("findAll()");
        return  buyerRepository.findAll();
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return buyerRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return buyerRepository.count();
    }

    @Transactional
    public Buyer Save(Buyer buyer){
        logger.debug("save()");
        return buyerRepository.save(buyer);
    }
    @Transactional
    public Buyer update(Buyer buyer){
        logger.debug("update()");
        Buyer buyer1 = buyerRepository.findOne(buyer.getId());
        mapper.map(buyer,buyer1);
        //buyer1 = buyer;
        return buyer1;
    }
    @Transactional
    public void delete(Long id){
        logger.debug("delete()");
        buyerRepository.delete(id);
    }

}
