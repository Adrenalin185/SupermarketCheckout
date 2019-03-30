package controllerTests;

import checkout.Application;
import checkout.controller.CheckoutController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Application.class)
public class CheckoutControllerTest {

    @Autowired
    CheckoutController checkoutController;

    @Test
    public void whenItemScanned_ThenReturnHttpResponseOkValue() throws Exception {
        assertEquals(checkoutController.addItemToBasket(), ResponseEntity.ok().body(HttpStatus.OK.value()));
    }
}
