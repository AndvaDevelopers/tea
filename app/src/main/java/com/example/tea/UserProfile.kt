package com.example.tea

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.animation.Animation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class UserProfile : AppCompatActivity() {

    lateinit var name:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        name=findViewById(R.id.nameska)

        val animator = ObjectAnimator.ofInt(name, "backgroundColor", Color.GRAY, Color.RED, Color.GREEN)
        animator.duration = 5000
        animator.setEvaluator(ArgbEvaluator())
        animator.repeatCount = Animation.REVERSE
        animator.repeatCount = Animation.INFINITE
        animator.start()

    }
}