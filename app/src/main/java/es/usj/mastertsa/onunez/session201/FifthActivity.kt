package es.usj.mastertsa.onunez.session201

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_fourth.*

class FifthActivity : AppCompatActivity() {

    val REQUEST = 1
    lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)
        btnStopRecording.visibility = View.GONE
        btnStartRecording.setOnClickListener {
            try {
                val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
                startActivityForResult(intent, REQUEST)
            }
            catch (e: ActivityNotFoundException) {
                Toast.makeText(this@FifthActivity, R.string.default_audio_app,
                    Toast.LENGTH_LONG).show()
                finish()
            }
        }
        btnPlayRecording.setOnClickListener {
            val mediaPlayer = MediaPlayer.create(this@FifthActivity, uri)
            mediaPlayer.start()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST)
            uri = data!!.data!!
    }

//    fun destroy() {
//        if (mediaPlayer != null)
//            mediaPlayer!!.release()
//    }
}