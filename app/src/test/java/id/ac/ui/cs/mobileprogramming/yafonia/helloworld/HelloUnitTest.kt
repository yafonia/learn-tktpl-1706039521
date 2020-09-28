package id.ac.ui.cs.mobileprogramming.yafonia.helloworld

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HelloUnitTest {
    @Test
    fun printHello_isCorrect() {
        var input = "Yafonia"
        var expected = "Hello Yafonia! You've done your best today!"
        var output = ""
        val mainActivity = MainActivity()
        output = mainActivity.printHello(input)
        assertEquals(expected, output)
    }
}
