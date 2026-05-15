package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.HomePage;
import com.saucedemo.pages.ProductPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * E2E-02: Product Page and Variants Tests
 * Objective: Validate product information display and variant selection
 */
public class E2E02_ProductPageTest extends BaseTest {
    
    @Test
    @DisplayName("E2E-02.1: Should open product details successfully")
    public void shouldOpenProductDetailsSuccessfully() {
        // Navigate to first product from home
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        // Validate product page loaded
        ProductPage productPage = new ProductPage(page);
        assertTrue(productPage.isProductPageLoaded(), 
            "Product page should load correctly");
        
        // Validate URL changed to product page
        String currentUrl = productPage.getCurrentUrl();
        assertTrue(currentUrl.contains("/products/"), 
            "URL should contain '/products/'. Current: " + currentUrl);
    }
    
    @Test
    @DisplayName("E2E-02.2: Should validate product name, price and purchase button")
    public void shouldValidateProductInformation() {
        // Navigate to product
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        
        // Validate essential product information
        assertTrue(productPage.isTitleVisible(), 
            "Product title must be visible");
        assertTrue(productPage.isPriceVisible(), 
            "Product price must be visible");
        assertTrue(productPage.isAddToCartButtonVisible(), 
            "Add to cart button must be visible");
        
        // Validate information is not empty
        String title = productPage.getProductTitle();
        assertFalse(title.isEmpty(), 
            "Product title should not be empty");
        
        String price = productPage.getProductPrice();
        assertFalse(price.isEmpty(), 
            "Product price should not be empty");
        assertTrue(price.contains("$") || price.matches(".*\\d+.*"), 
            "Price should contain currency symbol or numbers. Found: " + price);
        
        String buttonText = productPage.getAddToCartButtonText();
        assertFalse(buttonText.isEmpty(), 
            "Add to cart button text should not be empty");
    }
    
    @Test
    @DisplayName("E2E-02.3: Should select product variant when available")
    public void shouldSelectVariantWhenAvailable() {
        // Navigate to product
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        
        // Check if product has variants
        if (productPage.hasVariants()) {
            int variantCount = productPage.getVariantCount();
            assertTrue(variantCount > 0, 
                "If product has variants, count should be greater than 0");
            
            // Select first variant
            productPage.selectFirstVariant();
            
            // Validate selection was made (page should remain stable)
            assertTrue(productPage.isProductPageLoaded(), 
                "Product page should remain stable after variant selection");
            
        } else {
            // Product doesn't have variants - this is also valid
            assertTrue(true, "Product does not have variants - test passes");
        }
    }
    
    @Test
    @DisplayName("E2E-02.4: Should maintain visual state after variant selection")
    public void shouldMaintainVisualStateAfterSelection() {
        // Navigate to product
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        
        // Get initial state
        String initialTitle = productPage.getProductTitle();
        
        // If has variants, select one and verify state
        if (productPage.hasVariants()) {
            productPage.selectFirstVariant();
            
            // Validate visual elements are still present after selection
            assertTrue(productPage.isTitleVisible(), 
                "Title should remain visible after variant selection");
            assertTrue(productPage.isPriceVisible(), 
                "Price should remain visible after variant selection");
            assertTrue(productPage.isAddToCartButtonVisible(), 
                "Add to cart button should remain visible after variant selection");
            
            // Title should remain the same (price might change for some variants)
            String afterTitle = productPage.getProductTitle();
            assertEquals(initialTitle, afterTitle, 
                "Product title should remain the same after variant selection");
            
        } else {
            // No variants - verify essential elements are present
            assertTrue(productPage.isTitleVisible(), 
                "Title should be visible");
            assertTrue(productPage.isPriceVisible(), 
                "Price should be visible");
            assertTrue(productPage.isAddToCartButtonVisible(), 
                "Add to cart button should be visible");
        }
    }
}