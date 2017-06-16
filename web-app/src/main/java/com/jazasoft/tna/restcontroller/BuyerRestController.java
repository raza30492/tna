package com.jazasoft.tna.restcontroller;

import com.jazasoft.tna.assembler.BuyerAssembler;
import com.jazasoft.tna.entity.Buyer;
import com.jazasoft.tna.service.BuyerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by mtalam on 6/15/2017.
 */
@RestController
@RequestMapping("/api/buyers")
public class BuyerRestController {

    private final Logger logger = LoggerFactory.getLogger(BuyerRestController.class);

    @Autowired
    BuyerService buyerService;

    @Autowired
    BuyerAssembler buyerAssembler;

    @GetMapping
    public ResponseEntity<?> listAllBuyers(){
        logger.debug("listAllBuyers()");
        List<Buyer> buyers = buyerService.findAll();
        return new ResponseEntity<Object>(buyerAssembler.toResources(buyers), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getBuyer(@PathVariable("id") long id) {
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

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") long id,@Validated @RequestBody Buyer buyer) {
        logger.debug("updateBuyer(): id = {} \n {}",id,buyer);
        if (!buyerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        buyer.setId(id);
        buyer = buyerService.update(buyer);
        return new ResponseEntity<>(buyerAssembler.toResource(buyer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
        logger.debug("deleteBuyer(): id = {}",id);
        if (!buyerService.exists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        buyerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
