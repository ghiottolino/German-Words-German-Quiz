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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

// TODO : add many dictionaries (basic, advances, animals)

public class GermanGenderQuiz extends Activity implements OnClickListener {
	/** Called when the activity is first created. */
	
	public static final String PREFS_NAME = "GERMAN_GENDER_QUIZ_RECORD";
	
	public static final String RECORD_PREF_KEY = "RECORD";
	
	public static final String CONSECUTIVE_PREF_KEY = "CONSECUTIVE";
	
	public static final String TOTAL_PREF_KEY = "TOTAL";
	
	public static final String CORRECT_PREF_KEY = "CORRECT";
	
	
	
	public static final String DICTIONARIES_PREF_KEY = "DICTIONARIES";
	
	private TextView wordTextView;

	private TextView outputTextView;

	private TextView totalResultTextView;

	private TextView consecutiveResultTextView;

	private TextView recordTextView;

	private Gender currentGender;

	private String currentWord;

	private Map<String, Gender> words;

	private List<String> recentWrongAnsweredWords;

	private static final Integer RECENT_WRONG_ANSWERED_WORDS_SIZE = 10;

	private Integer consecutive = 0;

	private Integer correctAttempts = 0;

	private Integer totalAttempts = 0;

	private Random rg = new Random();
	
	private Integer currentRecord = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		// add the possibilities to use sharedsettings to dictionary service
		DictionaryService.getInstance().addSharedSettings( getSharedPreferences(PREFS_NAME, 0));
		
		wordTextView = (TextView) findViewById(R.id.word);
		totalResultTextView = (TextView) findViewById(R.id.totalResult);
		consecutiveResultTextView = (TextView) findViewById(R.id.consecutiveResult);
		recordTextView = (TextView) findViewById(R.id.record);
		outputTextView = (TextView) findViewById(R.id.output);
		outputTextView.setVisibility(0);
		outputTextView.setText("");
		
		consecutive =  getFieldInPreferences(CONSECUTIVE_PREF_KEY);
		correctAttempts =  getFieldInPreferences(CORRECT_PREF_KEY);
		totalAttempts =  getFieldInPreferences(TOTAL_PREF_KEY);
		currentRecord = getFieldInPreferences(RECORD_PREF_KEY);
		
		printCurrentRecord();


		updateTotalResult();
		updateConsecutiveResult();

