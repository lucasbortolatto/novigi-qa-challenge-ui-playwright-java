package com.saucedemo.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.saucedemo.config.TestConfig;

/**
 * Base class for all Page Objects
 * Contains common reusable methods for page interactions
 * 
 * All page objects should extend this class to inherit common functionality
 */
public class BasePage {
    
    protected Page page;
    
    /**
     * Constructor
     * @param page Playwright Page instance
     */
    public BasePage(Page page) {
        this.page = page;
    }
    
    /**
     * Click on element
     * @param selector CSS selector
     */
    protected void click(String selector) {
        waitForElementVisible(selector);
        page.click(selector);
    }
    
    /**
     * Fill input field
     * @param selector CSS selector
     * @param text Text to fill
     */
    protected void fill(String selector, String text) {
        waitForElementVisible(selector);
        page.fill(selector, text);
    }
    
    /**
     * Get text content of element
     * @param selector CSS selector
     * @return Element text content
     */
    protected String getText(String selector) {
        waitForElementVisible(selector);
        return page.textContent(selector);
    }
    
    /**
     * Check if element is visible
     * @param selector CSS selector
     * @return true if visible, false otherwise
     */
    protected boolean isVisible(String selector) {
        try {
            return page.isVisible(selector);
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wait for element to be visible
     * @param selector CSS selector
     */
    protected void waitForElementVisible(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(TestConfig.DEFAULT_TIMEOUT));
    }
    
    /**
     * Wait for element to be hidden
     * @param selector CSS selector
     */
    protected void waitForElementHidden(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.HIDDEN)
            .setTimeout(TestConfig.DEFAULT_TIMEOUT));
    }
    
    /**
     * Get current page URL
     * @return Current URL
     */
    public String getCurrentUrl() {
        return page.url();
    }
    
    /**
     * Get page title
     * @return Page title
     */
    public String getPageTitle() {
        return page.title();
    }
    
    /**
     * Scroll element into view
     * @param selector CSS selector
     */
    protected void scrollToElement(String selector) {
        page.locator(selector).scrollIntoViewIfNeeded();
    }
    
    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        page.waitForLoadState();
    }
    
    /**
     * Get count of elements matching selector
     * @param selector CSS selector
     * @return Element count
     */
    protected int getElementCount(String selector) {
        return page.locator(selector).count();
    }
}