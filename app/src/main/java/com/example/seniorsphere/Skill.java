package com.example.seniorsphere;
/*
* stores the data of each skill
*/
public class Skill{
    
    String skill;
    
    double hours;
    
    /*
    * creates an object that stores data on the skill
    * @Param name
    */
    public Skill(String name){
        skill  = name;
        hours = 0;
    }
    /*
    *returns the name of the skill
    * @Return the name of the skill
    */
    public String getSkill() {
        return skill;
    }
}
