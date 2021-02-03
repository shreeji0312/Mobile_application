package com.example.myapplication


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var paused = false
    private var canceled = false
    private var leftSeconds:Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var runningSeconds:Long = 30000
        val oneSecond:Long = 1000
        var temp = runningSeconds/1000
        timer.text = temp.toString()

        start.setOnClickListener{
            timer(runningSeconds,oneSecond).start()
            it.isEnabled = false
            stop.isEnabled = true
            up.isEnabled = false
            start.isEnabled = false
            down.isEnabled = false
            reset.isEnabled = false
            canceled = false
            paused = false
        }


        stop.setOnClickListener{
            canceled = true
            paused = false
            it.isEnabled = false
            start.isEnabled = true
            reset.isEnabled = true
        }

        reset.setOnClickListener{
            it.isEnabled = true
            start.isEnabled = true
            stop.isEnabled = true
            up.isEnabled = true
            down.isEnabled = true
            timer.text = "30"
        }


        up.setOnClickListener{
            paused = true
            canceled = false
            runningSeconds += 1000
            it.isEnabled = runningSeconds < 99000
            var temp = runningSeconds/1000
            timer.text = temp.toString()
        }


        down.setOnClickListener{
            paused = false
            canceled = false
            runningSeconds -= 1000
            it.isEnabled = runningSeconds > 5000
            var temp = runningSeconds/1000
            timer.text = temp.toString()
        }
    }


    private fun timer(runningSeconds:Long,oneSecond:Long):CountDownTimer{
        return object: CountDownTimer(runningSeconds,oneSecond){
            override fun onTick(currentSeconds: Long){
                val timeRemaining = "${currentSeconds/1000}"
                when {
                    paused -> {
                        timer.text = "${timer.text}"
                        leftSeconds = currentSeconds
                        cancel()
                    }
                    canceled -> {
                        timer.text = "${timer.text}"
                        cancel()
                    }
                    else -> {
                        timer.text = timeRemaining
                    }
                }
            }

            override fun onFinish() {
                timer.text = "Timer Complete"
            }
        }

    }
}


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}