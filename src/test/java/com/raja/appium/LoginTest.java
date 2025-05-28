package com.raja.appium;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.time.Duration;

public class LoginTest extends BaseTest {
  @Test
  public void loginTest(){
    WebDriverWait explicitWait = new WebDriverWait(androidDriver, Duration.ofSeconds(20));
    // FluentWait for a specific element
    FluentWait<AndroidDriver> fluentWait =
        new FluentWait<>(androidDriver)
            .withTimeout(Duration.ofSeconds(1000))
            .pollingEvery(Duration.ofSeconds(30))
            .ignoring(NoSuchElementException.class);

    System.out.println("Executing login test...");
    WebElement notificationAllowBtn =
        explicitWait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//android.widget.Button[@resource-id='com.android.permissioncontroller:id/permission_allow_button']")));
    notificationAllowBtn.click();

    WebElement enterDomain =
        explicitWait.until(
            ExpectedConditions.elementToBeClickable(By.className("android.widget.EditText")));
    enterDomain.click();
    enterDomain.clear();
    enterDomain.sendKeys("asp");

    androidDriver
        .findElement(AppiumBy.accessibilityId("Enter your Company Domain to Login"))
        .click();

    WebElement continueButton =
        explicitWait.until(
            ExpectedConditions.elementToBeClickable(By.className("android.widget.Button")));
    continueButton.click();

    WebElement enterUsername =
        explicitWait.until(
            ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"username\")")));
    enterUsername.click();
    enterUsername.clear();
    enterUsername.sendKeys("9649964096");

    WebElement enterPassword =
        explicitWait.until(
            ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"password\")")));
    enterPassword.click();
    enterPassword.clear();
    enterPassword.sendKeys("123456");

    WebElement loginButton =
        explicitWait.until(
            ExpectedConditions.elementToBeClickable(
                AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"kc-login\")")));
    loginButton.click();

    System.out.println("Waiting for sync to complete...");

    // Wait for the sync complete indicator
    By syncComplete = AppiumBy.androidUIAutomator("new UiSelector().description(\"Sync Complete\")");
    fluentWait.until(ExpectedConditions.visibilityOfElementLocated(syncComplete));
    System.out.println("Sync complete detected.");
    }
  }