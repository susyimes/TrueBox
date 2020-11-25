package com.susyimes.truebox

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.susyimes.funbox.network.Retrofits

import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    val coroutineExceptionHanlder by lazy {
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            Toast.makeText(this@MainActivity, "2222222", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlobalScope.launch {
            Log.e("threaddd",Thread.currentThread().name+"///1")

            withContext(Dispatchers.IO){
                Log.e("threaddd",Thread.currentThread().name+"///2")
            }
            Log.e("threaddd",Thread.currentThread().name+"///3")
        }
        MainScope().launch(coroutineExceptionHanlder) {
            Log.e("threaddd2",Thread.currentThread().name+"///111")

            longTimeWork()

            Toast.makeText(this@MainActivity, "12312312", Toast.LENGTH_SHORT).show()
            Log.e("threaddd2",Thread.currentThread().name+"///333")
        }
        //Retrofits.service.get("","")
    }

    suspend fun longTimeWork(){
        Log.e("threaddd2",Thread.currentThread().name+"///222")
        for (i in 0..1000000){
           Retrofits.getService(this@MainActivity)?.get("https://www.google.com/search?sxsrf=ALeKk00qCjlHAYl4Ro4mzPYhxX5DWqFnCA%3A1606286400429&ei=QPy9X-SzGbXVmAWv1RE&q=retrofit2+run+on+Dispathers.Main&oq=retrofit2+run+on+Dispathers.Main&gs_lcp=CgZwc3ktYWIQAzIHCCEQChCgAToCCAA6BAgjECc6BQgAEMsBOgYIABAIEB46BQghEKABUIn7H1iLmCFg5pohaANwAXgAgAHqBIgBsTuSAQwwLjI2LjMuMi4zLjGYAQCgAQGqAQdnd3Mtd2l6wAEB&sclient=psy-ab&ved=0ahUKEwjk07eSi53tAhW1KqYKHa9qBAAQ4dUDCA0&uact=5",null)
        }
    }
}