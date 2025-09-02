package com.example.core.utils

@Suppress("UNCHECKED_CAST")
fun <K, V> Map<out Any, Any?>.filterNotNull(): Map<K, V> {
    return this.filterNot { it.value == null } as Map<K, V>
}
