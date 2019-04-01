package controllerTests;

import checkout.Application;
import checkout.controller.DealController;
import checkout.entity.Deal;
import checkout.entity.SKU;
import checkout.repository.DealRepository;
import checkout.repository.SKURepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class DealControllerTests {

    @Autowired
    private DealController dealController;

    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private SKURepository skuRepository;

    private SKU skuA = new SKU("A", 0.5);
    private Deal deal = new Deal(skuA, 3, 1.2);

    @Before
    public void cleanDatabaseAfterTests(){
        dealRepository.deleteAll();
        skuRepository.deleteAll();
    }

    @Test
    public void whenAddingAValidNewDeal_ThenShouldReturnHttpStatus200(){
        skuRepository.save(skuA);
        assertEquals(dealController.addDeal(deal), ResponseEntity.ok().body(HttpStatus.OK));
    }

    @Test
    public void whenAddingAnInvalidDeal_ThenShouldReturnHttpStatus400(){
        assertEquals(dealController.addDeal(deal), ResponseEntity.badRequest().body("SKU doesn't exist"));
    }
}
