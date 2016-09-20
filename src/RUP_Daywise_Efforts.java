
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RUP_Daywise_Efforts {

    Date Effort_Date;
    double  Total_Efforts;
    double [] ResourcewiseEfforts;

   public void setrow(String effortdate, double efforts, int totalresources) throws ParseException 
   {
	   DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	   this.Effort_Date = dateFormat.parse(effortdate);
       this.Total_Efforts = this.Total_Efforts + efforts; 
       this.ResourcewiseEfforts = new double[totalresources];
   }

   public void addEfforts(double efforts, int totalresources, String[][] devqaresources, String devorqaflag, int devphasewiseefforts, int noofdevqaresourcesassignedforproject)
   {
	   this.Total_Efforts = this.Total_Efforts + efforts;
	   try
	   {
		   if (devorqaflag.equals("Dev"))
		   {
			   for (int i=0; i<totalresources; i++)
			   {
				   this.ResourcewiseEfforts[i] = this.ResourcewiseEfforts[i] + Double.parseDouble(devqaresources[i][1]) * efforts * 2 * devphasewiseefforts / (100 * noofdevqaresourcesassignedforproject); 
			   }
		   }
		   if (devorqaflag.equals("QA"))
		   {
			   for (int i=0; i<totalresources; i++)
			   {
				   this.ResourcewiseEfforts[i] = this.ResourcewiseEfforts[i] + Double.parseDouble(devqaresources[i][1]) * efforts * 2 * (100 - devphasewiseefforts) / (100 * noofdevqaresourcesassignedforproject) ; 
			   }
		   }
	   }
	   catch(ArrayIndexOutOfBoundsException exception) 
	   {
	        System.out.println("Array Index Out of Bound - Daywise efforts - addEfforts");
	   }
   }
   
   
   public boolean equalcompare(String comparedate) throws ParseException 
   {
	   DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	   Date comparedatedate = df.parse(comparedate);
	   
	   String reportDate = df.format(this.Effort_Date);
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
       
       if (date1.compareTo(this.Effort_Date)<0)
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
       
       if (date1.compareTo(this.Effort_Date)>0)
       {
    	   //System.out.println(date1 + "is after " + this.Effort_Date);
		   return true;
	   }
	   return false;
   }

   
   public void print() {
	   DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
       //System.out.println( "daywise Effort Date : " + dateFormat.format(this.Effort_Date) + "  Efforts : " + this.Total_Efforts);
       for (int i =0; i< this.ResourcewiseEfforts.length; i++ )
       {
    	   //System.out.print("Resource " + i + " Efforts " + this.ResourcewiseEfforts[i]);
       }
       //System.out.println();
   }

}
