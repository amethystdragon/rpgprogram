package map.layers;

public class LayerManager {
	
	private static LayerManager instance;
	
	private static EnvironmentLayer ENVIRONMENT;
	
	private ObjectLayer view;
	
	private CharacterLayer character;

	private LayerManager(){
		
	}
	
	public synchronized LayerManager getInstance(){
		if(instance==null){
			instance = new LayerManager();
		}
		return instance;
	}
	
	
}
