package crusader.mapper.util;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SoundUtil {
	public static void playSound(File audioFile) {
		playSound(audioFile.getAbsolutePath());
	}

	public static void playSound(String audioFile) {
		try {
			System.out.println("playing: " + audioFile);
			Runtime.getRuntime().exec("cmd /c \"\"%ProgramFiles(x86)%\\Windows Media Player\\wmplayer.exe\" /play \""
					+ audioFile + "\"\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static EventHandler<MouseEvent> createSoundEventHandler(Supplier<File> soundfileSup) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED) && e.getClickCount() == 2) {
					playSound(soundfileSup.get());
				}
			}
		};
	}
}
