package com.example.tea

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class SnacksPage : AppCompatActivity() {

    lateinit var choosedate: TextView
    lateinit var showdate: TextView
    var radioGroup: RadioGroup? = null
    lateinit var rb_mrng: RadioButton
    lateinit var rb_evng:RadioButton
    lateinit var ed_snackitams:EditText
    lateinit var ed_amount:EditText
    lateinit var ed_paidamount:EditText
    lateinit var bt_done:Button
    lateinit var bt_update:Button
    lateinit var tv_showinfo:TextView
    lateinit var tv_showsymbol:TextView
    lateinit var tv_showAmount:TextView

    var size = 0

    var pursum=0
    var paidsum=0

     var items: String="null"
    var purchaseAmount:String="0"
    lateinit var time:String
     lateinit var pass:String
    var date:String=""
    var paid_amount:String=""
    var childCount: Int=0

    lateinit var databaseReference:DatabaseReference
    lateinit var myReference: DatabaseReference

    var purlist=arrayListOf<Int>()
    var paidlist=arrayListOf<Int>()



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snacks_page)

        choosedate=findViewById(R.id.cal)
        showdate=findViewById(R.id.showdate)
        ed_snackitams=findViewById(R.id.snackitem)
        ed_amount=findViewById(R.id.amount)
        bt_done=findViewById(R.id.done)
        bt_update=findViewById(R.id.update)
        tv_showinfo=findViewById(R.id.showinfo)
        ed_paidamount=findViewById(R.id.paidamount)
       // rb_mrng=findViewById(R.id.mrng)
        //rb_evng=findViewById(R.id.evening)
        radioGroup = findViewById(R.id.radioGroup)
        tv_showsymbol=findViewById(R.id.symbol)
        tv_showAmount=findViewById(R.id.showamount)

        title = "Tea App"


        var sharedPref = getSharedPreferences("koilraj", Context.MODE_PRIVATE)

       pass = sharedPref.getString("pass", "").toString()

        val df=FirebaseDatabase.getInstance().getReference("Tea").child("SnackDetails").child(pass)

        df.addValueEventListener(object:ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (snap: DataSnapshot in snapshot.children) {
                        var s1: String = snap.child("purchaseAmount").value.toString()
                        var s2: String = snap.child("paidAmount").value.toString()

                        var i1 = Integer.parseInt(s1)
                        var i2 = Integer.parseInt(s2)



                        purlist.add(i1)
                        paidlist.add(i2)
                    }

                    for (num1 in purlist) {
                        pursum = pursum + num1
                    }
                    for (num2 in paidlist) {
                        paidsum = paidsum + num2
                    }

                    if (pursum > paidsum) {
                        tv_showsymbol.setText("-")
                        var sum = pursum - paidsum
                        tv_showAmount.setText("${sum}rs")
                    } else if (pursum < paidsum) {
                        tv_showsymbol.setText("+")
                        var sum = paidsum - pursum
                        tv_showAmount.setText("${sum}rs")
                    } else {
                        tv_showsymbol.setText("+")
                        var sum = paidsum - pursum
                        tv_showAmount.setText("${sum}rs")
                    }

                }
                else
                {
                    tv_showsymbol.setText("+")
                    tv_showAmount.setText("0rs")
                    Toast.makeText(applicationContext,"No data in your database",Toast.LENGTH_LONG).show()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        choosedate.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our edit text.
                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    showdate.setText(dat)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }

        bt_done.setOnClickListener {

            items=ed_snackitams.text.toString()
            purchaseAmount=ed_amount.text.toString()


            if (items.isNotBlank() && purchaseAmount.isNotBlank())
            {
                tv_showinfo.setText("Items : \n\t\t"+items+"\n"+"Amount : "+purchaseAmount+"rs")
                ed_paidamount.visibility= View.VISIBLE
            }
            else
            {
                Toast.makeText(applicationContext,"please fill the text",Toast.LENGTH_LONG).show()
            }
        }

        myReference = FirebaseDatabase.getInstance().getReference("Tea").child("SnackDetails").child(pass)

        myReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                size = dataSnapshot.childrenCount.toInt()
                Toast.makeText(applicationContext,"count : "+size,Toast.LENGTH_LONG).show()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

        bt_update.setOnClickListener {

            val intSelectButton: Int = radioGroup!!.checkedRadioButtonId
            rb_mrng = findViewById(intSelectButton)
            time=rb_mrng.text.toString()
            date=showdate.text.toString()
            paid_amount=ed_paidamount.text.toString()

            if (time.isEmpty())
            {
                Toast.makeText(applicationContext,"radio",Toast.LENGTH_LONG).show()
            }
            else if (date.equals("dd/mm/yyyy"))
            {
                Toast.makeText(applicationContext,"date",Toast.LENGTH_LONG).show()
            }
            else if (purchaseAmount.isEmpty())
            {
                Toast.makeText(applicationContext,"total amount",Toast.LENGTH_LONG).show()
            }
            else if (paid_amount.isEmpty())
            {
                Toast.makeText(applicationContext,"paid amount",Toast.LENGTH_LONG).show()
            }
            else if (items.isEmpty())
            {
                Toast.makeText(applicationContext,"items",Toast.LENGTH_LONG).show()
            }
            else if (pass.isEmpty())
            {
                Toast.makeText(applicationContext,"pass",Toast.LENGTH_LONG).show()
            }

            else {

                var databaseReference1=FirebaseDatabase.getInstance().getReference("Tea").child("SnackDetailsDate")

                databaseReference1.addValueEventListener(object : ValueEventListener
                {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        if (snapshot.child(pass).child(date).child(time).exists())
                        {

                        }
                        else
                        {

                            databaseReference=FirebaseDatabase.getInstance().getReference("Tea").child("SnackDetails")

                            val javaUpdateSnacks=JavaUpdateSnacks(date,time, items, purchaseAmount,paid_amount,pass)

                            databaseReference.child(pass).child((size+1).toString()).setValue(javaUpdateSnacks)

                            //---------------------------------------------------------------------------------------------------------------------------------

                            databaseReference1.child(pass).child(date).child(time).setValue(javaUpdateSnacks)
                            Toast.makeText(applicationContext,"Successfully updated...",Toast.LENGTH_LONG).show()

                            var ik=Intent(applicationContext,HomePage::class.java)
                            startActivity(ik)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })


            }
           /* else
            {
                Toast.makeText(applicationContext,"Please check null value is present...",Toast.LENGTH_LONG).show()
            }*/
        }
    }
}

