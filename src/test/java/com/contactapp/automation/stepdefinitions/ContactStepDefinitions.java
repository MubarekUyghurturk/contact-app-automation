package com.contactapp.automation.stepdefinitions;

import com.contactapp.automation.pages.ContactsPage;
import com.contactapp.automation.utils.ConfigReader;
import com.contactapp.automation.utils.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.time.Duration;

public class ContactStepDefinitions {

    private static final Duration ASSERTION_TIMEOUT = Duration.ofSeconds(5);

    private final ContactsPage contactsPage = new ContactsPage(DriverFactory.getDriver());
    private int contactCountBeforeAction;

    @Given("I am on the contacts page")
    public void i_am_on_the_contacts_page() {
        contactsPage.open(ConfigReader.getBaseUrl());
    }

    @Given("a contact {string} already exists")
    public void a_contact_already_exists(String fullName) {
        String[] parts = fullName.split(" ", 2);
        if (!contactsPage.isContactDisplayed(fullName)) {
            contactsPage.addContact(parts[0], parts[1]);
        }
        Assert.assertTrue(
                "Precondition failed: contact was not created",
                contactsPage.waitUntilContactDisplayed(fullName, ASSERTION_TIMEOUT)
        );
    }

    @When("I add a contact with first name {string} and last name {string}")
    public void i_add_a_contact_with_first_name_and_last_name(String firstName, String lastName) {
        contactCountBeforeAction = contactsPage.getContactCount();
        contactsPage.addContact(firstName, lastName);
    }

    @When("I remove the contact {string}")
    public void i_remove_the_contact(String fullName) {
        contactsPage.removeContact(fullName);
    }

    @Then("the contact {string} should appear in the contact list")
    public void the_contact_should_appear_in_the_contact_list(String fullName) {
        Assert.assertTrue(
                "Expected contact '" + fullName + "' to appear in the list",
                contactsPage.waitUntilContactDisplayed(fullName, ASSERTION_TIMEOUT)
        );
    }

    @Then("the contact {string} should not appear in the contact list")
    public void the_contact_should_not_appear_in_the_contact_list(String fullName) {
        Assert.assertTrue(
                "Expected contact '" + fullName + "' to be removed from the list",
                contactsPage.waitUntilContactNotDisplayed(fullName, ASSERTION_TIMEOUT)
        );
    }

    @Then("the contact list should not gain a new entry")
    public void the_contact_list_should_not_gain_a_new_entry() {
        Assert.assertEquals(contactCountBeforeAction, contactsPage.getContactCount());
    }

    @Then("an error message should be displayed")
    public void an_error_message_should_be_displayed() {
        Assert.assertTrue("Expected an error message to be displayed", contactsPage.isErrorDisplayed());
    }

    @Then("no error message should be displayed")
    public void no_error_message_should_be_displayed() {
        Assert.assertFalse("Expected no error message to be displayed", contactsPage.isErrorDisplayed());
    }
}
