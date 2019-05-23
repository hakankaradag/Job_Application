package applications;
import java.util.*;
import static java.util.Comparator.*;
import static java.util.stream.Collectors.*;

public class Position {
private String name;
private List<Skill> skills = new ArrayList<>();
private List<Applicant> applicants = new ArrayList<>();
private Applicant winner;

	Position(String name) {this.name = name;}
	public String getName() {return name;}
	public List<String> getApplicants() {
		return applicants.stream()
		.map(Applicant::getName)
		.sorted(comparing (s -> s))
		.collect(toList());
	}
	public String getWinner() {if (winner == null) return null; else return winner.getName();}

	void setWinner(Applicant winner) {this.winner = winner;}
	void addSkill(Skill skill) {skills.add(skill);} //può dare errore di duplicazione
	List<Skill> getSkills() {return skills;}
	void addApplicant(Applicant applicant) {applicants.add(applicant);}
	public int getApplicantN() {return applicants.size();}
	
	public String toString() {return name;}

}
