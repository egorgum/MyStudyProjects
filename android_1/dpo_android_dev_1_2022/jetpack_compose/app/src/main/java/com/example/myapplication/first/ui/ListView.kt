package com.example.myapplication.first.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
@Composable
fun ListView(character: Characters){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(10.dp),
           ) {
        Image(painter = rememberAsyncImagePainter(model  = character.image), contentDescription = null, modifier = Modifier.size(150.dp))
        Column(modifier = Modifier.padding(start = 5.dp)) {
            Text(text = character.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(text = "${character.status} - ${character.species}",
                fontSize = 15.sp)
            Text(text = "Last known location:",
                fontSize = 15.sp,
                color = MaterialTheme.colors.secondary)
            Text(text = character.locationInfo,
                fontSize = 15.sp
            )
        }

    }

}