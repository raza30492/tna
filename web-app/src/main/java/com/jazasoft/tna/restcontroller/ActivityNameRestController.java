package com.jazasoft.tna.restcontroller;

import com.jazasoft.tna.assembler.ActivityNameAssembler;
import com.jazasoft.tna.entity.ActivityName;
import com.jazasoft.tna.respository.ActivityNameRepository;
import com.jazasoft.tna.service.ActivityNameService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Mojahid Hussain on 17-Jun-17.
 */
@RestController
@RequestMapping("/api/activityNames")
public class ActivityNameRestController {

    private final Logger logger = LoggerFactory.getLogger(ActivityNameRestController.class);

    @Autowired
    ActivityNameService activityNameService;

    @Autowired
    ActivityNameAssembler activityNameAssembler;

    @GetMapping
    public ResponseEntity<?> listAllActivityNames(){
        logger.debug("listAllActivityNames()");
        List<ActivityName> activityNames = activityNameService.findAll();
        return new ResponseEntity<Object>(activityNameAssembler.toResources(activityNames), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getActivityName(@PathVariable("id") long id) {
        logger.debug("getActivityName(): id = {}",id);
        ActivityName activityName = activityNameService.findOne(id);
        if (activityName == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(activityNameAssembler.toResource(activityName), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createActivityName(@Valid @RequestBody ActivityName activityName) {
        logger.debug("createActivityName():\n {}", activityName.toString());
        activityName = activityNameService.Save(activityName);
        Link selfLink = linkTo(BuyerRestController.class).slash(activityName.getId()).withSelfRel();
        return ResponseEntity.created(URI.create(selfLink.getHref())).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateActivityName(@PathVariable("id") long id,@Validated @RequestBody ActivityName activityName) {
        logger.debug("updateActivityName(): id = {} \n {}",id,activityName);
        if (!activityNameService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        activityName.setId(id);
        activityName = activityNameService.update(activityName);
        return new ResponseEntity<>(activityNameAssembler.toResource(activityName), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteActivityName(@PathVariable("id") long id) {
        logger.debug("deleteActivityName(): id = {}",id);
        if (!activityNameService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        activityNameService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
