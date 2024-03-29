package com.example.seniorsphere;

public class Skill{
    String skill;
    double hours;

    public Skill(String name){
        skill = name;
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