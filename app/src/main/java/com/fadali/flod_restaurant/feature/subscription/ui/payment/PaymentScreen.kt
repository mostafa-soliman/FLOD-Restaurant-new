package com.fadali.flod_restaurant.feature.subscription.ui.payment

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.fadali.flod_restaurant.R
import com.fadali.flod_restaurant.app.navigation.navigateAndClearBackStack
import com.fadali.flod_restaurant.app.navigation.navigateBack
import com.fadali.flod_restaurant.app.navigation.routes.BottomNavRoutes
import com.fadali.flod_restaurant.app.navigation.routes.PlanRoutes
import com.fadali.flod_restaurant.app.navigation.routes.SubscriptionRoutes
import com.fadali.flod_restaurant.core.component.dialog.AppLoadingDialog
import com.fadali.flod_restaurant.core.component.preview.AppPreviews
import com.fadali.flod_restaurant.core.component.result.bottomsheet.BottomSheetResult
import com.fadali.flod_restaurant.core.component.result.bottomsheet.ResultBottomSheet
import com.fadali.flod_restaurant.core.component.topBar.AppTopBar
import com.smyvo.core.ui.mvi.HandleEffects
import com.smyvo.core.ui.mvi.HandleState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: PaymentViewModel = koinViewModel(),
    args: SubscriptionRoutes.Payment,
) {
    var showResultBottomSheet by rememberSaveable { mutableStateOf(false) }
    var bottomSheetResult: BottomSheetResult by remember { mutableStateOf(BottomSheetResult.None) }

    LaunchedEffect(Unit) {
        viewModel.sendEvent(PaymentEvent.LoadPaymentUrl(args.paymentUrl, args.subscriptionId))
    }
    HandleState(viewModel.state) { state ->
        PaymentScreenContent(
            state = state,
            sendEvent = { event -> viewModel.sendEvent(event) }
        )
    }
    HandleEffects(viewModel.effect) { effect ->
        when (effect) {
            PaymentEffect.NavigateBack -> navController.navigateBack()
            is PaymentEffect.NavigateToPackageDetails -> {
                navController.navigate(PlanRoutes.PackageDetails(subscriptionId = effect.subscriptionId)) {
                    popUpTo(BottomNavRoutes.Home) { inclusive = false }
                }
            }

            PaymentEffect.NavigateToNextScreen -> {
                navController.navigateAndClearBackStack(BottomNavRoutes.Home)
            }

            is PaymentEffect.ShowResultSheet -> {
                bottomSheetResult = effect.result
                showResultBottomSheet = true
            }
        }
    }
    ResultBottomSheet(
        bottomSheetResult = bottomSheetResult,
        isVisible = showResultBottomSheet,
        isDismissable = false,
        onDismiss = {
            showResultBottomSheet = false
        }
    )
}

@Composable
private fun PaymentScreenContent(
    state: PaymentState,
    sendEvent: (PaymentEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        AppTopBar(
            title = stringResource(R.string.payment),
            onBackClick = { sendEvent(PaymentEvent.OnBackPressed) }
        )

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AppLoadingDialog(state.isLoading)

            if (state.showWebView) {
                PaymentWebView(
                    url = state.paymentUrl,
                    onUrlChanged = { url -> sendEvent(PaymentEvent.OnUrlChanged(url)) },
                    onPageStartedLoading = { sendEvent(PaymentEvent.OnPageStartedLoading) },
                    onPageFinishedLoading = { sendEvent(PaymentEvent.OnPageFinishedLoading) },
                    onPageLoadError = { sendEvent(PaymentEvent.OnPageLoadError) }
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
private fun PaymentWebView(
    url: String,
    onUrlChanged: (String) -> Unit,
    onPageStartedLoading: () -> Unit,
    onPageFinishedLoading: () -> Unit,
    onPageLoadError: () -> Unit
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        onPageStartedLoading()
                        url?.let { onUrlChanged(it) }
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onPageFinishedLoading()
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        onPageLoadError()
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        errorCode: Int,
                        description: String?,
                        failingUrl: String?
                    ) {
                        super.onReceivedError(view, errorCode, description, failingUrl)
                        onPageLoadError()
                    }

                    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                        url?.let { onUrlChanged(it) }
                        return false
                    }

                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        request?.url?.toString()?.let { onUrlChanged(it) }
                        return false
                    }
                }
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                }
            }
        },
        update = { webView ->
            if (url.isNotEmpty()) {
                if (webView.url != url) {
                    webView.loadUrl(url)
                }
            } else {
                onPageLoadError()
            }
        }
    )
}

@AppPreviews
@Composable
private fun PaymentScreenPreview() {
    PaymentScreenContent(
        state = PaymentState(
            isLoading = false,
        ),
        sendEvent = {},
    )
}
