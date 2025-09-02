package com.example.core.network.model

import kotlinx.serialization.Serializable

/**
 * Pagination wrapper for list responses
 */
@Serializable
data class PaginatedResponse<T>(
    val data: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalItems: Long,
    val totalPages: Int,
    val hasNext: Boolean,
    val hasPrevious: Boolean
)
