package map.layers;

import java.awt.Point;
import java.util.ArrayList;

import map.blocks.Block;
import map.blocks.EnvironmentBlock;

public class EnvironmentLayer extends Layer {

	private static EnvironmentLayer instance;
	
	
	private EnvironmentLayer(){
		
		
	}
	
	public synchronized EnvironmentLayer getInstance(){
		if(instance==null){
			instance = new EnvironmentLayer();
		}
		return instance;
	}

	@Override
	public boolean addBlock(Block block) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeBlock(Block block) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Block getBlock(Point position) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
