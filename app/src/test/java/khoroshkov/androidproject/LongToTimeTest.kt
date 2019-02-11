package khoroshkov.androidproject

import khoroshkov.androidproject.utils.toTime
import org.junit.Test

import org.junit.Assert.*
import java.sql.Time

class LongToTimeTest {

    @Test
    fun first() {
        val duration = 173792L
        val time = Time(duration)
        assertEquals("0${time.minutes}:${time.seconds}", duration.toTime())
    }

    @Test
    fun second() {
        val duration = 193256L
        val time = Time(duration)
        assertEquals("0${time.minutes}:${time.seconds}", duration.toTime())
    }

    @Test
    fun third() {
        val duration = 127014L
        val time = Time(duration)
        assertEquals("0${time.minutes}:0${time.seconds}", duration.toTime())
    }

    @Test
    fun fourth() {
        val duration = 131227L
        val time = Time(duration)
        assertEquals("0${time.minutes}:${time.seconds}", duration.toTime())
    }
}