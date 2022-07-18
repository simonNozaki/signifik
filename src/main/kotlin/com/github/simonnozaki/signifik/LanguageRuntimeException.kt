package com.github.simonnozaki.signifik

/**
 * minimal-interpreter base runtime exception
 */
class LanguageRuntimeException(
    override val message: String?
) : RuntimeException()
