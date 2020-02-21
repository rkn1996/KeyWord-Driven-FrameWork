package keyworddriven;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookMethods 
{
   //declare global variable
	WebDriver driver;
	SimpleDateFormat sf;
	Date dt;
	WebDriverWait wait;
	
	public FacebookMethods()
	{
		sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		dt=new Date();
	}
	
	private String screenshot() throws Exception
	{
		String ssname=sf.format(dt)+".png";
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File dest=new File(ssname);
		FileHandler.copy(src, dest);
		return(dest.getAbsolutePath());		
	}
	
	public String openbrowser(String l,String d,String uc,String pc)
	{
		if(l.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","E://Testingtools//chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if(l.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","E://Testingtools//geckodriver.exe");
			driver=new FirefoxDriver();
		}
		else if(l.equalsIgnoreCase("opera"))
		{
			System.setProperty("webdriver.opera.driver","E://Testingtools//operadriver.exe");
			OperaOptions oo=new OperaOptions();
			oo.setBinary("C:\\Users\\USER\\AppData\\Local\\Programs\\Opera\\65.0.3467.78\\opera.exe");
			driver=new OperaDriver(oo);
		}
		else if(l.equalsIgnoreCase("edge"))
		{
			System.setProperty("webdriver.edge.driver","E://Testingtools//msedgedriver.exe");
			EdgeOptions eo=new EdgeOptions();
			eo.setBinary("C:\\Program Files (x86)\\microsoft\\Edge Beta\\Application\\msedge.exe");
			driver=new EdgeDriver(eo);
		}
		else if(l.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver","E://Testingtools//iedriverserver.exe");
			driver=new InternetExplorerDriver();
		}
		else
		{
			System.setProperty("webdriver.chrome.driver","E://Testingtools//chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(d);
			wait= new WebDriverWait(driver,20);
			return("unknown browser name,so we went to chrome by default");
		}
		driver.manage().window().maximize();
		driver.get(d);
		wait= new WebDriverWait(driver,20);
		return("Done");		
	}
	
	public String fill(String l,String d,String uc,String pc)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(l)));
		driver.findElement(By.xpath(l)).sendKeys(d);
		return("Done");
	}
	
	public String closesite(String l,String d,String uc,String pc) throws Exception
	{
		Thread.sleep(5000);
		driver.quit();
		return("Done");
	}
	
	public String click(String l,String d,String uc,String pc)
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(l)));
		driver.findElement(By.xpath(l)).click();
		return("Done");
	}
	
	public String validateuidandpwd(String l,String d,String uc,String pc) throws Exception
	{
		Thread.sleep(5000);
		try
		{
		if(uc.equals("valid") && pc.equals("valid") && driver.findElement(By.xpath("//a[text()='Home']")).isDisplayed())
		  {
			  return("Login test passed for valid uid and password");
		  }
		  else if(uc.equals("valid") && pc.equals("invalid") && driver.findElement(By.xpath("((//*[contains(text(),'The password that')])[1] | (//*[contains(text(),'The email address that')])[1])")).isDisplayed())
		  {
			  return("Login test passed for valid uid and invalid password");
		  }
		  else if(uc.equals("invalid") && pc.equals("valid") && driver.findElement(By.xpath("((//*[contains(text(),'The email address that')])[1]) | (//div[contains(text(),'We limit how ')])")).isDisplayed())
		  {
			  return("Login test passed for invalid uid and valid password");
		  }
		  else if(uc.equals("blank") && pc.equals("valid") && driver.findElement(By.xpath("//div[contains(text(),'The email address or phone number')]")).isDisplayed())
		  {
			  return("Login test passed for blank uid and valid password");
		  }
		  else if(uc.equals("valid") && pc.equals("blank") && driver.findElement(By.xpath("(//div[contains(text(),'The password that')] | (//*[contains(text(),'The email address that')])[1])")).isDisplayed())
		  {
			  return("Login test passed for valid uid and blank password");
		  }
		  else
		  {
			  String x=this.screenshot();
			  return("faceebook Userid and password test failed,so see "+x);
		  }
		}
		catch(Exception ex)
		{
			String x=this.screenshot();
			return(ex.getLocalizedMessage()+" "+x);
		}
	}
}
