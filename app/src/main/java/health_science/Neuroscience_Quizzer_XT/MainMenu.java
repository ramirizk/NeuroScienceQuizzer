package health_science.Neuroscience_Quizzer_XT;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainMenu extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        View practical1 = findViewById(R.id.practical1);
        practical1.setOnClickListener(this);
        View practical2 = findViewById(R.id.practical2);
        practical2.setOnClickListener(this);
        View final_test = findViewById(R.id.final_test);
        final_test.setOnClickListener(this);
        
        
    	DataBaseHelper myDbHelper = new DataBaseHelper(null);
		myDbHelper = new DataBaseHelper(this);
		
		try {
			myDbHelper.createDataBase();
			myDbHelper.close();
		} catch (IOException ioe) {
			throw new Error("unable to create database");
		}
		try{
			myDbHelper.openDataBase();
			myDbHelper.close();
		} catch(SQLException sqle){
			throw sqle;
		}
		Button About = (Button)findViewById(R.id.about);
		About.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				AlertDialog msgDialog;
				msgDialog = createAlertDialog("About","Created by Rami Y. Rizk and Dr. Mike Hammonds, Fall 2011", "Okay");
				msgDialog.show();
			}
		});
		
		
    }
    
    public void onClick(View thisView) {
    	switch (thisView.getId()){
    	case R.id.practical1:
    		Intent showp1 = new Intent(this, lab_practical_1.class);
    		
    		startActivity(showp1);
    		break;
    	case R.id.practical2:
    		Intent showp2 = new Intent(this, lab_practical_2.class);
    		startActivity(showp2);
    		break;
    	case R.id.final_test:
    		Intent showp3 = new Intent(this, lab_practical_3.class);
    		startActivity(showp3);
    		break; 	

    	}
    
    }

    private AlertDialog createAlertDialog(String title, String msg, String buttonText){
		AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		AlertDialog msgDialog = dialogBuilder.create();
		msgDialog.setTitle(title);
		msgDialog.setMessage(msg);
		msgDialog.setButton(buttonText, new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int idx){
				return;
			}
		});
		
		return msgDialog;
	}
}