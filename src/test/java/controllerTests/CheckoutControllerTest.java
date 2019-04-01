package controllerTests;

import checkout.Application;
import checkout.controller.CheckoutController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CheckoutControllerTest {

    @Autowired
    CheckoutController checkoutController;

    @Test
    public void whenItemScanned_ThenReturnHttpResponseOkValue() {
//        assertEquals(checkoutController.addItemToBasket(), ResponseEntity.ok().body(HttpStatus.OK.value()));
    }
}
