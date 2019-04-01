package checkout.services;

import checkout.entity.BasketItem;
import checkout.entity.Receipt;
import checkout.entity.SKU;
import checkout.repository.BasketItemRepository;
import checkout.repository.ReceiptRepository;
import checkout.repository.SKURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CheckoutService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private SKURepository skuRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    public ResponseEntity startNewBasket(String sKUID) {

        long receiptId = createReceipt();
        return addItemToBasket(sKUID, receiptId);
    }

    public ResponseEntity addNewItemToBasket(String sKUID, long receiptId) {

        return addItemToBasket(sKUID, receiptId);
    }

    private long createReceipt() {

        Receipt receipt = new Receipt();
        return receiptRepository.save(receipt).getId();
    }

    private ResponseEntity addItemToBasket(String sKUID, long receiptId) {

        Optional<SKU> sku = skuRepository.findById(sKUID);
        Optional<Receipt> receipt = receiptRepository.findById(receiptId);

        if (sku.isPresent() && receipt.isPresent()){

            Optional<BasketItem> basketItem = basketItemRepository.findById(basketItemRepository.save(new BasketItem(receipt.get(), sku.get())).getId());
            return ResponseEntity.ok().body(HttpStatus.OK);

        }

        return ResponseEntity.badRequest().body("Could not add unidentified SKU: " + sKUID);
    }
}
