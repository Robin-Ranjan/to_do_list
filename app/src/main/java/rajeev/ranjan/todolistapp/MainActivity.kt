package rajeev.ranjan.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rajeev.ranjan.todolistapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteViewModel: NoteViewModel

    private lateinit var addNoteLauncher: ActivityResultLauncher<Intent>
    private lateinit var updateNoteLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = "To Do Lists"

        noteViewModel = ViewModelProvider(this, NoteViewModelFactory(application)).get(NoteViewModel::class.java)

        // Register the launcher for adding a note
        addNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == RESULT_OK && data != null) {
                val title = data.getStringExtra("title") ?: ""
                val disp = data.getStringExtra("disp") ?: ""
                val note = Note(title, disp)
                noteViewModel.insert(note)
                Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
            }
        }

        // Register the launcher for updating a note
        updateNoteLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            if (result.resultCode == RESULT_OK && data != null) {
                val title = data.getStringExtra("title") ?: ""
                val disp = data.getStringExtra("disp") ?: ""
                val note = Note(title, disp).apply {
                    id = data.getIntExtra("id", 0)
                }
                noteViewModel.update(note)
                Toast.makeText(this, "Updated...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, DataInsert::class.java)
            intent.putExtra("type", "addMode")
            addNoteLauncher.launch(intent)
        }

        binding.recycle.layoutManager = LinearLayoutManager(this)
        binding.recycle.setHasFixedSize(true)

        val adapter = RVAdapter()
        binding.recycle.adapter = adapter

        noteViewModel.getAllNotes().observe(this) { notes ->
            adapter.submitList(notes)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val note = adapter.getNoteAt(viewHolder.adapterPosition)
                if (direction == ItemTouchHelper.RIGHT) {
                    noteViewModel.delete(note)
                    Toast.makeText(this@MainActivity, "Deleted...", Toast.LENGTH_SHORT).show()
                } else {
                    val intent = Intent(this@MainActivity, DataInsert::class.java).apply {
                        putExtra("title", note.title)
                        putExtra("disp", note.disp)
                        putExtra("id", note.id)
                        putExtra("type", "update")
                    }
                    updateNoteLauncher.launch(intent)
                }
            }
        }).attachToRecyclerView(binding.recycle)
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }
}
