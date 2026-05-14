package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.HomePage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * E2E-01: Home and Basic Navigation Tests
 * Objective: Ensure home page loads correctly and allows main navigation
 */
public class E2E01_HomeNavigationTest extends BaseTest {
    
    @Test
    @DisplayName("E2E-01.1: Should load home page successfully")
    public void shouldLoadHomePageSuccessfully() {
        HomePage homePage = new HomePage(page);
        
        // Validations
        assertTrue(homePage.isHomePageLoaded(), 
            "Home page should load correctly");
        assertTrue(homePage.isLogoVisible(), 
            "Logo should be visible");
        assertTrue(homePage.isCartIconVisible(), 
            "Cart icon should be visible");
        
        // Validate URL
        String currentUrl = homePage.getCurrentUrl();
        assertTrue(currentUrl.contains("sauce-demo.myshopify.com"), 
            "URL should match expected: " + currentUrl);
    }
    
    @Test
    @DisplayName("E2E-01.2: Should validate main home elements")
    public void shouldValidateMainHomeElements() {
        HomePage homePage = new HomePage(page);
        
        // Validate main elements
        assertTrue(homePage.isLogoVisible(), 
            "Logo must be visible");
        assertTrue(homePage.isCartIconVisible(), 
            "Cart must be visible");
        assertTrue(homePage.areProductsVisible(), 
            "Products must be visible on home");
        
        // Validate product count
        int productCount = homePage.getProductCount();
        assertTrue(productCount > 0, 
            "Should have at least 1 product visible. Found: " + productCount);
    }
    
    @Test
    @DisplayName("E2E-01.3: Should navigate to collection")
    public void shouldNavigateToCollection() {
        HomePage homePage = new HomePage(page);
        
        // Navigate to collection
        homePage.navigateToCollection();
        
        // Validate products still visible after navigation
        assertTrue(homePage.areProductsVisible(), 
            "Products should be visible after navigating to collection");
    }
    
    @Test
    @DisplayName("E2E-01.4: Should return to home after navigation")
    public void shouldReturnToHomeAfterNavigation() {
        HomePage homePage = new HomePage(page);
        
        // Navigate to first product
        homePage.clickFirstProduct();
        
        // Return to home by clicking logo
        homePage = new HomePage(page);
        homePage.clickLogo();
        
        // Validate returned to home
        assertTrue(homePage.isHomePageLoaded(), 
            "Should return to home after clicking logo");
        assertTrue(homePage.areProductsVisible(), 
            "Products should be visible after returning to home");
    }
}