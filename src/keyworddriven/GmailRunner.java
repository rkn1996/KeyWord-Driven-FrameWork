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

public class GmailRunner 
{
	public static void main(String[] args) throws Exception
	{
		//create object to methods class and collect all methods of that class
		GmailMethods obj=new GmailMethods();
		Method[] m=obj.getClass().getMethods();
		//connect to existing keyword-driven excel file for reading
		File f=new File("E:\\kdframeworkgmail.xlsx");
		FileInputStream fi=new FileInputStream(f);
		Workbook wb=WorkbookFactory.create(fi);
		//goto sheet1
		Sheet testssh=wb.getSheet("Sheet1"); //1st sheet
		int testscount=testssh.getPhysicalNumberOfRows();
		int nouc1=testssh.getRow(0).getPhysicalNumberOfCells();
		System.out.println("No. of tests is "+testscount);
		System.out.println("No. of used columns in tests sheet is"+nouc1);
		//goto sheet2
		Sheet stepssh=wb.getSheet("Sheet2"); //2st sheet
		int stepscount=stepssh.getPhysicalNumberOfRows();
		int nouc2=stepssh.getRow(0).getPhysicalNumberOfCells();
		System.out.println("No. of steps is "+stepscount);
		System.out.println("No. of used columns in steps sheet is"+nouc2);
		//create result columns in sheet1 before going to tests execution
		testssh.autoSizeColumn(nouc1);
		CellStyle cs=wb.createCellStyle();
		cs.setWrapText(true);
		SimpleDateFormat sf=new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss");
		Date dt=new Date();
		Cell thc=testssh.getRow(0).createCell(nouc1);
		thc.setCellStyle(cs);
		thc.setCellValue("Test results on "+sf.format(dt));
		try
		{
			//keyword driven
			for(int i=1;i<testscount;i++)
			{
				String tid=testssh.getRow(i).getCell(0).getStringCellValue();
				String mode=testssh.getRow(i).getCell(2).getStringCellValue();
				if(mode.equalsIgnoreCase("yes"))
				{
					int flag=0;
					//create result column in sheet2
					stepssh.autoSizeColumn(nouc2);
					Cell shc=stepssh.getRow(0).createCell(nouc2);
					shc.setCellStyle(cs);
					shc.setCellValue("step results on "+sf.format(dt));
					for(int j=1;j<stepscount;j++)
					{
						String sid=stepssh.getRow(j).getCell(0).getStringCellValue();
						if(tid.equalsIgnoreCase(sid))
						{
							String mn=stepssh.getRow(j).getCell(2).getStringCellValue();
							String l=stepssh.getRow(j).getCell(3).getStringCellValue();
							String d=stepssh.getRow(j).getCell(4).getStringCellValue();
							String c=stepssh.getRow(j).getCell(5).getStringCellValue();
							System.out.println(mn+" "+l+" "+d+" "+c);
							for(int k=0;k<m.length;k++)
							{
								if(m[k].getName().equalsIgnoreCase(mn))
								{
									String res=(String) m[k].invoke(obj,l,d,c);
									Cell src=stepssh.getRow(j).createCell(nouc2);
									src.setCellStyle(cs);
									src.setCellValue(res);
									res=res.toLowerCase();
									if(res.contains("Passed"))
									{
										if(res.contains("Done"))
										{
											flag=0;
										}
									}
									break;
								}
							}//for k ending
						}
					}//for j ending
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
			}//for i ending
		}
		catch(Exception ecx)
		{
			System.out.println(ecx.getMessage());
		}
		//save and closed
		FileOutputStream fo=new FileOutputStream(f);
		wb.write(fo);
		fo.close();
		fi.close();
		wb.close();
	}
}
