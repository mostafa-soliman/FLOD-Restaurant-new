package com.fadali.flod_restaurant.presentation.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeaderHero(
    imageUrl: String,
    titleTop: String,          // "Welcome to"
    titleBottom: String,       // "FLOD !"
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        NetworkImage(
            url = imageUrl,
            modifier = Modifier.matchParentSize()
        )

        Column(
            Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 20.dp)
        ) {
            Text(titleTop, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Text(titleBottom, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
    }
}
