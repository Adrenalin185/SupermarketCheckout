package checkout.controller;

import checkout.services.CheckoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class CheckoutController {

    @Autowired
    private CheckoutService checkoutService;

    @PutMapping("/scanneditem")
    public ResponseEntity startNewBasket(@RequestBody @Valid String sKUID){
        return checkoutService.startNewBasket(sKUID);
    }

    @PostMapping("/scanneditem/{receiptId}")
    public ResponseEntity addItemToBasket(@RequestBody @Valid String sKUID, @PathVariable long receiptId){
        return checkoutService.addNewItemToBasket(sKUID, receiptId);
    }


}
