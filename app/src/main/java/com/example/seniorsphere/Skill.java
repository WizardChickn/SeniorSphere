package com.example.seniorsphere;
//object that contains the data for each skill
//
public class Skill{
    String skill;
    double hours;

    public Skill(String name){
        skill  = name;
        hours = 0;
    }

    public String getSkill() {
        return skill;
    }
}
