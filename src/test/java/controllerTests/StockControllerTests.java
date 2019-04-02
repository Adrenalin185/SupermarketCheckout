package controllerTests;

import checkout.Application;
import checkout.controller.StockController;
import checkout.entity.SKU;
import checkout.repository.BasketItemRepository;
import checkout.repository.DealRepository;
import checkout.repository.ReceiptRepository;
import checkout.repository.SKURepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class StockControllerTests {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private StockController stockController;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private SKURepository skuRepository;

    @Autowired
    private ReceiptRepository receiptRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    private SKU skuA = new SKU("A", 0.5);

    @Before
    public void cleanDatabaseBetweenTests(){
        dealRepository.deleteAll();
        basketItemRepository.deleteAll();
        receiptRepository.deleteAll();
        skuRepository.deleteAll();
    }

    @Test
    public void whenAddingSKUA_ThenShouldReturnHttpStatus200(){

        assertEquals(ResponseEntity.ok().body(HttpStatus.OK), stockController.addNewSKU(skuA));
    }

    @Test
    public void whenAddingSKUAWhenItExists_ThenReturnHttpStatus400(){
        stockController.addNewSKU(skuA);
        assertEquals(ResponseEntity.badRequest().body("SKU already exists"), stockController.addNewSKU(skuA));
    }

    @Test
    public void whenAmendingSKUA_ThenReturnHttpStatus200(){
        stockController.addNewSKU(skuA);
        assertEquals(ResponseEntity.ok().body(HttpStatus.OK), stockController.amendSKU(new SKU("A", 0.85)));
    }

    @Test
    public void whenAmendingSKUAWhenItDoesntExist_ThenReturnHttpStatus400(){
        assertEquals(ResponseEntity.badRequest().body("SKU doesn't exist"), stockController.amendSKU(new SKU("A", 0.5)));
    }

    @Test
    public void whenAddingEmptySKU_ThenExpectException(){
        expectedException.expect(InvalidDataAccessApiUsageException.class);
        expectedException.expectMessage("The given id must not be null!");

        stockController.addNewSKU(new SKU());
    }

    @Test
    public void whenAmendingEmptySKU_ThenExpectException(){
        expectedException.expect(InvalidDataAccessApiUsageException.class);
        expectedException.expectMessage("The given id must not be null!");

        stockController.amendSKU(new SKU());
    }
}
