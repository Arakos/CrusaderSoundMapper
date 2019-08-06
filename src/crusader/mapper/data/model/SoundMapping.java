package crusader.mapper.data.model;

import java.util.Arrays;
import java.util.List;

public class SoundMapping {

	CrusaderSound origSound;

	List<SoundFile> replacements;

	public SoundMapping(CrusaderSound origSound, SoundFile... replacements) {
		this.origSound = origSound;
		this.replacements = Arrays.asList(replacements);
	}

}
