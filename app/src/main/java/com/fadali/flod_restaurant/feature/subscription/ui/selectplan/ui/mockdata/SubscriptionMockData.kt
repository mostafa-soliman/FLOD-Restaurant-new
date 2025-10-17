package com.fadali.flod_restaurant.feature.subscription.ui.selectplan.ui.mockdata

import com.fadali.flod_restaurant.feature.subscription.domain.model.branch.Branch
import com.fadali.flod_restaurant.feature.subscription.domain.model.category.ProductCategory
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.NutritionInfo
import com.fadali.flod_restaurant.feature.subscription.domain.model.packagedetails.PackageDetails

object SubscriptionMockData {
    val branches = listOf(
        Branch("1", "Anas ibn Malik Branch", 24.7390, 46.6535),
        Branch("2", "Uthman ibn Affan Branch", 24.7270, 46.6379),
        Branch("3", "Tahlia Street Branch", 24.6908, 46.6826),
    )

    val categories = listOf(
        ProductCategory(id = "1", displayName = "Chicken", imageUrl = "https://picsum.photos/seed/chicken/160/160"),
        ProductCategory(id = "2", displayName = "Salmon", imageUrl = "https://picsum.photos/seed/salmon/160/160"),
        ProductCategory(id = "3", displayName = "Variety", imageUrl = "https://picsum.photos/seed/variety/160/160"),
        ProductCategory(id = "4", displayName = "Beef", imageUrl = "https://picsum.photos/seed/beef/160/160"),
        ProductCategory(id = "4", displayName = "Salmon", imageUrl = "https://picsum.photos/seed/salmon2/160/160"),
        ProductCategory(id = "4", displayName = "Salmon", imageUrl = "https://picsum.photos/seed/salmon3/160/160"),
    )

    val nutritionInfo = NutritionInfo(
        id = "1",
        title = "Chicken Nutrition",
        calories = 500,
        protein = 30.0,
        carbohydrates = 50.0,
        fats = 20.0
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
            nutritionInfo = nutritionInfo,
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
            nutritionInfo = nutritionInfo,
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
            nutritionInfo = nutritionInfo,
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
            nutritionInfo = nutritionInfo,
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
            nutritionInfo = nutritionInfo,
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
            nutritionInfo = nutritionInfo,
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
            nutritionInfo = nutritionInfo,
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
            nutritionInfo = nutritionInfo,
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
            nutritionInfo = nutritionInfo,
            branches = emptyList()
        ),
    )
}
