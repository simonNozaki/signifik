package com.github.simonnozaki.interpreter

/**
 * Syntax Parser
 */
class Parser {
    private val node = Node.CommandsNode()

    fun parse(context: Context): List<Expression> {
        context.skipToken("program")
        return node.parse(context)
    }

    override fun toString(): String = "[program $node]"
}
