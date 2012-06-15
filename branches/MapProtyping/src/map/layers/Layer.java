package map.layers;

import java.awt.Point;
import java.util.ArrayList;

import map.blocks.Block;

public abstract class Layer {
	
	private ArrayList blockList;

	public Layer(){
		
	}
	
	public abstract boolean addBlock(Block block);
	
	public abstract boolean removeBlock(Block block);
	
	public abstract Block getBlock(Point position);
	
}
