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
import java.util.Set;
import java.util.Vector;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GermanGenderQuiz extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	private TextView wordTextView;

	private TextView outputTextView;

	private TextView totalResultTextView;

	private TextView consecutiveResultTextView;

	private Gender currentGender;

	private String currentWord;
	
	private Map<String,Gender> words;
	
	private Integer consecutive = 0;
	
	private Integer correctAttempts = 0;
	
	private Integer totalAttempts = 0;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		wordTextView = (TextView) findViewById(R.id.word);
		totalResultTextView = (TextView) findViewById(R.id.totalResult);
		consecutiveResultTextView = (TextView) findViewById(R.id.consecutiveResult);
		outputTextView = (TextView) findViewById(R.id.output);
		outputTextView.setVisibility(0);
		outputTextView.setText("");
		try {
			loadMap();
		} catch (IOException e) {
			loadMap2();
		}
		initTest();
		
		updateTotalResult();
		updateConsecutiveResult();
		
       ((Button)findViewById(R.id.der)).setOnClickListener(this);
       ((Button)findViewById(R.id.das)).setOnClickListener(this);
       ((Button)findViewById(R.id.die)).setOnClickListener(this);

		
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.der: {
			handleResponse(Gender.MASCULINE);
			break;
		}
		case R.id.das: {
			handleResponse(Gender.NEUTRAL);
			break;
		}
		case R.id.die: {
			handleResponse(Gender.FEMININE);
			break;
		}			
		}
	}

	public boolean handleResponse(Gender gender) {
		boolean correct = gender.equals(currentGender);
		
		totalAttempts++;
		
		if (correct)
		{			
			outputTextView.setVisibility(0);
			outputTextView.setText("");

			//showTextToClipboardNotification("OK.");
			
			correctAttempts++;
			consecutive++;
			updateTotalResult();
			updateConsecutiveResult();
			
			
			
			initTest();
		}
		else
		{
			outputTextView.setVisibility(1);
			outputTextView.setText("Wrong, try again.");
			
			consecutive=0;
			//showTextToClipboardNotification("Wrong.");

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
	public void initTest()
	{
		
		Set<String> keySet = words.keySet();
		List<String> keyList= new Vector<String>();
		keyList.addAll(keySet);
		Collections.shuffle(keyList);
		this.currentWord = keyList.get(0);	
		this.currentGender=words.get(currentWord);
		this.wordTextView.setText(currentWord);		
	}
	
	public void updateTotalResult()
	{
		totalResultTextView.setText("total: "+correctAttempts.toString()+"/"+totalAttempts.toString());
	}
	
	public void updateConsecutiveResult()
	{
		consecutiveResultTextView.setText("consecutive: "+consecutive.toString());
	}
	
	private void loadMap() throws IOException
	{
		words = new HashMap<String, GermanGenderQuiz.Gender>();

		
		InputStream in = this.getClass().getResourceAsStream("words_v0-1.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		 String strLine;
		while ((strLine = br.readLine()) != null)   {
			String[] split = strLine.split("\t", 2);
			if (split[0].equalsIgnoreCase("der"))
			{
				words.put(split[1], Gender.MASCULINE);
			}
			else if (split[0].equalsIgnoreCase("das"))
			{
				words.put(split[1], Gender.NEUTRAL);
			}
			else if (split[0].equalsIgnoreCase("die"))
			{
				words.put(split[1], Gender.FEMININE);
			}
		}		
	}
	
	private void loadMap2()
	{

			words = new HashMap<String, GermanGenderQuiz.Gender>();
			words.put("Fall", Gender.MASCULINE);
			words.put("Hund", Gender.MASCULINE);
			words.put("Tisch", Gender.MASCULINE);
			words.put("Gebäude", Gender.NEUTRAL);
			words.put("Geld", Gender.NEUTRAL);
			words.put("Ding", Gender.NEUTRAL);
			words.put("Versicherung", Gender.FEMININE);
			words.put("Stadt", Gender.FEMININE);
			words.put("Macht", Gender.FEMININE);
	}
	
	

}