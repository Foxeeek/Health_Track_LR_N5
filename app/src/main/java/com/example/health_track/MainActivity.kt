package com.example.health_track

import android.annotation.SuppressLint
import android.graphics.Color
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

    }

    private fun updateDashboard(){
        val totalVeight = aktivs.map { it.amount }.sum()
//        val lostKkal = aktivs.filter { it.amount < 0}.map { it.amount }.sum()
//        val takeKkal = aktivs.filter { it.amount > 0}.map { it.amount }.sum()
        val Kkallost :TextView = findViewById(R.id.kkal_free) as TextView
        val KkallostText :TextView = findViewById(R.id.kkal_counter) as TextView

//        val totalKkal = lostKkal + takeKkal
        if (totalVeight < 0){
            Kkallost.setTextColor(Color.GREEN)
            KkallostText.text = "Потрачено калорий"
        }else{
            Kkallost.setTextColor(Color.RED)
            KkallostText.text = "Набрано калорий"
        }
//        Kkallost.text = "%.2f Ккал".format(totalVeight)
        Kkallost.text = "%.2f Ккал".format(Math.abs(totalVeight  ))

    }

}