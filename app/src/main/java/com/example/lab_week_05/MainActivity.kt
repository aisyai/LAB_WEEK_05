package com.example.lab_week_05

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab_week_05.api.CatApiService
import com.example.lab_week_05.model.ImageData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var apiResponseView: TextView

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    private val catApiService = retrofit.create(CatApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiResponseView = findViewById(R.id.api_response)

        getCatImageDataResponse()
    }

    private fun getCatImageDataResponse() {
        val call = catApiService.searchImages(1, "full")
        call.enqueue(object : Callback<List<ImageData>> {
            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<List<ImageData>>, t: Throwable) {
                Log.e(MAIN_ACTIVITY, "Failed to get response", t)
                apiResponseView.text = "Request failed: ${t.message}"
            }

            override fun onResponse(call: Call<List<ImageData>>, response: Response<List<ImageData>>) {
                if(response.isSuccessful){
                    val image = response.body()
                    val firstImage = image?.firstOrNull()?.imageUrl ?: "No URL"
                    apiResponseView.text = getString(R.string.image_placeholder, firstImage)
                }
                else{
                    Log.e(MAIN_ACTIVITY, "Failed to get response\n" +
                            response.errorBody()?.string().orEmpty()
                    )
                }
            }
        })
    }

    companion object {
        const val MAIN_ACTIVITY = "MAIN_ACTIVITY"
    }
}