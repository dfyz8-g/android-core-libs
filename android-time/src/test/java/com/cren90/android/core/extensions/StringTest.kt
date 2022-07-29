package com.cren90.android.core.extensions

import org.junit.Test
import kotlin.random.Random

class StringTest {

    @Test
    fun testCapitalizeWords() {
        val toTest = "this is a string that Needs to be capitalized"
        val expected = "This Is A String That Needs To Be Capitalized"
        val result = toTest.capitalizeWords()

        assert(expected == result)
    }

    @Test
    fun testByteArrayHexString() {
        val bytes = Random.Default.nextBytes(16)
        val bytesAsHex = bytes.toHexLower()
        val fromHexString = bytesAsHex.fromHex()

        assert(bytes.contentEquals(fromHexString))
    }

}