package tfg.gui.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

//https://blogs.oracle.com/javajungle/entry/how_to_pretty_up_your
public class UserTableCell extends TableCell<PopularesModel,UserModel> {
	private VBox vb;
	private Label userName;
	private Label screenName;
	private ImageView profileImage;
	
	public UserTableCell() {
		this.vb = new VBox();
		vb.setAlignment(Pos.CENTER);
		this.userName=new Label();
		this.screenName = new Label();
		this.profileImage = new ImageView();
		this.profileImage.setFitHeight(50);
		this.profileImage.setFitWidth(50);
		this.vb.getChildren().addAll(this.profileImage,this.screenName,this.userName);
		setGraphic(vb);
	}
	
	public void updateItem(UserModel item, boolean empty) {
		if(item!=null) {
		this.userName.setText(item.getUserName());
		this.screenName.setText("@"+item.getScreenName());
		this.profileImage.setImage(new Image(item.getProfileImage()));
		}
	}
	public String getUsuario() {
		return this.screenName.getText();
	}
	
	

}
