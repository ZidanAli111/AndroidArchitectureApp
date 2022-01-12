package com.example.androidarchitectureapp;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.androidarchitectureapp.data.Note;
import com.example.androidarchitectureapp.data.NoteDao;
import com.example.androidarchitectureapp.data.NoteDatabase;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {

        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();

        /***
         * here getAllNotes() is from the NoteDao class:-
         *  @Query("SELECT * FROM NOTE_TABLE ORDER BY priority DESC")
         *     LiveData<List<Note>> getAllNotes();
         */
        allNotes = noteDao.getAllNotes();
    }

    /***
     * All the below methods are for the different database operations
     * all the below methods have operations in NoteDao class.
     * @param note
     */
    public void inset(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);

    }

    public void update(Note note) {

        new UpdateNoteAsyncTask(noteDao).execute(note);

    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);

    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    /***
     * All the below AsyncTask are for the above Database operations method.
     * each AsyncTask is to perform respective database operation
     *
     */
    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            /***
             * this .insert() method is from NoteDao class
             */
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private UpdateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            /***
             * this .update() method is from NoteDao class
             */
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            /***
             * this .delete() method is from NoteDao class
             */
            noteDao.delete(notes[0]);
            return null;
        }

    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Note, Void, Void> {

        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            /***
             * this  .deleteAllNotes() method is from NoteDao class
             */
            noteDao.deleteAllNotes();
            return null;
        }
    }
}




