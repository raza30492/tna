package com.jazasoft.tna.restcontroller;

import com.jazasoft.tna.assembler.BuyerAssembler;
import com.jazasoft.tna.assembler.LabelAssembler;
import com.jazasoft.tna.dto.RestError;
import com.jazasoft.tna.entity.Buyer;
import com.jazasoft.tna.entity.Label;
import com.jazasoft.tna.service.BuyerService;
import com.jazasoft.tna.service.LabelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

    @Autowired
    BuyerAssembler buyerAssembler;
    @Autowired
    LabelService labelService;
    @Autowired
    LabelAssembler labelAssembler;

    @GetMapping
    public ResponseEntity<?> listAllBuyers(){
        logger.debug("listAllBuyers()");
        List<Buyer> buyers = buyerService.findAll();
        return new ResponseEntity<Object>(buyerAssembler.toResources(buyers), HttpStatus.OK);
    }

    @GetMapping(ApiUrls.URL_BUYERS_BUYER)
    public ResponseEntity<?> getBuyer(@PathVariable("buyerId") long id) {
        logger.debug("getBuyer(): id = {}",id);
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
    public ResponseEntity<?> updateUser(@PathVariable("buyerId") long id,@Validated @RequestBody Buyer buyer) {
        logger.debug("updateBuyer(): id = {} \n {}",id,buyer);
        if (!buyerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        buyer.setId(id);
        buyer = buyerService.update(buyer);
        return new ResponseEntity<>(buyerAssembler.toResource(buyer), HttpStatus.OK);
    }

    @DeleteMapping(ApiUrls.URL_BUYERS_BUYER)
    public ResponseEntity<Void> deleteUser(@PathVariable("buyerId") long id) {
        logger.debug("deleteBuyer(): id = {}",id);
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
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
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
        Link selfLink= linkTo(methodOn(BuyerRestController.class).createBuyerLabel(buyerId, label)).slash(label.getId()).withSelfRel();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(selfLink.getHref()));
        return new ResponseEntity<>(labelAssembler.toResource(label),headers,HttpStatus.CREATED);
    }

    @PutMapping(ApiUrls.URL_BUYERS_BUYER_LABELS_LABEL)
    public ResponseEntity<?> updateBuyerLabel(
            @PathVariable("buyerId") long buyerId,
            @PathVariable("labelId") Long labelId,
            @Valid @RequestBody Label label) {

        logger.debug("updateBuyerLabel(): buyerId = {} , labelId = {}, {}", buyerId, labelId, label);
        System.out.println("buyerId = "+ buyerId+ " labelId= "+ labelId);
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



}
