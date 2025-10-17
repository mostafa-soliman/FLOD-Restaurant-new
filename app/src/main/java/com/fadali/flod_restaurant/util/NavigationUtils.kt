package com.fadali.flod_restaurant.util

import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import java.net.URLDecoder
import java.net.URLEncoder

/**
 * NavigationUtils provides utilities for creating custom NavTypes for type-safe Compose navigation.
 * This supports @Serializable data classes, non-primitive types, and collections using Kotlin serialization.
 */
object NavigationUtils {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    /**
     * Creates a custom NavType for @Serializable objects that can be used with type-safe Compose Navigation.
     * This function uses Kotlin serialization with JSON encoding and URL encoding for safe transport.
     * 
     * Usage example:
     * ```
     * @Serializable
     * data class User(val id: String, val name: String)
     * 
     * val userNavType = NavigationUtils.createNavType<User>()
     * 
     * // In your NavHost with type-safe navigation:
     * composable<UserDetail> { backStackEntry ->
     *     val userDetail = backStackEntry.toRoute<UserDetail>()
     *     UserDetailScreen(user = userDetail.user)
     * }
     * 
     * // Navigate with object:
     * navController.navigate(UserDetail(user = user))
     * ```
     * 
     * @param T The @Serializable type to create a NavType for
     * @return A custom NavType instance for the specified type
     */
    inline fun <reified T : Any> createNavType(): NavType<T> {
        return createNavType(serializer<T>())
    }

    /**
     * Creates a custom NavType with an explicit serializer.
     * Useful when you need more control over serialization.
     * 
     * @param serializer The KSerializer for the type
     * @return A custom NavType instance for the specified type
     */
    fun <T : Any> createNavType(serializer: KSerializer<T>): NavType<T> {
        return object : NavType<T>(isNullableAllowed = false) {
            override fun get(bundle: Bundle, key: String): T? {
                return bundle.getString(key)?.let { jsonString ->
                    try {
                        val decodedJson = URLDecoder.decode(jsonString, "UTF-8")
                        json.decodeFromString(serializer, decodedJson)
                    } catch (e: Exception) {
                        null
                    }
                }
            }

            override fun parseValue(value: String): T {
                return try {
                    val decodedJson = URLDecoder.decode(value, "UTF-8")
                    json.decodeFromString(serializer, decodedJson)
                } catch (e: Exception) {
                    throw IllegalArgumentException("Failed to parse navigation argument: $value", e)
                }
            }

            override fun put(bundle: Bundle, key: String, value: T) {
                val jsonString = json.encodeToString(serializer, value)
                val encodedJson = URLEncoder.encode(jsonString, "UTF-8")
                bundle.putString(key, encodedJson)
            }

            override fun serializeAsValue(value: T): String {
                val jsonString = json.encodeToString(serializer, value)
                return URLEncoder.encode(jsonString, "UTF-8")
            }
        }
    }

    /**
     * Creates a nullable custom NavType for @Serializable objects.
     * Similar to createNavType() but allows null values.
     * 
     * @param T The @Serializable type to create a NavType for
     * @return A custom nullable NavType instance for the specified type
     */
    inline fun <reified T : Any> createNullableNavType(): NavType<T?> {
        return createNullableNavType(serializer<T>())
    }

    /**
     * Creates a nullable custom NavType with an explicit serializer.
     * 
     * @param serializer The KSerializer for the type
     * @return A custom nullable NavType instance for the specified type
     */
    fun <T : Any> createNullableNavType(serializer: KSerializer<T>): NavType<T?> {
        return object : NavType<T?>(isNullableAllowed = true) {
            override fun get(bundle: Bundle, key: String): T? {
                return bundle.getString(key)?.let { jsonString ->
                    if (jsonString == "null") return null
                    try {
                        val decodedJson = URLDecoder.decode(jsonString, "UTF-8")
                        json.decodeFromString(serializer, decodedJson)
                    } catch (e: Exception) {
                        null
                    }
                }
            }

            override fun parseValue(value: String): T? {
                if (value == "null") return null
                return try {
                    val decodedJson = URLDecoder.decode(value, "UTF-8")
                    json.decodeFromString(serializer, decodedJson)
                } catch (e: Exception) {
                    throw IllegalArgumentException("Failed to parse navigation argument: $value", e)
                }
            }

            override fun put(bundle: Bundle, key: String, value: T?) {
                if (value == null) {
                    bundle.putString(key, "null")
                } else {
                    val jsonString = json.encodeToString(serializer, value)
                    val encodedJson = URLEncoder.encode(jsonString, "UTF-8")
                    bundle.putString(key, encodedJson)
                }
            }

            override fun serializeAsValue(value: T?): String {
                if (value == null) return "null"
                val jsonString = json.encodeToString(serializer, value)
                return URLEncoder.encode(jsonString, "UTF-8")
            }
        }
    }

