package br.com.raulreis.fitnesstracker


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(
                id = 1,
                drawableID = R.drawable.baseline_balance_24,
                textStringId = R.string.label_bmi
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableID = R.drawable.baseline_monitor_heart_24,
                textStringId = R.string.label_bmr
            )
        )
        mainItems.add(
            MainItem(
                id = 3,
                drawableID = R.drawable.baseline_app_registration_24,
                textStringId = R.string.records
            )
        )


        val adapter = MainAdapter(mainItems) { id ->
            when (id) {
                1 -> {
                    val intent = Intent(this@MainActivity, BmiActivity::class.java)
                    startActivity(intent)
                }

                2 -> {
                    val intent = Intent(this@MainActivity, BmrActivity::class.java)
                    startActivity(intent)
                }

                3-> {
                    val intent = Intent(this@MainActivity, ListCalcActivity::class.java)
                    intent.putExtra("type", "*")
                    startActivity(intent)
                }
            }
        }
        rvMain = findViewById(R.id.rvMainButtons)
        rvMain.adapter = adapter

        rvMain.layoutManager = GridLayoutManager(this, 2)

        // Classe para administrar a recyclerview e suas celulas (e os seus layouts de itens)
        // Adapter ->


    }

    private inner class MainAdapter(
        private val mainItems : List<MainItem>,
        private val onItemClickListener: (Int) -> Unit
    ): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        // 1 - Imforma qual é o layout XML da celula específica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - Informar quantas celulas essa listagem terá
        override fun getItemCount(): Int {
            return mainItems.size
        }

        // 3 - Método que será disparado toda vez que houver uma rolagem na tela e for necessário
        // trocar o conteúdo da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.imgItemIcon)
                val name: TextView = itemView.findViewById(R.id.txtItemName)
                val container : LinearLayout = itemView.findViewById(R.id.containerItemBmi)

                img.setImageResource(item.drawableID)
                name.setText(item.textStringId)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }

    //Classe da celula em si



}