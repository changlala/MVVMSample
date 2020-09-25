package com.chang.mvvmsample.model.database;

import android.content.Context;

import com.chang.mvvmsample.model.dao.UserDao;
import com.chang.mvvmsample.model.entity.User;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 1,entities = User.class)
public abstract class AppDB extends RoomDatabase {

    public abstract UserDao getUserDao();

    private static AppDB mAppDB;

    public static AppDB getInstance(Context context){

        if(mAppDB == null){
            mAppDB = Room.databaseBuilder(context,AppDB.class,"database").build();
        }
        return mAppDB;
    }

}
