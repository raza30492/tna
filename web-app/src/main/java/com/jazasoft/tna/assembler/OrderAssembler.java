package com.jazasoft.tna.assembler;

import com.jazasoft.tna.dto.OrderDto;
import com.jazasoft.tna.entity.Order;
import com.jazasoft.tna.restcontroller.BuyerRestController;
import com.jazasoft.tna.service.OrderService;
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
 * Created by Mojahid Hussain on 17-Jun-17.
 */
@Component
public class OrderAssembler extends ResourceAssemblerSupport<OrderDto, Resource>{

    public OrderAssembler() {
        super(BuyerRestController.class, Resource.class);
    }

    @Override
    public Resource toResource(OrderDto orderDto) {
        Collection<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(BuyerRestController.class)
                .loadBuyerLabelOrder(orderDto.getLabel().getBuyer().getId(), orderDto.getLabel().getId(),orderDto.getId()))
                .withSelfRel()
        );
        return new Resource<>(orderDto, links);
    }

}
