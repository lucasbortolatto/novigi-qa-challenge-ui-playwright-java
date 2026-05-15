package com.saucedemo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;

/**
 * Page Object for Product Page
 * Represents elements and actions available on individual product pages
 */
public class ProductPage extends BasePage {
    
    // ========== LOCATORS ==========
    // Using IDs and semantic selectors for stability
    
    // Product information
    private final String productTitle = "#buy h1";
    private final String productPrice = "#buy h2";
    private final String addToCartButton = "#add";
    
    // Variant selection
    private final String variantSelect = "select[name='id']";
    private final String variantOptions = "select[name='id'] option";
    
    // Product image
    private final String productImage = ".product_image img";
    
    /**
     * Constructor
     * @param page Playwright Page instance
     */
    public ProductPage(Page page) {
        super(page);
    }
    
    // ========== VALIDATION METHODS ==========
    
    /**
     * Check if product page loaded correctly
     * @return true if essential elements are present
     */
    public boolean isProductPageLoaded() {
        waitForPageLoad();
        return isVisible(productTitle) && 
               isVisible(productPrice) && 
               isVisible(addToCartButton);
    }
    
    /**
     * Check if product title is visible
     * @return true if title is visible
     */
    public boolean isTitleVisible() {
        return isVisible(productTitle);
    }
    
    /**
     * Check if product price is visible
     * @return true if price is visible
     */
    public boolean isPriceVisible() {
        return isVisible(productPrice);
    }
    
    /**
     * Check if add to cart button is visible
     * @return true if button is visible
     */
    public boolean isAddToCartButtonVisible() {
        return isVisible(addToCartButton);
    }
    
    /**
     * Check if product has variants (size, color, etc.)
     * @return true if variant selector exists
     */
    public boolean hasVariants() {
        return isVisible(variantSelect);
    }
    
    /**
     * Check if product image is visible
     * @return true if image is visible
     */
    public boolean isImageVisible() {
        return isVisible(productImage);
    }
    
    // ========== GETTER METHODS ==========
    
    /**
     * Get product title text
     * @return Product name/title
     */
    public String getProductTitle() {
        waitForElementVisible(productTitle);
        return getText(productTitle).trim();
    }
    
    /**
     * Get product price text
     * @return Product price (including currency symbol)
     */
    public String getProductPrice() {
        waitForElementVisible(productPrice);
        return getText(productPrice).trim();
    }
    
    /**
     * Get add to cart button text
     * @return Button text (e.g., "Add to Cart")
     */
    public String getAddToCartButtonText() {
        waitForElementVisible(addToCartButton);
        return page.inputValue(addToCartButton).trim();
    }
    
    /**
     * Get count of available variants
     * @return Number of variant options
     */
    public int getVariantCount() {
        if (!hasVariants()) {
            return 0;
        }
        // Subtract 1 to exclude the default "Select" option if present
        int count = getElementCount(variantOptions);
        String firstOption = page.locator(variantOptions).first().textContent();
        return firstOption.toLowerCase().contains("select") ? count - 1 : count;
    }
    
    /**
     * Get currently selected variant text
     * @return Selected variant option text, or empty if no variants
     */
    public String getSelectedVariant() {
        if (!hasVariants()) {
            return "";
        }
        return page.locator(variantSelect).inputValue();
    }
    
    // ========== ACTION METHODS ==========
    
    /**
     * Click add to cart button
     * @return ProductPage instance for method chaining
     */
    public ProductPage clickAddToCart() {
        click(addToCartButton);
        return this;
    }
    
    /**
     * Select a variant by visible text (e.g., "Small", "Red")
     * Only works if product has variants
     * 
     * @param variantText Visible text of the variant option
     * @return ProductPage instance for method chaining
     */
    public ProductPage selectVariantByText(String variantText) {
        if (!hasVariants()) {
            throw new IllegalStateException("Product does not have variants to select");
        }
        
        waitForElementVisible(variantSelect);
        // Correct Playwright syntax for selecting by label
        page.selectOption(variantSelect, new SelectOption().setLabel(variantText));
        
        // Wait for any visual update after selection
        waitForPageLoad();
        return this;
    }
    
    /**
     * Select a variant by index (0-based, excluding default "Select" option)
     * Only works if product has variants
     * 
     * @param index Zero-based index of the variant
     * @return ProductPage instance for method chaining
     */
    public ProductPage selectVariantByIndex(int index) {
        if (!hasVariants()) {
            throw new IllegalStateException("Product does not have variants to select");
        }
        
        waitForElementVisible(variantSelect);
        
        // Get all options
        int totalOptions = page.locator(variantOptions).count();
        String firstOption = page.locator(variantOptions).first().textContent();
        
        // If first option is "Select", offset by 1
        int actualIndex = firstOption.toLowerCase().contains("select") ? index + 1 : index;
        
        if (actualIndex >= totalOptions) {
            throw new IllegalArgumentException(
                "Variant index " + index + " is out of bounds. Available variants: " + 
                (totalOptions - (firstOption.toLowerCase().contains("select") ? 1 : 0))
            );
        }
        
        // Select by index
        page.selectOption(variantSelect, new SelectOption().setIndex(actualIndex));
        
        // Wait for any visual update after selection
        waitForPageLoad();
        return this;
    }
    
    /**
     * Select first available variant
     * Useful for products where variant selection is required
     * 
     * @return ProductPage instance for method chaining
     */
    public ProductPage selectFirstVariant() {
        if (!hasVariants()) {
            throw new IllegalStateException("Product does not have variants to select");
        }
        
        return selectVariantByIndex(0);
    }
}