package com.example.financial_literacy_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val register = findViewById<Button>(R.id.register_btn)
        val login = findViewById<Button>(R.id.login_btn)
        val emailtxt1 = findViewById<EditText>(R.id.email_txt)
        val password_txt1 = findViewById<EditText>(R.id.password_txt)

        register.setOnClickListener {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            finish()
        }

        login.setOnClickListener {
                when {
                    TextUtils.isEmpty(emailtxt1.getText().toString().trim { it <= ' ' }) -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Please enter your email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    TextUtils.isEmpty(password_txt1.getText().toString().trim { it <= ' ' }) -> {
                        Toast.makeText(
                            this@MainActivity,
                            "Please enter your password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    else -> {

                        val email:String = emailtxt1.getText().toString().trim { it <= ' '}
                        val password: String = password_txt1.getText().toString().trim { it <= ' '}

                        //Create a user in firestore
                        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(
                                OnCompleteListener<AuthResult> { task ->
                                    //Register Successful
                                    if (task.isSuccessful){
                                        //Registered User
                                        val firebaseUser: FirebaseUser = task.result!!.user!!

                                        Toast.makeText(
                                            this@MainActivity,
                                            "Logged In Successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val intent =
                                            Intent(this@MainActivity, HomeActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                        intent.putExtra("email_id", email)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        //Register Unsuccessful
                                        Toast.makeText(
                                            this@MainActivity,
                                            task.exception!!.message.toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            )
                    }
                }
            }

    }
}