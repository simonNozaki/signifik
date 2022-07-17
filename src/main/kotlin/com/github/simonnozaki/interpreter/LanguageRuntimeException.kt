package com.github.simonnozaki.interpreter

/**
 * minimal-interpreter base runtime exception
 */
class LanguageRuntimeException(
    override val message: String?
) : RuntimeException()
