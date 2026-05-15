package com.saucedemo.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for Home Page
 * Represents elements and actions available on the home page
 */
public class HomePage extends BasePage {
    
    // ========== LOCATORS ==========

    private final String logo = "#logo";
    private final String minicart = "#minicart";
    private final String cartLink = "#minicart a[href='/cart']";
    private final String navLinks = "header nav a";
    
    private final String productLink = "#page-content a[href*='/products/']";
    private final String firstProduct = "#product-1";
    
    /**
     * Constructor
     * @param page Playwright Page instance
     */
    public HomePage(Page page) {
        super(page);
    }
    
    // ========== VALIDATION METHODS ==========
    
    /**
     * Check if logo is visible
     * @return true if logo is visible
     */
    public boolean isLogoVisible() {
        return isVisible(logo);
    }
    
    /**
     * Check if navigation menu is visible
     * @return true if menu has links
     */
    public boolean isMenuVisible() {
        return getElementCount(navLinks) > 0;
    }
    
    /**
     * Check if cart icon is visible
     * @return true if cart icon is visible
     */
    public boolean isCartIconVisible() {
        return isVisible(minicart);
    }
    
    /**
     * Check if products are visible on page
     * @return true if at least one product is visible
     */
    public boolean areProductsVisible() {
        waitForPageLoad();
        return getElementCount(productLink) > 0;
    }
    
    /**
     * Check if home page loaded correctly
     * Validates essential elements are present
     * @return true if page loaded successfully
     */
    public boolean isHomePageLoaded() {
        return isLogoVisible() && isCartIconVisible();
    }
    
    // ========== ACTION METHODS ==========
    
    /**
     * Click on logo to return to home
     * @return HomePage instance for method chaining
     */
    public HomePage clickLogo() {
        click(logo);
        waitForPageLoad();
        return this;
    }
    
    /**
     * Click on cart icon to view cart
     */
    public void clickCartIcon() {
        click(cartLink);
        waitForPageLoad();
    }
    
    /**
     * Click on first visible product
     */
    public void clickFirstProduct() {
        waitForElementVisible(productLink);
        page.locator(firstProduct).click();
        waitForPageLoad();
    }
    
    /**
     * Navigate to catalog/collection
     * @return HomePage instance for method chaining
     */
    public HomePage navigateToCollection() {
        String catalogLink = "a[href*='/collections/']";
        if (isVisible(catalogLink)) {
            click(catalogLink);
            waitForPageLoad();
        }
        return this;
    }
    
    /**
     * Get count of products on page
     * @return Number of products visible
     */
    public int getProductCount() {
        waitForPageLoad();
        return getElementCount(productLink);
    }
}