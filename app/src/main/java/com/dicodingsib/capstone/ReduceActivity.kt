package com.dicodingsib.capstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class ReduceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reduce)

        val btnNext: ImageButton = findViewById(R.id.btnNext)
        val btnHome: ImageButton = findViewById(R.id.btnHome)
        btnNext.setOnClickListener{
            val intent = Intent(this, ReuseActivity::class.java)
            startActivity(intent)
        }
        btnHome.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}