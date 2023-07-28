package br.com.raulreis.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var btnBMI: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnBMI = findViewById(R.id.btnMainBMI)

        btnBMI.setOnClickListener {
            // navegar para a próxima tela
            var i = Intent(this, BmiActivity::class.java)
            startActivity(i)
        }
    }
}