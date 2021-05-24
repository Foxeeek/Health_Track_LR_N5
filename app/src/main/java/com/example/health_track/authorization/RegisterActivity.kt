package com.example.health_track.authorization

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.health_track.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    lateinit var User : FirebaseUser
    val TAG = "RegisterActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val RootView : ConstraintLayout = findViewById(R.id.registerwindow)

        RootView.setOnClickListener{
            this.window.decorView.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken,0)
        }

        val RegisterEmail : EditText = findViewById(R.id.RegEmailInput)
        val RegisterPass : EditText = findViewById(R.id.RegPassInput)
        val RegisterButton : Button = findViewById(R.id.RegBtn)





        RegisterButton.setOnClickListener {

            val RegisteredEmail = RegisterEmail.text.toString()
            val RegisteredPass = RegisterPass.text.toString()


            if (RegisteredEmail.isEmpty()){
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show()
            }else if (RegisteredPass.isEmpty()){
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
            }else{
                loginSignUser(RegisteredEmail,RegisteredPass)
            }



        }




        val imageBtn : ImageButton = findViewById(R.id.previosPage)

        imageBtn.setOnClickListener{finish()}

    }

    private fun loginSignUser(email : String , password :String) {
        auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(this, "Регистрация успешна", Toast.LENGTH_SHORT).show()

                    //updateUI(user)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }
}