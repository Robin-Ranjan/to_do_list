package rajeev.ranjan.todolistapp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    fun insert(note: Note?)
    @Update
    fun update(note: Note?)
    @Delete
    fun delete(note: Note?)


    @Query("SELECT * FROM my_notes")
    fun getAllData(): LiveData<List<Note>>
}