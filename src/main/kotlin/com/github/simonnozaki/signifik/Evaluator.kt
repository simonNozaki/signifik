package com.github.simonnozaki.signifik

import com.github.simonnozaki.signifik.Expression.BinaryExpression
import com.github.simonnozaki.signifik.Expression.IntegerLiteral
import com.github.simonnozaki.signifik.Expression.Operator.Add
import com.github.simonnozaki.signifik.Expression.Operator.Division
import com.github.simonnozaki.signifik.Expression.Operator.Minus
import com.github.simonnozaki.signifik.Expression.Operator.Multiply
import com.github.simonnozaki.signifik.Expression.PrimitiveLiteral

/**
 * Evaluator. This class evaluate expression.
 */
class Evaluator(
    val environment: Environment = Environment()
) {

    /**
     * Evaluate an expression and return value
     */
    fun evaluate(expression: Expression): Any {
        return when (expression) {
            is IntegerLiteral -> Int(expression.value)
            // TODO maybe change to control statement
            is PrimitiveLiteral -> Primitive(expression.value)
            is BinaryExpression -> {
                val left = expression.left
                val right = expression.right
                val operator = expression.operator

                // when increasing literals, these are to private functions...
                if (left is IntegerLiteral && right is IntegerLiteral) {
                    val result = when (operator) {
                        Add -> left.value + right.value
                        Minus -> left.value - right.value
                        Multiply -> left.value * right.value
                        Division -> left.value / right.value
                    }
                    return Int(result)
                }
                throw LanguageRuntimeException("Unknown expression: $expression")
            }
            else -> throw LanguageRuntimeException("$expression is not expected expression.")
        }
    }

    /**
     * Base interface for primitive types
     */
    interface Any {
        fun inspect(): String
    }

    data class Int(
        private val value: kotlin.Int
    ) : Any {
        override fun inspect(): String = value.toString()
    }

    data class Primitive(
        private val value: String
    ) : Any {
        override fun inspect(): String = value.toString()
    }

    data class Environment(
        val value: MutableMap<String, Any> = mutableMapOf()
    )
}
