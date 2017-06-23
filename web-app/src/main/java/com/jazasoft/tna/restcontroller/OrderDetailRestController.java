package com.jazasoft.tna.restcontroller;

import com.jazasoft.tna.ApiUrls;
import com.jazasoft.tna.assembler.OrderDetailAssembler;
import com.jazasoft.tna.entity.OrderDetail;
import com.jazasoft.tna.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Mojahid Hussain on 23-Jun-17.
 */
@RestController
@RequestMapping(ApiUrls.ROOT_URL_ORDER_DETAILS)
public class OrderDetailRestController {

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    OrderDetailAssembler orderDetailAssembler;

    @PatchMapping
    public ResponseEntity<?> updateBatch(@RequestBody List<OrderDetail> orderDetailList){
        orderDetailService.updateBatch(orderDetailList);
       return new ResponseEntity<>(orderDetailAssembler.toResources(orderDetailList), HttpStatus.OK);
    }
}
