package crusader.mapper.util;

import java.util.function.Function;
import java.util.function.Supplier;

import crusader.mapper.CSMapping;
import crusader.mapper.FileWrapper;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DragDropUtil {

	public static <T extends Event> EventHandler<T> createDragSourceHandler(Supplier<Dragboard> dbSupp, Supplier<String>keySupp ) {
		return new EventHandler<T>() {
			@Override
			public void handle(T event) {
		        /* drag was detected, start a drag-and-drop gesture*/
		        /* allow any transfer mode */
		        //Dragboard db = cView.startDragAndDrop(TransferMode.ANY);
		        Dragboard db = dbSupp.get();
		        /* Put a string on a dragboard */
		        ClipboardContent content = new ClipboardContent();
//		        content.putString("c:" + cView.getSelectionModel().getSelectedIndex());
		        content.putString(keySupp.get());
		        db.setContent(content);
		        
		        event.consume();				
			}
		};
	}
	
	public static EventHandler<DragEvent> createOnDragOverEvent(Supplier<String> dataSup) {
		return new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.getString() != null) {
                	if(!(getKey(db.getString()).equals(getKey(dataSup.get())))) {
                        event.acceptTransferModes(TransferMode.COPY);
                	}
                } else {
                    event.consume();
                }
			}
		};
	}
	
	public static EventHandler<DragEvent> createOnDragEvent(ObservableList<FileWrapper> cList, ObservableList<FileWrapper> sList, ListView<CSMapping> center, ObservableList<CSMapping> mList, Label currentSoundLabel) {
		return new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                String drop = db.getString();
                if(drop != null) {
                	String key = getKey(drop);
                	int value = Integer.parseInt(drop.split(":")[1]);
                	
                	FileWrapper file = key.equals("c") ? cList.get(value) : sList.get(value);
                	
                	if(center.getUserData() == null) {
                		currentSoundLabel.setText("currentSound=" + file.toString());
                		center.setUserData(drop);
                	} else {
                		String userData = (String) center.getUserData();
                    	String key2 = getKey(userData);
                    	int value2 = Integer.parseInt(userData.split(":")[1]);
                		FileWrapper file2 = key.equals("c") ? cList.get(value2) : sList.get(value2);
                		
                		if(!key.equals(key2)) {
                			if(key.equals("s")) {
	                			mList.add(new CSMapping(file , file2));
	                		} else {
	                			mList.add(new CSMapping(file2 , file));
	                		}
	                		center.setUserData(null);
                		}
                	}
                }
                event.setDropCompleted(drop != null);
                event.consume();				
			}
		};
	}
	
	
	private static String getKey(String s) {
		return s.contains(":") ? s.split(":")[0] : "";
	}

}
