package com.github.simonnozaki.interpreter

/**
 * Token representation
 */
data class Token(
    val tokenType: TokenType,
    val value: String
)
