package com.contactapp.automation.hooks;

import com.contactapp.automation.utils.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Hooks {

    private static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");

    @Before
    public void beforeScenario() {
        DriverFactory.getDriver();
    }

    @After
    public void afterScenario(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        try {
            if (scenario.isFailed()) {
                takeScreenshot(driver, scenario);
            }
        } finally {
            DriverFactory.quitDriver();
        }
    }

    private void takeScreenshot(WebDriver driver, Scenario scenario) {
        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        // Attach to the Cucumber report
        scenario.attach(screenshot, "image/png", scenario.getName());

        // Also save to disk for easy inspection outside the report
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            String safeName = scenario.getName().replaceAll("[^a-zA-Z0-9-_]", "_");
            File outputFile = SCREENSHOT_DIR.resolve(safeName + "-" + timestamp + ".png").toFile();
            Files.write(outputFile.toPath(), screenshot);
        } catch (IOException e) {
            System.err.println("Failed to save screenshot to disk: " + e.getMessage());
        }
    }
}
