package crusader.mapper.controler;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import crusader.mapper.data.DAO;
import crusader.mapper.data.model.CrusaderSound;
import crusader.mapper.data.model.FileWrapper;
import crusader.mapper.data.model.SoundMapping;
import crusader.mapper.ui.fragments.BaseDirectoryChooserFragment;
import crusader.mapper.ui.fragments.SoundMappingFragment;
import crusader.mapper.util.SoundUtil;
import crusader.mapper.util.UI_Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

public class MainControler implements Initializable {

	@FXML
	private ListView<CrusaderSound> crusaderSoundContainer;

	@FXML
	private TreeView<FileWrapper> workDir;

	@FXML
	private Button testbtn;

	@FXML
	private VBox contentContainer;

	@FXML
	private TextField filterText;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		DAO dao = DAO.getInstance();
		ObservableList<CrusaderSound> soundItems = FXCollections.observableArrayList(dao.getTaggedCrusaderSoundFiles());
		FilteredList<CrusaderSound> filteredList = new FilteredList<>(soundItems);
		filterText.textProperty().addListener(e -> {
			if (filterText.getText().length() > 2) {
				filteredList.setPredicate(
						soundItem -> soundItem.toString().toLowerCase().contains(filterText.getText().toLowerCase()));
			} else {
				filteredList.setPredicate(soundItem -> true);
			}
		});

		crusaderSoundContainer.setItems(filteredList);
		crusaderSoundContainer.setOnMouseClicked(e -> {
			CrusaderSound selectedSound = crusaderSoundContainer.getSelectionModel().getSelectedItem();
			if (e.getButton() == MouseButton.PRIMARY) {
				if (e.getClickCount() == 1) {
					new SoundMappingFragment(contentContainer, new SoundMapping(selectedSound, selectedSound, selectedSound)).createFragment();
				} else if (e.getClickCount() == 2) {
					SoundUtil.playSound(selectedSound.getFile());
				}
			}
		});

		workDir.setRoot(createTree(dao.getWorkingDir()));

		testbtn.setOnAction(event -> {
			UI_Util.openInNewWindow(new BaseDirectoryChooserFragment());
		});

	}


	private TreeItem<FileWrapper> createTree(FileWrapper wrapper) {
		TreeItem<FileWrapper> root = new TreeItem<>(wrapper);
		if (wrapper.getFile().isDirectory()) {
			for(File child : wrapper.getFile().listFiles()) {
				root.getChildren().add(createTree(new FileWrapper(child)));
			}
		}
		root.getChildren().sort((left, right) -> {
			File l = left.getValue().getFile();
			File r = right.getValue().getFile();
			if (l.isDirectory() == r.isDirectory()) {
				return l.getName().compareTo(r.getName());
			}
			return l.isDirectory() ? -1 : 1;
		});
		return root;
	}

}
