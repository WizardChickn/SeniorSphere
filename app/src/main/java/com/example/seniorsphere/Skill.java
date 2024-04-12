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

    public void addHours(double hours){
        this.hours+=hours;
    }

    public double getHours() {
        return hours;
    }

    public String getSkill() {
        return skill;
    }
}
