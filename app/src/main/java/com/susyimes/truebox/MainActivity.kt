package com.susyimes.truebox

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.susyimes.funbox.network.Retrofits


import kotlinx.coroutines.*
import java.lang.Exception
import java.lang.Thread.sleep

class MainActivity : AppCompatActivity() {
    private val coroutineExceptionHanlder by lazy {
        CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            Toast.makeText(this@MainActivity, "2222222", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(Dispatchers.Main+coroutineExceptionHanlder) {
            Log.e("threaddd",Thread.currentThread().name+"///1")

            withContext(Dispatchers.IO){
                Log.e("threaddd",Thread.currentThread().name+"///2")
            }
            Toast.makeText(this@MainActivity, "111111", Toast.LENGTH_SHORT).show()
            Log.e("threaddd",Thread.currentThread().name+"///3")
        }
        MainScope().launch(Dispatchers.IO+coroutineExceptionHanlder) {
            Log.e("threaddd2",Thread.currentThread().name+"///111")

            longTimeWork()
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity, "12312312", Toast.LENGTH_SHORT).show()
            }

            Log.e("threaddd2",Thread.currentThread().name+"///333")
        }
        //Retrofits.service.get("","")
    }

    suspend fun longTimeWork(){
        Log.e("threaddd2",Thread.currentThread().name+"///222")
        val hashMap = HashMap<String, Any>()
        sleep(15 * 1000)

    }
}