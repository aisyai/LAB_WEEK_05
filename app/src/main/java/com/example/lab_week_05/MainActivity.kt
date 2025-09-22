package com.example.lab_week_05

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_week_05.api.CatApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var apiResponseView: TextView

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.thecatapi.com/v1/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private val catApiService = retrofit.create(CatApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiResponseView = findViewById(R.id.api_response)

        getCatImageResponse()
    }

    private fun getCatImageResponse() {
        val call = catApiService.searchImages(1, "full")
        call.enqueue(object : Callback<String> {
            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e(MAIN_ACTIVITY, "Failed to get response", t)
                apiResponseView.text = "Request failed: ${t.message}"
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    apiResponseView.text = response.body()
                } else {
                    Log.e(MAIN_ACTIVITY, "Failed to get response: ${response.code()}")
                    apiResponseView.text = "Error: ${response.code()}"
                }
            }
        })
    }

    companion object {
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}
