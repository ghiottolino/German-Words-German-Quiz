package com.nicolatesser.germangenderquiz;

public enum Dictionary {

	BASIC ("Basic","words_v0-1.txt",true),
	
	ADVANCED("advanced","",false);
	
	private String name;
	private String fileName;
	private boolean isDefault;
	
	
	private Dictionary(String name, String fileName, boolean isDefault)
	{
		this.name=name;
		this.fileName = fileName;
		this.isDefault=isDefault;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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
	
	
}
