package com.example.tea

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainLogin : AppCompatActivity() {

    lateinit var tv_move:TextView
    lateinit var ed_uname: EditText
    lateinit var ed_upass: EditText
    lateinit var bt_login:Button

    lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)

        tv_move=findViewById(R.id.movetxt)
        ed_uname=findViewById(R.id.username)
        ed_upass=findViewById(R.id.userpassword)
        bt_login=findViewById(R.id.loginbtn)

        bt_login.setOnClickListener {

            val name=ed_uname.text.toString()
            var pass=ed_upass.text.toString()

            if (name.isEmpty() && pass.isEmpty())
            {
                Toast.makeText(applicationContext,"Please fill the field...",Toast.LENGTH_LONG).show()
            }
            else
            {
                databaseReference=FirebaseDatabase.getInstance().getReference("Tea").child("EmployeeDetails")

                databaseReference.get().addOnSuccessListener {

                    if (it.child(pass).exists())
                    {
                        val names=it.child(pass).child("name").value.toString()
                            if(names.equals(name)) {

                                var sp=getSharedPreferences("koilraj",Context.MODE_PRIVATE)
                                var editor:SharedPreferences.Editor=sp.edit()
                                editor.putString("name",name)
                                editor.putString("pass",pass)
                                editor.commit()

                                Toast.makeText(applicationContext, "valid user...", Toast.LENGTH_LONG).show()

                                var ik=Intent(applicationContext,HomePage::class.java)
                                startActivity(ik)
                            }

                        else
                        {
                            Toast.makeText(applicationContext,"return null value...",Toast.LENGTH_LONG).show()
                        }

                    }
                    else
                    {
                        Toast.makeText(applicationContext,"Invalid user...",Toast.LENGTH_LONG).show()
                        Thread.sleep(1000)
                        Toast.makeText(applicationContext,"Please enter the valid user details...",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        tv_move.setOnClickListener {

            val ik=Intent(applicationContext,RegisterPage::class.java)
            startActivity(ik)
        }
    }
}