package com.blblblbl.myapplication

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blblblbl.myapplication.data.Photo
import com.blblblbl.myapplication.data.PhotoDataBase
import com.blblblbl.myapplication.data.PhotosRepository
import com.blblblbl.myapplication.domain.SavePhotoUseCase
import com.blblblbl.myapplication.presentation.CameraViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.BeforeClass

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.blblblbl.myapplication", appContext.packageName)
    }
}

@RunWith(AndroidJUnit4::class)
class DataBaseTest {
    lateinit var db: PhotoDataBase
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    @Before
    fun init(){
        db = Room.databaseBuilder(
            appContext,
            PhotoDataBase::class.java,
            "db").build()
    }
    @Test
    fun useAppContextTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.blblblbl.myapplication", appContext.packageName)
    }
    @Test
    fun dbIsInitializedTest() {
        assertTrue(this::db.isInitialized)
    }
    @Test
    fun dbInsertTest(){
        val photo:Photo = Photo("aaaa","aaaa")
        val outPhoto:Photo
        runBlocking {
            db.photoDao().insert(photo)
            outPhoto = db.photoDao().getAllNotFLow().first()
        }
        assertEquals(photo,outPhoto)

    }
    @Test
    fun dbDeleteTest(){
        val photo:Photo = Photo("aaaa","aaaa")
        val list:List<Photo>
        runBlocking {
            db.photoDao().delete(photo)
            list =db.photoDao().getAllNotFLow()
        }
        assertEquals(0,list.size)
    }
    @Test
    fun dbClearTest(){
        val photo1:Photo = Photo("aaaa","aaaa")
        val photo2:Photo = Photo("bbbb","bbbb")
        var list:List<Photo>
        runBlocking {
            db.photoDao().insert(photo1)
            db.photoDao().insert(photo2)
            list = db.photoDao().getAllNotFLow()
        }
        assertEquals(2,list.size)
        runBlocking {
            db.photoDao().clear()
            list = db.photoDao().getAllNotFLow()
        }
        assertEquals(0,list.size)
    }
}

@RunWith(AndroidJUnit4::class)
class ViewModelAndDatabaseIntegrationTest {
    lateinit var db: PhotoDataBase
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    lateinit var cameraViewModel:CameraViewModel
    lateinit var savePhotoUseCase: SavePhotoUseCase
    lateinit var photosRepository: PhotosRepository

    @Before
    fun init(){
        db = Room.databaseBuilder(
            appContext,
            PhotoDataBase::class.java,
            "db").build()
        photosRepository = PhotosRepository(appContext)
        savePhotoUseCase =SavePhotoUseCase(photosRepository)
        cameraViewModel = CameraViewModel(savePhotoUseCase)
    }
    @Test
    fun useAppContextTest() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.blblblbl.myapplication", appContext.packageName)
    }
    @Test
    fun dbIsInitializedTest() {
        assertTrue(this::db.isInitialized)
    }
    @Test
    fun savePhotoTest(){
        val photo:Photo = Photo("aaaa","aaaa")
        val outPhoto:Photo
        runBlocking {
            cameraViewModel.savePhoto(photo.uri,photo.date)
            outPhoto = db.photoDao().getAllNotFLow().first()
        }
        assertEquals(photo,outPhoto)
    }

}