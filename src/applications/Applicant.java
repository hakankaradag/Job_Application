package applications;
import java.util.*;
import static java.util.Comparator.*;

class Applicant {
	private String name;
	private Map<Skill,Capability> capabilities = new TreeMap<>(comparing(Skill::getName));
	private Position position = null;
	private boolean winner = false;
	
	Applicant(String name) {this.name = name;}
	
	String getName() {return name;}
	void addCapability(Capability c) {capabilities.put(c.getSkill(),c);}
	Collection<Capability> getCapabilities() {return capabilities.values();}

	Position getPosition() {return position;}
	void setPosition(Position position) {this.position = position;}
	
	Capability getCapability(Skill skill) {return capabilities.get(skill);}

	boolean isWinner() {return winner;}

	void setWinner() {winner = true;}
	Collection<Skill> getSkills() {return capabilities.keySet();}
	
}
private boolean winner = false ;

void setwinner() {winner = true;}}