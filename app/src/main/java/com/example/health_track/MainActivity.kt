package com.example.health_track

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var deletedAktiv: Aktiv
    private lateinit var aktivs : List<Aktiv>
    private lateinit var oldaktivs : List<Aktiv>
    private lateinit var aktivsAdapter: AktivAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager
    private lateinit var db : AppDatabase

    var MyHeight : Int = 0
    var MyWeight : Int = 0
    var TargetOfTheDay : Int = 0
    var pref : SharedPreferences? = null
//    private lateinit var body : BodyPropertyActivity


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED)


        pref = getSharedPreferences("Body", MODE_PRIVATE)

        MyHeight = pref?.getInt("height",0)!!
        MyWeight = pref?.getInt("weight",0)!!
        TargetOfTheDay = pref?.getInt("target",0)!!



        val HeighttButton : LinearLayout = findViewById(R.id.MyHeight)
        val TargetOfDayButton : LinearLayout = findViewById(R.id.kkal_target_counter_total)
        val WeighttButton : LinearLayout = findViewById(R.id.Total_Current_weight)
        val recyclerView = findViewById<RecyclerView>(R.id.rec_activ)
        val plusBtn : FloatingActionButton = findViewById(R.id.plusBtn)

        updateData()

        WeighttButton.setOnClickListener{
            showdialogWeight()
        }
        HeighttButton.setOnClickListener{
            showdialogHeight()
        }
        TargetOfDayButton.setOnClickListener {
            ChangeTarget()
        }


        db = Room.databaseBuilder(this,AppDatabase::class.java,"aktiv").build()
//Прослушка нажатий меню
        val nav_view : NavigationView = findViewById(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener (this)
//---------------------------
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

//    Відновлення елементів списку
    private fun undoDelete(){
        GlobalScope.launch {
            db.aktivDao().insertAll(deletedAktiv)

            aktivs = oldaktivs
            runOnUiThread {
                aktivsAdapter.setData(aktivs)
                updateDashboard()
            }
        }
    }
    private fun showSackbar(){
    val view  = findViewById<View>(R.id.coordinator)
        val snackbar = Snackbar.make(view,"Элемент удалён!",Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo"){
            undoDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this,R.color.red))
            .setTextColor(ContextCompat.getColor(this,R.color.white))
            .show()
    }




//    Видалення елементів з списку
    private fun deleteAktiv(aktiv: Aktiv){
        deletedAktiv = aktiv
        oldaktivs = aktivs
        GlobalScope.launch {
            db.aktivDao().delete(aktiv)
            aktivs = aktivs.filter { it.id != aktiv.id }
            runOnUiThread {
                updateDashboard()
                aktivsAdapter.setData(aktivs)
                showSackbar()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }



//    Сайд бар меню переключателі
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.Statistic_page_menu ->  {
                finish()
                startActivity(Intent(this, StatisticActivity::class.java ))
            }
            R.id.IBV_page_menu ->  Toast.makeText(this,"Скоро будет ))" , Toast.LENGTH_SHORT).show()
            R.id.Setting_page_menu ->  Toast.makeText(this,"Скоро будет ))" , Toast.LENGTH_SHORT).show()
        }
        findViewById<DrawerLayout>(R.id.coordinator).closeDrawer(GravityCompat.START)

        return true
    }

    // Діалог вводду росту
    fun showdialogHeight(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Введите ваш рост")

// Set up the input
        val Height = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        Height.setHint("Ваш рост")
        Height.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(Height)

// Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
            try {
                var TempHeight : Int =  Height.text.toString().toInt()
                MyHeight = TempHeight
                saveDataHeight(TempHeight)

            } catch (e: NumberFormatException) {
                showdialogHeight()
            }


//            saveHeightData(Height.toString().toInt())

        })

        builder.show()
    }
//    Зміна цілі на день
fun ChangeTarget(){
    val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
    builder.setTitle("Введите вашу цель на день")

// Set up the input
    val target = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
    target.setHint("Ваша цель")
    target.inputType = InputType.TYPE_CLASS_NUMBER
    builder.setView(target)

// Set up the buttons
    builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
        // Here you get get input text from the Edittext
        try {
            var TempTarget : Int =  target.text.toString().toInt()
            TargetOfTheDay = TempTarget
            saveDataTarget(TempTarget)
        } catch (e: NumberFormatException) {
            showdialogHeight()
        }


    })

    builder.show()
}
// Діалог вводду ваги
    fun showdialogWeight(){
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Введите ваш вес")

// Set up the input
        val Weight = EditText(this)
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        Weight.setHint("Ваш вес")
        Weight.inputType = InputType.TYPE_CLASS_NUMBER
        builder.setView(Weight)

// Set up the buttons
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            // Here you get get input text from the Edittext
           try {
                var TempWeight : Int =  Weight.text.toString().toInt()
               MyWeight = TempWeight
                saveDataWeight(TempWeight)

            } catch (e: NumberFormatException) {
                showdialogHeight()
            }


        })

        builder.show()
    }




    fun saveDataHeight(Height: Int ){
        val editor = pref?.edit()
        editor?.putInt("height",Height)
        editor?.apply()

        updateData()
    }

    fun saveDataTarget(targett: Int ){
        val editor = pref?.edit()
        editor?.putInt("target",targett)
        editor?.apply()

        updateData()
    }

    fun saveDataWeight( Weight: Int){
        val editor = pref?.edit()
        editor?.putInt("weight",Weight)
        editor?.apply()

        updateData()
    }



    fun updateData(){
        val WeightCount : TextView = findViewById(R.id.Current_weight_count)
        val HeightCount : TextView = findViewById(R.id.MyHeightCount)
        val PerfectWeight : TextView = findViewById(R.id.Second_weight_Count)
        val TargetWeight : TextView = findViewById(R.id.target_num)
        val IMTLabel : TextView = findViewById(R.id.MyIMTCount)
        val targetLabel : TextView = findViewById(R.id.kkal_target_free)


        var perfectWeight : Double = (MyHeight.toDouble() * 4 / 2.54 - 128)*0.453
        var toPerfWeight : Double = perfectWeight - MyWeight.toDouble()
        var HeightInMetr : Double = MyHeight.toDouble() / 100
        var IMT : Double = MyWeight.toDouble() / (HeightInMetr * HeightInMetr )

        HeightCount.text = "$MyHeight"
        WeightCount.text = "${MyWeight}"
        PerfectWeight.text = "%.2f".format(perfectWeight)
        TargetWeight.text = "%.2f".format(toPerfWeight)
        IMTLabel.text = "%.2f".format(IMT)
        targetLabel.text = "${TargetOfTheDay} Ккал"


        if (IMT < 18.5 || IMT >= 25){
            IMTLabel.setTextColor(Color.RED)
        }else if( IMT >= 18.5 && IMT < 25) {
            IMTLabel.setTextColor(Color.GREEN)
        }

    }






}