package br.com.raulreis.fitnesstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.raulreis.fitnesstracker.model.Calc
import java.text.SimpleDateFormat

class ListCalcActivity : AppCompatActivity() {

    private lateinit var rvListCalc : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_calc)

        val type = intent?.extras?.getString("type") ?: throw IllegalStateException("type not found")


        val result = mutableListOf<Calc>()
        val adapter = ListCalcAdapter(result) { item ->
            val description = getString(bmiResponse(item.res)) +
                    "\n\n${getString(R.string.registred_on)} " +
                    SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(item.createdDate)
            val title = when(item.type) {
                "bmi" -> getString(R.string.bmi_response, item.res)
                "bmr" -> getString(R.string.bmr_response, item.res)
                else -> item.res.toString()
            }
            AlertDialog.Builder(this@ListCalcActivity)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton(android.R.string.ok) {_, _ ->
                    // Fazer nada, apensar fechar
                }
                .create()
                .show()
        }
        rvListCalc = findViewById(R.id.rvListCalc)
        rvListCalc.layoutManager = LinearLayoutManager(this@ListCalcActivity)
        rvListCalc.adapter = adapter


        Thread {
            val app = application as App
            val dao = app.db.calcDao()
            val response = dao.getRegisterByType(type)

            runOnUiThread {
                result.addAll(response)
                adapter.notifyDataSetChanged()
            }
        }.start()

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

    private inner class ListCalcAdapter(
        private val listCalcItems : List<Calc>,
        private val onItemClickListner: (Calc) -> Unit
    ) : RecyclerView.Adapter<ListCalcAdapter.ListCalcViewHolder>() {
        private inner class ListCalcViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: Calc) {
                val img : ImageView = itemView.findViewById(R.id.imgCalcItemImage)
                val type : TextView = itemView.findViewById(R.id.txvCalcItemType)
                val value : TextView = itemView.findViewById(R.id.txvCalcItemValue)
                val date : TextView = itemView.findViewById(R.id.txvCalcItemDate)
                val container : LinearLayout = itemView.findViewById(R.id.containerItemListCalc)

                when(item.type) {
                    "bmi" -> {
                        img.setImageResource(R.drawable.baseline_balance_24)
                        type.setText(R.string.bmi_label)
                    }
                    "bmr" -> {
                        img.setImageResource(R.drawable.baseline_monitor_heart_24)
                        type.setText(R.string.bmr_label)
                    }
                    else -> {
                        img.setImageResource(R.drawable.baseline_question_mark_24)
                        type.setText("?")
                    }
                }

                value.text = "%.2f".format(item.res)
                date.text = SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(item.createdDate)

                container.setOnClickListener {
                    onItemClickListner.invoke(item)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCalcViewHolder {
            val view = layoutInflater.inflate(R.layout.list_calc_item, parent, false)
            return ListCalcViewHolder(view)
        }

        override fun getItemCount(): Int {
            return listCalcItems.size
        }

        override fun onBindViewHolder(holder: ListCalcViewHolder, position: Int) {
            val itemCurrent = listCalcItems[position]
            holder.bind(itemCurrent)
        }
    }
}