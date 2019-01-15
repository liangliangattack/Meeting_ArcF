package com.example.administrator.access_school_client.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * ..
 * author:liangliangattack 1364744931@.qq.com
 * Administrator on 2018/9/13 15:09.
 */
public class TransText {
    private String basic[] = new String[5];
    private StringBuffer explains;
    private String web;
    private String tSpeakUrl;
    private String speakUrl;
    private String translation;
    public TransText(){

    }

    public TransText(String[] basic, StringBuffer explains, String web, String tSpeakUrl, String speakUrl, String translation) {
        this.basic = basic;
        this.explains = explains;
        this.web = web;
        this.tSpeakUrl = tSpeakUrl;
        this.speakUrl = speakUrl;
        this.translation = translation;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String[] getBasic() {
        return basic;
    }

    public void setBasic(String[] basic) {
        this.basic = basic;
    }

    public StringBuffer getExplains() {
        return explains;
    }

    public void setExplains(StringBuffer explains) {
        this.explains = explains;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String gettSpeakUrl() {
        return tSpeakUrl;
    }

    public void settSpeakUrl(String tSpeakUrl) {
        this.tSpeakUrl = tSpeakUrl;
    }

    public String getSpeakUrl() {
        return speakUrl;
    }

    public void setSpeakUrl(String speakUrl) {
        this.speakUrl = speakUrl;
    }
}
