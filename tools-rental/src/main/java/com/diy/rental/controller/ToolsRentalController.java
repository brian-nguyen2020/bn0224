package com.diy.rental.controller;

import com.diy.rental.http.CheckoutRequest;
import com.diy.rental.http.ResponseEnvelope;
import com.diy.rental.service.CheckoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/rental")
public class ToolsRentalController {
    private final CheckoutService checkoutService;
    @Autowired
    public ToolsRentalController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }
    @PostMapping("/checkout")
    public ResponseEntity<ResponseEnvelope> checkOut(@RequestBody CheckoutRequest checkoutRequest) throws  Exception {
        log.debug("Enter checkOut() {} with ", checkoutRequest);
        return new ResponseEntity<>(checkoutService.doCheckout(checkoutRequest), HttpStatus.OK);
    }
}
