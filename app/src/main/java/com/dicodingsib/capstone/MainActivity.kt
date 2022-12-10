 package com.dicodingsib.capstone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

 class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnReduce: Button = findViewById(R.id.btnReduce)
        btnReduce.setOnClickListener(this)

        val btnReuse: Button = findViewById(R.id.btnReuse)
        btnReuse.setOnClickListener(this)

        val btnRecycle: Button = findViewById(R.id.btnRecycle)
        btnRecycle.setOnClickListener(this)


    }

     override fun onClick(v: View?){
         when (v?.id) {
             R.id.btnRecycle -> {
                 val moveRecycle = Intent(this@MainActivity, RecycleActivity::class.java )
                 startActivity(moveRecycle)

             }

             R.id.btnReduce -> {
                 val moveReduce = Intent(this@MainActivity, ReduceActivity::class.java )
                 startActivity(moveReduce)

             }

             R.id.btnReuse -> {
                 val moveReuse = Intent(this@MainActivity, ReuseActivity::class.java )
                 startActivity(moveReuse)

             }

         }
     }
}