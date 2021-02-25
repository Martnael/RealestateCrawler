package com.adaur.Crawler.services;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

public class TestClassForTestingCrawlers {
    public static void main(String[] args) {
        File file = new File("src/main/resources/chromedriver.exe");
        System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());

        WebDriver driver = new ChromeDriver();
        driver.get("https://kaamos.ee/hobemetsa/hinnad/kadaka-tee-191a/");


        driver.close();
        driver.quit();

    }
}
