package com.fadali.flod_restaurant.feature.subscription.selectplan.ui.mockdata

import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails

object SubscriptionMockData {
    val branches = listOf(
        Branch(id = "1", displayName = "Branch 1, Al Malaz District, Riyadh, Saudi Arabia"),
        Branch(id = "2", displayName = "Branch 2, Al Malaz District, Riyadh, Saudi Arabia"),
    )

    val categories = listOf(
        ProductCategory(id = "1", displayName = "Chicken", imageUrl = "https://picsum.photos/seed/chicken/160/160"),
        ProductCategory(id = "2", displayName = "Salmon", imageUrl = "https://picsum.photos/seed/salmon/160/160"),
        ProductCategory(id = "3", displayName = "Variety", imageUrl = "https://picsum.photos/seed/variety/160/160"),
        ProductCategory(id = "4", displayName = "Beef", imageUrl = "https://picsum.photos/seed/beef/160/160"),
        ProductCategory(id = "4", displayName = "Salmon", imageUrl = "https://picsum.photos/seed/salmon2/160/160"),
        ProductCategory(id = "4", displayName = "Salmon", imageUrl = "https://picsum.photos/seed/salmon3/160/160"),
    )

    val packages = listOf(
        PackageDetails(
            id = "1",
            title = "Weekly Chicken Plan",
            description = "Package: 1. 7 meals per week | 1 meal per day",
            price = 400.30,
            durationDays = 12,
            mealsPerDay = 1,
            imageUrl = "https://picsum.photos/seed/chicken/160/160",
            branches = emptyList()
        ),
        PackageDetails(
            id = "2",
            title = "Weekly Chicken Plan",
            description = "Package: 2. 21 meals per week | 3 meal per day",
            price = 868.36,
            durationDays = 20,
            mealsPerDay = 3,
            imageUrl = "https://picsum.photos/seed/chicken/160/160",
            branches = emptyList()
        ),
        PackageDetails(
            id = "3",
            title = "Weekly Chicken Plan",
            description = "Package: 3. 14 meals per week | 2 meal per day",
            price = 940.50,
            durationDays = 28,
            mealsPerDay = 2,
            imageUrl = "https://picsum.photos/seed/chicken/160/160",
            branches = emptyList()
        ),
        PackageDetails(
            id = "4",
            title = "Weekly Chicken Plan",
            description = "Package: 4. 7 meals per week | 1 meal per day",
            price = 400.30,
            durationDays = 20,
            mealsPerDay = 1,
            imageUrl = "https://picsum.photos/seed/chicken/160/160",
            branches = emptyList()
        ),
        PackageDetails(
            id = "5",
            title = "Weekly Chicken Plan",
            description = "Package: 5. 14 meals per week | 3 meal per day",
            price = 868.36,
            durationDays = 28,
            mealsPerDay = 2,
            imageUrl = "https://picsum.photos/seed/chicken/160/160",
            branches = emptyList()
        ),
        PackageDetails(
            id = "6",
            title = "Weekly Chicken Plan",
            description = "Package: 6. 21 meals per week | 3 meal per day",
            price = 940.50,
            durationDays = 12,
            mealsPerDay = 3,
            imageUrl = "https://picsum.photos/seed/chicken/160/160",
            branches = emptyList()
        ),
        PackageDetails(
            id = "7",
            title = "Weekly Beef Plan",
            description = "Package: 7. 7 meals per week | 1 meal per day",
            price = 300.30,
            durationDays = 12,
            mealsPerDay = 1,
            imageUrl = "https://picsum.photos/seed/beef/160/160",
            branches = emptyList()
        ),
        PackageDetails(
            id = "8",
            title = "Weekly Beef Plan",
            description = "Package: 8. 21 meals per week | 3 meal per day",
            price = 768.36,
            durationDays = 20,
            mealsPerDay = 3,
            imageUrl = "https://picsum.photos/seed/beef/160/160",
            branches = emptyList()
        ),
        PackageDetails(
            id = "9",
            title = "Weekly Beef Plan",
            description = "Package: 9. 14 meals per week | 2 meal per day",
            price = 840.50,
            durationDays = 28,
            mealsPerDay = 2,
            imageUrl = "https://picsum.photos/seed/beef/160/160",
            branches = emptyList()
        ),
    )
}
