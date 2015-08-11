package tfg.gui.model;

import twitter4j.Status;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PopularesModel {
	
	private SimpleObjectProperty<TweetModel> tweetText;
	private SimpleObjectProperty<UserModel> tweetUser;
	private SimpleStringProperty rtCount;
	private SimpleStringProperty favCount;
	private SimpleStringProperty followersCount;
	private String idTweet;
	
	
	public PopularesModel(Status tweet){
		
		this.tweetText = new SimpleObjectProperty<TweetModel>(new TweetModel(tweet));
		this.idTweet = Long.toString(tweet.getId());
		this.tweetUser = new SimpleObjectProperty<UserModel>(new UserModel(tweet.getUser()));
		this.rtCount = new SimpleStringProperty(Integer.toString(tweet.getRetweetCount()));
		this.favCount = new SimpleStringProperty(Integer.toString(tweet.getFavoriteCount()));
		this.followersCount = new SimpleStringProperty(Integer.toString(tweet.getUser().getFollowersCount()));
	
		
	}
	public String getIdTweet() {
		return this.idTweet;
	}
	
	public Object getText(){
    	return this.tweetText.get();
    }
    
    public void setText(TweetModel tweetText){
    	this.tweetTextProperty().set(tweetText);
    }
    
    public ObjectProperty<TweetModel> tweetTextProperty(){
    	return tweetText;
    }


	public  ObjectProperty<UserModel> tweetUserProperty() {
		return this.tweetUser;
	}

	public Object getTweetUser() {
		return this.tweetUserProperty().get();
	}

	public final void setTweetUser(UserModel tweetUser) {
		this.tweetUserProperty().set(tweetUser);
	}

	public final SimpleStringProperty rtCountProperty() {
		return this.rtCount;
	}

	public final String getRtCount() {
		return this.rtCountProperty().get();
	}

	public final void setRtCount(String rtCount) {
		this.rtCountProperty().set(rtCount);
	}

	public final SimpleStringProperty favCountProperty() {
		return this.favCount;
	}

	public final String getFavCount() {
		return this.favCountProperty().get();
	}

	public final void setFavCount(String favCount) {
		this.favCountProperty().set(favCount);
	}

	public final SimpleStringProperty followersCountProperty() {
		return this.followersCount;
	}

	public final String getFollowersCount() {
		return this.followersCountProperty().get();
	}

	public final void setFollowersCount(String followersCount) {
		this.followersCountProperty().set(followersCount);
	}

	

	
    

}
