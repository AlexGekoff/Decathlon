package com.example.decathlon.Auto;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class MainWeb {

    private static WebDriver driver;


    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(WebDriver driver) {
        MainWeb.driver = driver;
    }
    //Gör en switch sats/modul för att man ska kunna välja olika websidor
    public static WebDriver initializeDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Ogiltigt webbläsare: " + browser);
        }
        //bestämmer storleken på webbpage/Browsern standard maximize setSize kräver olika dimensioner
        driver.manage().window().maximize();
        // driver.manage().window().setSize(new Dimension(375, 667))
        return driver;

    }
    //Lägger till wait så allting hinner laddas upp
    public static void waitForElementToBeVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    //Kod ifall man behöver stänga av webbläsaren efter varje testfall
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

}


