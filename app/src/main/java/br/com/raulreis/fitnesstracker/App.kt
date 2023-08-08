package br.com.raulreis.fitnesstracker

import android.app.Application
import br.com.raulreis.fitnesstracker.model.AppDatabase

class App : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}