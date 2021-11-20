package es.usj.mastertsa.onunez.session201

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_fourth.*
import java.io.File
import java.io.IOException
import java.net.ResponseCache

class FourthActivity : AppCompatActivity(), MediaPlayer.OnCompletionListener {
    lateinit var recorder : MediaRecorder
    lateinit var player : MediaPlayer
    lateinit var file : File

    private val RESPONSE = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fourth)
        btnPlayRecording.setOnClickListener { play() }
        btnStopRecording.setOnClickListener { stop() }
        btnStartRecording.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE), RESPONSE)
            }
            else {
                record()
            }
        }
        configureButtons(start = true, pause = false, play = false)
    }

    override fun onCompletion(mp: MediaPlayer?) {
        configureButtons()
        tvMessage.text = getString(R.string.ready)
    }

    private fun configureButtons(start: Boolean = true, pause: Boolean = true, play: Boolean = true) {
        btnStartRecording.isEnabled = start
        btnStopRecording.isEnabled = pause
        btnPlayRecording.isEnabled = play
    }

    private fun stop() {
        recorder.stop()
        recorder.release()
        player = MediaPlayer()
        player.setOnCompletionListener(this)
        try {
            player.setDataSource(file.absolutePath)
        }
        catch (e: IOException) { }
        try {
            player.prepare()
        }
        catch (e: IOException) { }
        configureButtons(start = true, pause = false, play = true)
        tvMessage.text = getString(R.string.ready)
    }

    private fun play() {
        player.start()
        configureButtons(start = false, pause = false, play = true)
        tvMessage.text = getString(R.string.playing)
    }

    private fun record() {
        recorder = MediaRecorder()
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        val path = File(Environment.getExternalStorageDirectory().path)
        try {
            file = File.createTempFile("temporal", ".3gp", path);
        }
        catch (e : IOException) { }
        recorder.setOutputFile(file.absolutePath);
        try {
            recorder.prepare();
        }
        catch (e : IOException) { }
        recorder.start()
        tvMessage.text = getString(R.string.recording)
        configureButtons(start = false, pause = true, play = false)
    }
}