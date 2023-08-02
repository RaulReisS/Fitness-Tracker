package br.com.raulreis.fitnesstracker

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

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

            val weight = edtWeight.text.toString().toInt()
            val height = edtHeight.text.toString().toInt()
            val result = calculateBmi(weight, height)

            val bmiResponseId = bmiResponse(result)

            AlertDialog.Builder(this)
                .setTitle(getString(R.string.bmi_response, result))
                .setMessage(bmiResponseId)
                .setPositiveButton(android.R.string.ok) { dialog, wich ->

            }
                .create()
                .show()

            // Passamos o nome o serviço e recebemos de volta o gerenciador daquele serviço
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken,0)

        }
    }
    @StringRes
    private fun bmiResponse(bmi: Double) : Int {
        return when {
            bmi < 15.0 -> R.string.bmi_severely_low_weight
            bmi < 16.0 -> R.string.bmi_very_low_weight
            bmi < 18.5 -> R.string.bmi_low_weight
            bmi < 25.0 -> R.string.normal
            bmi < 30.0 -> R.string.bmi_high_weight
            bmi < 35.0 -> R.string.bmi_so_high_weight
            bmi < 40.0 -> return R.string.bmi_severely_high_weight
            else -> R.string.bmi_extreme_weight
        }
    }

    private fun calculateBmi(weight: Int, height: Int) : Double {
        return weight/(height*height/10000.0)
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