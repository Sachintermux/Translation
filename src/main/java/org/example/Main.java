package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "E:\\Download\\chromedriver_win32\\chromedriver.exe");
        String fromLng = "en";
        String toLang = askLang();
        HashMap<String, String> hashMap = takeInputData();
        ChromeOptions options = new ChromeOptions();
//        options.setHeadless(true);
        options.addArguments("--headless");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://translate.google.co.in/?sl=en&tl=" + toLang + "&op=translate");
        hashMap.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                driver.findElement(By.className("er8xn")).sendKeys(s2);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ryNqvb")));
                String st = driver.findElement(By.className("ryNqvb")).getText();
                hashMap.replace(s, st);
                System.out.println(s2 + "  =  " + st);
                driver.findElement(By.xpath("//button[@aria-label='Clear source text']//div[@class='VfPpkd-Bz112c-RLmnJb']")).click();
            }
        });

        driver.close();

        hashMap.forEach(new BiConsumer<String, String>() {
            @Override
            public void accept(String s, String s2) {
                System.out.println(s + ">" + s2 + "</string>");
            }
        });
    }

    private static HashMap<String, String> takeInputData() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Start Line Number : ");
        Integer start = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter End Line Number : ");
        Integer end = Integer.parseInt(scanner.nextLine());
        HashMap<String, String> hashMap = new HashMap<>();
        int x = (end - start);
        for (int i = 0; i <= x; i++) {
            String text = scanner.nextLine();
            text = text.replace("</string>", "");
            String[] arr = text.split(">");
            hashMap.put(arr[0], arr[1].trim());
        }
        return hashMap;

    }

    private static String askLang() {
        String toLang = "hi";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose Language Hindi, Telugu, Marathi");
        String lang = scanner.nextLine();
        if (lang.equalsIgnoreCase("Hindi") || lang.equalsIgnoreCase("hi")) {
            System.out.println("Selected Lang : Hindi");
            toLang = "hi";
        } else if (lang.equalsIgnoreCase("Telugu") || lang.equalsIgnoreCase("te")) {
            System.out.println("Selected Lang : Telugu");
            toLang = "te";
        } else if (lang.equalsIgnoreCase("Marathi") || lang.equalsIgnoreCase("mr")) {
            System.out.println("Selected Lang : Marathi");
            toLang = "mr";
        }
        return toLang;
    }
}