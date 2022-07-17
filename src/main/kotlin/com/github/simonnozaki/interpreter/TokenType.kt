package com.github.simonnozaki.interpreter

/**
 * Token type enum
 */
enum class TokenType(
    val value: String
) {
    DIGIT(""),

    // control
    PROGRAM("program"),
    REPEAT("repeat"),
    END("end"),

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
    }
}
