package keyworddriven;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;

public class Tst {

	public static void main(String[] args) 
	{
		System.setProperty("webdriver.edge.driver","E://Testingtools//msedgedriver.exe");
		EdgeOptions eo=new EdgeOptions();
		eo.setBinary("C:\\Program Files (x86)\\microsoft\\Edge Beta\\Application\\msedge.exe");
		EdgeDriver driver=new EdgeDriver(eo);
		driver.get("https://www.facebook.com");

	}

}
