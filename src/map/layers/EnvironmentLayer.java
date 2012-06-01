package map.layers;

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
}
