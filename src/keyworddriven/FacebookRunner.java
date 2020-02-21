package keyworddriven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FacebookRunner 
{
	public static void main(String[] args) throws Exception
	{
		//create object to methods class and collect all methods
		FacebookMethods obj=new FacebookMethods();
		Method[] m=obj.getClass().getMethods();
		//connect to sheet1 and sheet2
		File f=new File("E:\\Projects\\kdframeworkfacebook.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		//goto sheet1
		Sheet testssh=wb.getSheet("Sheet1");
		int testcount=testssh.getPhysicalNumberOfRows();
		int nouc1=testssh.getRow(0).getPhysicalNumberOfCells();
		System.out.println("No. of rows in sheet1: "+testcount+" No. of column in sheet1: "+nouc1);
		//create new test column
		testssh.autoSizeColumn(nouc1);
		CellStyle cs=wb.createCellStyle();
		cs.setWrapText(true);
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		Cell tc=testssh.getRow(0).createCell(nouc1);
		tc.setCellStyle(cs);
		tc.setCellValue("Test results on "+sf.format(dt));
		//goto sheet2
		Sheet stepssh=wb.getSheet("Sheet2");
		int stepcount=stepssh.getPhysicalNumberOfRows();
		int nouc2=stepssh.getRow(0).getPhysicalNumberOfCells();
		System.out.println("No. of rows in sheet2: "+stepcount+" No. of column in sheet2: "+nouc2);
		try
		{
			for(int i=1;i<testcount;i++)
			{
				String tid=testssh.getRow(i).getCell(0).getStringCellValue();
				String mode=testssh.getRow(i).getCell(2).getStringCellValue();
				if(mode.equalsIgnoreCase("yes"))
				{
					int flag=0;
					//create result columns in sheet2
					stepssh.autoSizeColumn(nouc2);
					Cell sc=stepssh.getRow(0).createCell(nouc2);
					sc.setCellStyle(cs);
					sc.setCellValue("Test results on "+sf.format(dt));
					for(int j=1;j<stepcount;j++)
					{
						String sid=stepssh.getRow(j).getCell(0).getStringCellValue();
						if(tid.equalsIgnoreCase(sid))
						{
						    String mn=stepssh.getRow(j).getCell(2).getStringCellValue();
						    String l=stepssh.getRow(j).getCell(3).getStringCellValue();
						    String d=stepssh.getRow(j).getCell(4).getStringCellValue();
						    String uc=stepssh.getRow(j).getCell(5).getStringCellValue();
						    String pc=stepssh.getRow(j).getCell(6).getStringCellValue();
						    System.out.println(mn+" "+l+" "+d+" "+uc+" "+pc);
						    for(int k=0;k<m.length;k++)
						    {
						    	if(m[k].getName().equalsIgnoreCase(mn))
						    	{
						    		String res=(String) m[k].invoke(obj,l,d,uc,pc);
						    		Cell src=stepssh.getRow(j).createCell(nouc2);
						    		src.setCellStyle(cs);
						    		src.setCellValue(res);
						    		res=res.toLowerCase();
						    		if(!res.contains("passed"))
						    		{
						    			if(!res.contains("done"))
						    			{
						    				flag=1;
						    			}
						    		}
						    		break; 
						    	}
						    }//for k
						}
					}//for j
					Cell trc=testssh.getRow(i).createCell(nouc1);
					trc.setCellStyle(cs);
					if(flag==0)
					{
						trc.setCellValue("Test Passed");
					}
					else
					{
						trc.setCellValue("Test Failed");
					}
				}				
			}//for i
		}
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
        //save and close
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fo.close();
		fi.close();
		wb.close();
	}
}
