package com.example.retrofitapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.retrofitapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

const val BASE_URL = "https://catfact.ninja"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var TAG = "MainActivity"
    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequest::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getCurrentData()
        binding.rlMainScreen.setOnClickListener {
            getCurrentData()
        }
    }
    private fun getCurrentData(){
        GlobalScope.launch(Dispatchers.IO) {
            try {

                withContext(Dispatchers.Main){
                    binding.progressBar.visibility = View.VISIBLE
                }
                val res = api.getCatFact().awaitResponse()
                if (res.isSuccessful){
                    val data = res.body()!!
                    Log.d(TAG,data.fact)
                    withContext(Dispatchers.Main){
                        binding.progressBar.visibility = View.GONE
                        binding.tvMainText.setText(data.fact)
                    }
                }
            }catch (e:Exception){
                withContext(Dispatchers.Main){
                    binding.progressBar.visibility = View.GONE
                    binding.tvMainText.setText("Ups ha habido un error a solicitar los datos")
                }
            }
        }

    }
}