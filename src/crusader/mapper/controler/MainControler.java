package crusader.mapper.controler;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

public class MainControler implements Initializable {

	@FXML
	private ListView<Object> topleftlist;

	@FXML
	private ListView<Object> bottomleftlist;

	@FXML
	private Button testbtn;

	public MainControler() {
		// TODO Auto-generated constructor stub
		System.out.println("main controller");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		topleftlist.setItems(FXCollections.observableArrayList(Arrays.asList("a", "b")));
		bottomleftlist.setItems(FXCollections.observableArrayList(Arrays.asList("c", "d")));
		testbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("TEstbutton");
			}
		});
	}

}
