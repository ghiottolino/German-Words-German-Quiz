package com.nicolatesser.germangenderquiz;

import java.util.List;

public enum Dictionary {

	BASIC ("Basic","Basic (circa 450 words)","words_v0-1.txt",true),
	ADVANCED("Advanced"," Advanced (3000 words)","words_v1.txt",false);
	
	private String shortName;
	private String displayName;
	
	private String fileName;
	private boolean isDefault;
	
	
	private Dictionary(String shortName, String displayName, String fileName, boolean isDefault)
	{
		this.shortName=shortName;
		this.fileName = fileName;
		this.displayName= displayName;
		this.isDefault=isDefault;
	}

	public String getShortName() {
		return shortName;
	}


	public void setShortName(String shortName) {
		this.shortName = shortName;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public boolean isDefault() {
		return isDefault;
	}


	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
	public static Dictionary getByShortName(String shortName)
	{
		for (Dictionary dictionary : Dictionary.values())
		{
			if (dictionary.getShortName().equals(shortName))
			{
				return dictionary;
			}
		}
		return null;
	}
	
	public static String serialize (List<Dictionary> dictionaries)
	{
		if (dictionaries.size()==0)
		{
			return "";
		}
		String serialize="";
		for (Dictionary dictionary : dictionaries)
		{
			serialize = serialize+dictionary.getShortName()+",";
		}
		return serialize;
	}
	
	
	
}
