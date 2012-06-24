package map.blocks;

import java.awt.Point;

/**
 * This class creates Environment Blocks that hold environment data.  Blocks that hold data on the environment will use this class.
 * @author Sgt.hotshot@gmail.com
 *
 */
public class EnvironmentBlock extends Block {

	private static int VIEWMODIFYER;
	
	private static int MOVEMENTMODIFYER;
	
	private boolean isNonMoveable;
	
	private boolean isDestroyable;
	
	private int hitPoints;
	
	private boolean isPassible;
	
	private boolean isLandonable;
	
	/**
	 * Default Constructor
	 * Privatized to prevent anyone from using it
	 */
	private EnvironmentBlock() {
		super(0,0);
	}

	
	public EnvironmentBlock(int x, int y){
		super(x,y);
	}
	

	public EnvironmentBlock(Point pos){
		super(pos);
	}
	
	public EnvironmentBlock(int x, int y, Block North, Block South, Block East, Block West, Block SouthWest, Block NorthWest, Block SouthEast, Block NorthEast){
		super(x,y,North,South,East,West,SouthWest,NorthWest,SouthEast,NorthEast);
	}
	
	public EnvironmentBlock(Point pos, Block North, Block South, Block East, Block West, Block SouthWest, Block NorthWest, Block SouthEast, Block NorthEast){
		super(pos,North,South,East,West,SouthWest,NorthWest,SouthEast,NorthEast);
	}
	
	
}
