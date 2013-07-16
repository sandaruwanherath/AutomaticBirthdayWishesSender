
/**
 * S.W.H.M.S.P.Herath
 * 090181J
 */
package com.totsp.automaticsender;

import java.io.Console;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.List;
import com.totsp.DateManage.HelloDatePicker;
import com.totsp.ManageMessageSending.MessageToFacebook;
import com.totsp.ManageMessageSending.MessageToPhone;
import com.totsp.Database.DataHelper;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	
	static DataHelper dh;
	HelloDatePicker hp;
	MessageToPhone mtp;
	MessageToFacebook mtf;
	
	
	//DateChooser dc;
	private EditText text1;
	private EditText text2;
	private EditText text3;
	private EditText text4;
	private Button btnChangeDate;
	private DatePicker dpResult;
	
	
	private int year;
	private int month;
	private int day;
	private static final String TAG="Main";
	public static String birth;
	public static String birthContactNo;
	static final int DATE_DIALOG_ID = 999;
	public static boolean boo=true;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
            
            
              
        Button edit = (Button) findViewById(R.id.edit);
        Button back=(Button)findViewById(R.id.back);
        this.dh=new DataHelper(this);
        
        
        setBirthday();   // call method to check birthdays       
        
    }
    
    public void editBirthDay(View v){
    	switch (v.getId()) {
		case R.id.btnChangeDate:
			setCurrentDateOnView();  
	        addListenerOnButton();
			break;

		}
    	
        
    }
    
    public void goBack(View v){          //switch interface from edit view to the main window
    	setContentView(R.layout.main);
    }
    
    public void goEdit(View v){         //switch to the edit window
    	setContentView(R.layout.edit);
    	 
    }
    public void goDelete(View v){
    	setContentView(R.layout.delete);  //switch to the delete interface
    	addProfilesToDropdown();
    }
    
	public void addListenerOnButton() {
		 
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
 
		btnChangeDate.setOnClickListener(new OnClickListener() {
 
			//@Override
			public void onClick(View v) {
 
				showDialog(DATE_DIALOG_ID);
 
			}
 
		});
 
	}
	
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
		   // set date picker as current date
		   return new DatePickerDialog(this, datePickerListener,year, month,day);
		}
		return null;
	}
	
	private DatePickerDialog.OnDateSetListener datePickerListener= new DatePickerDialog.OnDateSetListener() {

// when dialog box is closed, below method will be called.
public void onDateSet(DatePicker view, int selectedYear,
	int selectedMonth, int selectedDay) {
year = selectedYear;
month = selectedMonth;
day = selectedDay;

// set selected date into textview

birth=new StringBuilder().append(year).append("-").append(month + 1).append("-").append(day).toString(); 
text3= (EditText) findViewById(R.id.editBirthday);
text3.setText(birth);
// set selected date into datepicker also
dpResult.init(year, month, day, null);

}
};

