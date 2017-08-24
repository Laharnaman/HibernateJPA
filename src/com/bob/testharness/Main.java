package com.bob.testharness;

import java.util.Random;

import com.bob.domain.Tutor;

public class Main {

	public static void main(String[] args) {
		int min=50000;
		int max=120000;
		Random r = new Random();
		for(int i=0;i<10;i++) {
			System.out.println( r.nextInt((max - min) + 1) + min);
		}

	}

}
