import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AddNumbersTest {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        driver.get("file:///M:/addition-webapp/src/main/webapp/index.html");
    }

    @Test
    public void testAddition() {
        WebElement num1 = driver.findElement(By.id("num1"));
        WebElement num2 = driver.findElement(By.id("num2"));
        WebElement button = driver.findElement(By.tagName("button"));

        num1.sendKeys("5");
        num2.sendKeys("7");

        button.click();

        // Wait for 10 seconds
        try {
            Thread.sleep(10000); // 5000 milliseconds = 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String result = driver.findElement(By.id("result")).getText();

        Assert.assertEquals(result, "Result: 12");
    }

    @AfterTest
    public void closeBrowser() {
        driver.quit();
    }
}