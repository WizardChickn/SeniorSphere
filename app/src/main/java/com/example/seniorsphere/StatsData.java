package com.example.seniorsphere;

import android.os.Bundle;

import java.util.ArrayList;
//contains data on the user's activity in the app
public class StatsData {
    ArrayList<Skill> ListOfSkills = new ArrayList<Skill>();

    public static String Name = "";
    public static Skill Skill1 = new Skill("Memory");
    public static Skill Skill2 = new Skill("Puzzles");

    public static Skill Skill3 = new Skill("Logic");


    public String getSkill(Skill skill) {
        return skill.getSkill();
    }

}