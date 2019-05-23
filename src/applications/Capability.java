package applications;

class Capability {

private int level;
private Applicant applicant;
private Skill skill;

Capability(int level, Applicant applicant, Skill skill) {
	this.level = level;	this.applicant = applicant; this.skill = skill;
	applicant.addCapability(this); skill.addCapability(this);
}
int getLevel() {return level;}
Applicant getApplicant() {return applicant;}
Skill getSkill() {return skill;}

	
}
