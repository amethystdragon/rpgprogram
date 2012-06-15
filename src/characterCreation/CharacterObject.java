package characterCreation;

/**
 * @author eberta
 * @version 1.0
 * @created 14-Jun-2012 8:10:53 PM
 */
public interface CharacterObject {

	/**
	 * 
	 * @param ability
	 */
	public void addAbility(Ability ability);

	/**
	 * 
	 * @param armor
	 */
	public void addArmor(Armor armor);

	/**
	 * 
	 * @param attribute
	 */
	public void addAttribute(Attribute attribute);

	/**
	 * 
	 * @param feat
	 */
	public void addFeat(Feat feat);

	/**
	 * 
	 * @param item
	 */
	public void addItem(Item item);

	/**
	 * 
	 * @param skill
	 */
	public void addSkill(Skill skill);

	/**
	 * 
	 * @param weapon
	 */
	public void addWeapon(Weapon weapon);

	/**
	 * 
	 * @param ability
	 */
	public Ability getAbility(String ability);

	/**
	 * 
	 * @param armor
	 */
	public Armor getArmor(String armor);

	/**
	 * 
	 * @param attribute
	 */
	public Attribute getAttribute(String attribute);

	/**
	 * 
	 * @param feat
	 */
	public Feat getFeat(String feat);

	/**
	 * 
	 * @param item
	 */
	public Item getItem(String item);

	/**
	 * 
	 * @param skill
	 */
	public Skill getSkill(String skill);

	/**
	 * 
	 * @param weapon
	 */
	public Weapon getWeapon(String weapon);

	/**
	 * 
	 * @param ability
	 */
	public boolean removeAbility(String ability);

	/**
	 * 
	 * @param armor
	 */
	public boolean removeArmor(String armor);

	/**
	 * 
	 * @param attribute
	 */
	public boolean removeAttribute(String attribute);

	/**
	 * 
	 * @param feat
	 */
	public boolean removeFeat(String feat);

	/**
	 * 
	 * @param item
	 */
	public boolean removeItem(String item);

	/**
	 * 
	 * @param skill
	 */
	public boolean removeSkill(String skill);

	/**
	 * 
	 * @param weapon
	 */
	public boolean removeWeapon(String weapon);

	/**
	 * 
	 * @param notes
	 */
	public void setNotes(String notes);

	/**
	 * 
	 * @param name
	 */
	public void setPlayerName(String name);

}