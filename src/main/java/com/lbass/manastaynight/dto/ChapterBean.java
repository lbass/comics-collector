package com.lbass.manastaynight.dto;

import java.net.URI;
import java.util.Arrays;

public class ChapterBean {

	private String chaterURI;
	private String title;
	private String chapterName;
	private String[] images;
	private int session_seq;
	private int order;

	public ChapterBean(String chapterURI, String chapterName, String title, String[] images) {
		this.chaterURI = chapterURI;
		this.chapterName = chapterName;
		this.title = title;
		this.images = images;
	}

	public String getChaterURI() {
		return chaterURI;
	}

	public void setChaterURI(String chaterURI) {
		this.chaterURI = chaterURI;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getSession_seq() {
		return session_seq;
	}

	public void setSession_seq(int session_seq) {
		this.session_seq = session_seq;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "ChapterBean [chaterURI=" + chaterURI + ", title=" + title + ", chapterName=" + chapterName + ", images="
				+ Arrays.toString(images) + ", session_seq=" + session_seq + ", order=" + order + "]";
	}

}
