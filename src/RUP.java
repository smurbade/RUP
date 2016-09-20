import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

 

//Created by Sachin Murbade

public class RUP {

	public static void main(String[] args) throws ParseException {
	
		int No_of_Dev_Resources = 0;
		int No_of_QA_Resources = 0;
		int total_resources = 0;
		String [][] Resources = null;
		
        String RUP_Input_File = "RUP_Input.csv";
        String cvsSplitBy = ",";
        int resource_count = 0;
        int row_num = 0;
        
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Daily Efforts Distribution");
        XSSFSheet sheet2 = workbook.createSheet("Weekly Efforts Distribution");
        
        try (BufferedReader PSF = new BufferedReader(new FileReader(RUP_Input_File))) 
        {
        	
/*        	File file = new File("RUP_Projection.csv");
        	if (!file.exists()) {
				file.createNewFile();
			}

        	FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Date, Total Efforts for the day");
*/			
			RUP_Array[] rup_array = new RUP_Array[25];

			
			RUP_Daywise_Efforts[] rup_daywise_efforts = new RUP_Daywise_Efforts[180];
			
			//System.out.printf("%ta, %<tb %<te, %<tY", nextDayOfWeek(Calendar.MONDAY));
			
			Date da = nextDayOfWeek(Calendar.MONDAY);
			DateFormat dateFormat33 = new SimpleDateFormat("MM/dd/yyyy");
		    
			//System.out.println(da);
			
			String[][] csvMatrix;
			
			int rup_array_count = 0;
			
			while ( rup_array_count < 180 )
			{
				rup_daywise_efforts[rup_array_count] = new RUP_Daywise_Efforts();
				rup_array_count++;
			}
			
			String RUPLine = "";
	        String[] DevEffortDist = null;
	    	LinkedList<String[]> rows = new LinkedList<String[]>();
	    	String dataRow;


        	while ((RUPLine = PSF.readLine()) != null) 
        	{

                String[] RUPRowArray = RUPLine.split(cvsSplitBy);
                row_num++;
                
                if ( RUPRowArray[0].isEmpty() )
                {
                	break;
                }
                
                //System.out.println(RUPRowArray[0]);

                if ( row_num == 1 )
                {
                	DevEffortDist = RUPLine.split(",");
                	for ( int i = 0; i < DevEffortDist.length; i++ )
                	{
                		//System.out.println(DevEffortDist[i]);
                	}
                }
                if ( ( row_num == 2 ) || ( row_num == 3 ) )
                {
        	    	rows.addLast(RUPLine.split(","));
        	    	if ( row_num == 3 )
        	    	{
        	    		csvMatrix = rows.toArray(new String[rows.size()][]);
        	    		
        	    		if (csvMatrix != null)
        	    		{
        	    			No_of_Dev_Resources = csvMatrix[0].length;
        	    			No_of_QA_Resources = csvMatrix[1].length;
        	    			
        	    			total_resources = No_of_Dev_Resources + No_of_QA_Resources;
        	    			
        	    			Resources = new String[total_resources][2];
        	    			for(int i = 0; i < csvMatrix[0].length-1; i++) 
        	    			{
        	    				Resources[i][0] = csvMatrix[0][i+1];
        	    				Resources[i][1] = "0";
        	    			}
        	    			for(int i = 0; i < csvMatrix[1].length-1; i++) 
        	    			{
        	    				Resources[i+No_of_Dev_Resources-1][0] = csvMatrix[1][i+1];
        	    				Resources[i+No_of_Dev_Resources-1][1] = "0";
        	    			}
        	    			for(int i = 0; i < total_resources-2; i ++) 
        	    			{
        	    				//System.out.println("RESOURCE NAMES :" + Resources[i][0] + " " + Resources[i][1] );
        	    			}
        	    			No_of_Dev_Resources = No_of_Dev_Resources -1;
        	    			No_of_QA_Resources  = No_of_QA_Resources -1;
        	    			total_resources = No_of_Dev_Resources + No_of_QA_Resources;
/*        	    			for (int i=0; i<total_resources; i++)
        	    			{

        	    				bw.write("," + Resources[i][0]);
        	    			}
        	    			bw.newLine();
*/        	    		}
        	    		else
        	    		{
        	    			System.out.println("Please enter Resources correctly");
        	    			return;
        	    		}
        	    	
        	    	}
        	    	
                }
                if ( ( row_num == 3 ) && (total_resources != 0) )
                {
        			rup_array_count = 0;
        			while ( rup_array_count < 180 )
        			{
        				rup_daywise_efforts[rup_array_count].setrow(dateFormat33.format(da), 0, total_resources);
        				//rup_daywise_efforts[rup_array_count].print();

        				String sourceDate = dateFormat33.format(da);
        				SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        				da = format.parse(sourceDate);
        				da = addDays(da, 1);				
        				//System.out.println(format.parse(sourceDate));
        				rup_array_count++;
        			}
                }
                	
                if ( row_num > 4 )
                {
                	//System.out.println("Row number :" + row_num);
                	//System.out.println("Dev resources : "+ RUPRowArray[18] + "   QA Resources :" + RUPRowArray[19]);
                	String[] Dev_Resources_for_project= RUPRowArray[18].split(";");
                	String[] QA_Resources_for_project = RUPRowArray[19].split(";");
                	int no_of_dev_resources_working_on_project = Dev_Resources_for_project.length;
                	int no_of_qa_resources_working_on_project  = QA_Resources_for_project.length;
                	for (int i=0; i < no_of_dev_resources_working_on_project; i++)
                	{
                		Dev_Resources_for_project[i] = Dev_Resources_for_project[i].trim();
                	}
                	for (int i=0; i < no_of_qa_resources_working_on_project; i++)
                	{
                		QA_Resources_for_project[i] = QA_Resources_for_project[i].trim();
                	}
                	//System.out.println("Dev resources : "+ no_of_dev_resources_working_on_project + "   QA Resources :" + no_of_qa_resources_working_on_project);
                	rup_array[row_num-5] = new RUP_Array();
                	rup_array[row_num-5].setrow(RUPRowArray[6], RUPRowArray[7], RUPRowArray[8], RUPRowArray[9], RUPRowArray[10], RUPRowArray[11], RUPRowArray[12], RUPRowArray[13], RUPRowArray[14], RUPRowArray[15], RUPRowArray[16], Double.parseDouble(RUPRowArray[17]), Double.parseDouble(RUPRowArray[1]), Double.parseDouble(RUPRowArray[2]), Double.parseDouble(RUPRowArray[3]), Double.parseDouble(RUPRowArray[4]), Double.parseDouble(RUPRowArray[5]));
                	DateFormat d1 = new SimpleDateFormat("MM/dd/yyyy");
                	Date sd6 = d1.parse(RUPRowArray[6]);
                	Date sd7 = d1.parse(RUPRowArray[7]);
                	Date sd8 = d1.parse(RUPRowArray[8]);
                	Date sd9 = d1.parse(RUPRowArray[9]);
                	Date sd10 = d1.parse(RUPRowArray[10]);
                	Date sd11 = d1.parse(RUPRowArray[11]);
                	Date sd12 = d1.parse(RUPRowArray[12]);
                	Date sd13 = d1.parse(RUPRowArray[13]);
                	Date sd14 = d1.parse(RUPRowArray[14]);
                	Date sd15 = d1.parse(RUPRowArray[15]);
                	//Calculate phase wise efforts per day
                	
                	double Req_efforts_per_day = 0;
                	double Des_efforts_per_day = 0;
                	double Const_efforts_per_day = 0;
                	double Test_efforts_per_day = 0;
                	double Rel_efforts_per_day = 0;

                	if (rup_array[row_num-5].Current_Phase.equals("R"))
                	{
                		Req_efforts_per_day   = rup_array[row_num-5].ReqEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd6, sd7);
                    	Req_efforts_per_day   = Req_efforts_per_day * (100 - rup_array[row_num-5].Current_Phase_Completion_Percentage) / 100;                    	
                    	Des_efforts_per_day   = rup_array[row_num-5].DesEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd8, sd9);
                    	Const_efforts_per_day = rup_array[row_num-5].ConstEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd10, sd11);
                    	Test_efforts_per_day  = rup_array[row_num-5].TestEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd12, sd13);
                    	Rel_efforts_per_day   = rup_array[row_num-5].RelEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd14, sd15);
                	}
                	if (rup_array[row_num-5].Current_Phase.equals("D"))
                	{
                    	Des_efforts_per_day   = rup_array[row_num-5].DesEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd8, sd9);
                    	Des_efforts_per_day   = Des_efforts_per_day * (100 - rup_array[row_num-5].Current_Phase_Completion_Percentage) / 100;                    	Des_efforts_per_day = rup_array[row_num-5].DesEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd8, sd9);
                    	Const_efforts_per_day = rup_array[row_num-5].ConstEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd10, sd11);
                    	Test_efforts_per_day  = rup_array[row_num-5].TestEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd12, sd13);
                    	Rel_efforts_per_day   = rup_array[row_num-5].RelEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd14, sd15);
                	}
                	if (rup_array[row_num-5].Current_Phase.equals("C"))
                	{
                    	Const_efforts_per_day   = rup_array[row_num-5].ConstEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd10, sd11);
                    	Const_efforts_per_day   = Const_efforts_per_day * (100 - rup_array[row_num-5].Current_Phase_Completion_Percentage) / 100;                    	Des_efforts_per_day = rup_array[row_num-5].DesEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd8, sd9);
                    	Test_efforts_per_day    = rup_array[row_num-5].TestEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd12, sd13);
                    	Rel_efforts_per_day     = rup_array[row_num-5].RelEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd14, sd15);
                	}
                	if (rup_array[row_num-5].Current_Phase.equals("T"))
                	{
                    	Test_efforts_per_day = rup_array[row_num-5].TestEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd12, sd13);
                    	Test_efforts_per_day = Test_efforts_per_day * (100 - rup_array[row_num-5].Current_Phase_Completion_Percentage) / 100;                    	Des_efforts_per_day = rup_array[row_num-5].DesEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd8, sd9);
                    	Rel_efforts_per_day  = rup_array[row_num-5].RelEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd14, sd15);
                	}
                	if (rup_array[row_num-5].Current_Phase.equals("L"))
                	{
                		Rel_efforts_per_day = rup_array[row_num-5].RelEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd14, sd15);
                    	Rel_efforts_per_day = Rel_efforts_per_day * (100 - rup_array[row_num-5].Current_Phase_Completion_Percentage) / 100;                    	Des_efforts_per_day = rup_array[row_num-5].DesEfforts / rup_array[row_num-5].CalculateDaywiseEfforts(sd8, sd9);
                	}

                	//System.out.println("Row Num = " + row_num);
                	//System.out.println("Req_efforts_per_day  : " + Req_efforts_per_day);
                	//System.out.println("Des_efforts_per_day  : " + Des_efforts_per_day);
                	//System.out.println("Const_efforts_per_day  : " + Const_efforts_per_day);
                	//System.out.println("Test_efforts_per_day  : " + Test_efforts_per_day);
                	//System.out.println("Rel_efforts_per_day  : " + Rel_efforts_per_day);
                	//fill RUP_Daywise_Efforts arrow with efforts for respective dates
        			

    				String QAResources[][] = new String[total_resources][2];
    				for(int i = 0; i < total_resources; i++) 
    				{
    					Resources[i][1] = "0";
    					QAResources[i][1] = "0";
    				}
    				for ( int i = 0; i < total_resources ; i++ )
    				{
    					for(int j = 0; j < 2; j++) 
        				{
    						QAResources[i][j]=Resources[i][j];
    						//System.out.println(QAResources[i][j] + " mapped to " + Resources[i][j] );
        				}
    				}
    				
    				//System.out.println("No of dev resource : " + no_of_dev_resources_working_on_project);		
    				//System.out.println("No of QA resource : " + no_of_qa_resources_working_on_project);		
    				for ( int i = 0; i < no_of_dev_resources_working_on_project ; i++ )
    				{
    					//System.out.println("Dev Resource here " + Dev_Resources_for_project[i]);
    					for(int j = 0; j < total_resources; j++) 
        				{
    						if (Resources[j][0].equalsIgnoreCase(Dev_Resources_for_project[i]))
    						{
    							Resources[j][1] = "1";
    							//System.out.println(Resources[j][0] + " Matched with " + Dev_Resources_for_project[i] );
    						}
        				}
    				}
    				for ( int i = 0; i < no_of_qa_resources_working_on_project ; i++ )
    				{
    					//System.out.println("QA Resource here " + QA_Resources_for_project[i]);
    					for(int j = 0; j < total_resources; j++) 
        				{
    						if (QAResources[j][0].equalsIgnoreCase(QA_Resources_for_project[i]))
    						{
    							QAResources[j][1] = "1";
    						}
        				}
    				}

