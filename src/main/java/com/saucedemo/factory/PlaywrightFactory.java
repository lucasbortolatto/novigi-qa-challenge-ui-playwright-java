package com.saucedemo.factory;

import com.microsoft.playwright.*;
import com.saucedemo.config.TestConfig;

import java.nio.file.Paths;

/**
 * Factory class for creating and configuring Playwright instances
 * Handles browser, context, and page creation with centralized settings
 * 
 * Follows the Factory pattern to encapsulate browser setup complexity
 */
public class PlaywrightFactory {
    
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    
    /**
     * Initialize Playwright instance
     * Uses lazy initialization pattern
     * 
     * @return Playwright instance
     */
    public Playwright initPlaywright() {
        if (playwright == null) {
            playwright = Playwright.create();
        }
        return playwright;
    }
    
    /**
     * Launch browser based on configuration
     * Supports chromium, firefox, and webkit
     * Browser type can be set via system property: -Dbrowser=firefox
     * 
     * @return Browser instance
     */
    public Browser launchBrowser() {
        initPlaywright();
        
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
            .setHeadless(TestConfig.HEADLESS);
        
        // Select browser type from config
        browser = switch (TestConfig.BROWSER_TYPE.toLowerCase()) {
            case "firefox" -> playwright.firefox().launch(launchOptions);
            case "webkit" -> playwright.webkit().launch(launchOptions);
            default -> playwright.chromium().launch(launchOptions);
        };
        
        return browser;
    }
    
    /**
     * Create new browser context with viewport settings
     * Context provides isolation between test scenarios
     * 
     * @param browser Browser instance
     * @return BrowserContext instance
     */
    public BrowserContext createContext(Browser browser) {
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
            .setViewportSize(TestConfig.VIEWPORT_WIDTH, TestConfig.VIEWPORT_HEIGHT);
        
        // Configure video recording if enabled
        if (TestConfig.RECORD_VIDEO) {
            contextOptions.setRecordVideoDir(Paths.get(TestConfig.VIDEOS_DIR));
        }
        
        context = browser.newContext(contextOptions);
        
        // Set default timeout for all operations
        context.setDefaultTimeout(TestConfig.DEFAULT_TIMEOUT);
        
        return context;
    }
    
    /**
     * Create new page in browser context
     * 
     * @param context BrowserContext instance
     * @return Page instance ready for navigation
     */
    public Page createPage(BrowserContext context) {
        return context.newPage();
    }
    
    /**
     * Clean up all Playwright resources
     * Closes context, browser, and playwright in correct order
     * Should be called in test teardown to prevent resource leaks
     */
    public void quit() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}