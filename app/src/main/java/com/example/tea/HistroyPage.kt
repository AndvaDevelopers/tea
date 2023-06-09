package com.example.tea

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HistroyPage : AppCompatActivity() {

    lateinit var hislist:ListView

    var sets= mutableSetOf<String>()
    lateinit var df:DatabaseReference

    lateinit var pass:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histroy_page)

        hislist=findViewById(R.id.hislist)

        var sharedPref = getSharedPreferences("koilraj", Context.MODE_PRIVATE)
        pass = sharedPref.getString("pass", "").toString()

        df=FirebaseDatabase.getInstance().getReference("Tea").child("SnackDetails").child(pass)

        df.addValueEventListener(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists())
                {
                for (snap : DataSnapshot in snapshot.children)
                {
                    var s=snap.child("date").value.toString()

                    sets.add(s);
                }
                    var aa=sets.toTypedArray();

                    var adap=ArrayAdapter(applicationContext, R.layout.listdesign,R.id.designtxt,aa)
                    hislist.adapter=adap
                }
                else
                {
                    Toast.makeText(applicationContext,"No data available...",Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
        hislist.setOnItemClickListener { parent, view, position, id ->

            var d=sets.elementAt(position)
            var mrngitem:String
            var mrngamt:String
            var mrngpaidamt:String
            var evngitem:String
            var evngamt:String
            var evngpaidamt:String
            var mrng:String=""
            var evng:String=""

            Toast.makeText(applicationContext,d,Toast.LENGTH_LONG).show()

            var df1:DatabaseReference=FirebaseDatabase.getInstance().getReference("Tea").child("SnackDetailsDate").child(pass).child(d)

            df1.addValueEventListener(object :ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.child("Morning").exists())
                    {
                        mrngitem=snapshot.child("Morning").child("items").value.toString()
                        mrngamt=snapshot.child("Morning").child("purchaseAmount").value.toString()
                        mrngpaidamt=snapshot.child("Morning").child("paidAmount").value.toString()

                         mrng="Morning : \nItem : "+mrngitem+"\nAmount : "+mrngamt+"rs\nPaid : "+mrngpaidamt+"rs"
                        Toast.makeText(applicationContext,mrng,Toast.LENGTH_LONG).show()


                    }
                    else
                    {
                        mrng="Morning : \nItem : -\nAmount : 0rs\nPaid : 0rs"
                        Toast.makeText(applicationContext,mrng,Toast.LENGTH_LONG).show()

                    }

                    if (snapshot.child("Evening").exists())
                    {
                        evngitem=snapshot.child("Evening").child("items").value.toString()
                        evngamt=snapshot.child("Evening").child("purchaseAmount").value.toString()
                        evngpaidamt=snapshot.child("Evening").child("paidAmount").value.toString()

                        evng="Morning : \nItem : "+evngitem+"\nAmount : "+evngamt+"rs\nPaid : "+evngpaidamt+"rs"

                    }
                    else
                    {
                        evng="Morning : \nItem : -\nAmount : 0rs\nPaid : 0rs"

                    }
                    var builder= AlertDialog.Builder(applicationContext)
                    builder.setTitle("Your Information!!!").setMessage(mrng)
                        .setIcon(R.drawable.ic_launcher_background)
                        .setCancelable(true)

                    builder.setPositiveButton("Done"){ dialoginterface,which->

                    }
                    builder.show()
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })


        }
    }
}