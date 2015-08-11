package tfg.gui.model;

import twitter4j.Status;

public class TweetModel {
	private String textTweet;
	private String idTweet;

	

	public TweetModel(Status tweet) {
		this.textTweet = tweet.getText();
		this.idTweet = Long.toString(tweet.getId());
		// TODO Auto-generated constructor stub
	}
	public String getTextTweet() {
		return textTweet;
	}

	public String getIdTweet() {
		return idTweet;
	}
}
