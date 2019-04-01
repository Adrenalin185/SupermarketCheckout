package checkout.services;

import checkout.entity.SKU;
import checkout.repository.SKURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    @Autowired
    SKURepository skuRepository;

    public ResponseEntity addSKU(SKU newSKU) {

        if(!skuRepository.findById(newSKU.getsKUID()).isPresent()){
            skuRepository.save(newSKU);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body("SKU already exists");
    }

    public ResponseEntity amendSKU(SKU sku) {

        if(skuRepository.findById(sku.getsKUID()).isPresent()){
            skuRepository.saveAndFlush(sku);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body("SKU doesn't exist");
    }
}
