package controllerTests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({CheckoutControllerTest.class, StockControllerTests.class, DealControllerTests.class})
@RunWith(Suite.class)
public class ControllerTestsRunner {
}