/*    				for ( int i = 0; i < total_resources ; i++ )
    				{
    					for(int j = 0; j < 2; j++) 
        				{
    						//System.out.println("Dev List here " + Resources[i][j]);
    						//System.out.println("QA List here "  + QAResources[i][j]);
        				}
    				}
    				
    				
*/
    				int rup_daywise_array_count = 0;
        			while ( rup_daywise_array_count < 180 )
        			{
        				if ( Weekday(rup_daywise_efforts[rup_daywise_array_count].Effort_Date ))
        				{
        				
	        				if ( ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[6]) ) || ( rup_daywise_efforts[rup_daywise_array_count].beforecompare(RUPRowArray[6]) ) ) && ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[7]) ) || ( rup_daywise_efforts[rup_daywise_array_count].aftercompare(RUPRowArray[7])  )     )   )
	        				{
	        					//System.out.println("RUP Arrow date " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date + "   Req Start Date " + RUPRowArray[6] + "   Req End Date " + RUPRowArray[7] );
	        					//System.out.println("Exact Match found");
	        					//rup_daywise_efforts[rup_daywise_array_count].addEfforts(Req_efforts_per_day);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Req_efforts_per_day/2, total_resources, Resources,  "Dev", Integer.parseInt(DevEffortDist[1]), no_of_dev_resources_working_on_project);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Req_efforts_per_day/2, total_resources, QAResources, "QA", Integer.parseInt(DevEffortDist[1]), no_of_qa_resources_working_on_project);
	        					//public void addEfforts(double efforts, String[][] devqaresources, String devorqaflag, int devphasewiseefforts, int noofdevqaresourcesassignedforproject)
	            				//System.out.println("Efforts Date	 : " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date  + "RUP Arrow Req efforts for day : " + rup_daywise_efforts[rup_daywise_array_count].Total_Efforts);
	        				}
	        				if ( ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[8]) ) || ( rup_daywise_efforts[rup_daywise_array_count].beforecompare(RUPRowArray[8]) ) ) && ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[9]) ) || ( rup_daywise_efforts[rup_daywise_array_count].aftercompare(RUPRowArray[9])  )     )   )
	        				{
	        					//System.out.println("RUP Arrow date " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date + "   Req Start Date " + RUPRowArray[6] + "   Req End Date " + RUPRowArray[7] );
	        					//System.out.println("Exact Match found");
//	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Des_efforts_per_day);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Des_efforts_per_day/2, total_resources, Resources,  "Dev", Integer.parseInt(DevEffortDist[2]), no_of_dev_resources_working_on_project);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Des_efforts_per_day/2, total_resources, QAResources, "QA", Integer.parseInt(DevEffortDist[2]), no_of_qa_resources_working_on_project);
	            				//System.out.println("Efforts Date	 : " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date  + "RUP Arrow Des efforts for day : " + rup_daywise_efforts[rup_daywise_array_count].Total_Efforts);
	        				}
	        				if ( ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[10]) ) || ( rup_daywise_efforts[rup_daywise_array_count].beforecompare(RUPRowArray[10]) ) ) && ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[11]) ) || ( rup_daywise_efforts[rup_daywise_array_count].aftercompare(RUPRowArray[11])  )     )   )
	        				{
	        					//System.out.println("RUP Arrow date " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date + "   Req Start Date " + RUPRowArray[6] + "   Req End Date " + RUPRowArray[7] );
	        					//System.out.println("Exact Match found");
//	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Const_efforts_per_day);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Const_efforts_per_day/2, total_resources, Resources,  "Dev", Integer.parseInt(DevEffortDist[3]), no_of_dev_resources_working_on_project);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Const_efforts_per_day/2, total_resources, QAResources, "QA", Integer.parseInt(DevEffortDist[3]), no_of_qa_resources_working_on_project);
	            				//System.out.println("Efforts Date	 : " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date  + "RUP Arrow Const efforts for day : " + rup_daywise_efforts[rup_daywise_array_count].Total_Efforts);
	        				}
	        				if ( ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[12]) ) || ( rup_daywise_efforts[rup_daywise_array_count].beforecompare(RUPRowArray[12]) ) ) && ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[13]) ) || ( rup_daywise_efforts[rup_daywise_array_count].aftercompare(RUPRowArray[13])  )     )   )
	        				{
	        					//System.out.println("RUP Arrow date " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date + "   Req Start Date " + RUPRowArray[6] + "   Req End Date " + RUPRowArray[7] );
	        					//System.out.println("Exact Match found");
//	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Test_efforts_per_day);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Test_efforts_per_day/2, total_resources, Resources,  "Dev", Integer.parseInt(DevEffortDist[4]), no_of_dev_resources_working_on_project);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Test_efforts_per_day/2, total_resources, QAResources, "QA", Integer.parseInt(DevEffortDist[4]), no_of_qa_resources_working_on_project);
	            				//System.out.println("Efforts Date	 : " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date  + "RUP Arrow Test efforts for day : " + rup_daywise_efforts[rup_daywise_array_count].Total_Efforts);
	        				}
	        				if ( ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[14]) ) || ( rup_daywise_efforts[rup_daywise_array_count].beforecompare(RUPRowArray[14]) ) ) && ( ( rup_daywise_efforts[rup_daywise_array_count].equalcompare(RUPRowArray[15]) ) || ( rup_daywise_efforts[rup_daywise_array_count].aftercompare(RUPRowArray[15])  )     )   )
	        				{
	        					//System.out.println("RUP Arrow date " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date + "   Req Start Date " + RUPRowArray[6] + "   Req End Date " + RUPRowArray[7] );
	        					//System.out.println("Exact Match found");
//	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Rel_efforts_per_day);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Rel_efforts_per_day/2, total_resources, Resources,  "Dev", Integer.parseInt(DevEffortDist[5]), no_of_dev_resources_working_on_project);
	        					rup_daywise_efforts[rup_daywise_array_count].addEfforts(Rel_efforts_per_day/2, total_resources, QAResources, "QA", Integer.parseInt(DevEffortDist[5]), no_of_qa_resources_working_on_project);
	            				//System.out.println("Efforts Date	 : " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date  + "RUP Arrow Rel efforts for day : " + rup_daywise_efforts[rup_daywise_array_count].Total_Efforts);
	        				}
	        				
        				}
        				
        				//System.out.print("Efforts Date	 : " + rup_daywise_efforts[rup_daywise_array_count].Effort_Date  + "RUP Arrow total efforts for day : " + rup_daywise_efforts[rup_daywise_array_count].Total_Efforts);
        				//System.out.println(rup_daywise_efforts[rup_daywise_array_count].Total_Efforts);
        				for (int i=0; i<total_resources; i++)
        				{
        					//System.out.print("  Resource" + i + " Efforts : " + rup_daywise_efforts[rup_daywise_array_count].ResourcewiseEfforts[i]);
        					//System.out.print("  " + Resources[i][0] + " Efforts : " + rup_daywise_efforts[rup_daywise_array_count].ResourcewiseEfforts[i]);
        				}
        				//System.out.println("");
        				rup_daywise_array_count++;
        			}

	    			}	
        			
                }
        	
