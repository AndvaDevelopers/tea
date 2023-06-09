package com.example.tea

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tea.BeanClass.JavaRegister
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterPage : AppCompatActivity() {

    lateinit var ed_name:EditText
    lateinit var ed_pass:EditText
    lateinit var ed_number:EditText
    lateinit var ed_mail:EditText
    lateinit var ed_team:EditText

    lateinit var bt_create:Button

    lateinit var databaseref:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        ed_name=findViewById(R.id.name)
        ed_pass=findViewById(R.id.pass)
        ed_number=findViewById(R.id.number)
        ed_mail=findViewById(R.id.mail)
        ed_team=findViewById(R.id.team)

        bt_create=findViewById(R.id.createbtn)

        bt_create.setOnClickListener {

            val name=ed_name.text.toString()
            val pass=ed_pass.text.toString()
            val number=ed_number.text.toString()
            val mail=ed_mail.text.toString()
            val team=ed_team.text.toString()

            if(name.isEmpty() && pass.isEmpty() && number.isEmpty() && mail.isEmpty() && team.isEmpty())
            {
                Toast.makeText(applicationContext,"Please fill the field...",Toast.LENGTH_LONG).show()
            }
            else
            {
                databaseref=FirebaseDatabase.getInstance().getReference("Tea").child("EmployeeDetails")

                val javareg= JavaRegister(name,pass,number,mail, team)

                databaseref.child(pass).setValue(javareg).addOnSuccessListener {
                    Toast.makeText(applicationContext,"Successfully created...",Toast.LENGTH_LONG).show()

                    val ik=Intent(applicationContext,MainLogin::class.java)
                    startActivity(ik)
                    finish()
                }


            }
        }
    }
}