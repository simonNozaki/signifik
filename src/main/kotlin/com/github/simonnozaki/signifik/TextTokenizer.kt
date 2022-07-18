package com.github.simonnozaki.signifik

/**
 * Text tokenizer( `StringTokenizer` custom implementation)
 */
class TextTokenizer(
    private val text: String
) {
    /**
     * Current position for delimited texts
     */
    private var currentPosition = 0

    /**
     * Terminal Position of tokens
     */
    private var maxPosition = 0

    /**
     * Delimited texts from an original text
     */
    private var limitedTexts = listOf<String>()

    fun hasMoreTokens() = currentPosition < maxPosition

    /**
     * Filter space from text tokens
     */
    fun skipSpace(): TextTokenizer {
        limitedTexts = limitedTexts.filter { it != "" }
        // Modify max position without space
        maxPosition = limitedTexts.size
        return this
    }

    /**
     * Parse and split text with regular expression
     */
    fun tokenize(regex: Regex): TextTokenizer {
        val tokenized = text.split(regex)
        limitedTexts = tokenized
        maxPosition = limitedTexts.size
        return this
    }

    /**
     * Get next token if there is a next token
     */
    fun nextToken(): String {
        currentPosition++
        if (currentPosition - 1 >= maxPosition) {
            throw NoSuchElementException()
        }

        return limitedTexts[currentPosition - 1]
    }
}
