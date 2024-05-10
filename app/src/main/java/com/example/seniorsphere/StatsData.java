package com.example.seniorsphere;




//contains data on the user's activity in the app
public class StatsData {

    public static Skill Skill1 = new Skill("Strategy");
    public static Skill Skill2 = new Skill("Logic");
    public static Skill Skill3 = new Skill("Patterns");
    public String getSkill(Skill skill) {
        return skill.getSkill();
    }
}