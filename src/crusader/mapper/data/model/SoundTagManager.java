package crusader.mapper.data.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class SoundTagManager {
	
	public enum META_TAG {
		UNIT, COMMAND, ACTION, ARAB;
	}
	
	public enum SOUND_TAG {
		
		ATTACK("_atk[\\w][1-5]", META_TAG.COMMAND)
		,MOVE("_m[1-5]", META_TAG.COMMAND)
		,DIG("_moat[1-3]", META_TAG.COMMAND)
		,SELECT("_s[1-5]", META_TAG.ACTION)
		,DISBAND("_disband1", META_TAG.ACTION)
		,ASSASIN("^aassasin", META_TAG.UNIT, META_TAG.ARAB)
		,A_ARCHER("^abow", META_TAG.UNIT, META_TAG.ARAB)
		,GRENADIER("^agrenadier", META_TAG.UNIT, META_TAG.ARAB)
		,HORSE_ARCHER("^ahorse", META_TAG.UNIT, META_TAG.ARAB)
		,SLAVE("^aslave", META_TAG.UNIT, META_TAG.ARAB)
		,SLINGER("^asling", META_TAG.UNIT, META_TAG.ARAB)
		,A_SWORDSMAN("^asword", META_TAG.UNIT, META_TAG.ARAB)
		,ARCHER("^arch", META_TAG.UNIT)
		,CROSSBOW("^cross", META_TAG.UNIT)
		,ENGINEER("^engineer", META_TAG.UNIT)
		,HORSE_SWORDSMAN("^knight", META_TAG.UNIT)
		,LADDER("^ladder", META_TAG.UNIT)
		,MACE("^mace", META_TAG.UNIT)
		,MONK("^monk", META_TAG.UNIT)
		,PIKE("^pike", META_TAG.UNIT)
		,SPEAR("^spear", META_TAG.UNIT)
		,SWORDSMAN("^sword", META_TAG.UNIT)
		,DIGGER("^tunnel", META_TAG.UNIT)
		;
	
		private final Pattern regex;
		
		private final Set<META_TAG> metaTags;
		
	
		private SOUND_TAG(String regex, META_TAG... metaTags) {
			this.regex = Pattern.compile(regex);
			this.metaTags = new HashSet<>(Arrays.asList(metaTags));
		}
	
		public Pattern getRegex() {
			return this.regex;
		}
		
		public Set<META_TAG> getMetaTags() {
			return this.metaTags;
		}
		
	}
	
	public static Set<SOUND_TAG> getSoundTags(String str) {
		Set<SOUND_TAG> tags = new HashSet<>();
		for (SOUND_TAG m : SOUND_TAG.values()) {
			if (m.getRegex().matcher(str.toLowerCase()).find()) {
				tags.add(m);
			}
		}
		return tags;
	}
	
	public static Set<META_TAG> getMetaTags(Collection<SOUND_TAG> soundTags) {
		return soundTags.stream().flatMap(tag -> tag.getMetaTags().stream()).collect(Collectors.toSet());
	}
}
