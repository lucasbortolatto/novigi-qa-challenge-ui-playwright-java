package com.saucedemo.pages;

import com.microsoft.playwright.Page;

/**
 * Page Object for Checkout Page
 * Represents the checkout form where users enter shipping information
 */
public class CheckoutPage extends BasePage {
    
    // ========== LOCATORS ==========
    // Form fields - using name attributes for stability
    private final String emailInput = "#email";
    private final String countrySelect = "select[name='countryCode']";
    private final String firstNameInput = "input[name='firstName']";
    private final String lastNameInput = "input[name='lastName']";
    private final String companyInput = "input[name='company']";
    private final String addressInput = "input[name='address1']";
    private final String apartmentInput = "input[name='address2']";
    private final String postalCodeInput = "#postalCode";
    private final String cityInput = "input[name='city']";
    private final String stateSelect = "select[name='zone']";
    private final String phoneInput = "input[name='phone']";
    
    // Buttons
    private final String continueButton = "button:has-text('Continue'), button[type='submit']";
    
    // Error messages
    private final String errorMessage = ".error, [class*='error'], [role='alert']";
    
    /**
     * Constructor
     * @param page Playwright Page instance
     */
    public CheckoutPage(Page page) {
        super(page);
    }
    
    // ========== VALIDATION METHODS ==========
    
    /**
     * Check if we're on checkout page
     * @return true if on checkout page
     */
    public boolean isCheckoutPage() {
        return page.url().contains("/checkout") || page.url().contains("/checkouts");
    }
    
    /**
     * Check if email field is visible
     * @return true if visible
     */
    public boolean isEmailFieldVisible() {
        return isVisible(emailInput);
    }
    
    /**
     * Check if error message is displayed
     * @return true if error is visible
     */
    public boolean hasErrorMessage() {
        return isVisible(errorMessage);
    }
    
    /**
     * Get error message text
     * @return Error message text or empty string
     */
    public String getErrorMessage() {
        if (hasErrorMessage()) {
            return getText(errorMessage);
        }
        return "";
    }
    
    // ========== FILL METHODS ==========
    
    /**
     * Fill email field
     * @param email Email address
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillEmail(String email) {
        waitForElementVisible(emailInput);
        page.fill(emailInput, email);
        return this;
    }
    
    /**
     * Fill first name
     * @param firstName First name
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillFirstName(String firstName) {
        page.fill(firstNameInput, firstName);
        return this;
    }
    
    /**
     * Fill last name (required)
     * @param lastName Last name
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillLastName(String lastName) {
        page.fill(lastNameInput, lastName);
        return this;
    }
    
    /**
     * Fill address (required)
     * @param address Street address
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillAddress(String address) {
        page.fill(addressInput, address);
        return this;
    }
    
    /**
     * Fill apartment/suite
     * @param apartment Apartment number
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillApartment(String apartment) {
        page.fill(apartmentInput, apartment);
        return this;
    }
    
    /**
     * Fill postal code (required)
     * @param postalCode Postal/ZIP code
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillPostalCode(String postalCode) {
        page.fill(postalCodeInput, postalCode);
        return this;
    }
    
    /**
     * Fill city (required)
     * @param city City name
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillCity(String city) {
        page.fill(cityInput, city);
        return this;
    }
    
    /**
     * Fill phone number
     * @param phone Phone number
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillPhone(String phone) {
        page.fill(phoneInput, phone);
        return this;
    }
    
    /**
     * Select country
     * @param countryCode Country code (e.g., "BR", "US")
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage selectCountry(String countryCode) {
        page.selectOption(countrySelect, countryCode);
        return this;
    }
    
    /**
     * Select state/province
     * @param stateCode State code (e.g., "SP", "CA")
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage selectState(String stateCode) {
        page.selectOption(stateSelect, stateCode);
        return this;
    }
    
    /**
     * Fill complete checkout form with all required fields
     * Uses Brazilian address as default
     * 
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillCompleteForm() {
        return fillCompleteForm(
            "test@example.com",
            "Test",
            "User",
            "Rua Teste 123",
            "13450-000",
            "Sumaré",
            "SP"
        );
    }
    
    /**
     * Fill complete checkout form with custom data
     * 
     * @param email Email address
     * @param firstName First name
     * @param lastName Last name
     * @param address Street address
     * @param postalCode Postal code
     * @param city City
     * @param state State code
     * @return CheckoutPage for method chaining
     */
    public CheckoutPage fillCompleteForm(
        String email,
        String firstName,
        String lastName,
        String address,
        String postalCode,
        String city,
        String state
    ) {
        fillEmail(email);
        fillFirstName(firstName);
        fillLastName(lastName);
        fillAddress(address);
        fillPostalCode(postalCode);
        fillCity(city);
        selectState(state);
        
        return this;
    }
    
    // ========== GETTER METHODS ==========
    
    /**
     * Get value from email field
     * @return Email value
     */
    public String getEmailValue() {
        return page.inputValue(emailInput);
    }
    
    /**
     * Get value from last name field
     * @return Last name value
     */
    public String getLastNameValue() {
        return page.inputValue(lastNameInput);
    }
    
    /**
     * Get value from address field
     * @return Address value
     */
    public String getAddressValue() {
        return page.inputValue(addressInput);
    }
    
    /**
     * Get value from city field
     * @return City value
     */
    public String getCityValue() {
        return page.inputValue(cityInput);
    }
    
    // ========== ACTION METHODS ==========
    
    /**
     * Click continue/submit button
     * Proceeds to next step of checkout
     */
    public void clickContinue() {
        waitForElementVisible(continueButton);
        click(continueButton);
        waitForPageLoad();
    }
    
    /**
     * Try to submit form without filling required fields
     * Used to test validation
     */
    public void submitEmptyForm() {
        click(continueButton);
        // Don't wait for page load - validation should prevent submission
        page.waitForTimeout(1000);
    }
}