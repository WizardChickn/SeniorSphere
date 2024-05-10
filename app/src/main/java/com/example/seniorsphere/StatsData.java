package com.example.seniorsphere;




/*
* contains data on the user's activity in the app
 */
public class StatsData {

    public static Skill Skill1 = new Skill("Strategy");
    public static Skill Skill2 = new Skill("Logic");
    public static Skill Skill3 = new Skill("Patterns");
    /*
    *returns the name of the skill for a skill object
    *@Param name of the skill object
    *@Return name of the skill
     */
    public String getSkill(Skill skill) {
        return skill.getSkill();
    }
}