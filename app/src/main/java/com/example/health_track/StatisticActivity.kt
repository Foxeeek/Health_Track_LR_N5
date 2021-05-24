package com.example.health_track

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.BarGraphSeries
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries

class StatisticActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED)

        val MyXArray = arrayOf(1,2,3,4,5,6,7)
        val MyYArray  = arrayOf(2,8,6,7,5,7,12)
        val LinearGraph : GraphView = findViewById(R.id.LinearGraph)
        val BarGraph : GraphView = findViewById(R.id.BarGraph)
        var Linearseries : LineGraphSeries<DataPoint> = LineGraphSeries<DataPoint>()
        var Barseries : BarGraphSeries<DataPoint> = BarGraphSeries<DataPoint>()

        val nav_view : NavigationView = findViewById(R.id.nav_view_Statistik)
        nav_view.setNavigationItemSelectedListener (this)

        var x : Int
        var y : Double
        LinearGraph.title = "Linear Graph"
        for (i in 0..6){
            x = MyXArray[i]
            var temp : Double = x.toDouble()
            y = Math.cos(temp)

            Linearseries.appendData(DataPoint(x.toDouble(),y.toDouble()),true,5000)
        }

        LinearGraph.addSeries(Linearseries)

        BarGraph.title = "Bar Graph"
        for (i in 0..6){
            x = MyXArray[i]
            var temp : Double = x.toDouble()
            y = Math.cos(temp)

            Barseries.appendData(DataPoint(x.toDouble(),y.toDouble()),true,5000)
        }

        BarGraph.addSeries(Barseries)

    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
//
            R.id.Main_page_menu ->  {
                finish()
                startActivity(Intent(this, MainActivity::class.java ))
            }
            R.id.IBV_page_menu ->  Toast.makeText(this,"Скоро будет ))" , Toast.LENGTH_SHORT).show()
            R.id.Setting_page_menu ->  Toast.makeText(this,"Скоро будет ))" , Toast.LENGTH_SHORT).show()
        }
        findViewById<DrawerLayout>(R.id.coordinatorStat).closeDrawer(GravityCompat.START)

        return true
    }
}