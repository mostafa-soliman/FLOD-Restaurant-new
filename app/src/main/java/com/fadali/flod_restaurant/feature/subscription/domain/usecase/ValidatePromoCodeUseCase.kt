package com.fadali.flod_restaurant.feature.subscription.domain.usecase

import com.fadali.flod_restaurant.feature.subscription.domain.SubscriptionRepo
import com.fadali.flod_restaurant.feature.subscription.domain.model.promocode.PromoCode

class ValidatePromoCodeUseCase(
    private val repo: SubscriptionRepo
) {
    suspend operator fun invoke(promoCode: String): PromoCode? {
        val promo = repo.validatePromoCode(promoCode)
        if (promo == null) return null

        val isValid = promo.isActive && promo.maxNumberOfUsesPerUser > promo.usageCount &&
                promo.maxNumberOfUses > promo.usageCount

        return if (isValid) promo else null
    }
}
