This file defines how the data is saved to a file for the character data and character templates.

# Introduction #

The character template files and character save files have the same internal layout with different extensions.  This document defines how the data is stored within the files.  This includes sections for the different sub types of the character creation. This file will probably be encoded in JSON to make it easy and quicker to use.


# Details #

There are different sections defined in the file. These sections include:
  * Main File Info
    * This section is for the Player name, the Date, and a Notes Section
  * Character Info
    * This section is for the Character Info
    * **Basic Info**
      * This section is for the basic information including character name, race, age, height, hair, eyes, alignment, money, etc.
    * **Attributes**
      * This section is for the attributes such as Constitution, Dexterity, Charisma, etc.
    * **Weapons**
      * This section is for the weapons and their attributes.
    * **Armor**
      * This section is for the armor for the character and its attributes.
    * **Skills**
      * This section is for the Skill set such as Appraise, Jump, Swim, etc.
    * **Feats**
      * This section is for the feats for the character such as defined in the different books of role playing.
    * **Abilities**
      * This section is for the different abilities of the character such as magic spells, telekinesis, etc
    * **Items**
      * This section is to include the items carried by the character and the characteristics of the items.

The indentation is an indicator of how this will be broken up in the JSON format.