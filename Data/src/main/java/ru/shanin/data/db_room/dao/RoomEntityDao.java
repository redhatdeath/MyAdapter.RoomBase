package ru.shanin.data.db_room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import ru.shanin.data.db_room.entity.RoomEntity;

@Dao
public interface RoomEntityDao {
    // SELECT ALL
    @Query("SELECT * FROM Peoples ORDER BY Last_name,First_name,Second_name ASC")
    List<RoomEntity> RoomEntityGetAll();

    // SELECT ONE BY ID
    @Query("SELECT * FROM Peoples WHERE People_id = :id")
    RoomEntity RoomEntityGetById(String id);

    // INSERT NEW RECORD
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void RoomEntityAddNew(RoomEntity roomPeople);

    // DELETE RECORD BY ID
    @Query("DELETE FROM Peoples WHERE People_id = :id")
    void RoomEntityDeleteById(String id);


}
