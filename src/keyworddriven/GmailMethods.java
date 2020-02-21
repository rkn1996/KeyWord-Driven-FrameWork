package keyworddriven;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
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

public class GmailMethods 
{
    //Declare global objects
	public WebDriver driver;
	public WebDriverWait wait;
	public SimpleDateFormat sf;
	public Date dt;
	//constructor method to run while create object to this class
	public GmailMethods()
	{
		//Define independent object
		sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		dt=new Date();
	}
	//Local methods for code re-usability in current classes
	private String screenshot() throws Exception
	{
		//get screenshot and attach to result
		String ssname=sf.format(dt)+".png";
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File dest=new File(ssname);
		FileHandler.copy(src,dest);
		return(dest.getAbsolutePath());
	}
	//method related to keyword-driven framework
	public String launch(String l,String d,String c)
	{
		if(l.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver","E://Testingtools//chromedriver.exe");
			driver=new ChromeDriver();
		}
		else if(l.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver","E:\\Testingtools\\geckodriver.exe");
			driver=new FirefoxDriver();
		}
		else if(l.equalsIgnoreCase("opera"))
		{
			System.setProperty("webdriver.opera.driver","E:\\Testingtools\\operadriver_win64\\operadriver.exe");
			OperaOptions oo=new OperaOptions();
			oo.setBinary("C:\\Users\\USER\\AppData\\Local\\Programs\\Opera\\65.0.3467.78\\opera.exe");
			driver=new OperaDriver(oo);
		}
		else if(l.equalsIgnoreCase("edge"))
		{
			EdgeOptions eo=new EdgeOptions();
			eo.setBinary("C:\\Program Files (x86)\\microsoft\\Edge Beta\\Application\\msedge.exe");
			System.setProperty("webdriver.edge.driver","E:\\Testingtools\\msedgedriver.exe");
			driver=new EdgeDriver(eo);
		}
		else if(l.equalsIgnoreCase("ie"))
		{
			//Set "ie" browser zoom level to exact 100% manually
			System.setProperty("webdriver.ie.driver","E:\\Testingtools\\iedriverserver.exe");
			driver=new InternetExplorerDriver();
		}
		else
		{
			//unknown browser
			System.setProperty("webdriver.chrome.driver","E://Testingtools//chromedriver.exe");
			driver=new ChromeDriver();
			driver.manage().window().maximize();
			driver.get(d);
			wait=new WebDriverWait(driver,20);
			return("unkonwn browser name, so we went to chrome by default");
		}
		//common code for any browser
		driver.manage().window().maximize();
		driver.get(d);
		wait=new WebDriverWait(driver,20);
		return("Done");
	}
	public String fill(String l,String d,String c)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(l)));
		driver.findElement(By.xpath(l)).sendKeys(d);
		return("Done");
	}
	public String close(String l,String d,String c) throws Exception
	{
		Thread.sleep(5000);
		driver.quit();
		return("Done");
	}
	public String click(String l,String d,String c)
	{
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(l)));
		driver.findElement(By.xpath(l)).click();
		return("Done");
	}
	public String validateuserid(String l,String d,String c) throws Exception
	{
		Thread.sleep(5000);
		try
		{
			if(c.equalsIgnoreCase("blank") && driver.findElement(By.xpath("(//*[contains(text(),'Enter an email')])[2]")).isDisplayed())
			{
				return("Login test passed for blank userid");
			}
			else if(c.equalsIgnoreCase("invalid") && driver.findElement(By.xpath("((//*[contains(text(),'find your Google Account')])[2])|((//*[contains(text(),'Enter a valid email')])[2])")).isDisplayed())
			{
				return("Login test passed for invalid userid");
			}
			else if(c.equalsIgnoreCase("valid") && driver.findElement(By.name("password")).isDisplayed())
			{
				return("Login test passed for valid userid");		
			}
			else
			{
				String x=this.screenshot();
				return("Gmail userid test failed,so see "+x);
			}
		}
		catch(Exception ex)
		{
			String x=this.screenshot();
			return(ex.getLocalizedMessage()+" "+x);
		}
	}
	public String validatepwd(String l,String d,String c) throws Exception
	{
		Thread.sleep(5000);
		try
		{
			if(c.equalsIgnoreCase("blank") && driver.findElement(By.xpath("//*[text()='Enter a password']")).isDisplayed())
			{
				return("Login test passed for blank password");
			}
			else if(c.equalsIgnoreCase("invalid") && driver.findElement(By.xpath("//*[contains(text(),'Wrong password')]")).isDisplayed())
			{
				return("Login test passed for invalid password");
			}
			else if(c.equalsIgnoreCase("valid") && driver.findElement(By.xpath("//*[text()='Compose']")).isDisplayed())
			{
				return("Login test passed for valid password");
			}
			else
			{
				String x=this.screenshot();
				return("Gmail password test failed,so see "+x);
			}
		}
		catch(Exception ex)
		{
			String x=this.screenshot();
			return(ex.getLocalizedMessage()+" "+x);
		}
	}		
}
