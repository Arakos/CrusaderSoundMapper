package crusader.mapper.ui.fragments;

import crusader.mapper.controler.SoundEntryController;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class SoundEntryFragment extends BaseFragment<VBox> {

	public SoundEntryFragment(Parent root, SoundEntryController controller) {
		super(root, controller);
	}

	@Override
	protected String getResourceFXML() {
		return "/fxml/SoundEntry.fxml";
	}

}
