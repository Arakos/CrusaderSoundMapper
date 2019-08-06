package crusader.mapper.util;

import java.io.File;
import java.io.IOException;
import java.util.function.Supplier;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class SoundUtil {

	public static void playSound(File audioFile) {
		try {
			System.out.println("playing: " + audioFile.getAbsolutePath());
			Runtime.getRuntime().exec("cmd /c \"\"%ProgramFiles(x86)%\\Windows Media Player\\wmplayer.exe\" /play \""
					+ audioFile.getAbsolutePath() + "\"\"");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public static void playSoundNative(File audioFile) {
		AudioInputStream stream = null;
		AudioFormat format;
		DataLine.Info info;
		Clip clip;
		try {
			for (Type t : AudioSystem.getAudioFileTypes()) {
				System.out.println(t.getExtension() + " -> " + audioFile.getName());
			}
			stream = AudioSystem.getAudioInputStream(audioFile);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
