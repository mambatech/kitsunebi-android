package com.exnor.vray.ui

import com.exnor.vray.R
import com.exnor.vray.common.Constants
import com.exnor.vray.common.showAlert
import com.exnor.vray.storage.Preferences
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class SubscribeConfigActivity : AppCompatActivity() {

    private lateinit var subUrl: EditText
    private lateinit var subBtn: Button

    class RetrieveConfigurationTask internal constructor(context: Context): AsyncTask<String, Void, String?>() {
        private var ctx: Context = context

        override fun doInBackground(vararg p0: String?): String? {
            if (p0[0] != null) {
                val obj = URL(p0[0])
                with(obj.openConnection() as HttpsURLConnection) {
                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()
                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        it.close()
                        return response.toString()
                    }
                }
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            if (result != null) {
                Preferences.putString(Constants.PREFERENCE_CONFIG_KEY, result)
                showAlert(ctx, "Configuration updated!")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subscribe_config)
        subUrl = findViewById<EditText>(R.id.sub_url)
        subBtn = findViewById<Button>(R.id.sub_btn)

        subUrl.setText(Preferences.getString(Constants.SUBSCRIBE_CONFIG_URL_KEY, ""))

        subBtn.setOnClickListener { view ->
            val url = subUrl.text.toString()
            Preferences.putString(Constants.SUBSCRIBE_CONFIG_URL_KEY, url)
            RetrieveConfigurationTask(this).execute(url)
        }
    }
}