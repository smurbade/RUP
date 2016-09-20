
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RUP_Output {

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
    int Efforts;
    // and many more fields ...

   public RUP_Output() {  // constructor 

   }

   public void setrow(String reqstartdate, String reqenddate, String desstartdate, String desenddate, String conststartdate, String constenddate, String teststartdate, String testenddate, String relstartdate, String relenddate, int efforts) {
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
       
       
       this.Efforts = efforts;
       
   }

   
   public void print() {
	   DateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
       System.out.println( "Req Start Date : " + dateFormat.format(this.Req_Start_Date) + "   Req End Date : " + dateFormat.format(this.Req_End_Date) + "   Des Start Date : " + dateFormat.format(this.Des_Start_Date) + "   Des End Date : " + dateFormat.format(this.Des_End_Date) + "   Const Start Date : " + dateFormat.format(this.Const_Start_Date) + "   Const End Date : " + dateFormat.format(this.Const_End_Date) + "   Testing Start Date : " + dateFormat.format(this.Test_Start_Date) + "   Testing End Date : " + dateFormat.format(this.Test_End_Date) + "   Rel Start Date : " + dateFormat.format(this.Rel_Start_Date) + "   Rel End Date : " + dateFormat.format(this.Rel_End_Date) + "   Efforts : " + this.Efforts);
   }

}