/*        	int rup_daywise_array_count = 0;
			while ( rup_daywise_array_count < 180 )
        	{
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				//bw.write(dateFormat.format(rup_daywise_efforts[rup_daywise_array_count].Effort_Date)  + "," + String.format("%.1f", rup_daywise_efforts[rup_daywise_array_count].Total_Efforts));
				bw.write(dateFormat.format(rup_daywise_efforts[rup_daywise_array_count].Effort_Date)  + "," + rup_daywise_efforts[rup_daywise_array_count].Total_Efforts);
				for (int i=0; i<total_resources; i++)
				{
					bw.write("," + String.format("%.1f", rup_daywise_efforts[rup_daywise_array_count].ResourcewiseEfforts[i]));
				}
				bw.newLine();
				rup_daywise_array_count++;
        	}
*/        	
			
			createHeaderRow(sheet, total_resources, Resources, "D" );
        	int rup_daywise_array_count = 0;
        	XSSFCellStyle cellStyledate = sheet.getWorkbook().createCellStyle();
        	XSSFFont font = sheet.getWorkbook().createFont();
    	    font.setFontHeightInPoints((short) 9);
		    font.setBold(true);
    	    cellStyledate.setFont(font);
    	    cellStyledate.setWrapText(true);
    	    cellStyledate.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyledate.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyledate.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyledate.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);

        	XSSFCellStyle cellStyletotalefforts = sheet.getWorkbook().createCellStyle();
        	XSSFFont font3 = sheet.getWorkbook().createFont();
    	    font3.setFontHeightInPoints((short) 9);
    	    cellStyletotalefforts.setFont(font3);
    	    cellStyletotalefforts.setWrapText(true);
    	    cellStyletotalefforts.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyletotalefforts.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyletotalefforts.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyletotalefforts.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);

    	    
	        for (int rowcount = 0; rowcount<180; rowcount++)
	        {
	            	Row row = sheet.createRow(rowcount+1);

	            	Cell celldate = row.createCell(0);
    				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    				celldate.setCellStyle(cellStyledate);
    				celldate.setCellValue(dateFormat.format(rup_daywise_efforts[rowcount].Effort_Date));

	            	Cell celltotalefforts = row.createCell(1);
    				celltotalefforts.setCellStyle(cellStyletotalefforts);
    				celltotalefforts.setCellValue(rup_daywise_efforts[rowcount].Total_Efforts);
    			    
	            	for (int column=0; column<total_resources; column++)
	            	{
	    			    Cell rupcell = row.createCell(column+2);

	    			    if ( rup_daywise_efforts[rowcount].ResourcewiseEfforts[column] > 1 )
	    			    {
	    			    XSSFCellStyle cellStyle2 = sheet.getWorkbook().createCellStyle();
	    	        	XSSFFont font2 = sheet.getWorkbook().createFont();
	    	    	    font2.setFontHeightInPoints((short) 9);
	    			    cellStyle2.setFont(font2);
	    			    cellStyle2.setWrapText(true);
	    			    cellStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 4, 4)));
	    	    	    cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    	    	    cellStyle2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    //cellStyle2.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("##.##"));
	    			    rupcell.setCellStyle(cellStyle2);
	    			    }
	    			    if ( ( rup_daywise_efforts[rowcount].ResourcewiseEfforts[column] > 0.75 ) && (rup_daywise_efforts[rowcount].ResourcewiseEfforts[column] <= 1 ) )
	    			    {
	    			    XSSFCellStyle cellStyle2 = sheet.getWorkbook().createCellStyle();
	    	        	XSSFFont font2 = sheet.getWorkbook().createFont();
	    	    	    font2.setFontHeightInPoints((short) 9);
	    			    cellStyle2.setFont(font2);
	    			    cellStyle2.setWrapText(true);
	    			    cellStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(248, 255, 4)));
	    	    	    cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    	    	    cellStyle2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    //cellStyle2.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("##.##"));
	    			    rupcell.setCellStyle(cellStyle2);
	    			    }
	    			    if ( rup_daywise_efforts[rowcount].ResourcewiseEfforts[column] <= 0.75 )
	    			    {
	    			    XSSFCellStyle cellStyle2 = sheet.getWorkbook().createCellStyle();
	    	        	XSSFFont font2 = sheet.getWorkbook().createFont();
	    	    	    font2.setFontHeightInPoints((short) 9);
	    			    cellStyle2.setFont(font2);
	    			    cellStyle2.setWrapText(true);
	    			    cellStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(4, 255, 38)));
	    	    	    cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    	    	    cellStyle2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    cellStyle2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
	    	    	    //cellStyle2.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("##.##"));
	    			    rupcell.setCellStyle(cellStyle2);
	    			    }
	    			    rupcell.setCellValue(rup_daywise_efforts[rowcount].ResourcewiseEfforts[column]);
	            	}	
	         }
	        
