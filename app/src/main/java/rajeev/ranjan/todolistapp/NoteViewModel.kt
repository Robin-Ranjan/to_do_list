package rajeev.ranjan.todolistapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepo: NoteRepo = NoteRepo(application)
    private val noteList: LiveData<List<Note>> = noteRepo.getAllData()

    fun insert(note: Note) {
        noteRepo.insertData(note)
    }

    fun update(note: Note) {
        noteRepo.updateData(note)
    }

    fun delete(note: Note) {
        noteRepo.deleteData(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return noteList
    }
}
