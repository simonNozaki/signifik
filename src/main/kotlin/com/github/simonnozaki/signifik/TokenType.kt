package com.github.simonnozaki.signifik

/**
 * Token type enum
 */
enum class TokenType(
    val value: String
) {
    DIGIT(""),
    IDENTIFIER(""),

    // control
    PROGRAM("program"),
    REPEAT("repeat"),
    END("end"),
    ASSIGNMENT("="),

    // keywords
    EOT(""),
    VARIABLE(""),

    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVISION("/");

    companion object {
        /**
         * Get operator toke type from char
         */
        fun getOperator(operator: Char): TokenType {
            return when (operator) {
                '+' -> PLUS
                '-' -> MINUS
                '*' -> MULTIPLY
                '/' -> DIVISION
                else -> throw RuntimeException()
            }
        }

        /**
         * Return true if token type is operator
         */
        fun typeIsOperator(tokenType: TokenType): Boolean = when (tokenType) {
            PLUS -> true
            MINUS -> true
            MULTIPLY -> true
            DIVISION -> true
            else -> false
        }

        /**
         * Keywords map: keyword literal -> related token
         */
        val keywords: Map<String, TokenType> = listOf(VARIABLE).associateBy { it.value }
    }
}
