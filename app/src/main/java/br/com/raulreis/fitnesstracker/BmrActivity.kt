package br.com.raulreis.fitnesstracker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.com.raulreis.fitnesstracker.model.Calc

class BmrActivity : AppCompatActivity() {

    private lateinit var autoLifestyle: AutoCompleteTextView
    private lateinit var edtWeight: EditText
    private lateinit var edtHeight: EditText
    private lateinit var edtAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmr)

        autoLifestyle = findViewById(R.id.autoLifestyle)
        edtWeight = findViewById(R.id.edtBmrWeight)
        edtHeight = findViewById(R.id.edtBmrHeight)
        edtAge = findViewById(R.id.edtBmrAge)
        val btnSend = findViewById<Button>(R.id.btnBmrSend)

        btnSend.setOnClickListener {
            if(!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val weight = edtWeight.text.toString().toInt()
            val height = edtHeight.text.toString().toInt()
            val age = edtAge.text.toString().toInt()

            val result = calculateBmr(weight, height, age)

            val response = bmrRequest(result)

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.bmr_response, response))
                .setPositiveButton(android.R.string.ok) { dialog, which ->
                    // Não fazer nada nesse botão, apenas fechar o alert dialog
                }
                .setNegativeButton(R.string.save) { dialog, which ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "bmr", res = result))

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()
                }
                .create()
                .show()

            // Passamos o nome o serviço e recebemos de volta o gerenciador daquele serviço
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken,0)
        }
    }

    override fun onResume() {
        super.onResume()
        setAutoOptions()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuSearch) {
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }
    private fun openListActivity() {
        val intent = Intent(this@BmrActivity, ListCalcActivity::class.java)
        intent.putExtra("type", "bmr")
        startActivity(intent)
    }

    private fun setAutoOptions() {
        val items = resources.getStringArray(R.array.bmr_lifestyle)
        autoLifestyle.setText(items.first())
        val adapter = ArrayAdapter(this@BmrActivity, android.R.layout.simple_list_item_1, items)
        autoLifestyle.setAdapter(adapter)
    }

    private fun calculateBmr(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun bmrRequest(bmr: Double): Double {
        val items = resources.getStringArray(R.array.bmr_lifestyle)
        return when(autoLifestyle.text.toString()) {
            items[0] -> bmr * 1.2
            items[1] -> bmr * 1.375
            items[2] -> bmr * 1.55
            items[3] -> bmr * 1.725
            items[4] -> bmr * 1.9
            else -> 0.0
        }
    }

    private fun validate() : Boolean {
        // Não pode ter valores nulos / vazios
        // Não pode inserir/começar com 0

        return edtWeight.text.toString().isNotEmpty()
                && edtHeight.text.toString().isNotEmpty()
                && edtAge.text.toString().isNotEmpty()
                && !edtWeight.text.toString().startsWith("0")
                && !edtHeight.text.toString().startsWith("0")
                && !edtAge.text.toString().startsWith("0")
    }
}