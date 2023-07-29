package br.com.raulreis.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class BmiActivity : AppCompatActivity() {

    private lateinit var edtWeight: EditText
    private lateinit var edtHeight: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        edtWeight = findViewById(R.id.edtBmiWeight)
        edtHeight = findViewById(R.id.edtBmiHeight)
        val btnSend = findViewById<Button>(R.id.btnBmiSend)

        btnSend.setOnClickListener {
            if(!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
    }

    private fun validate() : Boolean {
        // Não pode ter valores nulos / vazios
        // Não pode inserir/começar com 0

        return edtWeight.text.toString().isNotEmpty()
            && edtHeight.text.toString().isNotEmpty()
            && !edtWeight.text.toString().startsWith("0")
            && !edtHeight.text.toString().startsWith("0")
    }
}