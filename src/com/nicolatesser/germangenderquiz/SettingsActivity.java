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

import com.nicolatesser.germangenderquiz.GermanGenderQuiz.Gender;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Thanks to http://www.anddev.org/iconified_textlist_-_the_making_of-t97.html
 */
public class SettingsActivity extends Activity{
	/** Called when the activity is first created. */

	public static final String PREFS_NAME = "GERMAN_GENDER_QUIZ_RECORD";

	public static final String RECORD_PREF_KEY = "RECORD";

	private TextView mTextView;
	private ListView mListView;
	// Create CheckBox List Adapter, cbla
	private CheckBoxifiedTextListAdapter adapter;

	private CheckBox allDictionariesCheckBox = null;
	private List<CheckBox> checkBoxes = new Vector<CheckBox>();

	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		

		mTextView = (TextView) findViewById(R.id.text);
		mListView = (ListView) findViewById(R.id.list);
		// mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		List<String> dictionaries = getDictionaries();
		// showData(dictionaries);
		// prepareCheckboxListeners(dictionaries);
		// prepareCheckboxListeners2(dictionaries);
		System.out.println("test");

		adapter = new CheckBoxifiedTextListAdapter(this);
		for (int k = 0; k < dictionaries.size(); k++) {
			adapter.addItem(new CheckBoxifiedText(dictionaries.get(k), false));
		}
		// Display it
		mListView.setAdapter(adapter);

	}

	protected List<String> getDictionaries() {
		List<String> dictionaries = new Vector<String>();
		dictionaries.add(" All Dictionaries");
		dictionaries.add(" Basic");
		dictionaries.add(" Medium");
		dictionaries.add(" Advanced");
		dictionaries.add(" Animals");
		dictionaries.add(" Nature");
		dictionaries.add(" Home");
		return dictionaries;
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
			// startActivityForResult(myIntent, 0);
			startActivityIfNeeded(myIntent, 0);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}