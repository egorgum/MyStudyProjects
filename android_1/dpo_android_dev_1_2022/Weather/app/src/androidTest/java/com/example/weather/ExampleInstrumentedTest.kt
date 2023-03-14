package com.example.weather

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weather.ui.main.MainFragment.REST.RepositoryRest
import com.example.weather.ui.main.Room.DataBase
import com.example.weather.ui.main.Room.DataBaseWeather
import com.example.weather.ui.main.Room.WeatherDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // 1 test Unit
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.weather", appContext.packageName)
    }
}


@RunWith(AndroidJUnit4::class)
class  MainViewModelTest{
    lateinit var db: DataBase
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @Before
    fun init(){
        db = Room.databaseBuilder(
            appContext,
            DataBase::class.java,
            "db").build()
    }

    @Test
    //2 test Unit
    fun addWeatherTest() {
        val repo = RepositoryRest()
        val  dao = db.weatherDao()
        val viewModel = MainViewModel(repo, dao)
        runBlocking {
            viewModel.loadWeather("Москва")
        }
        assertEquals(true,viewModel.bundle.value != null)
    }

    @Test
    //3 test Unit
    fun getOneWeatherTest() {
        val repo = RepositoryRest()
        val  dao = db.weatherDao()
        val viewModel = MainViewModel(repo, dao)
        var a: DataBaseWeather?
        var b: DataBaseWeather?
        runBlocking {
            viewModel.getOneWeather("Москва")
            a = viewModel.tempROOM.value
            b = dao.getOneInfo("Москва")
        }
        assertEquals(true, a != null)
        assertEquals(true, b != null)
        assertEquals(a,b)
    }
    @Test
    //4 test Unit
    fun onDeleteTest() {
        val repo = RepositoryRest()
        val  dao = db.weatherDao()
        val viewModel = MainViewModel(repo, dao)
        val a = DataBaseWeather(city = "Москва", temp = 1.1, feels = 2.2, description = "ok", windSpeed = 3.3)
        runBlocking {
            dao.addInfo(a)
            viewModel.onDelete()
        }
        assertEquals(0, viewModel.allWeather.value.size)
    }
    @Test
    fun getBundleRest(){
        //5 test Unit
        val repo = RepositoryRest()
        val  dao = db.weatherDao()
        val viewModel = MainViewModel(repo, dao)
        viewModel.flag.value = false
        runBlocking {
            viewModel.getBundleRest("Moscow")
        }
        assertEquals(true, viewModel.flag.value)

    }
}
@RunWith(AndroidJUnit4::class)
class ViewModelAndDatabaseIntegrationTest {
    //Test Integration
    lateinit var db: DataBase
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    lateinit var viewModel: MainViewModel

    @Before
    fun init(){
        db = Room.databaseBuilder(
            appContext,
            DataBase::class.java,
            "db").build()
        val repo = RepositoryRest()
        val  dao = db.weatherDao()
        viewModel = MainViewModel(repo, dao)

    }

    @Test
    fun saveWeatherTest(){
        val weather:DataBaseWeather = DataBaseWeather(city = "Москва", temp = 1.1, feels = 2.2, description = "ok", windSpeed = 3.3)
        val outWeather:DataBaseWeather?
        runBlocking {
            viewModel.onDelete()
            db.weatherDao().addInfo(weather)
            viewModel.getOneWeather(weather.city)
            outWeather = viewModel.tempROOM.value
        }
        assertEquals(weather,outWeather)
    }
}