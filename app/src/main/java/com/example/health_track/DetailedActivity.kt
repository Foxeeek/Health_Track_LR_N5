package com.example.health_track

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

class DetailedActivity : AppCompatActivity() {
    private lateinit var aktivity : Aktiv
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailed)

        aktivity = intent.getSerializableExtra("aktivity") as Aktiv


        var updActiButton : Button = findViewById(R.id.updateActiv)
        var CloseButton : ImageButton = findViewById(R.id.CloBtn)
        var rootView : ConstraintLayout = findViewById(R.id.rootView)

        var labelLayout : TextInputLayout = findViewById(R.id.labelLayout)
        var labelInput : TextInputEditText = findViewById(R.id.labelInput)

        var KkalLayout : TextInputLayout = findViewById(R.id.KkallLayout)
        var KkalInput : TextInputEditText = findViewById(R.id.KkalInput)

        var descriptionLayout : TextInputLayout = findViewById(R.id.DescriptionLabel)
        var descriptionInput : TextInputEditText = findViewById(R.id.DescriptionInput)


        labelInput.setText(aktivity.label)
        KkalInput.setText(aktivity.amount.toString())
        descriptionInput.setText(aktivity.description)

        rootView.setOnClickListener{
            this.window.decorView.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }


        labelInput.addTextChangedListener {
            updActiButton.visibility = View.VISIBLE
            if (it!!.count() > 0 )
                labelLayout.error = null
        }

        KkalInput.addTextChangedListener {
            updActiButton.visibility = View.VISIBLE
            if (it!!.count() > 0 )
                KkalLayout.error = null
        }
        descriptionInput.addTextChangedListener {
            updActiButton.visibility = View.VISIBLE
        }

        updActiButton.setOnClickListener{

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
                val aktiv = Aktiv(aktivity.id,label,Kkal,description)
                update(aktiv)
            }
        }

        CloseButton.setOnClickListener {
            finish()
        }
    }
    private fun update(aktiv: Aktiv){
        val db = Room.databaseBuilder(this,AppDatabase::class.java,"aktiv").build()
        GlobalScope.launch {
            db.aktivDao().update(aktiv)
            finish()
        }
    }

}