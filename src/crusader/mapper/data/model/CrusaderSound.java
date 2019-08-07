package crusader.mapper.data.model;

import java.io.File;
import java.util.Set;

import crusader.mapper.data.model.SoundTagManager.SOUND_TAGS;

public class CrusaderSound extends SoundFile {

	final Set<SOUND_TAGS> tags;

	public CrusaderSound(File sound) {
		super(sound);
		this.tags = SoundTagManager.getSoundTags(sound.getName());
	}

	public Set<SOUND_TAGS> getTags() {
		return tags;
	}

	@Override
	public String toString() {
		return super.toString() + " " + tags + " " + SoundTagManager.getMetaTags(tags);
	}

}
