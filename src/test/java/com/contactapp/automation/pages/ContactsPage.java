package com.contactapp.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class ContactsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By addButton = By.cssSelector("#contact-form button[type='submit']");
    private final By errorMessage = By.id("error");
    private final By contactListItems = By.cssSelector("#contact-list li:not(.empty)");
    private final By removeButtons = By.cssSelector("#contact-list li .remove-btn");

    public ContactsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public ContactsPage open(String baseUrl) {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        return this;
    }

    public ContactsPage enterFirstName(String firstName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        input.clear();
        input.sendKeys(firstName);
        return this;
    }

    public ContactsPage enterLastName(String lastName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
        input.clear();
        input.sendKeys(lastName);
        return this;
    }

    public ContactsPage clickAdd() {
        driver.findElement(addButton).click();
        return this;
    }

    public ContactsPage addContact(String firstName, String lastName) {
        return enterFirstName(firstName).enterLastName(lastName).clickAdd();
    }

    public List<String> getContactNames() {
        // The app re-renders the whole list (innerHTML = '') after every add/remove,
        // so element handles collected just before a re-render can go stale mid-read.
        // Retry a couple of times rather than failing the test on that race.
        StaleElementReferenceException lastException = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            try {
                return driver.findElements(contactListItems).stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());
            } catch (StaleElementReferenceException e) {
                lastException = e;
            }
        }
        throw lastException;
    }

    public boolean isContactDisplayed(String fullName) {
        return getContactNames().stream().anyMatch(text -> text.contains(fullName));
    }

    public boolean isErrorDisplayed() {
        List<WebElement> errors = driver.findElements(errorMessage);
        return !errors.isEmpty() && errors.get(0).isDisplayed();
    }

    public String getErrorText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public void removeContact(String fullName) {
        List<WebElement> rows = driver.findElements(By.cssSelector("#contact-list li"));
        for (WebElement row : rows) {
            if (row.getText().contains(fullName)) {
                row.findElement(By.cssSelector(".remove-btn")).click();
                break;
            }
        }
    }

    public int getContactCount() {
        return getContactNames().size();
    }
}
