package com.farzan.chat;

import com.farzan.chat.model.ChatMessage;
import com.farzan.chat.pageobjects.ChatPage;
import com.farzan.chat.pageobjects.LoginPage;
import com.farzan.chat.pageobjects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ChatApplicationTests {


	@Test
	void contextLoads() {
	}

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	private String baseUrl;
	private String firstname = "Spring";
	private String lastname = "Framework";
	private String username = "spring.io";
	private String password = "$#79&A#&Q3Bk){tn-";
	private String message = "With Spring Boot you deploy everywhere you can find a JVM basically.";


	@BeforeAll
	public static void beforeAll()
	{
		WebDriverManager.chromedriver().setup();
	}

	@AfterAll
	public static void afterAll(){
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach(){
		this.driver = new ChromeDriver();
		baseUrl = "http://localhost:" + port;
	}

	@Test
	public void testUserSignupSigninSubmitMessage(){

		driver.get(baseUrl+"/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);

		driver.get(baseUrl+"/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.signin(username, password);

		ChatPage chatPage = new ChatPage(driver);
		chatPage.sendChatMessage(message);

		ChatMessage chatMessage = chatPage.getFirstChatMessage();

		assertEquals(username, chatMessage.getUsername());
		assertEquals(message, chatMessage.getMessageText());
	}

}
