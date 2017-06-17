package com.jazasoft.tna.service;

import com.jazasoft.tna.entity.Buyer;
import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.respository.LabelRepository;
import com.jazasoft.tna.respository.UserRespository;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by mtalam on 6/17/2017.
 */
@Service
@Transactional(readOnly = true)
public class LabelService {

    private final Logger logger = LoggerFactory.getLogger(LabelService.class);
    @Autowired LabelRepository labelRepository;
    @Autowired
    Mapper mapper;

    public Label findOne(Long id){
        logger.debug("findOne(): id = {}",id);
        return labelRepository.findOne(id);
    }

    public List<Label> findAll(){
        logger.debug("findAll()");
        return  labelRepository.findAll();
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return labelRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return labelRepository.count();
    }

    public List<Label> findLabelByBuyer(Buyer buyer){
        logger.debug("findByBuyer()");
        return  labelRepository.findByBuyer(buyer);
    }

    @Transactional
    public Label save(Label label){
        logger.debug("save()");
        return labelRepository.save(label);
    }
    @Transactional
    public Label update(Label label){
        logger.debug("update()");
        Label label1 = labelRepository.findOne(label.getId());
        mapper.map(label,label1);
        return label1;
    }
    @Transactional
    public void delete(Long id){
        logger.debug("delete()");
        labelRepository.delete(id);
    }

}
