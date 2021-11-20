package es.usj.mastertsa.onunez.session201

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        swActionBar.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) supportActionBar!!.show()
            else supportActionBar!!.hide()
        }
        tvSystemLanguage.text = Locale.getDefault().displayLanguage
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_buttons_menu, menu)
        return true
    }

    @SuppressLint("StringFormatInvalid")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val option = when(item.itemId) {
            R.id.camera -> resources.getString(R.string.camera)
            R.id.phone -> resources.getString(R.string.phone)
            R.id.myFirstOption -> resources.getString(R.string.first_option)
            R.id.mySecondOption -> resources.getString(R.string.second_option)
            else -> resources.getString(R.string.third_option)
        }
        Toast.makeText(this, resources.getString(R.string.option_selected, option) + " " + option, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}