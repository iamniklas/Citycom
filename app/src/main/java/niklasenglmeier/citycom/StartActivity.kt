package niklasenglmeier.citycom

import android.graphics.drawable.TransitionDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

class StartActivity : AppCompatActivity() {

    lateinit var linearLayout: ConstraintLayout

    lateinit var animation: Animation
    lateinit var animation2: Animation
    lateinit var animation3: Animation

    lateinit var text: TextView
    lateinit var text2: TextView
    lateinit var fabL: FloatingActionButton
    lateinit var fabR: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        linearLayout = findViewById(R.id.startLinearLayout)
        text = findViewById(R.id.textView)
        text2 = findViewById(R.id.textView2)
        fabL = findViewById(R.id.floatingActionButton)
        fabR = findViewById(R.id.floatingActionButton2)

        animation = AnimationUtils.loadAnimation(applicationContext, R.anim.startheadline)
        animation2 = AnimationUtils.loadAnimation(applicationContext, R.anim.startdescript)
        animation3 = AnimationUtils.loadAnimation(applicationContext, R.anim.fadein)

        val ha = Handler()
        ha.postDelayed({
            text.startAnimation(animation)
        }, 750)

        val ha1 = Handler()
        ha1.postDelayed({
            text2.startAnimation(animation2)
        }, 1250)

        val ha2 = Handler()
        ha2.postDelayed({
            fabL.startAnimation(animation3)
            fabR.startAnimation(animation3)
        }, 1750)

        Log.d("Citycom", "StartActivity alive.")

        val handler = Handler()
        handler.postDelayed({
            Log.d("Citycom", "Handler alive.")
            val transition = linearLayout.background as TransitionDrawable
            transition.startTransition(500)
        }, 150)
    }

}