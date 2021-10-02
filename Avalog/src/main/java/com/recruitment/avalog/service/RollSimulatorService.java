package com.recruitment.avalog.service;

import com.recruitment.avalog.dto.DiceRollsSimulationResultsDTO;
import com.recruitment.avalog.dto.SingeRollSimulationResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class responsible for a dice distribution simulation
 *
 * @author  MrDanTan
 */
@Service
public class RollSimulatorService {

    private static final Integer FIRST_OCCURRENCE = 1;

    private SingleRollSimulatorService singleRollSimulatorService;

    /**
     * Class constructor with full DI
     *
     * @param singleRollSimulatorService
     */
    @Autowired
    public RollSimulatorService(SingleRollSimulatorService singleRollSimulatorService) {
        this.singleRollSimulatorService = singleRollSimulatorService;
    }

    /**
     * Method with logic responsible for a dice distribution simulation
     *
     * @param numberOfRolls
     * @param numberOfDices
     * @param numberOfSides
     * @return
     */
    public DiceRollsSimulationResultsDTO simulate(int numberOfRolls, int numberOfDices, int numberOfSides) {
        if (!argumentsPositive(numberOfRolls, numberOfDices, numberOfSides)) {
            return prepareEmptyResults();
        }

        Map<Long, Integer> numberOfSumOccurrences = new TreeMap<>();
        List<SingeRollSimulationResultDTO> simulationResults = new LinkedList<SingeRollSimulationResultDTO>();

        for (int index = 0; index < numberOfRolls; index++) {
            int[] resultsForSingleThrow = singleRollSimulatorService.simulateSingleRoll(numberOfDices, numberOfSides);
            long singleThrowSum = sumValueForSingleThrow(resultsForSingleThrow);
            countSumOccurancesAndPutToMap(numberOfSumOccurrences, singleThrowSum);

            simulationResults.add(prepareSingleRollResultDtoObject(resultsForSingleThrow, singleThrowSum));

        }

        DiceRollsSimulationResultsDTO diceRollsSimulationResults = DiceRollsSimulationResultsDTO
                                                                        .builder()
                                                                        .simulationResults(simulationResults)
                                                                        .numberOfSumOccurrences(numberOfSumOccurrences)
                                                                        .build();
        return diceRollsSimulationResults;
    }

    private boolean argumentsPositive(int numberOfRolls, int numberOfDices, int numberOfSides) {
        return numberOfRolls > 0 && numberOfDices > 0 && numberOfSides > 0;
    }

    private DiceRollsSimulationResultsDTO prepareEmptyResults() {
        return DiceRollsSimulationResultsDTO
                .builder()
                .numberOfSumOccurrences(new TreeMap<Long, Integer>())
                .simulationResults(new LinkedList<SingeRollSimulationResultDTO>())
                .build();
    }

    private long sumValueForSingleThrow(int[] resultsForSingleThrow) {
        long result = resultsForSingleThrow[0];
        for(int index = 1; index < resultsForSingleThrow.length; index++) {
            result = result + resultsForSingleThrow[index];
        }
        return result;
    }

    private void countSumOccurancesAndPutToMap(Map<Long, Integer> numberOfSumOccurrences, long singleThrowSum) {
        if (numberOfSumOccurrences.containsKey(singleThrowSum)) {
            int currentNumberOfOccurrences = numberOfSumOccurrences.get(singleThrowSum);
            numberOfSumOccurrences.put(singleThrowSum, ++currentNumberOfOccurrences);
            return;
        }
        numberOfSumOccurrences.put(singleThrowSum, FIRST_OCCURRENCE);
    }

    private SingeRollSimulationResultDTO prepareSingleRollResultDtoObject(int[] resultsForSingleThrow, long singleThrowSum) {
        return SingeRollSimulationResultDTO.builder().diceResults(resultsForSingleThrow).sum(singleThrowSum).build();
    }

}
