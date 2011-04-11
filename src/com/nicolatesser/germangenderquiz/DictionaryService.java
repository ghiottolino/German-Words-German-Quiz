package com.nicolatesser.germangenderquiz;

import android.content.SharedPreferences;

public class DictionaryService {
	
	private static DictionaryService instance;
	
	private SharedPreferences settings;
	
    public static final String DICTIONARY_PREF_KEY = "DICT_";
    
	public static DictionaryService getInstance()
	{
		if (instance==null)
		{
			instance=new DictionaryService();
		}
		return instance;
	}
	
	public void addSharedSettings(SharedPreferences settings)
	{
		this.settings=settings;
	}
	
	public void setDictionary(String shortName, boolean checked)
	{
		SharedPreferences.Editor editor = settings.edit();
		String key = DICTIONARY_PREF_KEY + shortName;
		editor.putBoolean( key, checked);
		// Commit the edits!
		boolean commit = editor.commit();
	}
	
	public boolean getChecked(String shortName)
	{
		String key = DICTIONARY_PREF_KEY + shortName;
		boolean checked = settings.getBoolean(key, Dictionary.getByShortName(shortName).isDefault());
		return checked;
	}
	
	

}
