package niklasenglmeier.citycom

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class LauncherActivity : AppCompatActivity() {

    lateinit var settings: SharedPreferences

    var homeTown: String? = ""

    var swapActivity = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        settings = getSharedPreferences("settings", 0)
        homeTown = settings.getString("homeTown", "")

        Log.d("Citycom", "Hometown = "+ homeTown)

        homeTown = ""
        val debugEnabled = true

        if (debugEnabled) {
            when(homeTown) {
                "" -> {
                    swapActivity.setAction(Intent.ACTION_CALL)
                        .setClass(applicationContext, StartActivity::class.java)
                    startActivity(swapActivity)
                }

                else -> {
                    swapActivity.setAction(Intent.ACTION_CALL)
                        .setClass(applicationContext, MainActivity::class.java)
                    startActivity(swapActivity)
                }
            }
        }
    }
}