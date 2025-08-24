package com.fadali.flod_restaurant.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.i18n.LText
import com.fadali.flod_restaurant.util.Dimens

@Composable
fun HomeTopBar(userName: String, avatarUrl: String) {
    val cs = MaterialTheme.colorScheme
    Row(
        Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = Dimens.ScreenPadding, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Notifications, contentDescription = null, tint = cs.primary)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(LText(R.string.hello_user, userName), color = cs.primary, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
            NetworkImage(url = avatarUrl, modifier = Modifier.size(40.dp), shape = CircleShape)
        }
    }
}

@Composable
fun BottomNavBar(selected: Int, onSelect: (Int) -> Unit) {
    NavigationBar {
        NavigationBarItem(selected = selected == 0, onClick = { onSelect(0) }, icon = {
            Icon(Icons.Default.Home, null)
        }, label = { LText(R.string.nav_home)  })


        NavigationBarItem(selected = selected == 1, onClick = { onSelect(1) }, icon = {
            Icon(Icons.Default.List, null)
        }, label = {LText(R.string.nav_plans) })

        NavigationBarItem(selected = selected == 2, onClick = { onSelect(2) }, icon = {
            Icon(Icons.Default.Person, null)
        }, label = { LText(R.string.nav_profile)  })

        NavigationBarItem(selected = selected == 3, onClick = { onSelect(3) }, icon = {
            Icon(Icons.Default.Settings, null)
        }, label = { LText(R.string.nav_settings) })
    }
}
