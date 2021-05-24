package com.example.health_track.authorithation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.health_track.MainActivity
import com.example.health_track.R
import com.example.health_track.authorization.RegisterActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainAuthorithation : AppCompatActivity() {


    private lateinit var auth : FirebaseAuth
    lateinit var User : FirebaseUser
    val TAG = "RegisterActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_authorithation)
       ImIn()

        val RootView : ConstraintLayout = findViewById(R.id.screenmainauth)

        RootView.setOnClickListener{
            this.window.decorView.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }




        val enterBtn : Button = findViewById(R.id.InButton)
        val Login : EditText = findViewById(R.id.LoginInput)
        val Password : EditText = findViewById(R.id.PassInput)


        enterBtn.setOnClickListener {

            val tempPass = Password.text.toString()
            val tempLogin = Login.text.toString()

            if (tempLogin.isEmpty()){
                Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show()
            }else if (tempPass.isEmpty()){
                Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show()
            }else{
                LogInEmail(tempLogin, tempPass)
            }

        }





        val noSignIn : TextView = findViewById(R.id.noSignIn)
        val RegisterButton : Button = findViewById(R.id.registerButton)

        RegisterButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }

        noSignIn.setOnClickListener{
            AnonimAutori()
        }


    }


    private fun LogInEmail(email: String, password: String){
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    ImIn()
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Не правельный логин или пароль",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
            }
    }
    private fun ImIn(){
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null ){
            finish()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun AnonimAutori(){
        auth = Firebase.auth
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInAnonymously:success")
                    val user = auth.currentUser
                    ImIn()
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInAnonymously:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
//                    updateUI(null)
                }
            }
    }
}