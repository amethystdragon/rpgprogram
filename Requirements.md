# Details #
Goals of the project:
  * To provide an interface that is modular to provide support for any RPG.
  * To provide a chat system so that logs can be recorded and kept on file.
  * To provide a dice roller so that all the player's and Dungon Master's (DM) roles are fair.
  * To provide the DM total access to interplayer communication.
  * To store stat sheets of players that can be edited by the DM.
  * To provide the DM with a way to create and edit a map.
  * To allow the DM to display a map to players either in sections or its entirety.


---


# Basic Requirements #

## Maps ##

  * Sectioned
  * Linking
  * Free Draw / Grid (snap) Draw
  * Grid Overlay (square and hex)
  * Player/NPC/Special (i.e. traps, items, etc.) Tiles show on map
  * Charecter Movement

## Communication ##

  * Global Chat
  * Player to player chat (/wisper)
  * DM sees all chat
  * Dice roll (/roll) and player dice roll entered (/rolled)
  * Use of /me
  * DM command to att to log but unseen from players (/log or /note)

## Modularity ##

  * Dynamically generated interface
  * Dynamic storage
    * Map
    * Charecters
      * Position on map
      * Stats
  * Module
    * Statistics
    * Skills
    * Inventory Style - how they can carry (i.e. item number, weight, etc.)
    * Attributes (chatecter info i.e. name, picture, etc.)
    * Combat Style (predefined)
      1. Role inititive every round
      1. Role inititive every combat
      1. Inititive on charecter sheet
      1. Random assignment
      1. DM's disgression

## Statistics Tracking ##

  * Charecter Sheets
  * Save/load sessions
  * Editable by the DM
  * DM approval on player changes
  * Live change tracking of players by DM
  * Export to .pdf file