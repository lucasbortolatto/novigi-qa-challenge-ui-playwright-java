package com.saucedemo.config;

/**
 * Central configuration class for test execution
 * Contains URLs, timeouts, and browser settings
 * 
 * This is a utility class and should not be instantiated
 */
public final class TestConfig {
    
    // Private constructor to prevent instantiation
    private TestConfig() {
        throw new UnsupportedOperationException("Utility class");
    }
    
    // ========== URLs ==========
    public static final String BASE_URL = "https://sauce-demo.myshopify.com/";
    
    // ========== Timeouts (milliseconds) ==========
    public static final int DEFAULT_TIMEOUT = 30000;  // 30 seconds
    public static final int SHORT_TIMEOUT = 10000;    // 10 seconds
    
    // ========== Browser Configuration ==========
    // Can be overridden via system properties: -Dheadless=false -Dbrowser=firefox
    public static final boolean HEADLESS = Boolean.parseBoolean(
        System.getProperty("headless", "true")
    );
    public static final String BROWSER_TYPE = System.getProperty("browser", "chromium");
    
    public static final int VIEWPORT_WIDTH = 1920;
    public static final int VIEWPORT_HEIGHT = 1080;
    
    // ========== Directories ==========
    public static final String SCREENSHOTS_DIR = "target/screenshots";
    public static final String VIDEOS_DIR = "target/videos";
    
    // ========== Test Configuration ==========
    public static final boolean TAKE_SCREENSHOT_ON_FAILURE = true;
    public static final boolean RECORD_VIDEO = true;
    
    /**
     * Build complete URL by concatenating base URL with path
     * Useful for navigation to specific pages like /cart, /checkout
     * 
     * @param path URL path to append
     * @return Complete URL
     */
    public static String getUrl(String path) {
        return BASE_URL + (path.startsWith("/") ? path : "/" + path);
    }
}