package com.thetestingacademy.ex_22092024;



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class EbayTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Update the path
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void verifyItemCanBeAddedToCart() {
        // Step 2: Navigate to ebay.com
        driver.get("https://www.ebay.com");

        // Step 3: Search for 'book'
        WebElement searchBox = driver.findElement(By.xpath("//input[@type='text' and @placeholder='Search for anything']"));
        searchBox.sendKeys("book");
        searchBox.submit();

        // Step 4: Click on the first book in the list
        WebElement firstBook = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 's-item')][1]//h3")));
        firstBook.click();

        // Step 5: In the item listing page, click on 'Add to cart'
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@id, 'isCartBtn_btn')]")));
        addToCartButton.click();

        // Step 6: Verify the cart has been updated and displays the number of items in the cart
        WebElement cartCount = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='gh-cart-n']")));
        Assert.assertTrue(Integer.parseInt(cartCount.getText()) > 0, "Cart is not updated with the item.");

        System.out.println("Test Passed: Item added to the cart successfully.");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}