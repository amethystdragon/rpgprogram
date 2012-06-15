package map.layers;

import java.awt.Point;

import map.blocks.Block;


public class ObjectLayer extends Layer {

	private static ObjectLayer instance;
	
	private ObjectLayer(){
		
	}
	
	public synchronized ObjectLayer getInstance(){
		if(instance==null){
			instance = new ObjectLayer();
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
