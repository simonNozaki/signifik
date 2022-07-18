package com.github.simonnozaki.signifik

import java.util.StringTokenizer

/**
 * Lexer
 */
class Context(
    text: String
) {
    private val stringTokenizer: StringTokenizer

    /**
     * Current token string literal
     */
    private var currentTokenLiteral: String

    /**
     * Tokens parsed in this context
     */
    private val tokens = mutableListOf<Token>()

    /**
     * The position that represents the index of `tokens`
     */
    private var currentTokenIndex = 0

    init {
        stringTokenizer = StringTokenizer(text)
        val current = nextToken()
        currentTokenLiteral = current.value
    }

    private fun isOperator(c: Char): Boolean {
        return c == '+' || c == '-' || c == '*' || c == '/'
    }

    @Throws(java.lang.Exception::class)
    private fun startDigit(c: Char): Boolean {
        return Character.isDigit(c)
    }

    /**
     * Return the next token object from an original text.
     */
    fun nextToken(): Token {
        println("current ==> $currentTokenLiteral")
        if (this.stringTokenizer.hasMoreTokens()) {
            this.currentTokenLiteral = this.stringTokenizer.nextToken()
        }
        val initial = this.currentTokenLiteral[0]

        // get token
        val token = when {
            startDigit(initial) -> Token(
                TokenType.DIGIT,
                this.currentTokenLiteral
            )
            isOperator(initial) -> Token(
                TokenType.getOperator(initial),
                this.currentTokenLiteral
            )
            this.currentTokenLiteral == "program" -> Token(
                TokenType.PROGRAM,
                this.currentTokenLiteral
            )
            this.currentTokenLiteral == "repeat" -> Token(
                TokenType.REPEAT,
                this.currentTokenLiteral
            )
            this.currentTokenLiteral == "end" -> Token(
                TokenType.END,
                this.currentTokenLiteral
            )
            else -> throw LanguageRuntimeException("${this.currentTokenLiteral} is Undefined character")
        }

        // set token to tokens and the index
        this.tokens.add(token)
        this.currentTokenIndex = this.tokens.size - 1

        return token
    }

    /**
     * Return current token
     */
    fun getCurrentToken(): Token = tokens[currentTokenIndex]

    /**
     * Return tokens so far.
     */
    fun getTokens(): List<Token> = this.tokens.toList()

    /**
     * Returns the current index of the list `tokens`
     */
    fun getPosition(): Int = this.currentTokenIndex

    fun skipToken(token: String?) {
        if (this.currentTokenLiteral != token) {
            throw LanguageRuntimeException("${this.currentTokenLiteral} is not expected token: $token")
        }
        nextToken()
    }

    @Throws(NumberFormatException::class)
    fun getCurrentPosition(): Int {
        return Integer.parseInt(this.currentTokenLiteral)
    }
}
