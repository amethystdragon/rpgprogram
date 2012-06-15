package map.blocks;

import java.awt.Point;

/**
 * This Class is the basic Block that hold data of blocks around it and it's Position.  It
 * is up to who ever extends this class to add on additional functionality.
 * @author Sgt.hotshot@gmail.com
 *
 */
public abstract class Block {

	/**
	 * Position of Block
	 */
	private Point position;
	
	
	/**
	 * Block 0 = West
	 * Block 1 = East
	 * Block 2 = North
	 * Block 3 = South
	 * Block 4 = SouthWest
	 * Block 5 = NorthWest
	 * Block 6 = SouthEast
	 * Block 7 = NorthEast
	 * Stores the Neighboring Blocks
	 */
	private Block[] neighborBlocks;
	
	
	
	
	/**
	 * Default Constructor
	 * Privatized to prevent anyone from using it
	 */
	private Block(){
		
	}
	
	
	
	
	/**
	 * Constructor for a block without knowing any neighboring blocks using x and y
	 * @param x X position of the block
	 * @param y Y position of the block
	 */
	public Block(int x, int y){
		position = new Point(x,y);
		for(int size=0; size<=7; size++){
			neighborBlocks[size] = null;
		}
		
	}
	
	
	
	
	/**
	 * Constructor for a block with all the variables using x and y
	 * @param x X position of the block
	 * @param y Y position of the block
	 * @param North Neighboring Block to the North
	 * @param South Neighboring Block to the South
	 * @param East Neighboring Block to the East
	 * @param West Neighboring Block to the West
	 * @param SouthWest Neighboring Block to the SouthWest
	 * @param NorthWest Neighboring Block to the NorthWest
	 * @param SouthEast Neighboring Block to the SouthEast
	 * @param NorthEast Neighboring Block to the NorthEast
	 */
	public Block(int x, int y, Block North, Block South, Block East, Block West, Block SouthWest, Block NorthWest, Block SouthEast, Block NorthEast){
		position = new Point(x,y);
		neighborBlocks[0] = West;
		neighborBlocks[1] = East;
		neighborBlocks[2] = North;
		neighborBlocks[3] = South;
		neighborBlocks[4] = SouthWest;
		neighborBlocks[5] = NorthWest;
		neighborBlocks[6] = SouthEast;
		neighborBlocks[7] = NorthEast;
	}
	
	
	
	
	/**
	 * Constructor for a block without knowing any neighboring blocks using Points
	 * @param pos Point that holds the position of the Block
	 */
	public Block(Point pos){
		position = pos;
		for(int size=0; size<=7; size++){
			neighborBlocks[size] = null;
		}
	}
	
	
	
	
	/**
	 * Constructor for a block with all the variables using Points
	 * @param pos Point that holds the position of the Block
	 * @param North Neighboring Block to the North
	 * @param South Neighboring Block to the South
	 * @param East Neighboring Block to the East
	 * @param West Neighboring Block to the West
	 * @param SouthWest Neighboring Block to the SouthWest
	 * @param NorthWest Neighboring Block to the NorthWest
	 * @param SouthEast Neighboring Block to the SouthEast
	 * @param NorthEast Neighboring Block to the NorthEast
	 */
	public Block(Point pos, Block North, Block South, Block East, Block West, Block SouthWest, Block NorthWest, Block SouthEast, Block NorthEast){
		position = pos;
		neighborBlocks[0] = West;
		neighborBlocks[1] = East;
		neighborBlocks[2] = North;
		neighborBlocks[3] = South;
		neighborBlocks[4] = SouthWest;
		neighborBlocks[5] = NorthWest;
		neighborBlocks[6] = SouthEast;
		neighborBlocks[7] = NorthEast;
	}
	
	
	
	
	/**
	 * Returns the Neighboring Block to the West
	 * @return Block
	 */
	public Block getWestNeighborBlock(){
		return neighborBlocks[0];
	}
	
	
	
	
	/**
	 * Returns the Neighboring Block to the East
	 * @return Block
	 */
	public Block getEastNeighborBlock(){
		return neighborBlocks[1];
	}
	
	
	
	
	/**
	 * Returns the Neighboring Block to the North
	 * @return Block
	 */
	public Block getNorthNeighborBlock(){
		return neighborBlocks[2];
	}
	
	
	
	
	/**
	 * Returns the Neighboring Block to the South
	 * @return Block
	 */
	public Block getSouthNeighborBlock(){
		return neighborBlocks[3];
	}
	
	
	
	
	/**
	 * Returns the Neighboring Block to the SouthWest
	 * @return Block
	 */
	public Block getSouthWestNeighborBlock(){
		return neighborBlocks[4];
	}
	
	
	
	
	/**
	 * Returns the Neighboring Block to the NorthWest
	 * @return Block
	 */
	public Block getNorthWestNeighborBlock(){
		return neighborBlocks[5];
	}
	
	
	
	
	/**
	 * Returns the Neighboring Block to the SouthEast
	 * @return Block
	 */
	public Block getSouthEastNeighborBlock(){
		return neighborBlocks[6];
	}
	
	
	
	
	/**
	 * Returns the Neighboring Block to the NorthEast
	 * @return Block
	 */
	public Block getNorthEastNeighborBlock(){
		return neighborBlocks[7];
	}
	
	
	
	
	/**
	 * Returns the Point the block is at
	 * @return Position of the block in a Point
	 */
	public Point getPoint(){
		return position;
	}
	
	
	
	
	/**
	 * Returns the X Position of the Block
	 * @return The x Position of the block in an int
	 */
	public int getXPosition(){
		return position.x;
	}
	
	
	
	
	/**
	 * Returns the Y Position of the Block
	 * @return The X position of the block in an int
	 */
	public int getYPosition(){
		return position.y;
	}
	
	
	
	
	/**
	 * Sets the Point of the block
	 * @param pos Point
	 */
	public void setPoint(Point pos){
		position = pos;
	}
	
	
	
	
	/**
	 * Sets the X Position of the block
	 * @param x int x
	 */
	public void setXPosition(int x){
		position.x = x;
	}
	
	
	
	
	/**
	 * Sets the Y Position of the block
	 * @param y int y
	 */
	public void setYPosition(int y){
		position.y = y;
	}
	
	
	
	
	/**
	 * Sets the Block to the west
	 * @param west Block
	 */
	public void setWestBlock(Block west){
		neighborBlocks[0] = west;
	}
	
	
	
	
	/**
	 * Sets the Block to the east
	 * @param east Block
	 */
	public void setEastBlock(Block east){
		neighborBlocks[1] = east;
	}
	
	
	
	
	/**
	 * Sets the Block to the north
	 * @param north Block
	 */
	public void setNorthBlock(Block north){
		neighborBlocks[2] = north;
	}
	
	
	
	
	/**
	 * Sets the Block to the south
	 * @param south Block
	 */
	public void setSouthBlock(Block south){
		neighborBlocks[3] = south;
	}
	
	
	
	
	/**
	 * Sets the Block to the southWest
	 * @param southWest Block
	 */
	public void setSouthWestBlock(Block southWest){
		neighborBlocks[4] = southWest;
	}
	
	
	
	
	/**
	 * Sets the Block to the northWest
	 * @param northWest Block
	 */
	public void setNorthWestBlock(Block northWest){
		neighborBlocks[5] = northWest;
	}
	
	
	
	
	/**
	 * Sets the Block to the southEast
	 * @param southEast Block
	 */
	public void setSouthEastBlock(Block southEast){
		neighborBlocks[6] = southEast;
	}
	
	
	
	
	/**
	 * Sets the Block to the northEast
	 * @param northEast Block
	 */
	public void setNorthEastBlock(Block northEast){
		neighborBlocks[4] = northEast;
	}
}
