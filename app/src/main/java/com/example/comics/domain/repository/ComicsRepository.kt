package com.example.comics.domain.repository

import com.example.comics.domain.entity.Comic

interface ComicsRepository {
    suspend fun fetch(): List<Comic>
}