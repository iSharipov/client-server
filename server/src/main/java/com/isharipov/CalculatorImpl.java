package com.isharipov;

/**
 * Created by Илья on 12.09.2016.
 */
public class CalculatorImpl implements Calculator {
    @Override
    public double calculate(Integer a, Integer b) {
        return a * b;
    }
}
