package zerobank_login;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ZeroBank_Login {
	
	WebDriver driver;
	String login = "username";
	String goodPW = "password";
	String badPW1 = "passnerd";
	String badPW2 = "passsword";
		
	
	@Test
	public void zeroBank_Login() {
	driver.findElement(By.id("online-banking")).click();
	driver.findElement(By.id("account_summary_link")).click();
	
	// INVALID CREDENTIALS TEST
	
	driver.findElement(By.id("user_login")).sendKeys(login);
	driver.findElement(By.id("user_password")).sendKeys(badPW1);
	driver.findElement(By.xpath("//input[@value='Sign in']")).click();

	boolean loginFail1 = driver.findElement(By.xpath("//div[@class='alert alert-error'][contains(.,'Login and/or password are wrong.')]")).isDisplayed();
	Assert.assertTrue(loginFail1);
	System.out.println("CONFIRM Invalid Credentials: " + loginFail1);
	
		
	// 2ND INVALID CREDENTIALS TEST
	
	driver.findElement(By.id("user_login")).sendKeys(login);
	driver.findElement(By.id("user_password")).sendKeys(badPW2);
	driver.findElement(By.xpath("//input[@value='Sign in']")).click();

	boolean loginFail2 = driver.findElement(By.xpath("//div[@class='alert alert-error'][contains(.,'Login and/or password are wrong.')]")).isDisplayed();
	Assert.assertTrue(loginFail2);
	System.out.println("CONFIRM 2nd Invalid Credentials: " + loginFail2);
		
	
	// FORGOT PASSWORD
	driver.findElement(By.xpath("//a[contains(text(),'Forgot your password ?')]")).click();
	driver.findElement(By.id("user_email")).sendKeys(login);
	driver.findElement(By.name("submit")).click();
	boolean getPassword = driver.findElement(By.xpath("(//div[contains(.,'Your password will be sent to the following email: username')])[5]")).isDisplayed();
	Assert.assertTrue(getPassword);
	System.out.println("CONFIRM: " + getPassword);
	
	// VALID CREDENTIALS TEST
	driver.findElement(By.xpath("//a[@class='brand']")).click();
	driver.findElement(By.xpath("//button[@id='signin_button']")).click();
	driver.findElement(By.id("user_login")).sendKeys(login);
	driver.findElement(By.id("user_password")).sendKeys(goodPW);
	driver.findElement(By.id("user_remember_me")).click();
	driver.findElement(By.xpath("//input[@value='Sign in']")).click();	
		
	String expLanding = "Account Activity";
	String actLanding = driver.findElement(By.id("account_activity_tab")).getText();
	if (actLanding.equals(expLanding)) {
		System.out.println("SUCCESSFUL LOGIN CONFIRMATION: " + actLanding);
	}
	else {
		System.out.println("TEST FAILED");
		}
	}
		
	
	@BeforeMethod
	public void setUp() {
		System.out.println("Starting test");
		driver = utilities.DriverFactory.open("firefox");
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.get("http://zero.webappsecurity.com/index.html");
	}
	
	@AfterMethod
	public void tearDown() {
		System.out.println("Ending test");
		//driver.quit();
	}
		
}
