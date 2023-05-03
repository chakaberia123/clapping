package com.example.clapapp

import android.media.Image
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.os.postDelayed
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private var mediaPlayerClap: MediaPlayer? = null
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var mediaPlayersiuu: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar = findViewById(R.id.sbClapping)
        handler = Handler(Looper.getMainLooper())
        mediaPlayersiuu = MediaPlayer.create(this, R.raw.ronaldosiuu)

        val playButton = findViewById<FloatingActionButton>(R.id.fabPlay)
        playButton.setOnClickListener {
            if (mediaPlayerClap==null){
                mediaPlayerClap = MediaPlayer.create(this, R.raw.applauding)
                initializeSeekBar()
            }
            mediaPlayerClap?.start()
        }

        val pauseButton = findViewById<FloatingActionButton>(R.id.fabPause)
        pauseButton.setOnClickListener {
            mediaPlayerClap?.pause()
        }

        val stopButton = findViewById<FloatingActionButton>(R.id.fabStop)
        stopButton.setOnClickListener {
            mediaPlayerClap?.stop()
            mediaPlayerClap?.reset()
            mediaPlayerClap?.release()
            mediaPlayerClap = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0
        }


        val ronaldo = findViewById<ImageView>(R.id.imgronaldo)
        ronaldo.setOnClickListener {
            mediaPlayersiuu.start()
        }

    }
    private fun initializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) mediaPlayerClap?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
        val tvPlayed = findViewById<TextView>(R.id.tvPlayed)
        val tvDue = findViewById<TextView>(R.id.tvDue)
        seekBar.max = mediaPlayerClap!!.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayerClap!!.currentPosition

            val playedTime = mediaPlayerClap!!.currentPosition/1000
            tvPlayed.text = "$playedTime sec"
            val duration = mediaPlayerClap!!.duration/1000
            val dueTime = duration-playedTime
            tvDue.text = "$dueTime s"


            handler.postDelayed(runnable, 1000)

        }
        handler.postDelayed(runnable, 1000)
    }
}