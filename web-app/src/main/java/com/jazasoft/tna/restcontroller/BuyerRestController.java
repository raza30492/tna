package com.jazasoft.tna.restcontroller;

import com.jazasoft.tna.assembler.ActivityAssembler;
import com.jazasoft.tna.assembler.BuyerAssembler;
import com.jazasoft.tna.assembler.LabelAssembler;
import com.jazasoft.tna.assembler.OrderAssembler;
import com.jazasoft.tna.dto.OrderDto;
import com.jazasoft.tna.dto.RestError;
import com.jazasoft.tna.entity.Activity;
import com.jazasoft.tna.entity.Buyer;
import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.entity.Order;
import com.jazasoft.tna.service.ActivityService;
import com.jazasoft.tna.service.BuyerService;
import com.jazasoft.tna.service.LabelService;
import com.jazasoft.tna.service.OrderService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.jazasoft.tna.ApiUrls;

/**
 * Created by mtalam on 6/15/2017.
 */
@RestController
@RequestMapping(ApiUrls.ROOT_URL_BUYERS)
public class BuyerRestController {

    private final Logger logger = LoggerFactory.getLogger(BuyerRestController.class);

    @Autowired
    BuyerService buyerService;

    @Autowired Mapper mapper;

    @Autowired BuyerAssembler buyerAssembler;

    @Autowired LabelService labelService;

    @Autowired LabelAssembler labelAssembler;

    @Autowired OrderService orderService;

    @Autowired OrderAssembler orderAssembler;

    @Autowired ActivityService activityService;

    @Autowired ActivityAssembler activityAssembler;

