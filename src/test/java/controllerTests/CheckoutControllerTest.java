package controllerTests;

import checkout.Application;
import checkout.controller.CheckoutController;
import checkout.entity.Deal;
import checkout.entity.SKU;
import checkout.repository.BasketItemRepository;
import checkout.repository.DealRepository;
import checkout.repository.ReceiptRepository;
import checkout.repository.SKURepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CheckoutControllerTest {

    @Autowired
    private CheckoutController checkoutController;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private SKURepository skuRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    private SKU skuA = new SKU("A", 0.5);
    private SKU skuB = new SKU("B", 0.3);
    private Deal dealA = new Deal(skuA, 3, 1.3);

    @Before
    public void cleanAndRepopulateDatabaseBetweenTests(){
        dealRepository.deleteAll();
        basketItemRepository.deleteAll();
        receiptRepository.deleteAll();
        skuRepository.deleteAll();

        skuRepository.save(skuA);
        skuRepository.save(skuB);
        dealRepository.save(dealA);
    }

    @Test
    public void whenFirstItemScanned_ThenReturnHttpResponseOkValueAndReceiptId() {

        assertEquals(ResponseEntity.ok().body(1003L), checkoutController.startNewBasket("A"));
    }

    @Test
    public void whenFirstItemScannedDoesntExist_ThenReturnHttpStatus400(){

        assertEquals(ResponseEntity.badRequest().body("Could add item with SKU: G to cart"), checkoutController.startNewBasket("G"));
    }

    @Test
    public void whenAddingSecondItemToBasket_ThenReturnHttpStatus200(){

        ResponseEntity newBasket = checkoutController.startNewBasket("A");
        assertEquals(ResponseEntity.ok().body(newBasket.getBody()), checkoutController.addItemToBasket("A", Integer.parseInt(newBasket.getBody().toString())));
    }

    @Test
    public void whenAddingInvalidSecondItemToBasket_ThenReturnHttpStatus400(){

        ResponseEntity newBasket = checkoutController.startNewBasket("A");
        assertEquals(ResponseEntity.badRequest().body("Could add item with SKU: G to cart"), checkoutController.addItemToBasket("G", Integer.parseInt(newBasket.getBody().toString())));
    }

    @Test
    public void whenAddingSecondItemToNonExistantBasket_ThenReturnHttpStatus400(){

        assertEquals(ResponseEntity.badRequest().body("Receipt doesn't exist"), checkoutController.addItemToBasket("G", Integer.parseInt("999")));
    }

    @Test
    public void whenTotalingCart_ThenReturnHttpStatus200AndTotal(){

        ResponseEntity newBasket = checkoutController.startNewBasket("A");
        checkoutController.addItemToBasket("A", Integer.parseInt(newBasket.getBody().toString()));
        assertEquals(ResponseEntity.ok().body(1.0), checkoutController.getTotal(Integer.parseInt(newBasket.getBody().toString())));
    }

    @Test
    public void whenTotalingCartWithDifferentItems_ThenReturnHttpStatus200AndTotal(){

        ResponseEntity newBasket = checkoutController.startNewBasket("A");
        checkoutController.addItemToBasket("B", Integer.parseInt(newBasket.getBody().toString()));
        assertEquals(ResponseEntity.ok().body(0.8), checkoutController.getTotal(Integer.parseInt(newBasket.getBody().toString())));
    }

    @Test
    public void whenTotalingCartWithADeal_ThenShouldApplyDiscountAndReturnHttpStatus200AndTotal(){

        ResponseEntity newBasket = checkoutController.startNewBasket("A");
        checkoutController.addItemToBasket("A", Integer.parseInt(newBasket.getBody().toString()));
        checkoutController.addItemToBasket("A", Integer.parseInt(newBasket.getBody().toString()));
        assertEquals(ResponseEntity.ok().body(1.3), checkoutController.getTotal(Integer.parseInt(newBasket.getBody().toString())));
    }

    @Test
    public void whenTotalingCartWithADealAndExtraItem_ThenShouldApplyDiscountAndReturnHttpStatus200AndTotal(){

        ResponseEntity newBasket = checkoutController.startNewBasket("A");
        checkoutController.addItemToBasket("A", Integer.parseInt(newBasket.getBody().toString()));
        checkoutController.addItemToBasket("A", Integer.parseInt(newBasket.getBody().toString()));
        checkoutController.addItemToBasket("A", Integer.parseInt(newBasket.getBody().toString()));
        assertEquals(ResponseEntity.ok().body(1.8), checkoutController.getTotal(Integer.parseInt(newBasket.getBody().toString())));
    }

    @Test
    public void whenTotalingCartWithWrongId_ThenReturnHttpStatus400() {
        assertEquals(ResponseEntity.badRequest().body("Basket for id given is either empty or does not exist"), checkoutController.getTotal(999));
    }
}
