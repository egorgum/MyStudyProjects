package com.example.myapplication.first.ui

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.Person
import coil.compose.rememberAsyncImagePainter

@Composable
fun PersonView(name: String?, status: String?, location: String?, species: String?, gender: String?, origin: String?, image: String?){
    Log.d(ContentValues.TAG, "В персон: $image")
    Column(verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp)) {
        Image(painter = rememberAsyncImagePainter(model  = image), contentDescription = null, modifier = Modifier.size(200.dp).padding(20.dp), alignment = Alignment.TopCenter)
        Text(text = name?: "Что-то пошло не так",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Text(text = status?:"",
            fontSize = 15.sp)
        Text(text = if (location != null)"Last known location: $location" else "",
            fontSize = 15.sp)
        Text(text = if (species != null)"Species: $species" else "",
            fontSize = 15.sp)
        Text(text = if (gender != null)"Gender: $gender" else "",
            fontSize = 15.sp)
        Text(text = if (origin != null)"Origin: $origin" else "",
            fontSize = 15.sp)
    }
}