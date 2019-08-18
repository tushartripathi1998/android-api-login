package com.example.nextstepsession;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    public void addUser(Users user);

    @Query("select * from users")
    public List<Users> getUsers();

    @Delete
    public void deleteUser(Users user);
}
