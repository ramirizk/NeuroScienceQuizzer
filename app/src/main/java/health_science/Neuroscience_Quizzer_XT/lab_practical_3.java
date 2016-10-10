package health_science.Neuroscience_Quizzer_XT;

import health_science.Neuroscience_Quizzer_XT.DataBaseHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.*;

public class lab_practical_3 extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lab_practical_3);
		final int  a =0;
		final int[] questionnumber = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89};
		shuffleArray(questionnumber);
		QuesAndPic(questionnumber,a);
		ans(questionnumber, a);
		}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	public void QuesAndPic(int[] questionnumber, int a){
		View b = findViewById(R.id.nextp1);
		b.setVisibility(View.INVISIBLE);
		DataBaseHelper myDbHelper = new DataBaseHelper(null);
		myDbHelper = new DataBaseHelper(this);
		myDbHelper.close();
		myDbHelper.openDataBase();
		String question = "p3_"+questionnumber[a];
		String DataBaseTable = "questionsandpics_p3";
		Cursor question_text;	
		question_text = myDbHelper.fetchQuestion(question, DataBaseTable);
		myDbHelper.close();
		TextView actual_question = (TextView)findViewById(R.id.p3_ques);
		String Result=question_text.getString(question_text.getColumnIndex("Question"));
		actual_question.setText(Result);
		String picname =question_text.getString(question_text.getColumnIndex("Picture"));
		System.out.println(picname);
		System.out.println(question);
		Resources res = getResources();
		int resID = res.getIdentifier(picname, "drawable", getPackageName());
		Drawable drawable = res.getDrawable(resID);
		ImageView actual_picture = (ImageView)findViewById(R.id.p3_pic);
		actual_picture.setImageDrawable(drawable);
		
	}
	
	public void ans(final int[] questionnumber, final int a){
		DataBaseHelper myDbHelper = new DataBaseHelper(null);
		myDbHelper = new DataBaseHelper(this);
		myDbHelper.close();
		myDbHelper.openDataBase();
		Cursor answer;
		String DataBaseTable = "Answers_p3";
		System.out.println(a);
		String question = "p3_"+questionnumber[a];
		answer = myDbHelper.fetchQuestion(question, DataBaseTable);
		System.out.println(answer.getCount());
		final ArrayList<String> answer_text = new ArrayList<String>();
		if (answer.moveToFirst())
		{	
			int i=0;
			
			do {
				String answ1er = answer.getString(answer.getColumnIndex("Answer"));
				answer_text.add(i,answ1er);
				i++;
			} while (answer.moveToNext ());
		}
		
		System.out.println(answer_text);
		myDbHelper.close();
		
		Button checkbutton = (Button)findViewById(R.id.check);
	    checkbutton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				checkans(answer_text, questionnumber, a);
			}
		});
	}

	public static void shuffleArray(int[] a) {
		int n = a.length;
		Random random = new Random();
		random.nextInt();
		for (int i = 0; i < n; i++){
			int change = i +random.nextInt(n-i);
			swap(a, i, change);
		}
	}

	private static void swap(int[] a, int i, int change){
		int helper = a[i];
		a[i] = a[change];
		a[change] = helper;
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
	public void checkans(final ArrayList<String> answer_text, final int[] questionnumber, final int a) {
		
		final EditText ed = (EditText)findViewById(R.id.p3_ans);
		String their_ans = ed.getText().toString().toLowerCase();
		if(answer_text.contains(their_ans)){
		System.out.println("it Worked");
		AlertDialog msgDialog;
		msgDialog = createAlertDialog("Correct!", null, "Okay");
		msgDialog.show();
		
		View b = findViewById(R.id.nextp1);
		b.setVisibility(View.VISIBLE);
		Button nextbutton = (Button)findViewById(R.id.nextp1);
	    nextbutton.setOnClickListener(new View.OnClickListener() {
	    	public void onClick(View v){
	    		ed.setText("");
	    		 nextpush(a, questionnumber, answer_text);
	    	}
	    });
		}
		else {
			
			if (their_ans.length()==0){
				String nextletter = "The First Letter should be " + answer_text.get(0).charAt(0);
				AlertDialog msgDialog;
				msgDialog = createAlertDialog("At Least Make an Attempt", nextletter, "Okay");
				msgDialog.show();
				
			}
			else if((their_ans.length() > 0) && (their_ans.length() < answer_text.get(0).length())){
				int charpointer=their_ans.length();
				String nextletter = "The Next Letter Should Be " + answer_text.get(0).charAt(charpointer);
				AlertDialog msgDialog;
				msgDialog = createAlertDialog("Try Again", nextletter, "Okay");
				msgDialog.show();
			}
			else {
				AlertDialog msgDialog;
				msgDialog = createAlertDialog("Try Again", null, "Okay");
				msgDialog.show();
			}
				
		}
	}
	
	public void nextpush(int a, int[] questionnumber, ArrayList<String> answer_text){
		EditText ans = new EditText(this);
		ans.setText("");
		a=a+1;
		if (a != questionnumber.length){
			View b = findViewById(R.id.nextp1);
			b.setVisibility(View.INVISIBLE);
			QuesAndPic(questionnumber,a);
			ans(questionnumber, a);	
		}else{
			AlertDialog msgDialog = null;
			
			
			AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
			msgDialog = dialogBuilder.create();
			msgDialog.setTitle("COMPLETE!");
			msgDialog.setMessage("You Have Finished the Practice for Lab Practical 3");
			msgDialog.setButton("Okay", new DialogInterface.OnClickListener(){
				public void onClick(DialogInterface dialog, int idx){
					finish();
				}
			});
			msgDialog.show();
		}
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	
}
