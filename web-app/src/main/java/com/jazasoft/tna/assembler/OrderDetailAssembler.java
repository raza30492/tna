package com.jazasoft.tna.assembler;

import com.jazasoft.tna.entity.OrderDetail;
import com.jazasoft.tna.restcontroller.OrderDetailRestController;
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
 * Created by Mojahid Hussain on 23-Jun-17.
 */
@Component
public class OrderDetailAssembler extends ResourceAssemblerSupport<OrderDetail,Resource> {

    public OrderDetailAssembler(){ super(OrderDetailAssembler.class,Resource.class);}

    @Override
    public Resource toResource(OrderDetail orderDetail) {
        return new Resource(orderDetail);
    }

    @Override
    public List<Resource> toResources(Iterable<? extends OrderDetail> orderDetails) {
        List<Resource> resources = new ArrayList<>();
        for(OrderDetail orderDetail : orderDetails) {
            resources.add(toResource(orderDetail));
        }
        return resources;
    }
}
