package com.nicolatesser.germangenderquiz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	
	public static final String PREFS_NAME = "GERMAN_GENDER_QUIZ_RECORD";
	
	public static final String RECORD_PREF_KEY = "RECORD";
	

    private TextView mTextView;
    private ListView mListView;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		mTextView = (TextView) findViewById(R.id.text);
        mListView = (ListView) findViewById(R.id.list);
        List<String> dictionaries = getDictionaries();
        showData(dictionaries);
	}
	
	protected List<String> getDictionaries() {
		List<String> dictionaries = new Vector<String>();
		dictionaries.add("All");
		dictionaries.add("Basic");
		dictionaries.add("Medium");
		dictionaries.add("Advanced");

		dictionaries.add("Animals");
		dictionaries.add("Nature");
		dictionaries.add("Home");

		return dictionaries;
	}

	protected void showData(final List<String> data)
    {
    	int size = data.size();
    	
    	if (size==0)
    	{
    		mTextView.setText("No records are present. Click on the menu button and then click on add to add some records.");
    	}
    	else
    	{
    		mTextView.setText("Displaying '"+size+" 'records.");
    		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.settings_entry,R.id.value, data);
    		mListView.setAdapter(adapter);

    	}
    }

	

	public void onClick(View view) {
	
	}



	protected void showTextToClipboardNotification(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

	}

	

	
	

	
	
		
	
	// MENU
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.play_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		 case R.id.play:
			 // do something
			 Intent myIntent = new Intent(this, GermanGenderQuiz.class);
			 // TODO : how to return withouth starting the activity?
             //startActivityForResult(myIntent, 0);
             startActivityIfNeeded(myIntent, 0);
		 return true;


		default:
			return super.onOptionsItemSelected(item);
		}
	}


}