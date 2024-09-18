package rajeev.ranjan.todolistapp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_notes")
data class Note(
    var title: String,
    var disp: String,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
)
