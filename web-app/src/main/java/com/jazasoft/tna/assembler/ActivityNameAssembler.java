package com.jazasoft.tna.assembler;

import com.jazasoft.tna.entity.ActivityName;
import com.jazasoft.tna.restcontroller.ActivityNameRestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Mojahid Hussain on 17-Jun-17.
 */
@Component
public class ActivityNameAssembler  extends ResourceAssemblerSupport<ActivityName,Resource> {

    public ActivityNameAssembler(){
        super(ActivityNameRestController.class, Resource.class);
    }

    @Override
    public Resource toResource(ActivityName activityName) {
        return new Resource<>(activityName, linkTo(methodOn(ActivityNameRestController.class).getActivityName(activityName.getId())).withSelfRel());

    }

    @Override
    public List<Resource> toResources(Iterable<? extends ActivityName> activityNames) {
        List<Resource> resources = new ArrayList<>();
        for(ActivityName activityName : activityNames) {
            resources.add(new Resource<>(activityName, linkTo(methodOn(ActivityNameRestController.class).getActivityName(activityName.getId())).withSelfRel()));
        }
        return resources;
    }
}
