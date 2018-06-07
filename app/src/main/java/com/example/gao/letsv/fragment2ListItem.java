package com.example.gao.letsv;

/**
 * Created by xjl on 2018/5/31.
 */

public class fragment2ListItem {
    private String title;
    private String date;
    private String url;
    public fragment2ListItem(String a ,String b){
        this.title = a;
        this.date = b;
    }
    public fragment2ListItem(String a ,String b,String c){
        this.title = a;
        this.date = b;
        this.url = c;
    }
    public String getTitle() {
        return this.title;
    }
    public String getDate(){
        return this.date;
    }
    public String getUrl(){return this.url;}
}
