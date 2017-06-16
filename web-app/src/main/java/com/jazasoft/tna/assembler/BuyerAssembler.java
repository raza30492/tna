package com.jazasoft.tna.assembler;

import com.jazasoft.tna.entity.Buyer;
import com.jazasoft.tna.restcontroller.BuyerRestController;
import com.jazasoft.tna.restcontroller.UserRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by mtalam on 6/15/2017.
 */
@Component
public class BuyerAssembler extends ResourceAssemblerSupport<Buyer,Resource> {

    public BuyerAssembler(){ super(BuyerRestController.class,Resource.class);}

    @Override
    public Resource toResource(Buyer buyer) {
        //list of  Links
        Link selfLink = linkTo(methodOn(BuyerRestController.class).getBuyer(buyer.getId())).withSelfRel();
        return new Resource<>(buyer, selfLink);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends Buyer> entities) {
        return super.toResources(entities);
    }
}
