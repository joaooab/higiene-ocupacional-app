package br.com.joaoov.ui.component

import org.junit.Test

class DayOfWeekTest {


    @Test
    fun isSenquencial() {
        val list = DayOfWeek.values().toMutableList()
        list.removeFirst()
        list.removeLast()
        val isSequencial = DayOfWeek.isSequencial(list)
        assert(isSequencial)
    }

    @Test
    fun isNotSenquencial() {
        val list = DayOfWeek.values().toMutableList()
        list.removeAt(2)
        val isSequencial = DayOfWeek.isSequencial(list)
        assert(!isSequencial)
    }
}