package crusader.mapper.util;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SoundUtil {
	public static void playSound(File audioFile) {
		try {
			System.out.println("playing: " + audioFile.getAbsolutePath());
			Runtime.getRuntime().exec("cmd /c \"\"%ProgramFiles(x86)%\\Windows Media Player\\wmplayer.exe\" /play \""
					+ audioFile.getAbsolutePath() + "\"\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static EventHandler<MouseEvent> createSoundEventHandler(Supplier<File> soundfileSup) {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				if (e.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
					if (e.getClickCount() == 2) {
						playSound(soundfileSup.get());
					}
				}
			}
		};
	}
}
