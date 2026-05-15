package com.saucedemo.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for Cart Drawer
 * Represents the shopping cart overlay/drawer that appears when adding products
 * 
 * Note: This is a drawer/overlay, not a separate page
 */
public class CartPage extends BasePage {
    
    // ========== LOCATORS ==========
    // Cart drawer elements - drawer appears as overlay on top of current page
    
    // Product information in cart
    private final String productName = "#drawer h3";
    private final String productPrice = "#drawer .price.desktop";
    private final String productTotal = "#drawer .total.desktop";
    
    // Quantity management
    private final String quantityInput = "#drawer input[name='updates[]']";
    
    // Actions
    private final String removeButton = "#drawer .removeLine";
    private final String checkoutButton = "#drawer .actions input";
    
    // Cart state
    private final String emptyCartMessage = "#drawer .empty";
    
    /**
     * Constructor
     * @param page Playwright Page instance
     */
    public CartPage(Page page) {
        super(page);
    }
    
    // ========== NAVIGATION METHODS ==========
    
    /**
     * Open cart drawer by clicking cart icon in header
     * 
     * BUG WORKAROUND: Cart drawer does not open automatically after adding product.
     * A page refresh is required before the drawer can be opened by clicking the cart icon.
     * 
     * Expected behavior: Drawer should open immediately after "Add to Cart"
     * Actual behavior: Must refresh page and manually click cart icon
     * 
     * TODO: Remove page.reload() once bug is fixed
     * 
     * @return CartPage instance for method chaining
     */
    public CartPage openCartDrawer() {
        // BUG WORKAROUND: Click logo to go back to home
        page.locator("#logo a").click();
        waitForPageLoad();
        page.waitForTimeout(1500);

        // Click on cart icon - adjust selector based on actual cart icon
        page.locator("#minicart .toggle-drawer").click();
        page.waitForTimeout(2000);

        // Wait for drawer content to be visible
        waitForElementVisible(productName);
        return this;
    }
    
    // ========== VALIDATION METHODS ==========
    
    /**
     * Check if cart has items
     * @return true if cart contains at least one product
     */
    public boolean hasItems() {
        return getElementCount(productName) > 0;
    }
    
    /**
     * Check if cart is empty
     * @return true if cart has no items
     */
    public boolean isEmpty() {
        return isVisible(emptyCartMessage) || !hasItems();
    }
    
    /**
     * Check if product name is visible in cart
     * @return true if product name is visible
     */
    public boolean isProductNameVisible() {
        return isVisible(productName);
    }
    
    /**
     * Check if price is visible
     * @return true if price is visible
     */
    public boolean isPriceVisible() {
        return isVisible(productPrice);
    }
    
    /**
     * Check if quantity input is visible
     * @return true if quantity input is visible
     */
    public boolean isQuantityInputVisible() {
        return isVisible(quantityInput);
    }
    
    /**
     * Check if total is visible
     * @return true if total is visible
     */
    public boolean isTotalVisible() {
        return isVisible(productTotal);
    }
    
    // ========== GETTER METHODS ==========
    
    /**
     * Get product name from cart
     * @return Product name text
     */
    public String getProductName() {
        waitForElementVisible(productName);
        return getText(productName).trim();
    }
    
    /**
     * Get product price
     * @return Price text (e.g., "£55.00")
     */
    public String getProductPrice() {
        waitForElementVisible(productPrice);
        return getText(productPrice).trim();
    }
    
    /**
     * Get product total
     * @return Total price text (e.g., "£55.00")
     */
    public String getProductTotal() {
        waitForElementVisible(productTotal);
        return getText(productTotal).trim();
    }
    
    /**
     * Get current quantity from input
     * @return Current quantity value
     */
    public int getQuantity() {
        waitForElementVisible(quantityInput);
        String value = page.inputValue(quantityInput);
        return Integer.parseInt(value);
    }
    
    /**
     * Get number of items in cart
     * @return Count of product items
     */
    public int getItemCount() {
        return getElementCount(productName);
    }
    
    // ========== ACTION METHODS ==========
    
    /**
     * Update quantity by typing new value
     * Note: May need to click update button after changing quantity
     * 
     * @param quantity New quantity value
     * @return CartPage instance for method chaining
     */
    public CartPage updateQuantity(int quantity) {
        waitForElementVisible(quantityInput);
        
        // Clear and fill new quantity
        page.fill(quantityInput, String.valueOf(quantity));
        
        // Wait for potential UI update
        waitForPageLoad();
        return this;
    }
    
    /**
     * Increase quantity by 1
     * @return CartPage instance for method chaining
     */
    public CartPage increaseQuantity() {
        int currentQty = getQuantity();
        return updateQuantity(currentQty + 1);
    }
    
    /**
     * Decrease quantity by 1
     * @return CartPage instance for method chaining
     */
    public CartPage decreaseQuantity() {
        int currentQty = getQuantity();
        if (currentQty > 1) {
            return updateQuantity(currentQty - 1);
        }
        return this;
    }
    
    /**
     * Remove item from cart
     * Clicks the remove button/link
     * 
     * @return CartPage instance for method chaining
     */
    public CartPage removeItem() {
        waitForElementVisible(removeButton);
        click(removeButton);
        
        // Wait for item to be removed
        waitForPageLoad();
        return this;
    }
    
    /**
     * Click checkout button to proceed to checkout
     * Note: This will navigate to checkout page
     */
    public void clickCheckout() {
        waitForElementVisible(checkoutButton);
        click(checkoutButton);
        waitForPageLoad();
    }
    
    /**
     * Validate cart has expected quantity and price
     * Useful for quick assertion in tests
     * 
     * @param expectedQty Expected quantity
     * @param expectedPrice Expected price string (e.g., "£55.00")
     * @return true if both match
     */
    public boolean validateCartItem(int expectedQty, String expectedPrice) {
        int actualQty = getQuantity();
        String actualPrice = getProductPrice();
        
        return actualQty == expectedQty && actualPrice.equals(expectedPrice);
    }
}