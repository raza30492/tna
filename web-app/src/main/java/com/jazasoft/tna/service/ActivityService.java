package com.jazasoft.tna.service;

import com.jazasoft.tna.entity.Activity;
import com.jazasoft.tna.entity.ActivityName;
import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.respository.ActivityNameRepository;
import com.jazasoft.tna.respository.ActivityRepository;
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
public class ActivityService {

    private final Logger logger = LoggerFactory.getLogger(ActivityService.class);
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    Mapper mapper;
    @Autowired
    ActivityNameRepository activityNameRepository;

    public Activity findOne(Long id){
        logger.debug("findOne(): id = {}",id);
        return activityRepository.findOne(id);
    }

    public List<Activity> findAll(){
        logger.debug("findAll()");
        return  activityRepository.findAll();
    }

    public List<Activity> findAllByLabel(Label label){
        logger.debug("findAllByLabel");
        return  activityRepository.findByLabel(label);
    }

    public Boolean exists(Long id) {
        logger.debug("exists(): id = ",id);
        return activityRepository.exists(id);
    }

    public Long count(){
        logger.debug("count()");
        return activityRepository.count();
    }

    @Transactional
    public Activity Save(Activity activity){
        logger.debug("save()");
        ActivityName activityName = activityNameRepository.findOne(activity.getActivityNameId());
        activity.setActivityName(activityName);
        return activityRepository.save(activity);
    }
    @Transactional
    public Activity update(Activity activity){
        logger.debug("update()");
        ActivityName activityName = activityNameRepository.findOne(activity.getActivityNameId());
        activity.setActivityName(activityName);
        Activity activity1 = activityRepository.findOne(activity.getId());
        mapper.map(activity,activity1);
        return activity1;
    }
    @Transactional
    public void delete(Long id){
        logger.debug("delete()");
        activityRepository.delete(id);
    }

}
