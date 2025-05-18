package com.example.storiesapp.models;

public class Story {
    private int id;private String title;private String content;private String date;private int auteurId;private int paysId;private int categorieId;
    public int getId(){return id;}public void setId(int i){id=i;}
    public String getTitle(){return title;}public void setTitle(String t){title=t;}
    public String getContent(){return content;}public void setContent(String c){content=c;}
    public String getDate(){return date;}public void setDate(String d){date=d;}
    public int getAuteurId(){return auteurId;}public void setAuteurId(int a){auteurId=a;}
    public int getPaysId(){return paysId;}public void setPaysId(int p){paysId=p;}
    public int getCategorieId(){return categorieId;}public void setCategorieId(int c){categorieId=c;}
}