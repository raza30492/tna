package com.jazasoft.tna.service;

import com.jazasoft.tna.entity.ActivityName;
import com.jazasoft.tna.respository.ActivityNameRepository;
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
public class ActivityNameService {
    private final Logger logger = LoggerFactory.getLogger(ActivityNameService.class);
    @Autowired
    ActivityNameRepository activityNameRepository;

    @Autowired
    Mapper mapper;

    public ActivityName findOne(Long id){
        logger.debug("findOne(): id = {}",id);
        return activityNameRepository.findOne(id);
    }

    public List<ActivityName> findAll(){
        logger.debug("findAll()");
        return  activityNameRepository.findAll();
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return activityNameRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return activityNameRepository.count();
    }

    @Transactional
    public ActivityName Save(ActivityName activityName){
        logger.debug("save()");
        return activityNameRepository.save(activityName);
    }
    @Transactional
    public ActivityName update(ActivityName activityName){
        logger.debug("update()");
        ActivityName activityName1 = activityNameRepository.findOne(activityName.getId());
        mapper.map(activityName,activityName1);
        //buyer1 = buyer;
        return activityName1;
    }
    @Transactional
    public void delete(Long id){
        logger.debug("delete()");
        activityNameRepository.delete(id);
    }
}
