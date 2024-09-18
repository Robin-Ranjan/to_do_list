package rajeev.ranjan.todolistapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import rajeev.ranjan.todolistapp.databinding.ActivityDataInsertBinding

class DataInsert : AppCompatActivity() {
    private lateinit var binding: ActivityDataInsertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val type = intent.getStringExtra("type")

        if (type == "update") {
            binding.DIToolbar.title = "Update Note"
            binding.editTitle.setText(intent.getStringExtra("title"))
            binding.editDiscription.setText(intent.getStringExtra("disp"))
            val id = intent.getIntExtra("id", 0)
            binding.add.text = "Update Note"

            binding.add.setOnClickListener {
                val resultIntent = Intent().apply {
                    putExtra("title", binding.editTitle.text.toString())
                    putExtra("disp", binding.editDiscription.text.toString())
                    putExtra("id", id)
                }
                setResult(RESULT_OK, resultIntent)
                finish()
            }

        } else {
            binding.DIToolbar.title = "Add Note"
            binding.add.text = "Add Note"

            binding.add.setOnClickListener {
                if (binding.editTitle.text.toString().isEmpty() || binding.editDiscription.text.toString().isEmpty()) {
                    Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
                } else {
                    val resultIntent = Intent().apply {
                        putExtra("title", binding.editTitle.text.toString())
                        putExtra("disp", binding.editDiscription.text.toString())
                    }
                    setResult(RESULT_OK, resultIntent)
                    finish()
                }
            }
        }
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this@DataInsert, MainActivity::class.java))
    }
}
