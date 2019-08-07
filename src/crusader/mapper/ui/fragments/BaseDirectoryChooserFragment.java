package crusader.mapper.ui.fragments;

import crusader.mapper.controler.BaseDirectoryDialogController;
import javafx.scene.Parent;
import javafx.scene.control.TitledPane;

public class BaseDirectoryChooserFragment extends BaseFragment<TitledPane> {

	public BaseDirectoryChooserFragment() {
		this(new TitledPane(), new BaseDirectoryDialogController());
	}

	public BaseDirectoryChooserFragment(Parent parent, Object controller) {
		super(parent);
	}

	@Override
	protected String getResourceFXML() {
		return "/fxml/BaseDirectoryDialog.fxml";
	}

}
