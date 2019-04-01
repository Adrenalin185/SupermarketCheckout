package checkout.services;

import checkout.entity.Deal;
import checkout.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DealService {

    @Autowired
    private DealRepository dealRepository;

    public ResponseEntity addDeal(Deal deal) {

        dealRepository.save(deal);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
