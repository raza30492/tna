package com.jazasoft.tna.assembler;

import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.restcontroller.BuyerRestController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by mtalam on 6/17/2017.
 */
@Component
public class LabelAssembler extends ResourceAssemblerSupport<Label,Resource> {

    public LabelAssembler() {
        super(BuyerRestController.class, Resource.class);
    }

    @Override
    public Resource toResource(Label label) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(BuyerRestController.class)
                .loadBuyerLabel(label.getBuyer().getId(),label.getId()))
                .withSelfRel()
        );
        return new Resource<>(label, links);
    }
}

