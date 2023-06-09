package com.example.tea

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class HomePage : AppCompatActivity() {

    lateinit var c_profile:CardView
    lateinit var c_history:CardView
    lateinit var c_snacks:CardView
    lateinit var c_logout:CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        c_profile=findViewById(R.id.profiles)
        c_history=findViewById(R.id.historys)
        c_snacks=findViewById(R.id.snacks)
        c_logout=findViewById(R.id.logouts)

        c_profile.setOnClickListener{

            var ik= Intent(applicationContext,UserProfile::class.java)
            startActivity(ik)
        }

        c_snacks.setOnClickListener{
            var ik= Intent(applicationContext,SnacksPage::class.java)
            startActivity(ik)
        }


        c_logout.setOnClickListener{
            var ik= Intent(applicationContext,LogoutPage::class.java)
            startActivity(ik)
        }

        c_history.setOnClickListener{
            var ik= Intent(applicationContext,HistroyPage::class.java)
            startActivity(ik)
        }
    }
}