package checkout.controller;

import checkout.entity.SKU;
import checkout.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class StockController {

    @Autowired
    StockService stockService;

    @PutMapping("/add-sku")
    public ResponseEntity addNewSKU(@RequestBody @Valid SKU newSKU){
        return stockService.addSKU(newSKU);
    }

    @PostMapping("/amend-sku")
    public ResponseEntity amendSKU(@RequestBody @Valid SKU sku) {
        return stockService.amendSKU(sku);
    }
}
