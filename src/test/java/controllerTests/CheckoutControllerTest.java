package controllerTests;

import checkout.controller.CheckoutController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CheckoutController.class)
public class CheckoutControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void whenItemScanned_ThenReturnHttpResponseOkValue() throws Exception {
        mvc.perform(post("/scanneditem")).andExpect(status().isOk());
    }
}
