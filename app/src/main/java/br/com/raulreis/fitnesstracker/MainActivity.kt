package br.com.raulreis.fitnesstracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

//    private lateinit var btnBMI: LinearLayout
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = MainAdapter()
        rvMain = findViewById(R.id.rvMainButtons)
        rvMain.adapter = adapter

        rvMain.layoutManager = LinearLayoutManager(this)

        // Calsse para administrar a recyclerview e suas celulas (e os seus layouts de itens)
        // Adapter ->



//        btnBMI = findViewById(R.id.btnMainBMI)
//
//        btnBMI.setOnClickListener {
//            // navegar para a próxima tela
//            var i = Intent(this, BmiActivity::class.java)
//            startActivity(i)
//        }
    }

    private inner class MainAdapter: RecyclerView.Adapter<MainViewHolder>() {
        // 1 - Imforma qual é o layout XML da celula específica (item)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        // 2 - Informar quantas celular essa listagem terá
        override fun getItemCount(): Int {
            return 30
        }

        // 3 - Método que será disparado toda vez que houver uma rolagem na tela e for necessário
        // trocar o conteúdo da celula
        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        }
    }

    //Classe da celula em si
    private class MainViewHolder(view: View) : RecyclerView.ViewHolder(view)
}