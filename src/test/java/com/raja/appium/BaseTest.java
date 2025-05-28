package com.raja.appium;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class BaseTest {
  public static AppiumDriverLocalService serviceBuilder;
  public static AndroidDriver androidDriver;

  public static final String mainJSPath =
      "C:\\Users\\palle\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js";
  public static final String ipAddress = "127.0.0.1";
  public static final int portNumber = 4723;
  public static final String physicalDevice = "samsung SM-M325F";

  @BeforeClass
  public void appiumTest() throws MalformedURLException, URISyntaxException {

    System.out.println("Starting Appium server...");
    serviceBuilder =
        new AppiumServiceBuilder()
            .withAppiumJS(new File(mainJSPath))
            .withIPAddress(ipAddress)
            .usingPort(portNumber)
            .withTimeout(Duration.ofSeconds(60))
            .build();
    serviceBuilder.start();
    System.out.println("Appium server started at " + ipAddress + ":" + portNumber);
    System.out.println("Waiting for the Appium server to be ready...");

    if (!serviceBuilder.isRunning()) {
      throw new RuntimeException("Appium server failed to start!");
    }

    System.out.println("Running Appium test...");
    UiAutomator2Options options = new UiAutomator2Options();
    options.setDeviceName(physicalDevice);
    options.setApp("D:\\Appium_Learning\\appiumlearning\\src\\test\\java\\resources\\PROD.apk");
    options.setCapability("uiautomator2ServerInstallTimeout", 100000);
    options.setCapability("uiautomator2ServerLaunchTimeout", 100000);
    options.setCapability("adbExecTimeout", 100000);

    androidDriver = new AndroidDriver(new URI("http://127.0.0.1:4723").toURL(), options);

    androidDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  }
  @AfterClass
    public void tearDown() {
        System.out.println("Appium test completed successfully.");
        if (androidDriver != null) {
        androidDriver.quit();
        System.out.println("Quitting the driver...");
        }
        if (serviceBuilder != null) {
        serviceBuilder.stop();
        System.out.println("Appium server stopped.");
        }
  }
}
