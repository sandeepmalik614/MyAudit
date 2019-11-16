package com.myaudit.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myaudit.database.user.User;

@Dao
public interface UserDao {

    @Insert
    void addUser(User user);

    @Query("Select * from UserTable")
    User getUserInfo();

    @Query("UPDATE UserTable SET Name=:name")
    void updateUserName(String name);

    @Query("UPDATE UserTable SET Pin=:pin")
    void updateUserPin(String pin);

    @Query("UPDATE UserTable SET NickName=:answer")
    void updateUserNickName(String answer);
}
