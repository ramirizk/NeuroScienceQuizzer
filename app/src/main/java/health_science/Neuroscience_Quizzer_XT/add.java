package health_science.Neuroscience_Quizzer_XT;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.content.Intent;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.content.DialogInterface;


public class add extends Activity implements OnClickListener {
	public RadioButton rb1;
	public RadioButton rb2;
	public RadioButton rb3;
	public Button b1;
	final int PICTURE_ACTIVITY = 1;


@Override
public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add);
	rb1=(RadioButton)findViewById(R.id.lab1);
	rb2=(RadioButton)findViewById(R.id.lab2);
	rb3=(RadioButton)findViewById(R.id.lab3);
	b1=(Button)findViewById(R.id.addpic);
	b1.setOnClickListener(this);
	
}

	public void onClick(View thisView) {
		// TODO Auto-generated method stub
		if ((rb1.isChecked() == true) || (rb2.isChecked() == true) || (rb3.isChecked() == true)){
		Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(cameraIntent, PICTURE_ACTIVITY);
		}
		else{
			AlertDialog msgDialog;
			msgDialog = createAlertDialog("Please Select a Quiz to Add a Question to", null, null);
			msgDialog.show();
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
