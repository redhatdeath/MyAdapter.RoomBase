package ru.shanin.data.db_room.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ru.shanin.data.db_room.dao.RoomEntityDao;
import ru.shanin.data.db_room.entity.RoomEntity;

@Database(entities = {RoomEntity.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    private static LocalDatabase instance;

    public abstract RoomEntityDao roomEntityDao();

    public static LocalDatabase getInstance(Context context) {
        LocalDatabase tempInstance = instance;
        if (tempInstance != null)
            return tempInstance;
        else tempInstance = Room
                .databaseBuilder(
                        context,
                        LocalDatabase.class,
                        LocalDatabase.class.getSimpleName()
                )
                .build();
        instance = tempInstance;
        return tempInstance;
    }
}