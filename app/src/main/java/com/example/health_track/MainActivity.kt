package com.example.health_track

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var aktivs : ArrayList<Aktiv>
    private lateinit var aktivsAdapter: AktivAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.rec_activ)


        aktivs = arrayListOf(
                Aktiv("Пробежка",-250.00),
                Aktiv("Пица",250.00),
                Aktiv("Курица",250.00),
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

//        updateDashboard()

    }

//    @SuppressLint("WrongViewCast")
//    private fun updateDashboard(){
//        val totalVeight = aktivs.map { it.amount }.sum()
//        val lostKkal = aktivs.filter { it.amount < 0 }.map { it.amount }.sum()
//        val Kkallost = findViewById<TextView>(R.id.kkal_free)
//
//        Kkallost.text = "%2.f Ккал".format(lostKkal)
//    }

}