package map.blocks;

import java.awt.Point;

public class CharacterBlock extends Block {

	private CharacterBlock() {
		super(0,0);
	}
	
	public CharacterBlock(int x, int y){
		super(x,y);
	}
	
	public CharacterBlock(Point pos){
		super(pos);
	}
	
	public CharacterBlock(int x, int y, Block West, Block East, Block North, Block South, Block SouthWest, Block NorthWest, Block SouthEast, Block NorthEast){
		super(x,y,West,East,North,South,SouthWest,NorthWest,SouthEast,NorthEast);
	}
	
	public CharacterBlock(Point pos, Block West, Block East, Block North, Block South, Block SouthWest, Block NorthWest, Block SouthEast, Block NorthEast){
		super(pos,West,East,North,South,SouthWest,NorthWest,SouthEast,NorthEast);
	}

}
