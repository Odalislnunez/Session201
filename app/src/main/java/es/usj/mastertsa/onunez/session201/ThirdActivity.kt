package es.usj.mastertsa.onunez.session201

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_third.*
import org.jetbrains.anko.doAsync

class ThirdActivity : AppCompatActivity() {
    var url : String? = Uri.parse(Environment.getExternalStorageDirectory().path + "/Music/sound.mp3").path
    var position = 0
    var mediaPlayer: MediaPlayer? = null
    var permissionGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)
        askForPermission()
        btnStart.setOnClickListener {
            doAsync {
                start()
            }
        }

        btnPause.setOnClickListener { doAsync { pause() } }

        btnResume.setOnClickListener { doAsync { resume() } }

        btnStop.setOnClickListener { doAsync { stop() } }

        tBtnRepeat.setOnClickListener { doAsync { repeat() } }
    }

    private fun askForPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) { }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
            }
        }
        else {
            permissionGranted = true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionGranted = when (requestCode) {
            0 -> (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            else -> false
        }
    }

    private fun createMediaPlayer(): MediaPlayer {
        if(!permissionGranted) runOnUiThread{
            swSource.isChecked = true
        }
        return if (swSource.isChecked) {
            MediaPlayer.create(this, R.raw.sound)
        }
        else {
            MediaPlayer.create(this, Uri.parse(url))
        }
    }

    private fun destroy() {
        if (mediaPlayer != null)
            mediaPlayer!!.release()
    }

    private fun start() {
        destroy()
        mediaPlayer = createMediaPlayer()
        if (!tBtnRepeat.isChecked)
            mediaPlayer!!.setLooping(false)
        else
            mediaPlayer!!.setLooping(true)
        mediaPlayer!!.start()
    }

    private fun pause() {
        if (mediaPlayer != null && mediaPlayer!!.isPlaying()) {
            position = mediaPlayer!!.getCurrentPosition()
            mediaPlayer!!.pause()
        }
    }

    private fun resume() {
        if (mediaPlayer != null && !mediaPlayer!!.isPlaying()) {
            mediaPlayer!!.seekTo(position)
            mediaPlayer!!.start()
        }
    }

    private fun stop() {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
            position = 0
        }
    }

    private fun repeat() {
        stop()
        start()
    }

}