		((Button) findViewById(R.id.der)).setOnClickListener(this);
		((Button) findViewById(R.id.das)).setOnClickListener(this);
		((Button) findViewById(R.id.die)).setOnClickListener(this);
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		try {
			loadDictionaries();
		} catch (IOException e) {
			throw new RuntimeException("Could not load dictionary");
		}
		recentWrongAnsweredWords = new Vector<String>();
		initTest();

	}
	
	

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.der: {
			handleResponse(Gender.MASCULINE, view);
			break;
		}
		case R.id.das: {
			handleResponse(Gender.NEUTRAL, view);
			break;
		}
		case R.id.die: {
			handleResponse(Gender.FEMININE, view);
			break;
		}
		}
	}

	public boolean handleResponse(Gender gender, View view) {
		boolean correct = gender.equals(currentGender);

		totalAttempts++;

		if (correct) {
			outputTextView.setVisibility(0);
			outputTextView.setText("");
			// showTextToClipboardNotification("OK.");
			correctAttempts++;
			consecutive++;
			updateTotalResult();
			updateConsecutiveResult();
			updateRecord();
			initTest();
		} else {
			outputTextView.setVisibility(1);
			outputTextView.setText("Wrong, try again.");
			consecutive = 0;
			// showTextToClipboardNotification("Wrong.");
			view.setEnabled(false);
			if (!recentWrongAnsweredWords.contains(currentWord))
			{
				recentWrongAnsweredWords.add(currentWord);
			}
		}

		updateTotalResult();
		updateConsecutiveResult();

		return correct;
	}

	protected void showTextToClipboardNotification(String text) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();

	}

	public enum Gender {
		MASCULINE, FEMININE, NEUTRAL
	}

	//
	public void initTest() {

		String word = "";

		// if there are more than 10 words in the recentWrongAnsweredWords list
		// then retrieves one of that words
		if (shouldChooseFromWrongAnswers()) {
			word = getWordFromWrongAnswers();
		} else {
			word = getWordFromDictionary();
		}

		this.currentWord = word;
		this.currentGender = words.get(currentWord);
		this.wordTextView.setText(currentWord);

		// reset all buttons
		((Button) findViewById(R.id.der)).setEnabled(true);
		((Button) findViewById(R.id.das)).setEnabled(true);
		((Button) findViewById(R.id.die)).setEnabled(true);
	}

	public String getWordFromWrongAnswers() {
		Collections.shuffle(recentWrongAnsweredWords);
		String word = recentWrongAnsweredWords.get(0);
		recentWrongAnsweredWords.remove(word);
		return word;
	}

	public String getWordFromDictionary() {
		
		if (words.size()==0)
		{
			return "";
		}
		
		Set<String> keySet = words.keySet();
		List<String> keyList = new Vector<String>();
		keyList.addAll(keySet);
		Collections.shuffle(keyList);
		return keyList.get(0);
	}

	public boolean shouldChooseFromWrongAnswers() {
		if  ((recentWrongAnsweredWords==null)||(recentWrongAnsweredWords.size()==0))
		{
			return false;
		}
		
		if (recentWrongAnsweredWords.size() > RECENT_WRONG_ANSWERED_WORDS_SIZE) {
			return true;
		}

		int random = rg.nextInt(RECENT_WRONG_ANSWERED_WORDS_SIZE);

		if (random < recentWrongAnsweredWords.size()) {
			return true;
		} else {
			return false;
		}
	}

	public void updateTotalResult() {
		totalResultTextView.setText("total: " + correctAttempts.toString()
				+ "/" + totalAttempts.toString());

		saveFieldInPreferences(CORRECT_PREF_KEY, correctAttempts);
		saveFieldInPreferences(TOTAL_PREF_KEY, totalAttempts);

	}

	public void updateConsecutiveResult() {
		consecutiveResultTextView.setText("consecutive: "
				+ consecutive.toString());
		
		saveFieldInPreferences(CONSECUTIVE_PREF_KEY, consecutive);
		
	}
	
	private void loadDictionaries()  throws IOException
	{
		
		words = new HashMap<String, GermanGenderQuiz.Gender>();
		
		for (Dictionary dictionary : Dictionary.values())
		{
			String shortName = dictionary.getShortName();
			if (DictionaryService.getInstance().getChecked(shortName))
			{
				loadMap(dictionary.getFileName());					
			}
		}
	}
	

	private void loadMap(String fileName) throws IOException {
		

		InputStream in = this.getClass().getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		while ((strLine = br.readLine()) != null) {
			String[] split = strLine.split("\t", 2);
			if (split[0].equalsIgnoreCase("der")) {
				words.put(split[1], Gender.MASCULINE);
			} else if (split[0].equalsIgnoreCase("das")) {
				words.put(split[1], Gender.NEUTRAL);
			} else if (split[0].equalsIgnoreCase("die")) {
				words.put(split[1], Gender.FEMININE);
			}
		}
	}
	
	// RECORD
	
	protected void updateRecord()
	{
		if (consecutive>currentRecord)
		{
			this.currentRecord=consecutive;
			saveFieldInPreferences(RECORD_PREF_KEY,currentRecord);
			showTextToClipboardNotification("Congratulations, you set a new record: "+currentRecord);
			printCurrentRecord();
		}
	}
	

	public void printCurrentRecord() {
		recordTextView.setText("record: "
				+ currentRecord.toString());
	}
	
	protected Integer getFieldInPreferences(String fieldName) {

    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    	int record = settings.getInt(fieldName, 0);
    	return record;
	}
	
	protected void saveFieldInPreferences(String fieldName, Integer n) {

    	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(fieldName, n);

		// Commit the edits!
		boolean commit = editor.commit();
	}
	
	
	// MENU
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		 case R.id.settings:
			 // do something
			 Intent myIntent = new Intent(this, SettingsActivity.class);
             startActivityForResult(myIntent, 0);
		 return true;


		default:
			return super.onOptionsItemSelected(item);
		}
	}


}