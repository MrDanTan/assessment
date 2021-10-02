package com.recruitment.avalog.service;


import com.recruitment.avalog.dto.DiceRollsSimulationResultsDTO;
import com.recruitment.avalog.dto.SingeRollSimulationResultDTO;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

@RunWith(DataProviderRunner.class)
public class DiceRollSimulatorServiceTest {

    private static final int SIZE_EMPTY = 0;

    @InjectMocks
    private RollSimulatorService sut;

    @Mock
    private SingleRollSimulatorService singleRollSimulatorService;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void expectCorrectResult() {
        // given
        int numberOfRolls = 1;
        int numberOfDices = 3;
        int numberOfSides = 6;
        int[] singleSimulationResult = new int[] {4, 5, 6};
        long expectedSum = 15;
        int expectedSumOccurrences = 1;

        Mockito.when(singleRollSimulatorService.simulateSingleRoll(numberOfDices, numberOfSides))
               .thenReturn(singleSimulationResult);

        // when
        DiceRollsSimulationResultsDTO result = sut.simulate(numberOfRolls, numberOfDices, numberOfSides);

        // then
        Assert.assertNotNull(result);

        List<SingeRollSimulationResultDTO> simulationResultList = result.getSimulationResults();
        Assert.assertNotNull(simulationResultList);
        Assert.assertEquals(numberOfRolls, simulationResultList.size());
        Assert.assertArrayEquals(singleSimulationResult, simulationResultList.get(0).getDiceResults());
        Assert.assertEquals(expectedSum, simulationResultList.get(0).getSum());

        Map<Long, Integer> numberOfSumOccurrences = result.getNumberOfSumOccurrences();
        Assert.assertNotNull(numberOfSumOccurrences);
        Assert.assertNotNull(numberOfSumOccurrences.get(expectedSum));
        Assert.assertEquals(expectedSumOccurrences, numberOfSumOccurrences.get(expectedSum).intValue());
    }

    @Test
    @UseDataProvider("argumentsForEmptyResults")
    public void expectEmptyResults(int numberOfRolls, int numberOfDices, int numberOfSides) {

        // when
        DiceRollsSimulationResultsDTO result = sut.simulate(numberOfRolls, numberOfDices, numberOfSides);

        // then
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getSimulationResults());
        Assert.assertNotNull(result.getNumberOfSumOccurrences());
        Assert.assertEquals(SIZE_EMPTY, result.getSimulationResults().size());
        Assert.assertEquals(SIZE_EMPTY, result.getNumberOfSumOccurrences().keySet().size());
    }

    @DataProvider
    public static Object[][] argumentsForEmptyResults() {
        return new Object[][] {
                {0 , 1 , 1},
                {1, 0, 1},
                {1, 1, 0},
                {1 , 0 , 0},
                {0 , 1 , 0},
                {0 , 0 , 1},
                {0 , 0 , 0},
                {-1 , 0 , 0},
                {0 , -1 , 0},
                {0 , 0 , -1}
        };
    }


}