/////////////////////// Formatting
	        
/*
	        XSSFSheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();

	        XSSFConditionalFormattingRule rule3 = sheetCF.createConditionalFormattingRule(ComparisonOperator.GT, "-0.1" );
	        XSSFPatternFormatting patternFmtrule3 = rule3.createPatternFormatting();
	        patternFmtrule3.setFillBackgroundColor(IndexedColors.GREEN.index);
	        
	        XSSFConditionalFormattingRule rule2 = sheetCF.createConditionalFormattingRule(ComparisonOperator.GT, "0.75" );
	        XSSFPatternFormatting patternFmtrule2 = rule2.createPatternFormatting();
	        patternFmtrule2.setFillBackgroundColor(IndexedColors.YELLOW.index);

	        XSSFConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(ComparisonOperator.GT, "1");
	        XSSFFontFormatting fontFmt = rule1.createFontFormatting();
	        fontFmt.setFontStyle(true, false);
	        fontFmt.setFontColorIndex(IndexedColors.DARK_RED.index);
	        
	        //XSSFBorderFormatting bordFmt = rule1.createBorderFormatting();
	        //bordFmt.setBorderBottom(BorderFormatting.BORDER_THIN);
	        //bordFmt.setBorderTop(BorderFormatting.BORDER_THICK);
	        //bordFmt.setBorderLeft(BorderFormatting.BORDER_DASHED);
	        //bordFmt.setBorderRight(BorderFormatting.BORDER_DOTTED);

	        XSSFPatternFormatting patternFmt = rule1.createPatternFormatting();
	        patternFmt.setFillBackgroundColor(IndexedColors.RED.index);

	        
	        XSSFConditionalFormattingRule [] cfRules =
	        {
	            rule1, rule2, rule3
	        };

	        CellRangeAddress[] regions = {
	            CellRangeAddress.valueOf("C2:DZ181")
	        };

	        sheetCF.addConditionalFormatting(regions, cfRules);

	        XSSFSheetConditionalFormatting sheetCF2 = sheet.getSheetConditionalFormatting();

	        XSSFConditionalFormattingRule rule4 = sheetCF.createConditionalFormattingRule(ComparisonOperator.EQUAL , "" );
	        XSSFPatternFormatting patternFmtrule4 = rule4.createPatternFormatting();
	        patternFmtrule4.setFillBackgroundColor(IndexedColors.WHITE.index);

	        XSSFConditionalFormattingRule [] cfRules2 =
	        {
	            rule4
	        };

	        CellRangeAddress[] regions2 = {
	            CellRangeAddress.valueOf("C2:DZ181")
	        };

	        sheetCF2.addConditionalFormatting(regions2, cfRules2);

	        
*/	        
////////////////////////	        

        	XSSFCellStyle cellStyledate2 = sheet2.getWorkbook().createCellStyle();
        	XSSFFont font22 = sheet2.getWorkbook().createFont();
    	    font22.setFontHeightInPoints((short) 9);
		    font22.setBold(true);
    	    cellStyledate2.setFont(font22);
    	    cellStyledate2.setWrapText(true);
    	    cellStyledate2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyledate2.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyledate2.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyledate2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);

        	XSSFCellStyle cellStyletotalefforts2 = sheet2.getWorkbook().createCellStyle();
        	XSSFFont font32 = sheet2.getWorkbook().createFont();
    	    font32.setFontHeightInPoints((short) 9);
    	    cellStyletotalefforts2.setFont(font32);
    	    cellStyletotalefforts2.setWrapText(true);
    	    cellStyletotalefforts2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyletotalefforts2.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyletotalefforts2.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
    	    cellStyletotalefforts2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
    	    
			createHeaderRow(sheet2, total_resources, Resources, "W" );

    	    
			//bw.write("Week start date,Number of Resources required during the week");
          	//bw.newLine();
    
			// Calculate week wise efforts

			RUP_Weekwise_Efforts[] rup_weekwise_efforts = new RUP_Weekwise_Efforts[25];

			int daily_array_count = 0;
			int weekly_array_count = 0;
			
			while ( daily_array_count < 175 )
			{
				//System.out.println("daily_array_count :" + daily_array_count);
				rup_daywise_efforts[daily_array_count].print();
				int daycount = 0;
				while ( daycount < 7 )
				{
					if ( daycount == 0 )
					{	
						rup_weekwise_efforts[weekly_array_count] = new RUP_Weekwise_Efforts();
						   DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
						   String WeekDate = df.format(rup_daywise_efforts[daily_array_count].Effort_Date);
						   rup_weekwise_efforts[weekly_array_count].setrow(WeekDate, 0, total_resources);   
					}
					rup_weekwise_efforts[weekly_array_count].addEfforts(rup_daywise_efforts[daily_array_count].Total_Efforts, rup_daywise_efforts[daily_array_count].ResourcewiseEfforts, total_resources); 
					
					daycount++;
    				daily_array_count++;
				}
				//rup_weekwise_efforts[weekly_array_count].print(total_resources);
				
            	Row row2 = sheet2.createRow(weekly_array_count+1);

            	Cell celldate2 = row2.createCell(0);
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				celldate2.setCellStyle(cellStyledate2);
				celldate2.setCellValue(dateFormat.format(rup_weekwise_efforts[weekly_array_count].Week_Effort_Date));

            	Cell celltotalefforts2 = row2.createCell(1);
				celltotalefforts2.setCellStyle(cellStyletotalefforts);
				celltotalefforts2.setCellValue(rup_weekwise_efforts[weekly_array_count].Efforts);
            	
            	
            	for (int i=0; i<total_resources; i++)
            	{
	            	Cell resourceefforts = row2.createCell(i+2);
    			    if ( rup_weekwise_efforts[weekly_array_count].ResourceweeklyEfforts[i] > 5 )
    			    {
    			    XSSFCellStyle cellStyle2 = sheet2.getWorkbook().createCellStyle();
    	        	XSSFFont font2 = sheet2.getWorkbook().createFont();
    	    	    font2.setFontHeightInPoints((short) 9);
    			    cellStyle2.setFont(font2);
    			    cellStyle2.setWrapText(true);
    			    cellStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 4, 4)));
    	    	    cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
    	    	    cellStyle2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
    	    	    //cellStyle2.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("##.##"));
    	    	    resourceefforts.setCellStyle(cellStyle2);
    			    }
    			    if ( ( rup_weekwise_efforts[weekly_array_count].ResourceweeklyEfforts[i] > 3.75 ) && (rup_weekwise_efforts[weekly_array_count].ResourceweeklyEfforts[i] <= 5 ) )
    			    {
    			    XSSFCellStyle cellStyle2 = sheet2.getWorkbook().createCellStyle();
    	        	XSSFFont font2 = sheet2.getWorkbook().createFont();
    	    	    font2.setFontHeightInPoints((short) 9);
    			    cellStyle2.setFont(font2);
    			    cellStyle2.setWrapText(true);
    			    cellStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(248, 255, 4)));
    	    	    cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
    	    	    cellStyle2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
    	    	    //cellStyle2.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("##.##"));
    	    	    resourceefforts.setCellStyle(cellStyle2);
    			    }
    			    if ( rup_weekwise_efforts[weekly_array_count].ResourceweeklyEfforts[i] <= 3.75 )
    			    {
    			    XSSFCellStyle cellStyle2 = sheet2.getWorkbook().createCellStyle();
    	        	XSSFFont font2 = sheet2.getWorkbook().createFont();
    	    	    font2.setFontHeightInPoints((short) 9);
    			    cellStyle2.setFont(font2);
    			    cellStyle2.setWrapText(true);
    			    cellStyle2.setFillForegroundColor(new XSSFColor(new java.awt.Color(4, 255, 38)));
    	    	    cellStyle2.setFillPattern(CellStyle.SOLID_FOREGROUND);
    	    	    cellStyle2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
    	    	    cellStyle2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
    	    	    //cellStyle2.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("##.##"));
    	    	    resourceefforts.setCellStyle(cellStyle2);
    			    }
	            	resourceefforts.setCellValue(rup_weekwise_efforts[weekly_array_count].ResourceweeklyEfforts[i]);
            	}

