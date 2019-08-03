package crusader.mapper.fragments;

import java.io.File;
import java.util.function.Function;

import crusader.mapper.util.DragDropUtil;
import crusader.mapper.util.SoundUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.input.TransferMode;

public class MapperListView<T> extends ListView<T> {

	private static int KEY_COUNT = 0;

	private final int VIEW_KEY;

	public MapperListView(ObservableList<T> sourceList, Function<T, File> fileProvider) {

		super(sourceList);
		this.VIEW_KEY = KEY_COUNT++;
		this.setOnMouseClicked(SoundUtil
				.createSoundEventHandler(() -> fileProvider.apply(this.getSelectionModel().getSelectedItem())));

		this.setOnDragDetected(DragDropUtil.createDragSourceHandler(() -> this.startDragAndDrop(TransferMode.ANY),
				() -> VIEW_KEY + ":" + this.getSelectionModel().getSelectedIndex()));
	}

	public int getViewKey() {
		return VIEW_KEY;
	}

}
