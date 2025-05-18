package com.example.storiesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.storiesapp.models.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NAME="stories.db";
    private static final int VERSION=1;
    public DBHelper(Context c){ super(c,NAME,null,VERSION); }
    @Override public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Auteur(id INTEGER PRIMARY KEY,name TEXT,date TEXT)");
        db.execSQL("CREATE TABLE Pays(id INTEGER PRIMARY KEY,name TEXT,flag BLOB)");
        db.execSQL("CREATE TABLE Categorie(id INTEGER PRIMARY KEY,name TEXT,image BLOB)");
        db.execSQL("CREATE TABLE Histoire(id INTEGER PRIMARY KEY,title TEXT,image BLOB,content TEXT,date TEXT,auteur_id INTEGER,pays_id INTEGER,categorie_id INTEGER)");
    }
    @Override public void onUpgrade(SQLiteDatabase db,int o,int n){
        db.execSQL("DROP TABLE IF EXISTS Histoire");
        db.execSQL("DROP TABLE IF EXISTS Auteur");
        db.execSQL("DROP TABLE IF EXISTS Pays");
        db.execSQL("DROP TABLE IF EXISTS Categorie");
        onCreate(db);
    }
    // Story CRUD
    public long addStory(Story s, byte[] img){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues v=new ContentValues();
        v.put("title",s.getTitle());
        v.put("image",img);
        v.put("content",s.getContent());
        v.put("date",s.getDate());
        v.put("auteur_id",s.getAuteurId());
        v.put("pays_id",s.getPaysId());
        v.put("categorie_id",s.getCategorieId());
        long id=db.insert("Histoire",null,v);
        return id;
    }
    public List<Story> getStories(String filter){
        List<Story> l=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        String q="SELECT * FROM Histoire";
        if(filter!=null) q+=" WHERE "+filter;
        Cursor c=db.rawQuery(q,null);
        while(c.moveToNext()){
            Story s=new Story();
            s.setId(c.getInt(0));s.setTitle(c.getString(1));s.setContent(c.getString(3));s.setDate(c.getString(4));
            s.setAuteurId(c.getInt(5));s.setPaysId(c.getInt(6));s.setCategorieId(c.getInt(7));
            l.add(s);
        }
        c.close();
        return l;
    }
    public void updateStory(Story s, byte[] img){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues v=new ContentValues();
        v.put("title",s.getTitle());v.put("image",img);v.put("content",s.getContent());v.put("date",s.getDate());
        v.put("auteur_id",s.getAuteurId());v.put("pays_id",s.getPaysId());v.put("categorie_id",s.getCategorieId());
        db.update("Histoire",v,"id=?",new String[]{String.valueOf(s.getId())});
    }
    public void deleteStories(int[] ids){
        SQLiteDatabase db=getWritableDatabase();
        for(int id:ids) db.delete("Histoire","id=?",new String[]{String.valueOf(id)});
    }
    // Generic for Auteur, Pays, Categorie
    public long addAuteur(Auteur a){
        SQLiteDatabase db=getWritableDatabase();ContentValues v=new ContentValues();v.put("name",a.getName());v.put("date",a.getDate());return db.insert("Auteur",null,v);
    }
    public List<Auteur> getAuteurs(){
        List<Auteur> l=new ArrayList<>();SQLiteDatabase db=getReadableDatabase();Cursor c=db.rawQuery("SELECT * FROM Auteur",null);
        while(c.moveToNext()){Auteur a=new Auteur();a.setId(c.getInt(0));a.setName(c.getString(1));a.setDate(c.getString(2));l.add(a);}
        c.close();return l;
    }
    public void deleteAuteur(int id){getWritableDatabase().delete("Auteur","id=?",new String[]{String.valueOf(id)});}
    public long addPays(Pays p){SQLiteDatabase db=getWritableDatabase();ContentValues v=new ContentValues();v.put("name",p.getName());v.put("flag",p.getFlag());return db.insert("Pays",null,v);}
    public List<Pays> getPays(){List<Pays> l=new ArrayList<>();SQLiteDatabase db=getReadableDatabase();Cursor c=db.rawQuery("SELECT * FROM Pays",null);
        while(c.moveToNext()){Pays p=new Pays();p.setId(c.getInt(0));p.setName(c.getString(1));p.setFlag(c.getBlob(2));l.add(p);}c.close();return l;}
    public void deletePays(int id){getWritableDatabase().delete("Pays","id=?",new String[]{String.valueOf(id)});}
    public long addCategorie(Categorie c){SQLiteDatabase db=getWritableDatabase();ContentValues v=new ContentValues();v.put("name",c.getName());v.put("image",c.getImage());return db.insert("Categorie",null,v);}
    public List<Categorie> getCategories(){List<Categorie> l=new ArrayList<>();SQLiteDatabase db=getReadableDatabase();Cursor c=db.rawQuery("SELECT * FROM Categorie",null);
        while(c.moveToNext()){Categorie ct=new Categorie();ct.setId(c.getInt(0));ct.setName(c.getString(1));ct.setImage(c.getBlob(2));l.add(ct);}c.close();return l;}
    public void deleteCategorie(int id){getWritableDatabase().delete("Categorie","id=?",new String[]{String.valueOf(id)});}
}