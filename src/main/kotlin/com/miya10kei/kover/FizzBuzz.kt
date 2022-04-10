package com.miya10kei.kover

class FizzBuzz {

    fun calculate(value: Int): String {
        return if (value % 15 == 0) "FizzBuzz"
        else if (value % 3 == 0) "Fizz"
        else if (value % 5 == 0) "Buzz"
        else value.toString()
    }
}