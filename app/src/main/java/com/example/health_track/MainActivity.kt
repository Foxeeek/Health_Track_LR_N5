package com.example.health_track

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var deletedAktiv: Aktiv
    private lateinit var aktivs : List<Aktiv>
    private lateinit var oldaktivs : List<Aktiv>
    private lateinit var aktivsAdapter: AktivAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager
    private lateinit var db : AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED)

        val recyclerView = findViewById<RecyclerView>(R.id.rec_activ)
        val plusBtn : FloatingActionButton = findViewById(R.id.plusBtn)

        db = Room.databaseBuilder(this,AppDatabase::class.java,"aktiv").build()


        aktivs = arrayListOf()

        aktivsAdapter = AktivAdapter(aktivs)
        linearlayoutManager = LinearLayoutManager(this)

        recyclerView.apply {
            adapter = aktivsAdapter
            layoutManager = linearlayoutManager

        }

//        swipe to remove
        val itemTochHelper = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
               deleteAktiv(aktivs[viewHolder.adapterPosition])
            }

        }

        val swipeHelper = ItemTouchHelper(itemTochHelper)
        swipeHelper.attachToRecyclerView(recyclerView)

        plusBtn.setOnClickListener {
            val intent = Intent(this, AddKkalActivity::class.java )
            startActivity(intent)
        }

    }
    private fun fetchAll(){
        GlobalScope.launch {
            aktivs = db.aktivDao().getAll()

            runOnUiThread {
                updateDashboard()
                aktivsAdapter.setData(aktivs)
            }
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

    private fun deleteAktiv(aktiv: Aktiv){
        deletedAktiv = aktiv
        oldaktivs = aktivs

        GlobalScope.launch {
            db.aktivDao().delete(aktiv)

            aktivs = aktivs.filter { it.id != aktiv.id }

            runOnUiThread {
                updateDashboard()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        fetchAll()
    }

}