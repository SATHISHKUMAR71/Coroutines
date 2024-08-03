package com.example.coroutinesinandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    val scope = CoroutineScope(Dispatchers.IO + CoroutineName("OwnScope"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lifecycleScope.launch {
            println("Activity coroutine")
            delay(5000)
            doNetworkCall1()
            doNetworkCall2()
            println("Hello from Coroutines background worker ${Thread.currentThread().name}")
        }

        scope.launch {

            val job1 = launch {
//                while (true){
//                    delay(100)
//                    println("Own Scope job 1")
//                }
                while (isActive){
                    println("Own Scope job 1")
                }
            }
            val job2 = launch {
//                while (true){
//                    delay(100)
//                    println("Own Scope job 2")
//                }
                while (isActive){
                    println("Own Scope job 2")
                }
            }

            delay(2000)
            job1.cancelAndJoin()
            delay(1000)
            println("Job 1 CANCELLED")
        }

        println("Main Thread ${Thread.currentThread().name}")
        Intent(this,SecondActivity::class.java).apply {
            startActivity(this)
        }
        finish()
    }

    private suspend fun doNetworkCall2(){
        delay(5000)
        println("This is the Network call 2 ${Thread.currentThread().name}")
    }
    private suspend fun doNetworkCall1(){
        delay(5000)
        println("This is the Network call 1 ${Thread.currentThread().name}")
    }

    override fun onPause() {
        super.onPause()
        println("On Pause")
    }

    override fun onStop() {
        super.onStop()
        println("On Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("On Destroy")
    }
}


