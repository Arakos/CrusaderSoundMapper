package crusader.mapper.data.model;

import java.util.Arrays;
import java.util.List;

public class SoundMapping {

	CrusaderSound origSound;

	List<FileWrapper> replacements;

	public SoundMapping(CrusaderSound origSound, FileWrapper... replacements) {
		this.origSound = origSound;
		this.replacements = Arrays.asList(replacements);
	}

}
