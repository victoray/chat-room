package com.odinson.chatroom;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class ChatRoomApplicationTests {

    private WebDriver driver;

    @Before
    public void start() throws Exception {
        System.setProperty("webdriver.chrome.driver", "/Users/victoray/Downloads/chromedriver");
        driver = new ChromeDriver();
    }

    @Test
    public void testLogin(){
        // Go to login page
        driver.get("http://localhost:8080/");

        String username = "fury";

        // Check if on the correct page
        assertEquals("Chat Room Login", driver.getTitle());

        // Enter username
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys(username);

        // Click on the login button
        WebElement button = driver.findElement(By.className("act-but"));
        button.click();

        // Check if login is successful
        assertEquals("Chat Room", driver.getTitle());

        WebElement usernameElement = driver.findElement(By.id("username"));
        assertEquals(username, usernameElement.getText());
    }

    private void newUsers() {
        // Open new tabs with new users
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String username = "odin";
        String username1 = "fury";
        js.executeScript("window.open('http://localhost:8080/index?username=" + username + "','_blank');");
        js.executeScript("window.open('http://localhost:8080/index?username=" + username1 + "','_blank');");
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
    }
    @Test
    public void joinChat() {

        newUsers();
        // See if all users are connected
        WebElement chatNumber = driver.findElement(By.className("chat-num"));
        assertEquals(String.valueOf(2), chatNumber.getText());
    }

    @Test
    public void chat() {

        newUsers();

        // Send message
        String msg = "message";
        WebElement chat = driver.findElement(By.id("msg"));
        chat.sendKeys(msg);
        chat.sendKeys(Keys.ENTER);

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(2));


//        add all messages to a list
        List<WebElement> messages = driver.findElements(By.className("message-content"));

//        get last element and check if it contains the message
        WebElement message = messages.get(messages.size() - 1);
        assertTrue(message.getText().contains(msg));
    }

    @Test
    public void leave() {
        newUsers();

        WebElement logoutBtn = driver.findElement(By.tagName("a"));
        logoutBtn.click();

        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        // Go to logged in user
        driver.switchTo().window(tabs.get(2));
        WebElement chatNumber = driver.findElement(By.className("chat-num"));
        // Check if user is logged out
        assertEquals(Integer.toString(1), chatNumber.getText());
    }


    @After
    public void end() throws Exception {
        driver.quit();
    }
}
