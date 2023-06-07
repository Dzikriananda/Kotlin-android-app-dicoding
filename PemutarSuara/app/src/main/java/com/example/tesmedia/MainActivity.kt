package com.example.tesmedia

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.media.MediaPlayer
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var mediaPlayer:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mediaPlayer = MediaPlayer()


        // register all the buttons using their appropriate IDs
        val bPlay: Button = findViewById(R.id.playButton)
        val bPause: Button = findViewById(R.id.pauseButton)
        val bStop: Button = findViewById(R.id.stopButton)

        // handle the start button to
        // start the audio playback
        bPlay.setOnClickListener {
            openFilePicker()
        }

        // handle the pause button to put the
        // MediaPlayer instance at the Pause state
        bPause.setOnClickListener {
            // pause() method can be used to
            // pause the mediaplyer instance
            if(mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }
            else{
                mediaPlayer.start()
            }

        }

        // handle the stop button to stop playing
        // and prepare the mediaplayer instance
        // for the next instance of play
        bStop.setOnClickListener {
            // stop() method is used to completely
            // stop playing the mediaplayer instance
            mediaPlayer.stop()

            // after stopping the mediaplayer instance
            // it is again need to be prepared
            // for the next instance of playback
            mediaPlayer.prepare()
        }


    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"

        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri: Uri? = data?.data
            uri?.let {
                try {
                    var fileDescriptor = contentResolver.openFileDescriptor(uri, "r")
                    fileDescriptor?.let { fd ->
                        mediaPlayer.reset()
                        mediaPlayer.setDataSource(fd.fileDescriptor)
                        mediaPlayer.prepare()
                        mediaPlayer.start()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error playing audio", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun getFileNameFromUri(uri: Uri): String? {
        var fileName: String? = null
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (displayNameIndex != -1) {
                    fileName = it.getString(displayNameIndex)
                    Log.i("namafile",fileName as String)
                }
            }
        }
        cursor?.close()
        return fileName
    }

    companion object {
        private const val FILE_PICKER_REQUEST_CODE = 1
        private const val STORAGE_PERMISSION_REQUEST_CODE = 2
    }



}