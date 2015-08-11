package tfg.gui.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class TweetTableCell extends TableCell<PopularesModel,TweetModel> {
	
	private Label textArea;
	private String getIdTweet;
	private HBox hbox;
	
	public TweetTableCell() {
		this.hbox=new HBox();
		hbox.setAlignment(Pos.CENTER);
		this.textArea=new Label();
		this.hbox.getChildren().addAll(textArea);
		setGraphic(hbox);
		
	}
	@Override
	public void updateItem(TweetModel item, boolean empty) {
		if(item!=null) {
		this.textArea.setText(item.getTextTweet());
	//	this.textArea.setEditable(false);
		this.getIdTweet=item.getIdTweet();
		}
	}
	public String getIdTweet() {
		return this.getIdTweet;
	}

}
