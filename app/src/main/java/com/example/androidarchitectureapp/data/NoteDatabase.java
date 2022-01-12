package com.example.androidarchitectureapp.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    /**
     * we create this instance as we want this class to be singleton
     * means we don't want this  class to create multiple instances instead we use the same instance
     * everywhere.
     */
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    /**
     * We create our  new single Database instance  and we cut this method from the outside and synchronized means
     * only one thread at a time can access this method, this way you don't accidentally create two instances of this
     * class where two different thread try to access this method.
     *
     * @param context
     * @return
     */
    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            //here we don't use new keyword to create the new instance as this class is
            // singleton so instead we use Room.databaseBuilder();
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class,
                    "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();

        }
        return instance;
    }

    /***
     * We create this co we can populate some data before.
     */
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            noteDao.insert(new Note("Title 1", "Description 1", 1));
            noteDao.insert(new Note("Title 2", "Description 2", 2));
            noteDao.insert(new Note("Title 3", "Description 3", 3));
            return null;
        }
    }
}