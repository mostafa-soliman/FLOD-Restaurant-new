package com.fadali.flod_restaurant.feature.subscription.ui.terms

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.button.AppPrimaryButton
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.topBar.AppTopBar
import com.fadali.flod_restaurant.feature.subscription.ui.terms.utils.SubscriptionTermsAndConditionsConstants.termsAssetUrl
import com.fadali.flod_restaurant.presentation.ui.theme.FLODRestaurantTheme
import com.fadali.flod_restaurant.util.Dimens
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SubscriptionTermsAndConditionsScreen(
    navController: NavController,
    viewModel: SubscriptionTermsAndConditionsViewModel = koinViewModel(),
    args: SubscriptionRoutes.TermsAndConditions
) {
    LaunchedEffect(Unit) {
        viewModel.handleEvent(SubscriptionTermsAndConditionsUiEvent.LoadData(args))
    }
    HandleState(viewModel.state) { state ->
        SubscriptionTermsAndConditionsContent(
            state = state,
            onEvent = viewModel::handleEvent
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            is SubscriptionTermsAndConditionsUiEffect.NavigateToPayment -> {
                navController.popBackStack()
                navController.navigate(effect.route)
            }

            SubscriptionTermsAndConditionsUiEffect.NavigateBack -> {
                navController.navigateUp()
            }
        }
    }
}

@Composable
private fun SubscriptionTermsAndConditionsContent(
    state: SubscriptionTermsAndConditionsState,
    onEvent: (SubscriptionTermsAndConditionsUiEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                title = stringResource(R.string.terms_and_conditions),
                onBackClick = { onEvent(SubscriptionTermsAndConditionsUiEvent.OnBackPressed) },
                modifier = Modifier.statusBarsPadding()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            AppLoadingDialog(state.isLoading)

            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                ) {
                    TermsAndConditionsHtmlContent(
                        htmlContent = state.termsAndConditionsText,
                        onScrollChanged = { hasReachedEnd ->
                            onEvent(
                                SubscriptionTermsAndConditionsUiEvent.OnScrollPositionChanged(
                                    hasReachedEnd
                                )
                            )
                        }
                    )
                }
                AppPrimaryButton(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(horizontal = 20.dp)
                        .padding(top = 10.dp),
                    text = stringResource(R.string.accept),
                    onClick = { onEvent(SubscriptionTermsAndConditionsUiEvent.OnAcceptButtonClicked) },
                    enabled = state.isAcceptButtonEnabled
                )
            }
        }
    }
}

@Composable
private fun TermsAndConditionsHtmlContent(
    htmlContent: String,
    onScrollChanged: (Boolean) -> Unit
) {
    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Dimens.ScreenPadding),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                settings.apply {
                    javaScriptEnabled = false
                    setSupportZoom(false)
                    builtInZoomControls = false
                    displayZoomControls = false
                }
                setOnScrollChangeListener { _, _, scrollY, _, _ ->
                    val density = resources.displayMetrics.density
                    val contentHeightPx = (contentHeight * density).toInt()
                    val currentScrollPosition = scrollY + height
                    val hasReachedEnd = currentScrollPosition >= contentHeightPx - 10
                    onScrollChanged(hasReachedEnd)
                }
                if (htmlContent.isNotBlank()) {
                    loadDataWithBaseURL(
                        null,
                        htmlContent,
                        "text/html",
                        "UTF-8",
                        null
                    )
                } else {
                    loadUrl(termsAssetUrl)
                }
            }
        },
        update = { webView ->
            if (htmlContent.isNotBlank()) {
                webView.loadDataWithBaseURL(
                    null,
                    htmlContent,
                    "text/html",
                    "UTF-8",
                    null
                )
            } else {
                webView.loadUrl(termsAssetUrl)
            }
        }
    )
}

@AppPreviews
@Composable
private fun SubscriptionTermsAndConditionsScreenPreview() {
    FLODRestaurantTheme {
        SubscriptionTermsAndConditionsContent(
            state = SubscriptionTermsAndConditionsState(
                termsAndConditionsText = "<h1>Terms and Conditions</h1><p>This is a sample terms and conditions text.</p>",
                isAcceptButtonEnabled = true,
                isLoading = false
            ),
            onEvent = {}
        )
    }
}
