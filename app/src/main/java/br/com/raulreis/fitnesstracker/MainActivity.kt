package br.com.raulreis.fitnesstracker


import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
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
                textStringId = R.string.label_bmi,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(
                id = 2,
                drawableID = R.drawable.baseline_balance_24,
                textStringId = R.string.label_bmr,
                color = Color.YELLOW
            )
        )

        val adapter = MainAdapter(mainItems)
        rvMain = findViewById(R.id.rvMainButtons)
        rvMain.adapter = adapter

        rvMain.layoutManager = LinearLayoutManager(this)

        // Calsse para administrar a recyclerview e suas celulas (e os seus layouts de itens)
        // Adapter ->


    }

    private inner class MainAdapter(private val mainItems : List<MainItem>): RecyclerView.Adapter<MainViewHolder>() {
        // 1 - Imforma qual é o layout XML da celula específica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - Informar quantas celular essa listagem terá
        override fun getItemCount(): Int {
            return mainItems.size
        }

        // 3 - Método que será disparado toda vez que houver uma rolagem na tela e for necessário
        // trocar o conteúdo da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }
    }

    //Classe da celula em si
    private class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: MainItem) {
            val img: ImageView = itemView.findViewById(R.id.imgItemIcon)
            val name: TextView = itemView.findViewById(R.id.txtItemName)
            val container : LinearLayout = itemView as LinearLayout

            img.setImageResource(item.drawableID)
            name.setText(item.textStringId)
            container.setBackgroundColor(item.color)
        }
    }
}