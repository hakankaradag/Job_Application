package applications;
import java.util.*;
import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

public class HandleApplications {
private Map<String, Skill> skills = new TreeMap<>();
private Map<String, Position> positions = new TreeMap<>();
private Map<String, Applicant> applicants = new TreeMap<>();

	public void addSkills(String... names) throws ApplicationException {
		for (String name: names) {
			if (skills.containsKey(name)) throw new ApplicationException("duplicated skill " + name);
			skills.put(name,  new Skill(name));
		}
	}
	
	public void addPosition(String name, String... skillNames) throws ApplicationException {
		if (positions.containsKey(name)) throw new ApplicationException("duplicated position " + name);
		Position position = new Position(name);
		positions.put(name, position);
		for (String skillName: skillNames) {
			Skill skill = skills.get(skillName);
			if (skill == null) throw new ApplicationException("skill not found " + skillName);
			position.addSkill(skill);
			skill.addPosition(position);
		}
	}
	public Skill getSkill(String name) {return skills.get(name);}
	public Position getPosition(String name) {return positions.get(name);}
	
	public void addApplicant(String name, String capabilities) throws ApplicationException {
		if (applicants.containsKey(name)) throw new ApplicationException("duplicated applicant " + name);
		Applicant applicant = new Applicant(name);
		applicants.put(applicant.getName(), applicant);
		Scanner scanner = new Scanner(capabilities);
		scanner.useDelimiter("[,:]");
		while (scanner.hasNext()) {
			String skillName = scanner.next();
			int skillValue = scanner.nextInt();
			//System.out.println(skillName + " " + skillValue);
			Skill skill = skills.get(skillName);
			if (skill == null) {scanner.close();
				throw new ApplicationException("skill not found " + skillName);
			}
			if (skillValue < 1 || skillValue > 10) {scanner.close();
				throw new ApplicationException(String.format("invalid level %d for skill %s", skillValue, skillName));
			}
			new Capability(skillValue, applicant, skill);
		}
		scanner.close();
	}
	
	public String getCapabilities(String applicantName) throws ApplicationException {
		Applicant applicant = applicants.get(applicantName);
		if (applicant == null) throw new ApplicationException("applicant not found " + applicantName);
		return applicant.getCapabilities().stream()
		.sorted(comparing((Capability c) -> c.getSkill().getName()))
		.map(c -> c.getSkill().getName() + ":" + c.getLevel())
		.collect(joining(","));
	}
	
	public void enterApplication(String applicantName, String positionName) throws ApplicationException {
		Applicant applicant = applicants.get(applicantName);
		if (applicant == null) throw new ApplicationException("applicant not found " + applicantName);
		Position position = positions.get(positionName);
		if (position == null) throw new ApplicationException("position not found " + positionName);
		if (applicant.getPosition() != null) throw new ApplicationException("applicant already applied for a position " + applicant.getPosition().getName());
		//si controlla che applicant abbia capabilities per tutti gli skill
		for (Skill skill: position.getSkills()) {
			if (applicant.getCapability(skill) == null) 
				throw new ApplicationException(String.format("capability %s not found in applicant %s ", 
						skill.getName(), applicant.getName()));
		}
		applicant.setPosition(position);
		position.addApplicant(applicant);
	}
	
	
	public int setWinner(String applicantName, String positionName) throws ApplicationException {
		Applicant applicant = applicants.get(applicantName);
		Position position = positions.get(positionName);
		if(position.getWinner() != null) throw new ApplicationException(
				String.format("position %s already assigned to %s ", position.getName(),
						position.getWinner()));
		int requiredCap = position.getSkills().size() * 6;
		if (applicant.getPosition() != position) 
			throw new ApplicationException(String.format("applicant %s didn't apply for position %s ", 
				applicant.getName(), position.getName()));
		int capSum = 0;
		for (Skill skill: position.getSkills()) capSum += applicant.getCapability(skill).getLevel();
		if (capSum <= requiredCap)
			throw new ApplicationException(String.format("applicant %s has not the overall capability (%d, %d) for position %s ", 
				applicant.getName(), capSum, requiredCap, position.getName()));
		position.setWinner(applicant);
		System.out.println(String.format("applicant %s selected (%d, %d) for position %s ", 
				applicant.getName(), capSum, requiredCap, position.getName()));
		return capSum;
	}
	
	public SortedMap<String, Long> skill_nApplicants() {
		return applicants.values().stream()
		.flatMap(applicant -> applicant.getSkills().stream())
		.collect(groupingBy(Skill::getName, TreeMap::new, counting()));
	}
	
	public String maxPosition() {
		return positions.values().stream()
			.max(comparing(Position::getApplicantN)).get().getName();
				
	}
}



/*
		String[] capabilitiesString = capabilities.split(",");
		for (String capabilityString: capabilitiesString) {
			String[] values = capabilityString:
		}

*/
