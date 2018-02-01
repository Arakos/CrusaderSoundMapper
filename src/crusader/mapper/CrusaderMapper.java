package crusader.mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CrusaderMapper extends Application {
	
	private static final String CRUSADER_PATH = "cursaderpath";
	private static final String SOUND_PATH = "soundpath";

	private static final String C_SPEECH_PATH = "fx" + File.separatorChar + "speech";
	
	private static final String EXTENSION = "wav";


	private File cDir = null;
	private File sDir = null;
	
	ObservableList<FileWrapper> cList = null;
	ObservableList<FileWrapper> sList = null;
	
	ObservableList<CSMapping> mList = null;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		prepareDirectories(stage);
		prepareLists();
		createUI(stage);
	}
	
	private void createUI(Stage stage) {
		Label currentSoundLabel = new Label("currentSound=");
		Label cDirLabel = new Label("cdir=" + cDir.getAbsolutePath());
		Label sDirLabel = new Label("sdir=" + sDir.getAbsolutePath());
		HBox bottom = new HBox(5, cDirLabel, sDirLabel, currentSoundLabel);
		
		ListView<FileWrapper> cView = new ListView<FileWrapper>(cList);
		cView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
					if(e.getClickCount() == 2) {
						playSound(cView.getSelectionModel().getSelectedItem().getFile());
					}
				}
			}
		});
		cView.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
		        /* drag was detected, start a drag-and-drop gesture*/
		        /* allow any transfer mode */
		        Dragboard db = cView.startDragAndDrop(TransferMode.ANY);
		        
		        /* Put a string on a dragboard */
		        ClipboardContent content = new ClipboardContent();
		        content.putString("c:" + cView.getSelectionModel().getSelectedIndex());
		        db.setContent(content);
		        
		        event.consume();				
			}
		});
		
		ListView<FileWrapper> sView = new ListView<FileWrapper>(sList);
		sView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
					if(e.getClickCount() == 2) {
						playSound(sView.getSelectionModel().getSelectedItem().getFile());
					}
				}
			}
		});
		sView.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
		        /* drag was detected, start a drag-and-drop gesture*/
		        /* allow any transfer mode */
		        Dragboard db = sView.startDragAndDrop(TransferMode.ANY);
		        
		        /* Put a string on a dragboard */
		        ClipboardContent content = new ClipboardContent();
		        content.putString("s:" + sView.getSelectionModel().getSelectedIndex());
		        db.setContent(content);
		        
		        event.consume();				
			}
		});
		
		ListView<CSMapping> center = new ListView<CSMapping>(mList);
        center.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.getString() != null) {
                	if( center.getUserData() == null || !(getKey(db.getString()).equals(getKey((String) center.getUserData())))) {
                        event.acceptTransferModes(TransferMode.COPY);
                	}
                } else {
                    event.consume();
                }
			}
		});	
        
        center.setOnDragDropped(new EventHandler<DragEvent>() {

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
                		FileWrapper file2 = key2.equals("c") ? cList.get(value2) : sList.get(value2);
                		
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
		});
        
        center.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if(e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
					if(e.getClickCount() == 2) {
						
					}
				}			
			}
		});
        
        Button crusaderSourceDirButton = new Button("set crusader src");
        crusaderSourceDirButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		        final DirectoryChooser dirChooser = new DirectoryChooser();
		        if(cDir.exists() && cDir.isDirectory()) {
		        	dirChooser.setInitialDirectory(cDir);
		        }
                File file = dirChooser.showDialog(stage);
                if(file == null) {
                	return;
                } else if (file.exists() && file.isDirectory()) {
                	cDir = file.getAbsoluteFile();
                	sDirLabel.setText("cdir=" + cDir.getAbsolutePath());
                } else {
                	String msg = "cant set " + file + " as source dir";
                	System.out.println(msg);
                	showAlert(msg, null);
                }
			}
		});
        
        Button soundSourceDirButton = new Button("set fun sound src");
        soundSourceDirButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		        final DirectoryChooser dirChooser = new DirectoryChooser();
		        if(sDir.exists() && sDir.isDirectory()) {
		        	dirChooser.setInitialDirectory(sDir);
		        }
                File file = dirChooser.showDialog(stage);
                if(file == null) {
                	return;
                } else if (file.exists() && file.isDirectory()) {
                	sDir = file.getAbsoluteFile();
                	sDirLabel.setText("sdir=" + sDir.getAbsolutePath());
                } else {
                	String msg = "cant set " + file + " as source dir";
                	System.out.println(msg);
                	showAlert(msg, null);
                }
			}
		});
        
        Button save = new Button("save config");
        save.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		System.out.println();
    			File f = new File(sDir.getAbsolutePath() + "/config_" + System.currentTimeMillis() + ".txt");

    			String content = "";
        		for(CSMapping m : mList) {
        			System.out.println(m.toCVS());
        			content += m.toCVS() + "\n";
        		}
        		try {
        			if(!f.exists()) {
        				f.createNewFile();
        			}
					FileOutputStream out = new FileOutputStream(f);
					out.write(content.getBytes());
					out.flush();
					out.close();
					showAlert("config saved: " + f.getAbsolutePath(), AlertType.INFORMATION, null);
				} catch (FileNotFoundException e) {
					showAlert("file not found while safing config", e);
				} catch (IOException e) {
					showAlert("io problem while safing config", e);
				}
        		
        	}
		});
        
        Button load = new Button("load config");
        load.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
		        final FileChooser fileChooser = new FileChooser();
		        fileChooser.setInitialDirectory(sDir);
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                	mList.clear();
                	BufferedReader in = null;
                    try {
						in = new BufferedReader( new FileReader(file) );
						String line = "";
						while((line = in.readLine()) != null) {
							String[] s = line.split(",");
							if(s.length == 2) {
								mList.add(new CSMapping(new FileWrapper(new File(s[0])), new FileWrapper(new File(s[1]))));
							} else {
								showAlert("cant read line: " + line, null);
								mList.clear();
								return;
							}
						}
					} catch (FileNotFoundException e) {
						showAlert("file not found while loading config", e);
						e.printStackTrace();
					} catch (IOException e) {
						showAlert("io problem while loading config", e);
						e.printStackTrace();
					} finally {
						if(in != null)
							try {
								in.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
                    
                }
			}
		});
        
        Button clear = new Button("clear list");
        clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mList.clear();
			}
		});  
        
        Button doit = new Button("create file copies");
        doit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {


				File mappedSoundsDir = new File(sDir.getAbsoluteFile() + "/mapped_" + System.currentTimeMillis());
				if(mappedSoundsDir.mkdir()) {
					String allErrors = "";
					for(CSMapping m : mList) {
						File f1 = null;
						File f2 = null;
						try {
							f1 = m.getcFile().getFile();
							f2 = new File(mappedSoundsDir.getPath() + "/" + m.getsFile().getFile().getName());
							
							if(f1.exists()) {
								if(f2.createNewFile()) {
									copyFile(f1, f2);
								} else {
									allErrors += "outputfile " + f2 + " already existed!\n";
								}
							} else {
								allErrors += "inputfile " + f1 + " doesnt exist!\n";
							}
						} catch(Exception e) {
							showAlert("exception while copying: " + f1 + " -> " + f2, e);
							allErrors += "unknown error while copying: " + f1 + "\n";
							e.printStackTrace();
						}
					}
					if(!allErrors.isEmpty()) {
						showAlert("following files where not copied:\n" + allErrors, null);
					} else {
						showAlert("done, no errors :)", AlertType.INFORMATION, null);
					}
				} else {
					String msg = "failed to create dir: " + mappedSoundsDir;
					System.err.println(msg);
					showAlert(msg, null);
				}
			}
		});
        
        HBox top = new HBox(5, load, save, clear, doit, crusaderSourceDirButton, soundSourceDirButton);
        
		BorderPane root = new BorderPane(center,top,sView,bottom,cView);
        bindProps(top, root, true, false);
        bindProps(bottom, root, true, false);
        bindProps(cView, root, false, true);
        bindProps(sView, root, false, true);
