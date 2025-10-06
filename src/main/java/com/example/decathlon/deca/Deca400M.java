package com.example.decathlon.deca;

import com.example.decathlon.common.CalcTrackAndField;
import com.example.decathlon.common.InputResult;

public class Deca400M {

	private int score;
	private double A = 1.53775;
	private double B = 82;
	private double C = 1.81;

	CalcTrackAndField calc = new CalcTrackAndField();

	// Calculate the score based on time. All running events.
	public int calculateResult(double runningTime) {

					score = calc.calculateTrack(A, B, C, runningTime);

		System.out.println("The result is: " + score);
		return score;
	}

}
