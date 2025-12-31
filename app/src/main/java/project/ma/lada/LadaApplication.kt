package project.ma.lada

import android.app.Application
import project.ma.lada.di.AppContainer
import project.ma.lada.di.AppDataContainer

class LadaApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer()
    }
}
