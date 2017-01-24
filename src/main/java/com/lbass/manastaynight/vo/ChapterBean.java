package com.lbass.manastaynight.vo;

import java.net.URI;

public class ChapterBean {

	private URI chaterURI;
	private String chapterName;
	private boolean isError = false;
	private String errorMessage;
	
	public ChapterBean(URI uri, String comicChaterName) {
		this.chaterURI = uri;
		this.chapterName = comicChaterName;
	}
	
	public ChapterBean(String comicChaterName, boolean isError, String errorMessage) {
		this.chapterName = comicChaterName;
		this.isError = isError;
		this.errorMessage = errorMessage;
		
	}
	
	public URI getChaterURI() {
		return chaterURI;
	}
	
	public void setChaterURI(URI chaterURI) {
		this.chaterURI = chaterURI;
	}
	
	public String getChapterName() {
		return chapterName;
	}
	
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}
	
	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ChapterBean [chaterURI=" + chaterURI + ", chapterName=" + chapterName + ", isError=" + isError
				+ ", errorMessage=" + errorMessage + "]";
	}
	
}
