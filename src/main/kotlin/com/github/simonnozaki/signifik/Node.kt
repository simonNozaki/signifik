package com.github.simonnozaki.signifik

import com.github.simonnozaki.signifik.Expression.IntegerLiteral

/**
 * Root node of parsing
 */
sealed class Node {
    abstract fun parse(context: Context): Expression

    /**
     * Node for repeat syntax. The control will be back to the root control, `CommandsNode`
     */
    class RepeatCommandNode {
        private var position = 0
        private val node: CommandsNode = CommandsNode()
        fun parse(context: Context): List<Expression> {
            context.skipToken("repeat")
            position = context.getCurrentPosition()
            context.nextToken()
            return node.parse(context)
        }

        override fun toString(): String {
            return "[repeat $position $node]"
        }
    }

    /**
     * Primitive node. This node create concrete ASTs from tokens.
     */
    class PrimitiveCommandNode : Node() {
        private var name: String = ""

        override fun parse(context: Context): Expression {
            // Create expression from current Token
            val position = context.getPosition()
            println("### Position ===> $position; Token ===> ${context.getTokens()[position]} ###")
            val currentToken = context.getCurrentToken()
            name = currentToken.value

            // When the current is operator, create binary expression
            return if (TokenType.typeIsOperator(currentToken.tokenType)) {
                val previous = getPreviousToken(context)
                val operator = when (currentToken.tokenType) {
                    TokenType.PLUS -> Expression.Operator.Add
                    TokenType.MINUS -> Expression.Operator.Minus
                    TokenType.MULTIPLY -> Expression.Operator.Multiply
                    TokenType.DIVISION -> Expression.Operator.Division
                    else -> throw LanguageRuntimeException("")
                }
                context.skipToken(name)
                val current = context.getCurrentToken()

                Expression.BinaryExpression(operator, IntegerLiteral.of(previous.value), IntegerLiteral.of(current.value))
            } else {
                context.skipToken(name)
                IntegerLiteral.of(currentToken.value)
            }
        }

        override fun toString(): String {
            return this.name
        }
        private fun getPreviousToken(context: Context): Token {
            val position = context.getPosition()
            val previous = context.getTokens()[position - 1]
            if (previous.tokenType != TokenType.DIGIT) {
                throw LanguageRuntimeException("Arithmetic operator can not be resolved. Previous ===> $previous")
            }
            return previous
        }
    }

    /**
     * Commands node. This control until a current token is `repeat` or `end`.
     */
    class CommandsNode {
        private val commands = mutableListOf<Expression>()
        fun parse(context: Context): List<Expression> {
            while (true) {
                if (context.getCurrentToken().tokenType == TokenType.END) {
                    context.skipToken("end")
                    break
                }
                if (context.getCurrentToken().tokenType == TokenType.REPEAT) {
                    val expressions = RepeatCommandNode().parse(context)
                    commands.addAll(expressions)
                } else {
                    val expression = PrimitiveCommandNode().parse(context)
                    commands.add(expression)
                }
            }
            return this.commands.toList()
        }

        override fun toString(): String = commands.toString()
    }
}
