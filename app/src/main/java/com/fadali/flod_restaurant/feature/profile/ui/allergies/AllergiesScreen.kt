package com.fadali.flod_restaurant.feature.profile.ui.allergies

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.AppErrorDialog
import com.fadali.flod_restaurant.core.component.dialog.errordialog.ErrorDialogState
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.textfield.AppTextField
import com.fadali.flod_restaurant.core.util.resolve
import com.fadali.flod_restaurant.feature.profile.domain.model.AllergyData
import com.fadali.flod_restaurant.presentation.ui.components.NetworkImage
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.presentation.ui.theme.accentBlue
import com.fadali.flod_restaurant.presentation.ui.theme.appText
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AllergiesScreen(
    navController: NavController,
    viewModel: AllergiesViewModel = koinViewModel(),
) {
    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var errorDialogState by remember { mutableStateOf(ErrorDialogState.default) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(AllergiesEvent.LoadAllergies)
    }
    HandleState(viewModel.state) { state ->
        AllergiesScreenContent(
            state = state,
            sendEvent = { event -> viewModel.sendEvent(event) }
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            AllergiesEffect.NavigateToHome -> navController.navigate(AuthRoutes.Onboarding)
            is AllergiesEffect.ShowError -> {
                showErrorDialog = true
                effect.dialogState?.let { errorDialogState = it }
            }
        }
    }
    AppErrorDialog(
        isVisible = showErrorDialog,
        title = errorDialogState.title.resolve(),
        message = errorDialogState.message.resolve(),
        primaryButtonText = errorDialogState.primaryButtonText.resolve(),
        secondaryButtonText = errorDialogState.secondaryButtonText?.resolve(),
        isDismissible = errorDialogState.isDismissible,
        onPrimaryBtnClicked = errorDialogState.onPrimaryBtnClicked,
        onDismiss = {
            showErrorDialog = false
            errorDialogState.onDismiss()
        }
    )
}

@Composable
private fun AllergiesScreenContent(
    state: AllergiesState,
    sendEvent: (AllergiesEvent) -> Unit,
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
            .background(colorScheme.background)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppLoadingDialog(isVisible = state.isLoading)

        Text(
            text = stringResource(R.string.allergies_title_line1) + "\n" +
                    stringResource(R.string.allergies_title_line2),
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = 26.sp,
                fontWeight = FontWeight.SemiBold,
                color = colorScheme.accentBlue,
                textAlign = TextAlign.Center,
            )
        )
        Spacer(modifier = Modifier.size(20.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.query,
            onValueChange = { query -> sendEvent(AllergiesEvent.OnSearchQueryChange(query)) },
            keyboardType = KeyboardType.Text,
            placeholder = stringResource(R.string.search_allergens_hint),
            leadingIcon = {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    contentDescription = stringResource(R.string.search_allergens_hint)
                )
            },
            maxLength = 20,
            backgroundColor = colorScheme.background,
        )
        Spacer(Modifier.height(10.dp))
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(26.dp)
        ) {
            state.allergies.forEach { item ->
                val isSelected = state.selectedAllergies.any { it.id == item.id }
                AllergyChip(
                    label = item.displayName,
                    imageUrl = item.imageUrl,
                    selected = isSelected,
                    onToggle = { sendEvent(AllergiesEvent.OnToggleAllergy(allergy = item)) },
                )
            }
        }
        Spacer(Modifier.size(20.dp))
        AppPrimaryButton(
            text = stringResource(R.string.start_label),
            onClick = { sendEvent(AllergiesEvent.OnStartBtnClicked) },
        )
    }
}

@Composable
private fun AllergyChip(
    label: String,
    imageUrl: String,
    selected: Boolean,
    onToggle: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val background = if (selected) colorScheme.primary else colorScheme.background
    val borderColor = if (selected) colorScheme.primary else colorScheme.outline
    val textStyle = MaterialTheme.typography.titleMedium.copy(
        color = if (selected) colorScheme.onPrimary else colorScheme.appText,
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )

    Row(
        modifier = Modifier
            .background(
                color = background,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                border = BorderStroke(1.dp, borderColor),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onToggle() }
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = textStyle
        )
        Spacer(Modifier.width(8.dp))
        NetworkImage(
            modifier = Modifier.size(28.dp),
            url = imageUrl,
            contentScale = ContentScale.Inside
        )
    }
}

@AppPreviews
@Composable
fun AllergiesScreenContentPreview() {
    FLODRestaurantTheme {
        AllergiesScreenContent(
            state = AllergiesState(
                allergies = listOf(
                    AllergyData(id = "", "Milk", ""),
                    AllergyData(id = "", "Egg", ""),
                    AllergyData(id = "", "Chilli", ""),
                    AllergyData(id = "", "Milk", ""),
                    AllergyData(id = "", "Milk", ""),
                )
            ),
            sendEvent = {}
        )
    }
}
