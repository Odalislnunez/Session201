package es.usj.mastertsa.onunez.session201

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    @SuppressLint("StringFormatInvalid")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val option : String = when(item!!.itemId) {
            R.id.myFirstOption -> resources.getString(R.string.first_option)
            R.id.mySecondOption -> resources.getString(R.string.second_option)
            else -> resources.getString(R.string.third_option)
        }
        Toast.makeText(this, resources.getString(R.string.option_selected) +": " + option, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}