package controllerTests;

import checkout.Application;
import checkout.controller.StockController;
import checkout.entity.SKU;
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
    private SKURepository skuRepository;

    private SKU skuA = new SKU("A", 0.5);

    @Before
    public void cleanDatabaseBetweenTests(){
        skuRepository.deleteAll();
    }

    @Test
    public void whenAddingSKUA_ThenShouldReturnHttpStatus200(){

        assertEquals(stockController.addNewSKU(skuA), ResponseEntity.ok().body(HttpStatus.OK));
    }

    @Test
    public void whenAddingSKUAWhenItExists_ThenReturnHttpStatus400(){
        stockController.addNewSKU(skuA);
        assertEquals(stockController.addNewSKU(skuA), ResponseEntity.badRequest().body("SKU already exists"));
    }

    @Test
    public void whenAmendingSKUA_ThenReturnHttpStatus200(){
        stockController.addNewSKU(skuA);
        assertEquals(stockController.amendSKU(new SKU("A", 0.85)), ResponseEntity.ok().body(HttpStatus.OK));
    }

    @Test
    public void whenAmendingSKUAWhenItDoesntExist_ThenReturnHttpStatus400(){
        assertEquals(stockController.amendSKU(new SKU("A", 0.5)), ResponseEntity.badRequest().body("SKU doesn't exist"));
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
