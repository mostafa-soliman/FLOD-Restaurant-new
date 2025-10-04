package com.fadali.flod_restaurant.feature.auth.ui.otp.mapper

import com.fadali.flod_restaurant.app.navigation.graphs.Graph
import com.fadali.flod_restaurant.app.navigation.routes.AuthRoutes
import com.fadali.flod_restaurant.app.navigation.routes.Route
import com.fadali.flod_restaurant.feature.auth.ui.otp.OtpFlowType

fun OtpFlowType.toNextScreenRoute(): Route = when (this) {
    OtpFlowType.LOGIN -> Graph.HomeGraph.startDestination
    OtpFlowType.SIGN_UP -> AuthRoutes.CompleteUserData
}
