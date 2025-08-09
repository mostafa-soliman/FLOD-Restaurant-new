package com.fadali.flod_restaurant.presentation.ui.screens.onboard

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.util.UiDimensions
import androidx.compose.foundation.clickable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.core.os.LocaleListCompat
import com.fadali.flod_restaurant.data.LanguageDataStore
import kotlinx.coroutines.launch


@Composable
fun OnboardScreen(navController: NavController) {
    OnboardScreenContent(
        onLoginClick = {
            navController.navigate("home")
        },
        onSignUpClick = {
            navController.navigate("signup")
        }
    )
}

@Composable
fun OnboardScreenContent(onLoginClick: () -> Unit = {}, onSignUpClick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .weight(0.55f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboard),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "FLOD Logo",
                    modifier = Modifier
                        .size(180.dp)
                )

                Text(
                    text = stringResource(id = R.string.tagline),
                    fontSize = 36.sp,
                    color = Color.White,
                    lineHeight = 36.sp
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(0.45f)
                .fillMaxWidth()
                .background(Color(0xFF700B18))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd
            ) {
                val context = LocalContext.current
                val scope = rememberCoroutineScope()

                val currentTags = AppCompatDelegate.getApplicationLocales()
                    .toLanguageTags()
                    .ifEmpty { "en" }

                val toggleLabel = if (currentTags.startsWith("ar")) "En" else "ع"

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                    modifier = Modifier
                        .size(44.dp)
                        .clickable {
                            val next = if (currentTags.startsWith("ar")) "en" else "ar"
                            scope.launch {
                                LanguageDataStore.setLanguage(context, next)
                                val locales = LocaleListCompat.forLanguageTags(next)
                                AppCompatDelegate.setApplicationLocales(locales)
                                (context as? Activity)?.recreate()
                            }
                        },
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = toggleLabel,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

//            Box(
//                modifier = Modifier.fillMaxWidth(),
//                contentAlignment = Alignment.TopEnd
//            ) {
//                Surface(
//                    shape = CircleShape,
//                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
//                ) {
//                    Text(
//                        text = "En",
//                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
//                        fontSize = 14.sp,
//                        color = MaterialTheme.colorScheme.onSurface
//                    )
//                }
//            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.welcome_back),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(id = R.string.sign_in_description),
                fontSize = 14.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(
                    onClick = {},
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .width(110.dp)
                        .height(UiDimensions.TextFieldHeight),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.saudi_flag),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("+966", color = Color.Black)
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    label = { Text(text = stringResource(id = R.string.phone_number)) },
                    modifier = Modifier
                        .weight(1f)
                        .height(UiDimensions.TextFieldHeight),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLoginClick,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.width(110.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text(text = stringResource(id = R.string.login), color = Color(0xFF700B18))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = stringResource(id = R.string.signup_prompt),
                    color = Color.White,
                    modifier = Modifier.alignBy(FirstBaseline)
                )
                Spacer(modifier = Modifier.width(4.dp))
                TextButton( onClick = onSignUpClick,
                    modifier = Modifier.alignBy(FirstBaseline),
                    contentPadding = PaddingValues(0.dp)) {
                    Text(text = stringResource(id = R.string.signup_link),
                        color = Color.White,
                        textDecoration = TextDecoration.Underline)
                }
            }
        }
    }
}
