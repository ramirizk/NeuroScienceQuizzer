package health_science.Neuroscience_Quizzer_XT;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {
	
	int id =0;
	public static final String KEY_ROWID = "_id";
	public static final String QUESTION_TEXT = "Question";
	public static final String ANSWER_TEXT = "Answer";
	public static final String PICTURE_LOCATION = "Picture";
			
	
	
	private static String DB_PATH = "/data/data/health_science.Neuroscience_Quizzer_XT/databases/";
	
	private static String DB_NAME = "NeuroDatabase";
	
	private SQLiteDatabase myDataBase;
	
	private final Context myContext;
	
	public DataBaseHelper(Context context) {
		
		super(context, DB_NAME, null, 1);
		this.myContext = context;
	}
	
	public void createDataBase() throws IOException{
		boolean dbExist = checkDataBase();
		SQLiteDatabase db_Read = null;
		if(dbExist){
			
		}else{
			
			db_Read = this.getReadableDatabase();
			db_Read.close();
			try {
					
					copyDataBase();
			} catch (IOException e) {
				
				throw new Error("Error Copying Database");
			}
		}
	}
	private boolean checkDataBase(){
		
		SQLiteDatabase checkDB = null;
		
		try{
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			
		}catch(SQLiteException e){
			
		}
		
		if(checkDB != null){
			checkDB.close();
		}
		return checkDB != null;
	}
	
	private void copyDataBase() throws IOException{
		try
		{
			File fileTest = myContext.getFileStreamPath(DB_NAME);
			boolean exists = fileTest.exists();
			String outFileName = DB_PATH + DB_NAME;
			if (!exists){
				OutputStream myOutput = new FileOutputStream(outFileName);
				InputStream myInput = myContext.getAssets().open("NeuroDatabase.sqlite");
						
				//InputStream myInput = myContext.getAssets().open(DB_NAME);
				
				byte[] buffer = new byte[1024];
				@SuppressWarnings("unused")
				int length;
				while((length = myInput.read(buffer)) > 0 ) {
					myOutput.write(buffer);
				}
				myInput.close();

			while ((length = myInput.read(buffer)) > 0)
			{
				myOutput.write(buffer);
			}
			
			myInput.close();
			myOutput.flush();
			myOutput.close();
			}
			
		}
			catch (IOException e)
			{
				System.out.println(e.toString());
			}
		}
		
	
	
	public void openDataBase() throws SQLException{
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	@Override
		public synchronized void close() {
		if(myDataBase != null)
			myDataBase.close();
		
		super.close();
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		
	}
	
	public ContentValues getAllValues(String table) {
		
		ContentValues cv=new ContentValues();
		cv.getAsString(table.toString());
		return cv;
	}
	
	public Cursor fetchQuestion(String rowId, String DATABASE_TABLE) throws SQLException {
		Cursor mCursor;
		
		if (("Answers_p1".equals(DATABASE_TABLE)) || ("Answers_p2".equals(DATABASE_TABLE)) || ("Answers_p3".equals(DATABASE_TABLE))) {
			String state= "SELECT * FROM " + DATABASE_TABLE + " WHERE _id = " +"'" +  rowId + "'";
			 mCursor = myDataBase.rawQuery(state, null);
		}else{
			String state= "SELECT Question, Picture FROM " + DATABASE_TABLE + " WHERE _id = " +"'" +  rowId + "'";
			 mCursor = myDataBase.rawQuery(state, null);	
		}
		
		
		
		
		if (mCursor != null) {
			mCursor.moveToFirst();
		}
		
		return mCursor;
	}
	
	/*
	public int getAllEntries() {
		Cursor cursor = myDataBase.rawQuery("SELECT COUNT(Quote) FROM tblRandomQuotes", null);
		if(cursor.moveToFirst()) {
			return cursor.getInt(0);
		}
		return cursor.getInt(0);
	} */
	/*
	public String getRandomEntry() {
		id = getAllEntries();
		Random random = new Random ();
		int rand = random.nextInt(getAllEntries());
		if(rand == 0)
			++rand;
		Cursor cursor = myDataBase.rawQuery("SELECT Quote FROM tblRandomQuotes WHERE _id = " + rand, null);
		if(cursor.moveToFirst()) {
			return cursor.getString(0);
		}
		return cursor.getString(0);
	} */
}
