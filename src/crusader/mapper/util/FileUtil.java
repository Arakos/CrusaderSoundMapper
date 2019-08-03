package crusader.mapper.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileUtil {

	public static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!destFile.exists()) {
			destFile.createNewFile();
		}

		RandomAccessFile source = null;
		RandomAccessFile destination = null;
		try {
			source = new RandomAccessFile(sourceFile, "rw");
			destination = new RandomAccessFile(destFile, "rw");

			long position = 0;
			long count = source.getChannel().size();

			source.getChannel().transferTo(position, count, destination.getChannel());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}
}
