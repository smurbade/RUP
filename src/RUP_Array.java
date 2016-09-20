import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RUP_Array {

    Date Req_Start_Date;
    Date Req_End_Date;
    Date Des_Start_Date;
    Date Des_End_Date;
    Date Const_Start_Date;
    Date Const_End_Date;
    Date Test_Start_Date;
    Date Test_End_Date;
    Date Rel_Start_Date;
    Date Rel_End_Date;
    String Current_Phase;
    double Current_Phase_Completion_Percentage;
    double ReqEfforts;
    double DesEfforts;
    double ConstEfforts;
    double TestEfforts;
    double RelEfforts;

   public void setrow(String reqstartdate, String reqenddate, String desstartdate, String desenddate, String conststartdate, String constenddate, String teststartdate, String testenddate, String relstartdate, String relenddate, String currentphase, double currentphasecompletionpercentage, double efforts1, double efforts2, double efforts3, double efforts4, double efforts5) {
	   DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat2 = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat3 = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat4 = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat5 = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat6 = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat7 = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat8 = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat9 = new SimpleDateFormat("mm/dd/yyyy");
	   DateFormat dateFormat10 = new SimpleDateFormat("mm/dd/yyyy");
	   try 
	   {
		   this.Req_Start_Date = dateFormat.parse(reqstartdate);
	   }
	   catch (ParseException e) 
	   {
		   e.printStackTrace();
	   }
       try 
       {
    	   this.Req_End_Date = dateFormat2.parse(reqenddate);
       } 
       catch (ParseException e) 
       {
    	   e.printStackTrace();
       }
	   try 
	   {
		   this.Des_Start_Date = dateFormat3.parse(desstartdate);
	   }
	   catch (ParseException e) 
	   {
		   e.printStackTrace();
	   }
       try 
       {
    	   this.Des_End_Date = dateFormat4.parse(desenddate);
       } 
       catch (ParseException e) 
       {
    	   e.printStackTrace();
       }
	   try 
	   {
		   this.Const_Start_Date = dateFormat5.parse(conststartdate);
	   }
	   catch (ParseException e) 
	   {
		   e.printStackTrace();
	   }
       try 
       {
    	   this.Const_End_Date = dateFormat6.parse(constenddate);
       } 
       catch (ParseException e) 
       {
    	   e.printStackTrace();
       }
	   try 
	   {
		   this.Test_Start_Date = dateFormat7.parse(teststartdate);
	   }
	   catch (ParseException e) 
	   {
		   e.printStackTrace();
	   }
       try 
       {
    	   this.Test_End_Date = dateFormat8.parse(testenddate);
       } 
       catch (ParseException e) 
       {
    	   e.printStackTrace();
       }
	   try 
	   {
		   this.Rel_Start_Date = dateFormat9.parse(relstartdate);
	   }
	   catch (ParseException e) 
	   {
		   e.printStackTrace();
	   }
       try 
       {
    	   this.Rel_End_Date = dateFormat10.parse(relenddate);
       } 
       catch (ParseException e) 
       {
    	   e.printStackTrace();
       }
       
       this.Current_Phase = currentphase;
       this.Current_Phase_Completion_Percentage = currentphasecompletionpercentage;
       this.ReqEfforts = efforts1;
       this.DesEfforts = efforts2;
       this.ConstEfforts = efforts3;
       this.TestEfforts = efforts4;
       this.RelEfforts = efforts5;
       
   }

   
   public void print() {
	   DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
       System.out.println( "Req Start Date : " + dateFormat.format(this.Req_Start_Date) + "   Req End Date : " + dateFormat.format(this.Req_End_Date) + "   Des Start Date : " + dateFormat.format(this.Des_Start_Date) + "   Des End Date : " + dateFormat.format(this.Des_End_Date) + "   Const Start Date : " + dateFormat.format(this.Const_Start_Date) + "   Const End Date : " + dateFormat.format(this.Const_End_Date) + "   Testing Start Date : " + dateFormat.format(this.Test_Start_Date) + "   Testing End Date : " + dateFormat.format(this.Test_End_Date) + "   Rel Start Date : " + dateFormat.format(this.Rel_Start_Date) + "   Rel End Date : " + dateFormat.format(this.Rel_End_Date) + "   Efforts : " + this.ReqEfforts);
       //System.out.println(CalculateDaywiseEfforts(this.Req_Start_Date, this.Req_End_Date));
   }
   
   public int CalculateDaywiseEfforts(Date strdate, Date nddate) {
	   Calendar startcal = Calendar.getInstance();
	   Calendar endcal = Calendar.getInstance();
	   DateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
	   startcal.setTime(strdate);
	   endcal.setTime(nddate);
	   int numberofdaysbetweendates = 0;
	   //System.out.println("Before While loop - Start date " + startcal.getTime() + " End date " + endcal.getTime());
	   if ( sdf.format(startcal.getTime()).equals(sdf.format(endcal.getTime())) )
		   return 1;
	   while(true)
	   {
		   if (startcal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startcal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) 
		   {
			   numberofdaysbetweendates++;
		   }

	     startcal.add(Calendar.DAY_OF_MONTH, 1);

	    //if (dateFormat.format(startcal.getTime()).equals(dateFormat2.format(endcal.getTime()))) 
	    if ( sdf.format(startcal.getTime()).equals(sdf.format(endcal.getTime())) && startcal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startcal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY )  
	    {
	    	//System.out.println("Start date " + startcal.getTime() + " Matched with " + endcal.getTime());
	    	numberofdaysbetweendates++;
	    	break;
	    }
	    else if ( sdf.format(startcal.getTime()).equals(sdf.format(endcal.getTime())) )  
	    {
	    	//System.out.println("Start date " + startcal.getTime() + " Matched with " + endcal.getTime());
	    	break;
	    }
	   }	   
	   if (numberofdaysbetweendates==0)
	   {
		   //System.out.println("The subtraction between days is " + (numberofdaysbetweendates));
		   return 1;
	   }
	   else
	   {
		   //System.out.println("The subtraction between days is " + (numberofdaysbetweendates));
		   return (numberofdaysbetweendates);
	   }
		   
   }

}
