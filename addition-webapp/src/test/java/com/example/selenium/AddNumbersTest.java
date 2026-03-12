package com.example.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class AddNumbersTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void setup() {
        // Set ChromeDriver executable path if needed (uncomment and update path if necessary)
        // System.setProperty("webdriver.chrome.driver", "C:/path/to/chromedriver.exe");

        // Setup Chrome in headless mode for Jenkins
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");           // Run without GUI
        options.addArguments("--no-sandbox");         // Needed for some Linux environments
        options.addArguments("--disable-dev-shm-usage"); // Prevent crashes in Docker/Jenkins
        driver = new ChromeDriver(options);

        // Set implicit wait as a fallback
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Initialize explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Get the local HTML file path and fix slashes for Windows
        String filePath = "file:///" + System.getProperty("user.dir").replace("\\", "/")
                + "/addition-webapp/src/main/webapp/index.html";

        driver.get(filePath);

        // Wait for the first input element to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("num1")));
    }

    @Test
    public void testAddition() {
        // Find input fields and button
        WebElement num1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("num1")));
        WebElement num2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("num2")));
        WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("addBtn")));
        WebElement result = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));

        // Enter values
        num1.sendKeys("5");
        num2.sendKeys("3");

        // Click Add button
        addBtn.click();

        // Verify result
        String sum = result.getText();
        assertEquals(sum, "8", "Addition result is incorrect!");
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
