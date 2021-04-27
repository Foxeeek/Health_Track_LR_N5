package com.example.health_track

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var aktivs : ArrayList<Aktiv>
    private lateinit var aktivsAdapter: AktivAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED)

        val recyclerView = findViewById<RecyclerView>(R.id.rec_activ)
        val plusBtn : FloatingActionButton = findViewById(R.id.plusBtn)


        aktivs = arrayListOf(
                Aktiv("Пробежка",-250.00),
                Aktiv("Курица",300.00),
                Aktiv("Курица",300.00),
                Aktiv("Курица",300.00),
                Aktiv("Курица",300.00),
                Aktiv("Турник",-500.00),
                Aktiv("Турник",-500.00),
                Aktiv("Турник",-500.00),
                Aktiv("Турник",-500.00),
                Aktiv("Пробежка",-250.00),
                Aktiv("Хотьба",-50.00)
        )

        aktivsAdapter = AktivAdapter(aktivs)
        linearlayoutManager = LinearLayoutManager(this)

        recyclerView.apply {
            adapter = aktivsAdapter
            layoutManager = linearlayoutManager

        }

        updateDashboard()

        plusBtn.setOnClickListener {
            val intent = Intent(this, AddKkalActivity::class.java )
            startActivity(intent)
        }

    }

    private fun updateDashboard(){
        val totalVeight = aktivs.map { it.amount }.sum()

        val Kkallost :TextView = findViewById(R.id.kkal_free) as TextView
        val KkallostText :TextView = findViewById(R.id.kkal_counter) as TextView

        if (totalVeight < 0){
            Kkallost.setTextColor(Color.GREEN)
            KkallostText.text = "Потрачено калорий"
        }else{
            Kkallost.setTextColor(Color.RED)
            KkallostText.text = "Набрано калорий"
        }

        Kkallost.text = "%.2f Ккал".format(Math.abs(totalVeight  ))

    }

}