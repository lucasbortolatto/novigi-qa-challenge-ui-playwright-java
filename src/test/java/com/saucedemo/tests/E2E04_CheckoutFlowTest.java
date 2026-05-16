package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.HomePage;
import com.saucedemo.pages.ProductPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * E2E-04: Checkout Flow Tests
 * Objective: Verify data filling and checkout progression
 */
public class E2E04_CheckoutFlowTest extends BaseTest {
    
    @Test
    @DisplayName("E2E-04.1: Should navigate from cart to checkout")
    public void shouldNavigateToCheckout() {
        // Add product to cart
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        productPage.clickAddToCart();
        
        // Navigate through cart to checkout
        CartPage cartPage = new CartPage(page);
        cartPage.openCartDrawer();
        cartPage.clickCheckout();
        
        assertTrue(page.url().contains("/cart"), 
            "Should be on cart page after clicking checkout in drawer");
        
        cartPage.clickCheckoutOnCartPage();
        
        // Validate checkout page
        CheckoutPage checkoutPage = new CheckoutPage(page);
        assertTrue(checkoutPage.isCheckoutPage(), 
            "Should be on checkout page");
        assertTrue(checkoutPage.isEmailFieldVisible(), 
            "Email field should be visible");
        assertTrue(page.url().contains("checkout"), 
            "URL should contain 'checkout'");
    }
    
    @Test
    @DisplayName("E2E-04.2: Should fill checkout form with valid data")
    public void shouldFillCheckoutForm() {
        // Navigate to checkout
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        productPage.clickAddToCart();
        
        CartPage cartPage = new CartPage(page);
        cartPage.openCartDrawer();
        cartPage.clickCheckout();
        cartPage.clickCheckoutOnCartPage();
        
        // Fill form
        CheckoutPage checkoutPage = new CheckoutPage(page);
        checkoutPage.fillCompleteForm(
            "test.user@example.com",
            "Test",
            "User",
            "Rua Teste 123",
            "13450-000",
            "Sumaré",
            "SP"
        );
        
        // Validate filled data
        assertEquals("test.user@example.com", checkoutPage.getEmailValue());
        assertEquals("User", checkoutPage.getLastNameValue());
        assertEquals("Rua Teste 123", checkoutPage.getAddressValue());
        assertEquals("Sumaré", checkoutPage.getCityValue());
    }
    
    @Test
    @DisplayName("E2E-04.3: Should validate required fields")
    public void shouldValidateRequiredFields() {
        // Navigate to checkout
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        productPage.clickAddToCart();
        
        CartPage cartPage = new CartPage(page);
        cartPage.openCartDrawer();
        cartPage.clickCheckout();
        cartPage.clickCheckoutOnCartPage();
        
        CheckoutPage checkoutPage = new CheckoutPage(page);
        
        // Attempt to submit empty form
        checkoutPage.submitEmptyForm();
        
        assertTrue(checkoutPage.isCheckoutPage(), 
            "Should remain on checkout page after invalid submission");
        assertTrue(page.url().contains("checkout"), 
            "Should not proceed with empty required fields");
        
        // Partial fill should also fail
        checkoutPage.fillEmail("test@example.com");
        checkoutPage.submitEmptyForm();
        
        assertTrue(page.url().contains("checkout"), 
            "Should not proceed without all required fields");
    }
    
    @Test
    @DisplayName("E2E-04.4: Should complete checkout flow")
    public void shouldCompleteCheckoutFlow() {
        // Add product to cart
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        productPage.clickAddToCart();
        
        // Navigate to checkout
        CartPage cartPage = new CartPage(page);
        cartPage.openCartDrawer();
        cartPage.clickCheckout();
        cartPage.clickCheckoutOnCartPage();
        
        // Fill checkout form
        CheckoutPage checkoutPage = new CheckoutPage(page);
        checkoutPage.fillCompleteForm(
            "qa.test@example.com",
            "QA",
            "Tester",
            "Avenida Automação 456",
            "13450-999",
            "Sumaré",
            "SP"
        );
        
        // Validate
        assertEquals("qa.test@example.com", checkoutPage.getEmailValue());
        assertEquals("Tester", checkoutPage.getLastNameValue());
        assertEquals("Avenida Automação 456", checkoutPage.getAddressValue());
        assertEquals("Sumaré", checkoutPage.getCityValue());
        assertTrue(checkoutPage.isCheckoutPage());
    }
}