//				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				//System.out.println(String.format("%.1f", rup_weekwise_efforts[weekly_array_count].Efforts / 5));
				//bw.write(dateFormat.format(rup_weekwise_efforts[weekly_array_count].Week_Effort_Date) + "," + String.format("%.1f", rup_weekwise_efforts[weekly_array_count].Efforts / 5));
              	//bw.newLine();

				weekly_array_count++;
			}    
            
			
//			bw.close();	        
	        
	        
	        
	        
			try (FileOutputStream outputStream = new FileOutputStream("Resourcewise_Efforts_Distribution.xlsx"))
	        {
	            workbook.write(outputStream);
	        } catch (FileNotFoundException e) 
	        {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) 
	        {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        }
        catch(FileNotFoundException exception)
        {
            System.out.println("RUP Input file not found.");
        }
        catch (IOException c)
        {
        	c.printStackTrace();
        } // End of RUP_Input_File read
        
	}

	public static Date nextDayOfWeek(int dow) 
	{
        Calendar date = Calendar.getInstance();
        int diff = dow - date.get(Calendar.DAY_OF_WEEK);
        if ((diff == 0)) 
        {
        	return date.getTime();
        }
        if (!(diff > 0)) 
        {
            diff += 7;
        }
        
        date.add(Calendar.DAY_OF_MONTH, diff);
        return date.getTime();
    }
	
	
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	public static boolean Weekday(Date rupdaywiseeffortsdate) 
	{
		   Calendar startcal = Calendar.getInstance();
		   startcal.setTime(rupdaywiseeffortsdate);
		   if (startcal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startcal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) 
		   {
			   return true;
		   }
		   return false;
	}
	
	private static void createHeaderRow(XSSFSheet sheet, int noresources, String[][] resourcelist, String DW) 
	{
		 
		XSSFCellStyle cellStyleHeader = sheet.getWorkbook().createCellStyle();
	    XSSFFont font = sheet.getWorkbook().createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short) 14);
	    cellStyleHeader.setFont(font);
	    cellStyleHeader.setFillForegroundColor(new XSSFColor(new java.awt.Color(214, 4, 255)));
	    cellStyleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);

	    cellStyleHeader.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
	    cellStyleHeader.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
	    cellStyleHeader.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
	    cellStyleHeader.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
	    
	    
	    Row row = sheet.createRow(0);
	    Cell cellDate = row.createCell(0);
	 
	    cellDate.setCellStyle(cellStyleHeader);
	    cellDate.setCellValue("Date");
	 
	    Cell celltotalefforts = row.createCell(1);
	    celltotalefforts.setCellStyle(cellStyleHeader);
	    if ( DW == "D" )
	    celltotalefforts.setCellValue("Day wise Efforts");
	    if ( DW == "W" )
		    celltotalefforts.setCellValue("Week wise Efforts");
		    
	    for (int i=0; i<noresources; i++)
		{
			//System.out.print("  " + resourcelist[i][0] + " Efforts : " );
		    Cell cellresourcename = row.createCell(i+2);
		    cellresourcename.setCellStyle(cellStyleHeader);
		    cellresourcename.setCellValue(resourcelist[i][0]);
		}
	    
	}
	private static void createHeaderRow2(XSSFSheet sheet2, int noresources, String[][] resourcelist, String DW) 
	{
		 
		XSSFCellStyle cellStyleHeader = sheet2.getWorkbook().createCellStyle();
	    XSSFFont font = sheet2.getWorkbook().createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short) 14);
	    cellStyleHeader.setFont(font);
	    cellStyleHeader.setFillForegroundColor(new XSSFColor(new java.awt.Color(214, 4, 255)));
	    cellStyleHeader.setFillPattern(CellStyle.SOLID_FOREGROUND);

	    cellStyleHeader.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
	    cellStyleHeader.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
	    cellStyleHeader.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
	    cellStyleHeader.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
	    
	    
	    Row row2 = sheet2.createRow(0);
	    Cell cellDate = row2.createCell(0);
	 
	    cellDate.setCellStyle(cellStyleHeader);
	    cellDate.setCellValue("Date");
	 
	    Cell celltotalefforts = row2.createCell(1);
	    celltotalefforts.setCellStyle(cellStyleHeader);
	    if ( DW == "D" )
	    celltotalefforts.setCellValue("Day wise Efforts");
	    if ( DW == "W" )
		    celltotalefforts.setCellValue("Week wise Efforts");
		    
	    for (int i=0; i<noresources; i++)
		{
			//System.out.print("  " + resourcelist[i][0] + " Efforts : " );
		    Cell cellresourcename = row2.createCell(i+2);
		    cellresourcename.setCellStyle(cellStyleHeader);
		    cellresourcename.setCellValue(resourcelist[i][0]);
		}
	    
	}
}