package com.github.simonnozaki.interpreter

val source = """
    program
      1 + 2 * 3
      repeat 2
        20 + 30
      end
    end
"""

fun main() {
    val context = Context(source)

    val expressions = Parser().parse(context)
    println("node = $expressions")
}
