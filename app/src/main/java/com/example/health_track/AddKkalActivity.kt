package com.example.health_track

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddKkalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kkal)

        var addActiButton : Button = findViewById(R.id.addActiv)
        var CloseButton : ImageButton = findViewById(R.id.CloBtn)
        var rootView : ConstraintLayout = findViewById(R.id.rootview)

        var labelLayout : TextInputLayout = findViewById(R.id.labelLayout)
        var labelInput : TextInputEditText = findViewById(R.id.labelInput)

        var KkalLayout : TextInputLayout = findViewById(R.id.KkallLayout)
        var KkalInput : TextInputEditText = findViewById(R.id.KkalInput)

        var descriptionLayout : TextInputLayout = findViewById(R.id.DescriptionLabel)
        var descriptionInput : TextInputEditText = findViewById(R.id.DescriptionInput)




        rootView.setOnClickListener{
            this.window.decorView.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }

        labelInput.addTextChangedListener {
            if (it!!.count() > 0 )
                labelLayout.error = null
        }

        KkalInput.addTextChangedListener {
            if (it!!.count() > 0 )
                KkalLayout.error = null
        }

        addActiButton.setOnClickListener{

            val label = labelInput.text.toString()
            val description = descriptionInput.text.toString()
            val Kkal = KkalInput.text.toString().toDoubleOrNull()

            if (label.isEmpty()) {
                labelLayout.error = "Пожалуйста , введите настоящие значение"
            } else if (description.isEmpty()){
                descriptionLayout.error = "Пожалуйста , введите настоящие значение"
            }
            else if (Kkal == null) {
                KkalLayout.error = "Пожалуйста , введите настоящие значение"
            } else {
                val aktiv = Aktiv(0,label,Kkal,description)
                insert(aktiv)
            }
        }

        CloseButton.setOnClickListener {
            finish()
        }
    }
    private fun insert(aktiv: Aktiv){
    val db = Room.databaseBuilder(this,AppDatabase::class.java,"aktiv").build()
        GlobalScope.launch {
            db.aktivDao().insertAll(aktiv)
            finish()
        }
    }

}