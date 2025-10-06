package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;

public class DecaPoleVault {

	private int score;
	private double A = 0.2797;
	private double B = 100;
	private double C = 1.35;


	CalcTrackAndField calc = new CalcTrackAndField();


	// Calculate the score based on distance and height. Measured in centimetres.
	public int calculateResult(double distance) {


					score = calc.calculateField(A, B, C, distance);

		System.out.println("The result is: " + score);
		return score;
	}

}
