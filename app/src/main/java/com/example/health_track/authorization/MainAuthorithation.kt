package com.example.health_track.authorithation

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.health_track.MainActivity
import com.example.health_track.R

class MainAuthorithation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_authorithation)

        val noSignIn : TextView = findViewById(R.id.noSignIn)


        noSignIn.setOnClickListener{
            finish()
            val intent : Intent = Intent(this, MainActivity::class.java)
            intent.putExtra("logIn",1)
            startActivity(intent)

        }


    }
}