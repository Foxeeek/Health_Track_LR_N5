package com.example.health_track

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddKkalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_kkal)

        var addActiButton : Button = findViewById(R.id.addActiv)
        var CloseButton : ImageButton = findViewById(R.id.CloBtn)

        var labelLayout : TextInputLayout = findViewById(R.id.labelLayout)
        var labelInput : TextInputEditText = findViewById(R.id.labelInput)

        var KkalLayout : TextInputLayout = findViewById(R.id.KkallLayout)
        var KkalInput : TextInputEditText = findViewById(R.id.KkalInput)

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
            val Kkal = KkalInput.text.toString().toDoubleOrNull()

            if (label.isEmpty()) {
                labelLayout.error = "Пожалуйста , введите настоящие значение"
            }
            if (Kkal == null) {
                KkalLayout.error = "Пожалуйста , введите настоящие значение"
            }
        }

        CloseButton.setOnClickListener {
            finish()
        }
    }

}