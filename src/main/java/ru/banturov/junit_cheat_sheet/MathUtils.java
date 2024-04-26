package ru.banturov.junit_cheat_sheet;

public class MathUtils {

	public int add(int a, int b) {
		return a + b;
	}
	
	public int substract(int a, int b) {
		return a - b;
	}
	
	public int multiply(int a, int b) {
		return a * b;
	}
	
	public int divide(int a, int b) {
		return a / b;
	}
	
	public double computeCircleArea(double radius) {
		return Math.PI * Math.pow(radius, 2);
	}
	
}
