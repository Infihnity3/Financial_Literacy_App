package com.example.financial_literacy_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val logout_btn = findViewById<Button>(R.id.logout_btn)
        val home_user_id = findViewById<TextView>(R.id.home_user_id)
        val home_email_id = findViewById<TextView>(R.id.home_email_id)
        val userID = intent.getStringExtra("user_id")
        val emailID = intent.getStringExtra("email_id")

        home_user_id.apply { text="User ID:\n $userID" }
        home_email_id.apply { text="Email:\n $emailID" }
        //Logout from app
        logout_btn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
            finish()
        }
    }
}