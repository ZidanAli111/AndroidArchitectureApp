package com.example.androidarchitectureapp.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class},version = 1)
public  abstract class NoteDatabase extends RoomDatabase {
    /**
     * we create this instance as we want this class to be singleton
     * means we don't want this  class to create multiple instances instead we use the same instance
     * everywhere.
     */
    private static NoteDatabase instance;

    public abstract  NoteDao noteDao();

    /**
     * We create our  new single Database instance  and we cut this method from the outside and synchronized means
     * only one thread at a time can access this method, this way you don't accidentally create two instances of this
     * class where two different thread try to access this method.
     * @param context
     * @return
     */
    public  static synchronized  NoteDatabase getInstance(Context context)
    {
        if (instance==null)
        {
            //here we don't use new keyword to create the new instance as this class is
            // singleton so instead we use Room.databaseBuilder();
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return instance;
    }
}