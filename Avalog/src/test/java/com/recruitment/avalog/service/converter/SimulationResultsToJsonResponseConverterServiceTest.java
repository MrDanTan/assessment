package com.recruitment.avalog.service.converter;

import com.recruitment.avalog.dto.DiceRollsSimulationResultsDTO;
import com.recruitment.avalog.dto.SingeRollSimulationResultDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationResultsToJsonResponseConverterServiceTest {

    @InjectMocks
    private SimulationResultsToJsonResponseConverterService sut;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void expectCorrectResults() {
        // given
        List<SingeRollSimulationResultDTO> simulationResults = new ArrayList<>();
        simulationResults.add(SingeRollSimulationResultDTO.builder().sum(1).diceResults(new int[]{1, 1}).build());
        simulationResults.add(SingeRollSimulationResultDTO.builder().sum(1000).diceResults(new int[]{6, 6, 7, 8}).build());

        Map<Long, Integer> numberOfSumOccurrences = new HashMap<>();
        numberOfSumOccurrences.put(5L, 11);

        DiceRollsSimulationResultsDTO diceRollsSimulationResults = DiceRollsSimulationResultsDTO
                .builder()
                .simulationResults(simulationResults)
                .numberOfSumOccurrences(numberOfSumOccurrences)
                .build();

        // when
        String result = sut.convertToJson(diceRollsSimulationResults);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"occurrences\":11"));
        Assert.assertTrue(result.contains("\"value\":5}"));
        Assert.assertTrue(result.contains("\"roll_values\":[1,1]"));
        Assert.assertTrue(result.contains("\"roll_values_sum\":1000"));
        Assert.assertTrue(result.contains("\"roll_values\":[6,6,7,8]"));
    }

    @Test
    public void expectEmptySimulationResultsResults() {
        // given
        List<SingeRollSimulationResultDTO> simulationResults = new ArrayList<>();

        Map<Long, Integer> numberOfSumOccurrences = new HashMap<>();
        numberOfSumOccurrences.put(5L, 11);

        DiceRollsSimulationResultsDTO diceRollsSimulationResults = DiceRollsSimulationResultsDTO
                .builder()
                .simulationResults(simulationResults)
                .numberOfSumOccurrences(numberOfSumOccurrences)
                .build();

        // when
        String result = sut.convertToJson(diceRollsSimulationResults);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"list_of_rolls\":[]"));
        Assert.assertTrue(result.contains("\"occurrences\":11"));
        Assert.assertTrue(result.contains("\"value\":5}"));
    }

    @Test
    public void expectEmptySumsResults() {
        // given
        List<SingeRollSimulationResultDTO> simulationResults = new ArrayList<>();
        simulationResults.add(SingeRollSimulationResultDTO.builder().sum(1).diceResults(new int[]{1, 1}).build());
        simulationResults.add(SingeRollSimulationResultDTO.builder().sum(1000).diceResults(new int[]{6, 6, 7, 8}).build());

        Map<Long, Integer> numberOfSumOccurrences = new HashMap<>();

        DiceRollsSimulationResultsDTO diceRollsSimulationResults = DiceRollsSimulationResultsDTO
                .builder()
                .simulationResults(simulationResults)
                .numberOfSumOccurrences(numberOfSumOccurrences)
                .build();

        // when
        String result = sut.convertToJson(diceRollsSimulationResults);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"sum_occurrences\":[]"));
        Assert.assertTrue(result.contains("\"roll_values\":[1,1]"));
        Assert.assertTrue(result.contains("\"roll_values_sum\":1000"));
        Assert.assertTrue(result.contains("\"roll_values\":[6,6,7,8]"));
    }

    @Test
    public void expectEmptyResultsForNullInput() {
        // given
        List<SingeRollSimulationResultDTO> simulationResults = null;
        Map<Long, Integer> numberOfSumOccurrences = null;

        DiceRollsSimulationResultsDTO diceRollsSimulationResults = DiceRollsSimulationResultsDTO
                .builder()
                .simulationResults(simulationResults)
                .numberOfSumOccurrences(numberOfSumOccurrences)
                .build();

        // when
        String result = sut.convertToJson(diceRollsSimulationResults);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"sum_occurrences\":[]"));
        Assert.assertTrue(result.contains("\"list_of_rolls\":[]"));
    }

}