    @GetMapping
    public ResponseEntity<?> listAllBuyers() {
        logger.debug("listAllBuyers()");
        List<Buyer> buyers = buyerService.findAll();
        return new ResponseEntity<Object>(buyerAssembler.toResources(buyers), HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_BUYERS_BUYER)
    public ResponseEntity<?> getBuyer(@PathVariable("buyerId") long id) {
        logger.debug("getBuyer(): id = {}", id);
        Buyer buyer = buyerService.findOne(id);
        if (buyer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(buyerAssembler.toResource(buyer), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody Buyer buyer) {
        logger.debug("createBuyer():\n {}", buyer.toString());
        buyer = buyerService.Save(buyer);
        Link selfLink = linkTo(BuyerRestController.class).slash(buyer.getId()).withSelfRel();
        return ResponseEntity.created(URI.create(selfLink.getHref())).build();
    }

    @PutMapping(ApiUrls.URL_BUYERS_BUYER)
    public ResponseEntity<?> updateUser(@PathVariable("buyerId") long id, @Validated @RequestBody Buyer buyer) {
        logger.debug("updateBuyer(): id = {} \n {}", id, buyer);
        if (!buyerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        buyer.setId(id);
        buyer = buyerService.update(buyer);
        return new ResponseEntity<>(buyerAssembler.toResource(buyer), HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_BUYERS_BUYER)
    public ResponseEntity<Void> deleteUser(@PathVariable("buyerId") long id) {
        logger.debug("deleteBuyer(): id = {}", id);
        if (!buyerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        buyerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /////////////////// LABEL APIs /////////////////////////////////////////////
    @GetMapping(ApiUrls.URL_BUYERS_BUYER_LABELS)
    public ResponseEntity<?> loadBuyerLabels(
            @PathVariable("buyerId") Long buyerId) {
        logger.debug("loadBuyerLabels(): buyerId = {}", buyerId);
        // Category category = categoryService.findOne(categoryId, false, false);
        Buyer buyer = buyerService.findOne(buyerId);

        if (buyer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Label> labels = labelService.findLabelByBuyer(buyer);
        return new ResponseEntity<Object>(labelAssembler.toResources(labels), HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL)
    public ResponseEntity<?> loadBuyerLabel(
            @PathVariable("buyerId") Long buyerId,
            @PathVariable("labelId") Long labelId) {
        logger.debug("loadBuyerLabel(): buyerId = {} , labelId = {}", buyerId, labelId);
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);

        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in " + buyer;
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(labelAssembler.toResource(label), HttpStatus.OK);

    }

    @PostMapping(ApiUrls.URL_BUYERS_BUYER_LABELS)
    public ResponseEntity<?> createBuyerLabel(
            @PathVariable("buyerId") Long buyerId,
            @Valid @RequestBody Label label
    ) {
        logger.debug("createBuyerLabel(): buyerId= {} , label = \n {}", buyerId, label.toString());
        Buyer buyer = buyerService.findOne(buyerId);

        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        label.setBuyer(buyer);
        label = labelService.save(label);
        Link selfLink = linkTo(methodOn(BuyerRestController.class).createBuyerLabel(buyerId, label)).slash(label.getId()).withSelfRel();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(selfLink.getHref()));
        return new ResponseEntity<>(labelAssembler.toResource(label), headers, HttpStatus.CREATED);
    }

    @PutMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL)
    public ResponseEntity<?> updateBuyerLabel(
            @PathVariable("buyerId") long buyerId,
            @PathVariable("labelId") Long labelId,
            @Valid @RequestBody Label label) {

        logger.debug("updateBuyerLabel(): buyerId = {} , labelId = {}, {}", buyerId, labelId, label);
        System.out.println("buyerId = " + buyerId + " labelId= " + labelId);
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Label label1 = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label1 == null) {
            String msg = "label with id = " + labelId + " not found in " + buyer.getId();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        label.setId(labelId);
        label.setBuyer(buyer);
        label = labelService.update(label);
        return new ResponseEntity<>(labelAssembler.toResource(label), HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL)
    public ResponseEntity<?> deleteBuyerLabel(@PathVariable("buyerId") long buyerId, @PathVariable("labelId") Long labelId) {

        logger.debug("deleteBuyerLabel(): buyerId = {} , labelId = {}", buyerId, labelId);
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in " + buyer;
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        labelService.delete(labelId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    //////////////////////////// ACTIVITY API /////////////////////////////////////////////////////////////////////////
@GetMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ACTIVITIES)
public ResponseEntity<?> LoadBuyersLabelActivities(
        @PathVariable("buyerId") Long buyerId,
        @PathVariable("labelId") Long labelId) {
    logger.debug("LoadBuyersLabelActivities(): buyerId = {}, labelId = {}", buyerId, labelId);
    Buyer buyer = buyerService.findOne(buyerId);
    RestError error;
    if (buyer == null) {
        error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    Label label = buyer.getLabels().stream()
            .filter(l -> l.getId().equals(labelId))
            .findAny().orElse(null);
    if (label == null) {
        String msg = "label with id = " + label + " not found in " + buyer;
        error = new RestError(404, 404, msg, "", "");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    List<Activity> activities = activityService.findAllByLabel(label);
    return new ResponseEntity<>(activityAssembler.toResources( activities), HttpStatus.OK);
}

@GetMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ACTIVITIES_ACTIVITY)
  public  ResponseEntity<?> LoadBuyersLabelActivity(@PathVariable("buyerId") Long buyerId,
                                                    @PathVariable("labelId") Long labelId,
                                                    @PathVariable("activityId") Long activityId){
    logger.debug("LoadBuyersLabelActivity(): buyerId = {}, labelId = {}, activityId", buyerId, labelId,activityId);
    Buyer buyer = buyerService.findOne(buyerId);
    RestError error;
    if (buyer == null) {
        error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    Label label = buyer.getLabels().stream()
            .filter(l -> l.getId().equals(labelId))
            .findAny().orElse(null);
    if (label == null) {
        String msg = "label with id = " + label + " not found in " + buyer;
        error = new RestError(404, 404, msg, "", "");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    Activity activity = label.getActivities().stream()
            .filter(a -> a.getId().equals(activityId))
            .findAny().orElse(null);

    if (activity == null) {
        String msg = "Activity with id = " + activityId + " not found in " + label + " and  " + buyer;
        error = new RestError(404, 404, msg, "", "");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(activityAssembler.toResource(activity), HttpStatus.OK);
}

    @PostMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ACTIVITIES)
    public ResponseEntity<?> createBuyerLabelActivity
            (@PathVariable("buyerId") Long buyerId,
             @PathVariable("labelId") Long labelId,
             @Valid @RequestBody Activity activity){

        logger.debug("createBuyerLabelActivity(): buyerId= {} , labelId = {}, {}", buyerId, labelId, activity);
        /*///////Checking for Duplicate Id///////*/
        if (activity.getId() != null && activityService.exists(activity.getId())) {
            String msg = "Duplicate Id.";
            RestError error = new RestError(409, 409, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        /*///////////////////checking if Buyer and Label exist ////////////*/
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        System.out.println("lebel is////////"+label);
        activity.setLabel(label);
        activity = activityService.Save(activity);
        return new ResponseEntity<>(activityAssembler.toResource(activity), HttpStatus.OK);
    }

    @PutMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ACTIVITIES_ACTIVITY)
    public ResponseEntity<?> updateBuyerLabelActivity(
            @PathVariable("buyerId") long buyerId,
            @PathVariable("labelId") Long labelId,
            @PathVariable("activityId") Long activityId,
            @Valid @RequestBody Activity activity) {

        logger.debug("updateBuyerLabelActivity(): buyerId= {} , labelId = {}, activityId = {}, {}", buyerId, labelId,activityId, activity);
        /*///////////////////checking if Buyer, Label and Activity exist ////////////*/
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Activity activity1 = label.getActivities().stream()
                .filter(a -> a.getId().equals(activityId))
                .findAny().orElse(null);

        if (activity1 == null) {
            String msg = "Activity with id = " + activityId + " not found in Label:  " + label.getLabel() + ", Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        activity.setId(activityId);
        activity.setLabel(label);
        activity = activityService.update(activity);
        return new ResponseEntity<>(activityAssembler.toResource(activity), HttpStatus.OK);
    }


    @DeleteMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ACTIVITIES_ACTIVITY)
    public ResponseEntity<?> deleteBuyerLabelActivity(
            @PathVariable("buyerId") long buyerId,
            @PathVariable("labelId") Long labelId,
            @PathVariable("activityId") Long activityId) {

        logger.debug("deleteBuyerLabelActivity(): buyerId= {} , labelId = {}, activityId = {}, {}", buyerId, labelId, activityId);

        /*///////////////////checking if Buyer, Label and Order exist ////////////*/
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Activity activity = label.getActivities().stream()
                .filter(o -> o.getId().equals(activityId))
                .findAny().orElse(null);

        if (activity == null) {
            String msg = "Activity with id = " + activityId + " not found in Label:  " + label.getLabel() + ", Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        activityService.delete(activityId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // / /////////////////// /* Api For Order  *///////////////////////////////////////////////////////////////////

    @GetMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ORDERS)
    public ResponseEntity<?> LoadBuyersBuyerOrders(
            @PathVariable("buyerId") Long buyerId,
            @PathVariable("labelId") Long labelId) {
        logger.debug("LoadBuyersBuyerOrders(): buyerId = {}, labelId = {}", buyerId, labelId);
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "label with id = " + label + " not found in " + buyer;
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<OrderDto> orders = orderService.findAllByLabel(label);
        return new ResponseEntity<>(orderAssembler.toResources( orders), HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ORDERS_ORDER)
    public ResponseEntity<?> loadBuyerLabelOrder(
            @PathVariable("buyerId") Long buyerId,
            @PathVariable("labelId") Long labelId,
            @PathVariable("orderId") Long orderId) {
        logger.debug("loadBuyerLabelOrder(): buyerId = {} , labelId = {}", buyerId, labelId);
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in " + buyer;
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Order order = label.getOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findAny().orElse(null);
        if (order == null) {
            String msg = "Order with id = " + orderId + " not found in " + label + " and  " + buyer;
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderAssembler.toResource(mapper.map(order, OrderDto.class)), HttpStatus.OK);
    }

    @PostMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ORDERS)

    public ResponseEntity<?> createBuyerLabelOrder(
            @PathVariable("buyerId") Long buyerId,
            @PathVariable("labelId") Long labelId,
            @Valid @RequestBody OrderDto orderDto
    ) {
        logger.debug("createBuyerLabelOrder(): buyerId= {} , labelId = {}, {}", buyerId, labelId, orderDto);
        /*///////Checking for Duplicate Id///////*/
        if (orderDto.getId() != null && orderService.exists(orderDto.getId())) {
            String msg = "Duplicate Id.";
            RestError error = new RestError(409, 409, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.CONFLICT);
        }

        /*///////////////////checking if Buyer and Label exist ////////////*/
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        System.out.println("lebel is////////"+label);
        orderDto.setLabel(label);
        orderDto = orderService.Save(orderDto);


        return new ResponseEntity<>(orderAssembler.toResource(orderDto), HttpStatus.OK);
    }

    @PutMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ORDERS_ORDER)
    public ResponseEntity<?> updateBuyerLabelOrder(
            @PathVariable("buyerId") long buyerId,
            @PathVariable("labelId") Long labelId,
            @PathVariable("orderId") Long orderId,
            @Valid @RequestBody OrderDto orderDto) {

        logger.debug("updateBuyerLabelOrder(): buyerId= {} , labelId = {}, orderId = {}, {}", buyerId, labelId,orderId, orderDto);


        /*///////////////////checking if Buyer, Label and Order exist ////////////*/
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Order order = label.getOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findAny().orElse(null);

        if (order == null) {
            String msg = "Order with id = " + orderId + " not found in Label:  " + label.getLabel() + ", Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        orderDto.setId(orderId);
        orderDto.setLabel(label);
        orderDto = orderService.update(orderDto);
        return new ResponseEntity<>(orderAssembler.toResource(orderDto), HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL_ORDERS_ORDER)
    public ResponseEntity<?> deleteBuyerLabelOrder(
            @PathVariable("buyerId") long buyerId,
            @PathVariable("labelId") Long labelId,
            @PathVariable("orderId") Long orderId){

        logger.debug("deleteBuyerLabelOrder(): buyerId= {} , labelId = {}, orderId = {}, {}", buyerId, labelId,orderId);
        /*///////////////////checking if Buyer, Label and Order exist ////////////*/
        Buyer buyer = buyerService.findOne(buyerId);
        RestError error;
        if (buyer == null) {
            error = new RestError(404, 404, "Buyer with id = " + buyerId + " not found", "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

        }
        Label label = buyer.getLabels().stream()
                .filter(l -> l.getId().equals(labelId))
                .findAny().orElse(null);
        if (label == null) {
            String msg = "Label with id = " + labelId + " not found in Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        Order order = label.getOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findAny().orElse(null);

        if (order == null) {
            String msg = "Order with id = " + orderId + " not found in Label:  " + label.getLabel() + ", Buyer: " + buyer.getName();
            error = new RestError(404, 404, msg, "", "");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        orderService.delete(orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
