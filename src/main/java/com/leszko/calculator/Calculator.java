package com.leszko.calculator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
*this is a comment for the class to get rid of the javadoc error checkstyle
*not sure if this will work
*/
@Service
public class Calculator {
        final static int FIRST_CONSTANT1 = 3;
	@Cacheable("sum")
	public int sum(int a, int b) {
		return a + b;
	}
}
