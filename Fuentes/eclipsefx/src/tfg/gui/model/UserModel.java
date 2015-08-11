package tfg.gui.model;

import twitter4j.User;

public class UserModel {
//http://blog.ngopal.com.np/2011/10/01/tableview-cell-modifiy-in-javafx/	
	private String userName;
	private String screenName;
	private String profileImage;
	
	
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getScreenName() {
		return screenName;
	}


	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}


	public String getProfileImage() {
		return profileImage;
	}


	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}


	public UserModel(User tweet) {
		this.userName = tweet.getName();
		this.screenName = tweet.getScreenName();
		this.profileImage =tweet.getProfileImageURL();
	}


	

}
