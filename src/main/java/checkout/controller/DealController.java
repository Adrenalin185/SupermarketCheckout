package checkout.controller;

import checkout.entity.Deal;
import checkout.services.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class DealController {

    @Autowired
    private DealService dealService;

    @PutMapping("/add-deal")
    public ResponseEntity addDeal(@RequestBody @Valid Deal deal){
        return dealService.addDeal(deal);
    }
}
