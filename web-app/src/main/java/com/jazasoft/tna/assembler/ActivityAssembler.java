package com.jazasoft.tna.assembler;

import com.jazasoft.tna.entity.Activity;
import com.jazasoft.tna.entity.ActivityName;
import com.jazasoft.tna.restcontroller.BuyerRestController;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Mojahid Hussain on 21-Jun-17.
 */
@Component
public class ActivityAssembler  extends ResourceAssemblerSupport<Activity,Resource> {

    public  ActivityAssembler(){ super(BuyerRestController.class,Resource.class);}

    @Override
    public Resource toResource(Activity activity) {
        return new Resource<>(activity, linkTo(methodOn(BuyerRestController.class).LoadBuyersLabelActivities(activity.getLabel().getBuyer().getId(),activity.getLabel().getId())).withSelfRel());

    }


}
