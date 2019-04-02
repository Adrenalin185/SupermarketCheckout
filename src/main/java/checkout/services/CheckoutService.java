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

            double priceTotal = totalsCalculator(basketItemList);


            return ResponseEntity.ok().body(priceTotal);
        }

        return ResponseEntity.badRequest().body("Basket for id given is either empty or does not exist");
    }

    private long createReceipt() {

        Receipt receipt = new Receipt();
        return receiptRepository.save(receipt).getId();
    }

    private ResponseEntity addItemToBasket(String sKUID, long receiptId) {

        Optional<Receipt> receipt = receiptRepository.findById(receiptId);

        if (receipt.isPresent()){

            Optional<SKU> sku = skuRepository.findById(sKUID);

            if (sku.isPresent()) {

                basketItemRepository.save(new BasketItem(receipt.get(), sku.get()));
                return ResponseEntity.ok().body(receipt.get().getId());
            }

            return ResponseEntity.badRequest().body("Could add item with SKU: " + sKUID + " to cart");
        }

        return ResponseEntity.badRequest().body("Receipt doesn't exist");
    }

    private double totalsCalculator(List<BasketItem> basketItems){
        Map<String, Integer> itemCount = new HashMap<>();
        double cost = 0;

        for (BasketItem basketItem: basketItems) {

            String skuId = basketItem.getSku().getsKUID();

            if (!itemCount.containsKey(skuId)){

                itemCount.put(skuId, 1);
            } else {
                itemCount.put(skuId, itemCount.get(skuId)+1);
            }


        }

        for (String key : itemCount.keySet()){

            System.out.println(key);

            Optional<SKU> sku = skuRepository.findById(key);

            if (sku.isPresent()) {

                Optional<Deal> deal = dealRepository.findBySku(sku.get());
                int count = itemCount.get(key);
                System.out.println(count);

                if (deal.isPresent()) {

                    int required = deal.get().getProductsRequired();
                    double dealPrice = deal.get().getNewPrice();

                    while (count >= required) {
                        cost = cost + dealPrice;
                        count = count - required;
                    }
                }

                double remainingItems = count * skuRepository.findById(key).get().getPrice();
                cost = cost + remainingItems;
            }
        }

        return cost;
    }
}
