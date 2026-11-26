package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.HomePage;
import pages.ProductsPage;

public class SmokeTest extends BaseTest {

    @Test
    public void homePageNavigatesToProducts() {
        ProductsPage products = new HomePage(driver).open().goToProducts();
        Assert.assertTrue(products.isOpen(), "Products page should be open, but URL is " + products.currentUrl());
    }
}