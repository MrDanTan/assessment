package com.recruitment.avalog.service;

import com.recruitment.avalog.dto.DiceRollsSimulationResultsDTO;
import com.recruitment.avalog.dto.SingeRollSimulationResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class RollSimulatorService {

    private static final Integer FIRST_OCCURRENCE = 1;

    private SingleRollSimulatorService singleRollSimulatorService;

    @Autowired
    public RollSimulatorService(SingleRollSimulatorService singleRollSimulatorService) {
        this.singleRollSimulatorService = singleRollSimulatorService;
    }

    public DiceRollsSimulationResultsDTO simulate(int numberOfRolls, int numberOfDices, int numberOfSides) {
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
