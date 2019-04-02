package checkout.services;

import checkout.entity.BasketItem;
import checkout.entity.Deal;
import checkout.entity.Receipt;
import checkout.entity.SKU;
import checkout.repository.BasketItemRepository;
import checkout.repository.DealRepository;
import checkout.repository.ReceiptRepository;
import checkout.repository.SKURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CheckoutService {

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private SKURepository skuRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    @Autowired
    private DealRepository dealRepository;

    public ResponseEntity startNewBasket(String sKUID) {

        long receiptId = createReceipt();
        return addItemToBasket(sKUID, receiptId);
    }

    public ResponseEntity addNewItemToBasket(String sKUID, long receiptId) {

        return addItemToBasket(sKUID, receiptId);
    }

    public ResponseEntity getTotal(long receiptId) {

        List<BasketItem> basketItemList = basketItemRepository.findAllItemsByReceiptId(receiptId);

        if (!basketItemList.isEmpty()){

            double priceTotal = 0;

            for (BasketItem basketItem: basketItemList) {

                priceTotal = priceTotal + basketItem.getSku().getPrice();
            }

            return ResponseEntity.ok().body(priceTotal);
        }

        return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
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

    private double totalsCalculator(List<BasketItem> basketItems){

        Map<String, Integer> itemCount = new HashMap<>();
        double cost = 0;

        // creates list of how many skus are in the basket
        for (BasketItem basketItem: basketItems) {

            itemCount.put(basketItem.getSku().getsKUID(), +1);
        }

        //check if there are any deals in the basket
        for (String key : itemCount.keySet()){
            Optional<Deal> deal = dealRepository.findDealBySku(key);
            int count = itemCount.get(key);

            if (deal.isPresent()){
                int required = deal.get().getProductsRequired();
                double dealPrice = deal.get().getNewPrice();

                while (count >= required){
                    cost = cost + dealPrice;
                    count = count - required;
                }
            }

            double remainingItems = count * skuRepository.getOne(key).getPrice();
            cost = cost + remainingItems;
        }

        return cost;
    }
}
