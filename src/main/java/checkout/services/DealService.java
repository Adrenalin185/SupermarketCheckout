package checkout.services;

import checkout.entity.Deal;
import checkout.entity.SKU;
import checkout.repository.DealRepository;
import checkout.repository.SKURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DealService {

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private SKURepository skuRepository;

    public ResponseEntity addDeal(Deal deal) {

        Optional<SKU> sku = skuRepository.findById(deal.getSku().getsKUID());


        if (sku.isPresent()) {
            dealRepository.save(deal);
            return ResponseEntity.ok().body(HttpStatus.OK);
        }

        return ResponseEntity.badRequest().body("SKU doesn't exist");
    }
}
