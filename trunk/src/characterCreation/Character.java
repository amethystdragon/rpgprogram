package characterCreation;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author eberta
 * @version 1.0
 * @created 14-Jun-2012 8:10:44 PM
 */
public class Character implements CharacterObject {

	private Map<String,Ability> AbilityList;
	private Map<String,Armor> ArmorList;
	private Map<String,Attribute> AttributeList;
	private Map<String,Feat> FeatList;
	private Map<String,Item> ItemList;
	private String Notes;
	private String PlayerName;
	private Map<String,Skill> SkillList;
	private Map<String,Weapon> WeaponsList;

	private Character(){

		AbilityList = new HashMap<String,Ability>();
		ArmorList = new HashMap<String,Armor>();
		AttributeList = new HashMap<String,Attribute>();
		FeatList = new HashMap<String,Feat>();
		ItemList = new HashMap<String,Item>();
		SkillList = new HashMap<String,Skill>();
		WeaponsList = new HashMap<String,Weapon>();
		Notes = "";
		PlayerName = "";
		
	}

	private Character CreateCharacter(Character template) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 
	 * @param File
	 */
	public static Character CreateCharacter(File template){
		Character character = new Character();
		return character;
	}
	
	/**
	 * 
	 * @param Ability
	 */
	public void addAbility(Ability ability){
		AbilityList.put(ability.getName(),ability);
	}

	/**
	 * 
	 * @param Armor
	 */
	public void addArmor(Armor armor){
		ArmorList.put(armor.getName(), armor);
	}

	/**
	 * 
	 * @param Attribute
	 */
	public void addAttribute(Attribute attribute){
		AttributeList.put(attribute.getName(), attribute);
	}

	/**
	 * 
	 * @param Feat
	 */
	public void addFeat(Feat feat){
		FeatList.put(feat.getName(), feat);
	}

	/**
	 * 
	 * @param Item
	 */
	public void addItem(Item item){
		ItemList.put(item.getName(), item);
	}

	/**
	 * 
	 * @param Skill
	 */
	public void addSkill(Skill skill){
		SkillList.put(skill.getName(), skill);
	}

	/**
	 * 
	 * @param Weapon
	 */
	public void addWeapon(Weapon weapon){
		WeaponsList.put(weapon.getName(), weapon);
	}


	/**
	 * 
	 * @param String
	 */
	public Ability getAbility(String abilityName){
		Ability ability = null;
		if(AbilityList.containsKey(abilityName)){
			ability = AbilityList.get(abilityName);
		}
		return ability;
	}

	/**
	 * 
	 * @param String
	 */
	public Armor getArmor(String armorName){
		Armor armor = null;
		if(ArmorList.containsKey(armorName)){
			armor = ArmorList.get(armorName);
		}
		return armor;
	}

	/**
	 * 
	 * @param String
	 */
	public Attribute getAttribute(String attributeName){
		Attribute attribute = null;
		if(AttributeList.containsKey(attributeName)){
			attribute = AttributeList.get(attributeName);
		}
		return attribute;
	}

	/**
	 * 
	 * @param String
	 */
	public Feat getFeat(String featName){
		Feat feat = null;
		if(FeatList.containsKey(featName)){
			feat = FeatList.get(featName);
		}
		return feat;
	}

	/**
	 * 
	 * @param String
	 */
	public Item getItem(String itemName){
		Item item = null;
		if(ItemList.containsKey(itemName)){
			item = ItemList.get(itemName);
		}
		return item;
	}

	public String getNotes(){
		return Notes;
	}

	public String getPlayerName(){
		return PlayerName;
	}

	/**
	 * 
	 * @param String
	 */
	public Skill getSkill(String skillName){
		Skill skill = null;
		if(SkillList.containsKey(skillName)){
			skill = SkillList.get(skillName);
		}
		return skill;
	}

	/**
	 * 
	 * @param String
	 */
	public Weapon getWeapon(String weaponName){
		Weapon weapon = null;
		if(WeaponsList.containsKey(weaponName)){
			weapon = WeaponsList.get(weaponName);
		}
		return weapon;
	}

	/**
	 * 
	 * @param String
	 */
	public boolean removeAbility(String abilityName){
		Ability a = AbilityList.remove(abilityName);
		if(a != null){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public boolean removeArmor(String armorName){
		Armor a = ArmorList.remove(armorName);
		if(a != null){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public boolean removeAttribute(String attributeName){
		Attribute a = AttributeList.remove(attributeName);
		if(a != null){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public boolean removeFeat(String featName){
		Feat a = FeatList.remove(featName);
		if(a != null){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public boolean removeItem(String itemName){
		Item a = ItemList.remove(itemName);
		if(a != null){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public boolean removeSkill(String skillName){
		Skill a = SkillList.remove(skillName);
		if(a != null){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public boolean removeWeapon(String weaponName){
		Weapon a = WeaponsList.remove(weaponName);
		if(a != null){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param String
	 */
	public void setNotes(String notes){
		Notes = notes;
	}

	/**
	 * 
	 * @param String
	 */
	public void setPlayerName(String name){
		PlayerName = name;
	}

}//end Character