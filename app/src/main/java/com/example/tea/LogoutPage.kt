package com.example.tea

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class LogoutPage : AppCompatActivity() {

    lateinit var iv_logout:ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logout_page)

        iv_logout=findViewById(R.id.logoutoption)

        val animation: Animation =
            AlphaAnimation(2f, 0f) //to change visibility from visible to invisible

        animation.duration = 1000 //1 second duration for each animation cycle

        animation.interpolator = LinearInterpolator()
        animation.repeatCount = Animation.INFINITE //repeating indefinitely

        animation.repeatMode = Animation.REVERSE //animation will start from end point once ended.

        iv_logout.startAnimation(animation)
    }
}