public void setCurrentDateOnView() {
	 
	
	dpResult = (DatePicker) findViewById(R.id.dpResult);

	final Calendar c = Calendar.getInstance();
	year = c.get(Calendar.YEAR);
	month = c.get(Calendar.MONTH);
	day = c.get(Calendar.DAY_OF_MONTH);	

	// set current date into datepicker
	dpResult.init(year, month, day, null);

}
    
  
    
        public void onClick(View v)  // insert contacts
        {         
        	text1 = (EditText) findViewById(R.id.editName);
        	text2 = (EditText) findViewById(R.id.editContactNo);
        	text3= (EditText) findViewById(R.id.editBirthday);
            text4 = (EditText) findViewById(R.id.editMessage);
         
        	String contactName = text1.getText().toString();
        	String contactNo = text2.getText().toString();
        	String birthDay = birth;                          //pppppppppppppppppppp
        	String conMessage = text4.getText().toString();
        	
        	 
        	if(contactName.equals("")){        	         	     //check contact name
        	     Toastdis("Enter Contact Name");
        	}else if(contactNo.equals("")){						//check contact number
        		Toastdis("Enter Contact Number");
        	}else if(conMessage.equals("")){                        //check message
        		Toastdis("Enter your Message");
        	}else if(birthDay.equals("")){                    //check birthday
        		Toastdis("Enter Contact Birthday");
        	}else{
        		if(Validity(contactName)){
        			dh.addContact(contactName,contactNo,conMessage,birthDay,this);    //insert into the database  
            		Toastdis("success");
        		}else{
        			Toastdis("Can't save"+" "+contactName+" "+"Already exists");
        		}
        	}
        }   
        
        public boolean Validity(String name){
        	boo=true;
        	List<String> names=dh.getData(this);
        	for(int u=0;u<names.size();u++){
        		if(name.equals(names.get(u))){
        			boo=false;
        		}
        	}
        	
        	return boo;
        }
        
        public void setBirthday(){         //check birthdays with current date
        	
        	HelloDatePicker hp=new HelloDatePicker();     //call HelloDataPicker class
            hp.getCurrentDate(this);
          
            List<String> fin=new ArrayList<String>();    
            List<String> listOfBirth=dh.getRelevantName(this);   //list to get whole list
            List<String> subBirth=new ArrayList<String>();    //list for birthdays
            List<String> subContact=new ArrayList<String>();    //list for contacts
            subContact=listOfBirth.subList(0, listOfBirth.size()/2);
            subBirth=listOfBirth.subList(listOfBirth.size()/2,listOfBirth.size());
          
            for(int j=0;j<subBirth.size();j++){
            	List<String> listOfMess=dh.getRelevantName(this);
           	    String[] a=subBirth.get(j).split("-");
           	    String mon=a[1];
           	    String dat=a[2];
           	    
           	 
           	 if(HelloDatePicker.currentDate.equals(dat)&&HelloDatePicker.currentMonth.equals(mon)){  //if current date tally with birthdays
           		birthContactNo=subContact.get(j);
           		System.out.println(subContact.get(j)+ " kkkkk");
           		 listOfMess.add(subContact.get(j));   //add contacts have birthdays today
           		
           		 String mess=selectMessage(subContact.get(j));
           		 
           		 sendSMS(subContact.get(j), mess);     //call message sending method
           		 //Context context = getApplicationContext();
            	 //int duration = Toast.LENGTH_LONG;
            	 //CharSequence text = "message sent to- "+subContact.get(j);       //display text "message send"	    
          	     //Toast toast = Toast.makeText(context, text, duration);
          	   
          	     String newMes="Message sent to- "+subContact.get(j);
          	     alertbox(newMes);
           	 }
           	
                }
           
        }
        
        /**
         * 
         * @param number
         * @return 
         */
        public String selectMessage(String number){
        	  String num = "";
             List<String> mesAndCon=dh.getMessage(this);   //list to get whole list
             List<String> subCon=new ArrayList<String>();    //list for contacts
             List<String> subMest=new ArrayList<String>();    //list for messages
             subCon=mesAndCon.subList(0, mesAndCon.size()/2);
             subMest=mesAndCon.subList(mesAndCon.size()/2,mesAndCon.size());
             
             for(int p=0;p<subCon.size();p++){
            	 if(subCon.get(p).equals(number)){
            		  num=subMest.get(p);
            		  
            	 }
             }
             return num;
        }
        
        
        
        /**
         * 
         * @param v
         */
        public void clearButtonMethod(View v){            //clear edit interface
        	text1 = (EditText) findViewById(R.id.editName);
        	text2 = (EditText) findViewById(R.id.editContactNo);
        	text3= (EditText) findViewById(R.id.editBirthday);
            text4 = (EditText) findViewById(R.id.editMessage);
            text1.setText("");
            text2.setText("");
            text3.setText("");
            text4.setText("");
            Toastdis("successfully deleted");              //display text "success delete"
        }
        
       public void rawDelete(View v){          //delete selected raw
    	   
    	   AutoCompleteTextView text=(AutoCompleteTextView)findViewById(R.id.contactToDelete);
    	   String toDeleteName = text.getText().toString();
    	   
    	   if(!Validity(toDeleteName)){
    	   dh.delete(toDeleteName,this);   	      	   
       		Toastdis("Successfully Deleted");                //display "deleted"
     	    AutoCompleteTextView textv = (AutoCompleteTextView) findViewById(R.id.contactToDelete);
     	     textv.setText("");
    	   }else{
    		   Toastdis("Select some contact to delete");
    	   }
       }
    
       
       protected void alertbox(String mess)      //to display alert box 
       {
    	   AlertDialog ad = new AlertDialog.Builder(this).create();  
    	   ad.setCancelable(false);                               // This blocks the 'BACK' button  
    	   ad.setMessage(mess);  
    	   ad.setButton("OK", new DialogInterface.OnClickListener() {  
    	       //@Override  
    	       public void onClick(DialogInterface dialog, int which) {  
    	           dialog.dismiss();                      
    	       }  
    	   });  
    	   ad.show(); 
       }
    
    
    
    private void sendSMS(String phoneNumber, String message)      //message send to the another phone
    {        
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
 
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,new Intent(SENT), 0);
 
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,new Intent(DELIVERED), 0);
 
  
        SmsManager sms = SmsManager.getDefault();  //get the default instance of the sms manager
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);        //send a Text based SMS
    }
    
     
    
    public void addProfilesToDropdown() {
    	List<String> Profiles=dh.getData(this); 
    	 
    	 String[] namesa= Profiles.toArray(new String[Profiles.size()]);
    	AutoCompleteTextView textView2 = (AutoCompleteTextView) findViewById(R.id.contactToDelete);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,R.layout.list_item, namesa);
		
		textView2.setAdapter(adapter2);
	}
    
    /**
     * 
     * @param message
     */
    public void Toastdis(String message){
    	Context context = getApplicationContext(); 
      	int duration = Toast.LENGTH_SHORT;
      	CharSequence text1 = message;       	    
  	    Toast toast = Toast.makeText(context, text1, duration);
  	    toast.show();
    }
    
  
    
    
}