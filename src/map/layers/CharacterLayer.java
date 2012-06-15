package map.layers;

import java.awt.Point;

import map.blocks.Block;

public class CharacterLayer extends Layer {
	
	private static CharacterLayer instance;

	private CharacterLayer(){
		
	}
	
	public synchronized CharacterLayer getInstance(){
		if(instance==null){
			instance = new CharacterLayer();
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
