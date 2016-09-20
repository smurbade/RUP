
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RUP_Weekwise_Efforts {

    Date Week_Effort_Date;
    double  Efforts;
    double [] ResourceweeklyEfforts;


   public void setrow(String effortdate, double efforts, int totalresources) throws ParseException 
   {
	   DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	   this.Week_Effort_Date = dateFormat.parse(effortdate);
       this.Efforts = this.Efforts + efforts;
       this.ResourceweeklyEfforts = new double[totalresources];
   }

   public void addEfforts(double efforts, double[] resourcedailyefforts, int totalresources)
   {
	   this.Efforts = this.Efforts + efforts;
	   for(int i=0; i<totalresources; i++)
	   {
		   this.ResourceweeklyEfforts[i] = this.ResourceweeklyEfforts[i] + resourcedailyefforts[i];
	   }
	   
   }
   
   public boolean equalcompare(String comparedate) throws ParseException 
   {
	   DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	   Date comparedatedate = df.parse(comparedate);
	   
	   String reportDate = df.format(this.Week_Effort_Date);
	   String changedcomparedate = df.format(comparedatedate);
	   
	   //System.out.println("Inside compare Effort Date: " + reportDate);
		
	   //System.out.println("Inside compare comparedate " + changedcomparedate);
	   if (reportDate.equals(changedcomparedate))
	   {
		   return true;
	   }
	   return false;
   }

   public boolean beforecompare(String comparedate) throws ParseException 
   {
	   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       Date date1 = sdf.parse(comparedate);
       
       if (date1.compareTo(this.Week_Effort_Date)<0)
       {
    	   //System.out.println(date1 + "is before " + this.Effort_Date);
		   return true;
	   }
	   return false;
   }
   
   public boolean aftercompare(String comparedate) throws ParseException 
   {
	   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
       Date date1 = sdf.parse(comparedate);
       
       if (date1.compareTo(this.Week_Effort_Date)>0)
       {
    	   //System.out.println(date1 + "is after " + this.Effort_Date);
		   return true;
	   }
	   return false;
   }

   public void print(int totalresources) {
	   DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
       System.out.print( "Week wise Effort Date : " + dateFormat.format(this.Week_Effort_Date) + "  Efforts : " + this.Efforts);
	   for(int i=0; i<totalresources; i++)
	   {
	       System.out.print( " : " + this.ResourceweeklyEfforts[i]);
	   }
	   System.out.println("");
   }

}
