package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    lateinit var runnable: Runnable  //Runnable-A task that can be scheduled for one-time or repeated execution by a Timer.
    private var handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //create a raw folder in res and import the music2.mp3
        val mediaPlayer = MediaPlayer.create(this,R.raw.music2)
        //create a seekbar and set to 0
        val seekBar = findViewById<SeekBar>(R.id.seekbar)
        seekBar.progress = 0
        //seekbar maximum limit would be according to the song
        seekBar.max = mediaPlayer.duration
        //play and pause fun
        val playbtn = findViewById<ImageButton>(R.id.playbtn)
        playbtn.setOnClickListener {
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
                playbtn.setBackgroundResource(R.drawable.pausebutton)
            }else{
                mediaPlayer.pause()
                playbtn.setBackgroundResource(R.drawable.playbutton)
            }
        }
        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if (changed)
                    mediaPlayer.seekTo(pos)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        runnable = Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable,1000)
            //Postdelayed-Causes the Runnable to be added to the message queue, to be run after the specified amount of time elapses.
        }
        handler.postDelayed(runnable,1000)
        //after the song is over the seekbar should come to 0
        mediaPlayer.setOnCompletionListener {
            playbtn.setImageResource(R.drawable.playbutton)
            seekBar.progress = 0
        }
    }
}