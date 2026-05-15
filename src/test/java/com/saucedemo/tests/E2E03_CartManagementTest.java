package com.saucedemo.tests;

import com.saucedemo.base.BaseTest;
import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.HomePage;
import com.saucedemo.pages.ProductPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * E2E-03: Cart Addition and Management Tests
 * Objective: Validate main cart operations
 */
public class E2E03_CartManagementTest extends BaseTest {
    
    @Test
    @DisplayName("E2E-03.1: Should add product to cart successfully")
    public void shouldAddProductToCart() {
        // Navigate to product and add to cart
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        productPage.clickAddToCart();
        
        // Open cart drawer (includes bug workaround)
        CartPage cartPage = new CartPage(page);
        cartPage.openCartDrawer();
        
        // Validate cart has the product
        assertTrue(cartPage.hasItems(), 
            "Cart should contain items after adding product");
        assertTrue(cartPage.isProductNameVisible(), 
            "Product name should be visible in cart");
        assertTrue(cartPage.isPriceVisible(), 
            "Price should be visible in cart");
        
        // Validate product details
        String cartProductName = cartPage.getProductName();
        assertTrue(cartProductName.contains("Grey") || cartProductName.contains("jacket"), 
            "Cart should contain the added product. Got: " + cartProductName);
    }
    
    @Test
    @DisplayName("E2E-03.2: Should validate quantity and subtotal")
    public void shouldValidateQuantityAndSubtotal() {
        // Add product to cart
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        productPage.clickAddToCart();
        
        // Open cart drawer
        CartPage cartPage = new CartPage(page);
        cartPage.openCartDrawer();
        
        // Validate quantity
        assertTrue(cartPage.isQuantityInputVisible(), 
            "Quantity input should be visible");
        
        int quantity = cartPage.getQuantity();
        assertEquals(1, quantity, 
            "Initial quantity should be 1");
        
        // Validate price and total
        assertTrue(cartPage.isPriceVisible(), 
            "Price should be visible");
        assertTrue(cartPage.isTotalVisible(), 
            "Total should be visible");
        
        String price = cartPage.getProductPrice();
        String total = cartPage.getProductTotal();
        
        assertFalse(price.isEmpty(), 
            "Price should not be empty");
        assertFalse(total.isEmpty(), 
            "Total should not be empty");
        
        // For quantity 1, price should equal total
        assertEquals(price, total, 
            "For quantity 1, price should equal total");
    }
    
    @Test
    @DisplayName("E2E-03.3: Should update item quantity")
    public void shouldUpdateItemQuantity() {
        // Add product to cart
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        productPage.clickAddToCart();
        
        // Open cart drawer
        CartPage cartPage = new CartPage(page);
        cartPage.openCartDrawer();
        
        // Get initial quantity
        int initialQuantity = cartPage.getQuantity();
        assertEquals(1, initialQuantity, 
            "Initial quantity should be 1");
        
        // Update quantity to 2
        cartPage.updateQuantity(2);
        page.waitForTimeout(1000);
        
        // Validate quantity changed
        int updatedQuantity = cartPage.getQuantity();
        assertEquals(2, updatedQuantity, 
            "Quantity should be updated to 2");
        
        // Validate cart still has items
        assertTrue(cartPage.hasItems(), 
            "Cart should still contain items after quantity update");
    }
    
    @Test
    @DisplayName("E2E-03.4: Should remove item and validate empty cart")
    public void shouldRemoveItemAndValidateEmptyCart() {
        // Add product to cart
        HomePage homePage = new HomePage(page);
        homePage.clickFirstProduct();
        
        ProductPage productPage = new ProductPage(page);
        productPage.clickAddToCart();
        
        // Open cart drawer
        CartPage cartPage = new CartPage(page);
        cartPage.openCartDrawer();
        
        // Validate cart has items before removal
        assertTrue(cartPage.hasItems(), 
            "Cart should have items before removal");
        
        int itemCountBefore = cartPage.getItemCount();
        assertTrue(itemCountBefore > 0, 
            "Item count should be greater than 0 before removal");
        
        // Remove item
        cartPage.removeItem();
        page.waitForTimeout(2000);
        
        // Validate cart is empty
        int itemCountAfter = cartPage.getItemCount();
        assertEquals(0, itemCountAfter, 
            "Cart should be empty after removing item. Before: " + itemCountBefore + ", After: " + itemCountAfter);
        
        assertFalse(cartPage.hasItems(), 
            "Cart should not have items after removal");
    }
}