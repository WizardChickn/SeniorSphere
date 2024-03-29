package com.example.seniorsphere;

import java.util.ArrayList;

public class StatsData {
    ArrayList<Skill> ListOfSkills = new ArrayList<Skill>();

    Skill Skill1 = new Skill("Memory");
    Skill Skill2 = new Skill("Puzzles");

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




}