    /**
     * Creates a custom NavType for List collections of @Serializable objects.
     * 
     * Usage example:
     * ```
     * @Serializable
     * data class User(val id: String, val name: String)
     * 
     * val userListNavType = NavigationUtils.createListNavType<User>()
     * 
     * // In your route:
     * @Serializable
     * data class UserList(val users: List<User>)
     * 
     * // Navigate:
     * navController.navigate(UserList(users = userList))
     * ```
     * 
     * @param T The @Serializable element type of the list
     * @return A custom NavType instance for List<T>
     */
    inline fun <reified T : Any> createListNavType(): NavType<List<T>> {
        return createNavType(ListSerializer(serializer<T>()))
    }

    /**
     * Creates a custom NavType for Set collections of @Serializable objects.
     * 
     * @param T The @Serializable element type of the set
     * @return A custom NavType instance for Set<T>
     */
    inline fun <reified T : Any> createSetNavType(): NavType<Set<T>> {
        return createNavType(SetSerializer(serializer<T>()))
    }

    /**
     * Creates a custom NavType for Map collections with String keys and @Serializable values.
     * 
     * @param T The @Serializable value type of the map
     * @return A custom NavType instance for Map<String, T>
     */
    inline fun <reified T : Any> createMapNavType(): NavType<Map<String, T>> {
        return createNavType(MapSerializer(
            serializer<String>(),
            serializer<T>()
        ))
    }

    /**
     * Creates a custom NavType for @Serializable enums, providing an efficient way to pass enum values.
     * 
     * Usage example:
     * ```
     * @Serializable
     * enum class UserRole { ADMIN, USER, GUEST }
     * 
     * val roleNavType = NavigationUtils.createEnumNavType<UserRole>()
     * 
     * // In your route:
     * @Serializable
     * data class Settings(val role: UserRole)
     * 
     * // Navigate:
     * navController.navigate(Settings(role = UserRole.ADMIN))
     * ```
     * 
     * @param T The @Serializable enum type to create a NavType for
     * @return A custom NavType instance for the specified enum type
     */
    inline fun <reified T : Enum<T>> createEnumNavType(): NavType<T> {
        return NavType.EnumType(T::class.java)
    }

    /**
     * Extension function to safely extract a custom object from navigation arguments.
     * Use this with the legacy string-based navigation when needed.
     * 
     * Usage example:
     * ```
     * val user = backStackEntry.arguments?.getCustomObject<User>("user", userNavType)
     * ```
     * 
     * @param key The argument key
     * @param navType The custom NavType used for this argument
     * @return The deserialized object or null if not found/invalid
     */
    fun <T : Any> Bundle.getCustomObject(key: String, navType: NavType<T>): T? {
        return navType[this, key]
    }

    /**
     * Registry for custom NavTypes that can be used with type-safe navigation.
     * Register your custom NavTypes here to use them with the type-safe navigation API.
     */
    object NavTypeRegistry {
        private val navTypes = mutableMapOf<String, NavType<*>>()

        /**
         * Register a NavType for a specific class.
         * 
         * @param clazz The class to register the NavType for
         * @param navType The NavType instance
         */
        fun <T : Any> register(clazz: Class<T>, navType: NavType<T>) {
            navTypes[clazz.name] = navType
        }

        /**
         * Register a NavType for a reified type.
         * 
         * @param navType The NavType instance
         */
        inline fun <reified T : Any> register(navType: NavType<T>) {
            register(T::class.java, navType)
        }

        /**
         * Get a NavType for a specific class.
         * 
         * @param clazz The class to get the NavType for
         * @return The NavType instance or null if not found
         */
        @Suppress("UNCHECKED_CAST")
        fun <T : Any> get(clazz: Class<T>): NavType<T>? {
            return navTypes[clazz.name] as? NavType<T>
        }

        /**
         * Get a NavType for a reified type.
         * 
         * @return The NavType instance or null if not found
         */
        inline fun <reified T : Any> get(): NavType<T>? {
            return get(T::class.java)
        }

        /**
         * Get all registered NavTypes.
         * 
         * @return A map of class names to NavType instances
         */
        fun getAll(): Map<String, NavType<*>> = navTypes.toMap()
    }

    /**
     * Utility function to register common NavTypes automatically.
     * Call this once in your application to register commonly used NavTypes.
     * 
     * Usage example:
     * ```
     * // In your Application class or main activity:
     * NavigationUtils.registerCommonNavTypes()
     * ```
     */
    inline fun <reified T : Any> registerNavType() {
        NavTypeRegistry.register(createNavType<T>())
    }

    /**
     * Utility function to register a nullable NavType.
     */
    inline fun <reified T : Any> registerNullableNavType() {
        // Note: We can't directly register nullable types in the registry
        // as they have different class representations
        // This is for manual registration when needed
    }
}
