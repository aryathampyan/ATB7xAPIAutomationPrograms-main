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
import java.util.Set;

public class EbayTest {
    WebDriver driver;
    WebDriverWait wait;


    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10

        @Test
        public void verifyItemCanBeAddedToCart() {
            // Step 2: Navigate to ebay.com
            driver.get("https://www.ebay.com");

            // Step 3: Search for 'book'
            WebElement searchBox = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
            searchBox.sendKeys("book");
            searchBox.submit();

            // Step 4: Click on the first book in the list
            WebElement firstBook = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='item57841b8566']/div/div[2]/a/div/span")));
            firstBook.click();

            // Handle the new window or tab
            String originalWindow = driver.getWindowHandle();
            Set<String> allWindows = driver.getWindowHandles();
            for (String window : allWindows) {
                if (!window.equals(originalWindow)) {
                    driver.switchTo().window(window);
                    break;
                }
            }

            // Step 5: In the item listing page, click on 'Add to cart'
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"atcBtn_btn_1\"]")));
            addToCartButton.click();

            // Step 6: Verify the cart has been updated and displays the number of items in the cart
            WebElement cartIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='gh-cart__icon']")));
            String ariaLabel = cartIcon.getAttribute("aria-label");
            Assert.assertTrue(ariaLabel.contains("1 items"), "Cart is not showing the number 1.");

            System.out.println("Test Passed: Cart symbol shows the number 1.");

            // Switch back to the original window
            driver.switchTo().window(originalWindow);
        }

        @AfterClass
        public void teardown() {
            if (driver != null) {
                driver.quit();
            }
        }
    }