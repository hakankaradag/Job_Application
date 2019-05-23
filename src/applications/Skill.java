package applications;
import java.util.*;

public class Skill {

	private String name;
	private Map<String, Position> positions = new TreeMap<>();
	private List<Capability> capabilities = new ArrayList<>();
	
	Skill(String name) {this.name = name;}
	
	public String getName() {return name;}
	public List<Position> getPositions() {return new ArrayList<>(positions.values());}
	
	
	void addPosition(Position position) {positions.put(position.getName(), position);}
	void addCapability(Capability c) {capabilities.add(c);}
	List<Capability> getCapabilities() {return capabilities;}
	
}
