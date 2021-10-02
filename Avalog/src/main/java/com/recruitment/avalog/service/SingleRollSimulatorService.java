package com.recruitment.avalog.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SingleRollSimulatorService {

    private static final int MINIMAL_VALUE_ON_DICE = 1;

    public int[] simulateSingleRoll(int numberOfDices, int numberOfSides) {
        int[] results = new int[numberOfDices];
        Random random = new Random();
        int randomBound = (numberOfSides + 1) - MINIMAL_VALUE_ON_DICE;
        for(int index = 0; index < numberOfDices; index++) {
            results[index] = random.nextInt(randomBound) + MINIMAL_VALUE_ON_DICE;
        }
        return results;
    }

}
