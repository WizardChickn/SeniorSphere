package com.example.seniorsphere;

import android.os.Bundle;

import java.util.ArrayList;

public class StatsData {
    ArrayList<Skill> ListOfSkills = new ArrayList<Skill>();

    public static String Name = "";
    public static Skill Skill1 = new Skill("Memory");
    public static Skill Skill2 = new Skill("Puzzles");




    public String getSkill(Skill skill) {
        return skill.getSkill();
    }

}
