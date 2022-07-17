package com.github.simonnozaki.interpreter

/**
 * Expression.
 */
sealed class Expression {
    data class BinaryExpression(
        val operator: Operator,
        val left: Expression,
        val right: Expression
    ) : Expression()

    data class IntegerLiteral(
        val value: Int
    ) : Expression() {
        companion object {
            fun of(v: String): IntegerLiteral {
                return IntegerLiteral(Integer.parseInt(v))
            }
        }
    }

    data class PrimitiveLiteral(
        val value: String
    ) : Expression()

    /**
     * Operator objects
     */
    sealed class Operator(
        val value: String
    ) {
        object Add : Operator("+") {
            override fun toString(): String = "Add(value=+)"
        }
        object Minus : Operator("-") {
            override fun toString(): String = "Minus(value=-)"
        }
        object Multiply : Operator("*") {
            override fun toString(): String = "Multiply(value=*)"
        }
        object Division : Operator("/") {
            override fun toString(): String = "Division(value=/)"
        }
    }
}
