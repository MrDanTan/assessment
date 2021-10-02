package com.recruitment.avalog.service;

import org.springframework.stereotype.Service;

import java.util.Random;


/**
 * Class responsible for simulation of single dice throw.
 *
 * @author  MrDanTan
 */
@Service
public class SingleRollSimulatorService {

    private static final int MINIMAL_VALUE_ON_DICE = 1;

    /**
     * Method with logic responsible for simulation of single dice throw.
     *
     * @param numberOfDices
     * @param numberOfSides
     * @return
     */
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
