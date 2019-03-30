package checkout.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CheckoutController {

    @PostMapping("/scanneditem")
    public ResponseEntity addItemToBasket(){
        return ResponseEntity.ok().body(HttpStatus.OK.value());
    }
}
