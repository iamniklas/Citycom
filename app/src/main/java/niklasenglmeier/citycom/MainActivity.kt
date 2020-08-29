package niklasenglmeier.citycom

import android.annotation.SuppressLint
import android.content.Context
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.lang.NullPointerException
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.widget.Toast

class MainActivity : AppCompatActivity(), View.OnClickListener {
    //Front-End
    private lateinit var editTextInput: EditText
    private lateinit var buttonSearch: Button
    private lateinit var progressbarSearch: ProgressBar
    private var arrayUi = arrayOfNulls<TextView?>(6)
    //Back-End
    private lateinit var stringUrl: String
    private lateinit var cnctMng: ConnectivityManager
    private lateinit var acnctMng: ConnectivityManager
    private lateinit var netInfo: NetworkInfo
    private lateinit var activeNetInfo: NetworkInfo
    lateinit var nc: NetworkCapabilities

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findIds()
        Log.d("Citycom", "MainActivity alive.")
        cnctMng = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        nc = cnctMng.getNetworkCapabilities(cnctMng.activeNetwork)
        netInfo = cnctMng.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        activeNetInfo = cnctMng.activeNetworkInfo

        if (activeNetInfo != null) {
            // connected to the internet
            if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
            } else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to mobile data
            }
        } else {
            // not connected to the internet
        }

        buttonSearch.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonSearchCity -> {
                overwriteTextViews(Array(6) {"Lade..." })
                progressbarSearch.isIndeterminate = true
                stringUrl = "https://www.google.com/search?q=wetter+" + editTextInput.text.toString()
                AsyncThread().execute(stringUrl)
            }
        }
    }
    fun overwriteTextViews(overwriteArray: Array<String?> = arrayOfNulls(6)) {
        for (i in 0..5) {
            arrayUi[i]!!.text = overwriteArray[i]
        }
    }
    fun toaster(output: String) {
        Toast.makeText(applicationContext, output, Toast.LENGTH_SHORT).show()
    }
    private fun findIds() {
        editTextInput = findViewById(R.id.plaintextCityInput)
        buttonSearch = findViewById(R.id.buttonSearchCity)
        progressbarSearch = findViewById(R.id.progressBar)
        arrayUi[0] = findViewById(R.id.textviewInformationHeadline)
        arrayUi[1] = findViewById(R.id.texviewInformationWeatherValue)
        arrayUi[2] = findViewById(R.id.textviewInformationTemperaturValue)
        arrayUi[3] = findViewById(R.id.textviewInformationPrecipitationValue)
        arrayUi[4] = findViewById(R.id.textviewInformationHumidityValue)
        arrayUi[5] = findViewById(R.id.textviewInformationWindspeedValue)
    }

    @SuppressLint("StaticFieldLeak")
    internal inner class AsyncThread: AsyncTask<String, String, String>() {
        private lateinit var document: Document
        private val arrayWeatherData = arrayOfNulls<String>(6)
        private var toastOutput = ""

        override fun doInBackground(vararg params: String?): String {
            val stringUrl: String? = params[0]
            if(netInfo.isConnected) {
                try {
                    document = Jsoup.connect(stringUrl).get()
                    arrayWeatherData[0] = "Lokale Daten für " + document.getElementById("wob_loc").text() + " für " + document.getElementById("wob_dts").text()
                    arrayWeatherData[1] = document.getElementById("wob_dc").text()
                    arrayWeatherData[2] = document.getElementById("wob_tm").text() + " °C"
                    arrayWeatherData[3] = document.getElementById("wob_pp").text()
                    arrayWeatherData[4] = document.getElementById("wob_hm").text()
                    arrayWeatherData[5] = document.getElementById("wob_ws").text()
                }
                catch (npe: NullPointerException) {
                    toastOutput = "Fehler beim Lesen der Wetterdaten. \nOrt wurde nicht gefunden"
                    for(i in 0..5)
                    arrayWeatherData[i] = "Keine Daten vorhanden"
                }
            }
            else
                toastOutput = "Gerät nicht verbunden"
            return ""
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            overwriteTextViews(arrayWeatherData)
            if (!toastOutput.isEmpty())
                toaster(toastOutput)
            progressbarSearch.isIndeterminate = false
        }
    }
}