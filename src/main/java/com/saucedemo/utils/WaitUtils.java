package com.saucedemo.utils;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import com.saucedemo.config.TestConfig;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for common wait operations and helper methods
 */
public class WaitUtils {
    
    /**
     * Wait for element to be visible on page
     * @param page Page instance
     * @param selector CSS selector
     */
    public static void waitForElementVisible(Page page, String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(TestConfig.DEFAULT_TIMEOUT));
    }
    
    /**
     * Wait for element to be visible with custom timeout
     * @param page Page instance
     * @param selector CSS selector
     * @param timeout Timeout in milliseconds
     */
    public static void waitForElementVisible(Page page, String selector, double timeout) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.VISIBLE)
            .setTimeout(timeout));
    }
    
    /**
     * Wait for element to be hidden/removed
     * @param page Page instance
     * @param selector CSS selector
     */
    public static void waitForElementHidden(Page page, String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions()
            .setState(WaitForSelectorState.HIDDEN)
            .setTimeout(TestConfig.DEFAULT_TIMEOUT));
    }
    
    /**
     * Wait for page navigation to complete
     * @param page Page instance
     */
    public static void waitForNavigation(Page page) {
        page.waitForLoadState();
    }
    
    /**
     * Wait for URL to contain specific text
     * @param page Page instance
     * @param urlPart URL fragment to wait for
     */
    public static void waitForUrlContains(Page page, String urlPart) {
        page.waitForURL("**/" + urlPart + "**", new Page.WaitForURLOptions()
            .setTimeout(TestConfig.DEFAULT_TIMEOUT));
    }
    
    /**
     * Take screenshot with timestamp in filename
     * @param page Page instance
     * @param testName Name of the test
     * @return Path to saved screenshot
     */
    public static String takeScreenshot(Page page, String testName) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = TestConfig.SCREENSHOTS_DIR + "/" + fileName;
        
        // Create directory if it doesn't exist
        Paths.get(TestConfig.SCREENSHOTS_DIR).toFile().mkdirs();
        
        page.screenshot(new Page.ScreenshotOptions()
            .setPath(Paths.get(filePath))
            .setFullPage(true));
        
        return filePath;
    }
    
    /**
     * Hard wait - use only when absolutely necessary
     * Prefer explicit waits over sleep
     * @param milliseconds Time to sleep
     */
    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}