package com.chang.mvvmsample.model.dao;

import com.chang.mvvmsample.model.entity.User;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {


    @Insert
    Long insertUser(User user);

    @Query("select * from User where id=:iid")
    User getUserById(long iid);

    @Query("select * from User ")
    List<User> getAllUser();


}
