package com.saucedemo.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.saucedemo.config.TestConfig;
import com.saucedemo.factory.PlaywrightFactory;
import com.saucedemo.utils.WaitUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

/**
 * Base test class for all E2E tests
 * Handles browser setup and teardown
 * 
 * All test classes should extend this class
 */
public class BaseTest {
    
    protected PlaywrightFactory playwrightFactory;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    
    /**
     * Setup method - runs before each test
     * Initializes browser and navigates to base URL
     */
    @BeforeEach
    public void setup() {
        // Initialize Playwright and create browser
        playwrightFactory = new PlaywrightFactory();
        browser = playwrightFactory.launchBrowser();
        context = playwrightFactory.createContext(browser);
        page = playwrightFactory.createPage(context);
        
        // Navigate to base URL
        page.navigate(TestConfig.BASE_URL);
    }
    
    /**
     * Teardown method - runs after each test
     * Takes screenshot on failure and closes browser
     * 
     * @param testInfo JUnit test information
     */
    @AfterEach
    public void teardown(TestInfo testInfo) {
        // Take screenshot on failure if enabled
        if (TestConfig.TAKE_SCREENSHOT_ON_FAILURE) {
            // Check if test failed by attempting to get failure reason
            try {
                String testName = testInfo.getDisplayName().replaceAll("[^a-zA-Z0-9]", "_");
                WaitUtils.takeScreenshot(page, testName);
            } catch (Exception e) {
                System.err.println("Could not take screenshot: " + e.getMessage());
            }
        }
        
        // Close browser and playwright
        if (playwrightFactory != null) {
            playwrightFactory.quit();
        }
    }
    
    /**
     * Helper method to navigate to specific path
     * @param path URL path
     */
    protected void navigateTo(String path) {
        page.navigate(TestConfig.getUrl(path));
    }
}