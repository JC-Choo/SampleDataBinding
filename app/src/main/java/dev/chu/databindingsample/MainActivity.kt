package dev.chu.databindingsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import dev.chu.databindingsample.text_input_layout.TextInputLayoutActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.bt00).setOnClickListener {
            startActivity(Intent(this, TextInputLayoutActivity::class.java))
        }
    }
}