//        bindProps(center, root,);

        Scene scene = new Scene(root);
        
        stage.setTitle("Crusader Sound Mapping Helper");
        stage.setWidth(600);
        stage.setHeight(600);
        stage.setScene(scene);
        
        root.prefWidthProperty().bind(stage.widthProperty());
        root.prefHeightProperty().bind(stage.heightProperty());	
        
        stage.show();
	}

	/**
	 * binds r1 to r2
	 * @param r1 
	 * @param r2
	 */
	private void bindProps(Region r1, Region r2, boolean width, boolean height) {
		if(width)
			r1.prefWidthProperty().bind(r2.widthProperty());
		if(height)
			r1.prefHeightProperty().bind(r2.heightProperty());	
	}

	private void prepareLists() {
		File speechDir = new File(cDir.getAbsolutePath() + File.separatorChar + C_SPEECH_PATH);
		cList = createList(speechDir, EXTENSION);
		sList = createList(sDir, EXTENSION);
		mList = FXCollections.observableArrayList();
	}

	private ObservableList<FileWrapper> createList(File dir, String extension) {
		if(dir.isDirectory()) {
			File[] childreen = dir.listFiles(file -> file.getName().endsWith(extension));
			ObservableList<FileWrapper> result = FXCollections.observableArrayList();
			for(File child : childreen) {
				result.add(new FileWrapper(child));
			}
			return result;
		}
		return null;
	}

	private void prepareDirectories(Stage stage) {
		cDir = checkDirSet(stage, cDir, "Select Stronghold Crusader Game File");
		sDir = checkDirSet(stage, sDir, "Select Sound Source File");		
	}

	private File checkDirSet(Stage stage, File dir, String msg) {
		if(dir == null || !dir.isDirectory()) {
			showAlert(msg, null);
	        DirectoryChooser dirChooser = new DirectoryChooser();
	        dir = checkDirSet(stage, dirChooser.showDialog(stage), msg);
		}
		return dir;
	}

	@Override
	public void init() throws Exception {
		super.init();
		Preferences prefs = Preferences.userNodeForPackage(CrusaderMapper.class);
		String c = prefs.get(CRUSADER_PATH, null);
		String s = prefs.get(SOUND_PATH, null);
		File tmp = null;
		if(c != null) {
			tmp = new File(c);
			if(tmp.exists() && tmp.isDirectory()) {
				cDir = tmp;
			}
		}
		if(s != null) {
			tmp = new File(s);
			if(tmp.exists() && tmp.isDirectory()) {
				sDir = tmp;
			}
		}
	}
	
	@Override
	public void stop() throws Exception {
		super.stop();
		Preferences prefs = Preferences.userNodeForPackage(CrusaderMapper.class);
		prefs.put(CRUSADER_PATH, cDir.getAbsolutePath());
		prefs.put(SOUND_PATH, sDir.getAbsolutePath());
		prefs.flush();
	}
	
	
	private static void playSound(File audioFile) {
	    try {
	    	System.out.println("playing: " + audioFile.getAbsolutePath());
			Runtime.getRuntime().exec("C:\\Program Files (x86)\\Windows Media Player\\wmplayer.exe /play \"" + audioFile.getAbsolutePath() + "\"");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	 public static void copyFile(File sourceFile, File destFile) throws IOException {
	     if(!destFile.exists()) {
	      destFile.createNewFile();
	     }

	     RandomAccessFile source = null;
	     RandomAccessFile destination = null;
	     try {
	    	 source = new RandomAccessFile(sourceFile,"rw");
	    	 destination =new RandomAccessFile(destFile,"rw");

	      long position = 0;
	      long count    = source.getChannel().size();

	      source.getChannel().transferTo(position, count, destination.getChannel());
	     }
	     finally {
	      if(source != null) {
	       source.close();
	      }
	      if(destination != null) {
	       destination.close();
	      }
	    }
	 }
	
	private static String getKey(String s) {
		return s.contains(":") ? s.split(":")[0] : "";
	}
	
	 public static void showAlert(String msg, Exception e) {
		 showAlert(msg, AlertType.ERROR, e);
	 }
	 
	 public static void showAlert(String msg, AlertType type, Exception e) {
		 Alert alert = new Alert(type);
		 alert.setTitle(type.name());
		 alert.setHeaderText(msg == null ? "No message available" : msg);
		 alert.setContentText(e == null ? type.equals(AlertType.ERROR) ? "No exception information available" : "" : e.toString());
		 alert.showAndWait();
	 }

}
