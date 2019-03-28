package com.example.administrator.access_school_client.Fragments.FragmentSQ;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2019/3/26 17:43.
 */
public class Meet {
    String name;
    int isRun;
    String person;
    String time;

    public Meet(String name, int isRun, String person, String time) {
        this.name = name;
        this.isRun = isRun;
        this.person = person;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsRun() {
        return isRun;
    }

    public void setIsRun(int isRun) {
        this.isRun = isRun;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
