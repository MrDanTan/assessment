package com.recruitment.avalog.dao;

import com.recruitment.avalog.dto.RelativeDistributionDTO;
import com.recruitment.avalog.dto.RelativeDistributionSingelGroupDTO;
import com.recruitment.avalog.dto.SimulationThrowsGroupsDTO;
import com.recruitment.avalog.dto.SingeRollSimulationResultDTO;
import com.recruitment.avalog.entity.DiceRollSimulationEntity;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RunWith(DataProviderRunner.class)
public class DiceRollSimulationDaoTest {

    @InjectMocks
    private DiceRollSimulationDao sut;

    @Mock
    private DiceRollSimulationRepository diceRollSimulationRepository;

    @Captor
    ArgumentCaptor<DiceRollSimulationEntity> dicerRollSimulationEntityCaptor;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @UseDataProvider("correctSimulationResultsResults")
    public void expectCorrectSave(List<SingeRollSimulationResultDTO> simulationResults, int numberOfDices, int numberOfSides) {
        // given
        long simulationNumber = 7;
        Mockito.when(diceRollSimulationRepository.getMaxSimulationId()).thenReturn(simulationNumber);
        Mockito.when(diceRollSimulationRepository.save(ArgumentMatchers.any(DiceRollSimulationEntity.class))).thenReturn(null);

        // when
        sut.save(simulationResults, numberOfDices, numberOfSides);

        // then
        Mockito.verify(diceRollSimulationRepository, Mockito.times(simulationResults.size()))
               .save(dicerRollSimulationEntityCaptor.capture());
        List<DiceRollSimulationEntity> capturedEntities =  dicerRollSimulationEntityCaptor.getAllValues();

        Assert.assertNotNull(capturedEntities);
        Assert.assertEquals(simulationResults.size(), capturedEntities.size());

        for (DiceRollSimulationEntity entity : capturedEntities) {
             Assert.assertEquals(numberOfDices, (int)entity.getNumberOfDice());
             Assert.assertEquals(numberOfSides, (int)entity.getNumberOfDiceSides());
             Assert.assertEquals(simulationNumber + 1, (long)entity.getSimulationNumber());
        }
    }

    @DataProvider
    public static Object[][] correctSimulationResultsResults() {
        List<SingeRollSimulationResultDTO> simulationResults = new ArrayList<SingeRollSimulationResultDTO>();
        simulationResults.add(SingeRollSimulationResultDTO.builder().diceResults(new int[]{2, 2, 2}).sum(6).build());
        simulationResults.add(SingeRollSimulationResultDTO.builder().diceResults(new int[]{1, 1, 1}).sum(3).build());

        return new Object[][] {
                {simulationResults, 3, 6},
                {simulationResults, 0, 1},
                {simulationResults, 1, 0},
                {simulationResults, -1, -1}
        };
    }

    @Test
    @UseDataProvider("emptySimulationResults")
    public void expectNothingWasSavedForEmptySimulationResults(List<SingeRollSimulationResultDTO> simulationResults) {
        // given

        int numberOfDices = 3;
        int numberOfSides = 6;

        Mockito.when(diceRollSimulationRepository.getMaxSimulationId()).thenReturn(7L);
        Mockito.when(diceRollSimulationRepository.save(ArgumentMatchers.any(DiceRollSimulationEntity.class))).thenReturn(null);

        // when
        sut.save(simulationResults, numberOfDices, numberOfSides);

        // then
        Mockito.verify(diceRollSimulationRepository, Mockito.times(simulationResults == null ? 0 : simulationResults.size()))
               .save(ArgumentMatchers.any(DiceRollSimulationEntity.class));
    }

    @DataProvider
    public static Object[][] emptySimulationResults() {
        return new Object[][] {
                {new ArrayList<SingeRollSimulationResultDTO>()},
                {null}
        };
    }

    @Test
    public void expectCorrectSimulationsAndThrowsForDiceNumberDiceSideNumberCombination() {
        // given
        List<Object[]> expectedResults = new ArrayList<Object[]>(); ;
        expectedResults.add(new Object[] {new BigInteger("1"), new BigInteger("1"), 1, 1});
        expectedResults.add(new Object[] {new BigInteger("1"), new BigInteger("2"), 3, 4});

        Mockito.when(diceRollSimulationRepository.getSimulationsAndThrowsForDiceNumberDiceSideNumberCombination())
                .thenReturn(expectedResults);

        // when
        List<SimulationThrowsGroupsDTO> results = sut.getGroupsForDiceNumberDiceSideNumberCombination();

        // then
        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.size());

        int index = 0;
        for (SimulationThrowsGroupsDTO singleResult : results) {
            Assert.assertEquals(expectedResults.get(index)[0], singleResult.getNumberSimulations());
            Assert.assertEquals(expectedResults.get(index)[1], singleResult.getNumberRequests());
            Assert.assertEquals(expectedResults.get(index)[2], singleResult.getNumbeDice());
            Assert.assertEquals(expectedResults.get(index)[3], singleResult.getNumberDiceSides());
            index++;
        }
    }

    @Test
    public void expectEmptyResultsForSimulationsAndThrowsForDiceNumberDiceSideNumberCombination() {
        // given
        List<Object[]> expectedResults = new ArrayList<Object[]>(); ;

        Mockito.when(diceRollSimulationRepository.getSimulationsAndThrowsForDiceNumberDiceSideNumberCombination())
                .thenReturn(expectedResults);

        // when
        List<SimulationThrowsGroupsDTO> results = sut.getGroupsForDiceNumberDiceSideNumberCombination();

        // then
        Assert.assertNotNull(results);
        Assert.assertEquals(0, results.size());
    }

    @Test
    public void expectCorrectResultsForRelativeDistribution() {
        // given
        long numberOfAllSimulations = 10;
        int numberOfDices = 3;
        int numberOfSides = 6;

        String[] relativeDistributionsExpected = new String[] {"0,5", "0,2"};
        List<Object[]> expectedResults = new ArrayList<Object[]>(); ;
        expectedResults.add(new Object[] {new BigInteger("5"), new BigInteger("10")});
        expectedResults.add(new Object[] {new BigInteger("2"), new BigInteger("7")});

        Mockito.when(diceRollSimulationRepository.count()).thenReturn(numberOfAllSimulations);
        Mockito.when(diceRollSimulationRepository.getRelativeDistribution(numberOfDices, numberOfSides)).thenReturn(expectedResults);

        // when
        RelativeDistributionDTO results = sut.getRelativeDistribution(numberOfDices, numberOfSides);

        // then
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getNumberOfAllSimulations());
        Assert.assertEquals(expectedResults.size(), results.getRelativeDistributionSingelGroupList().size());
        Assert.assertEquals(numberOfAllSimulations, results.getNumberOfAllSimulations());

        int index = 0;
        for(RelativeDistributionSingelGroupDTO relativeDistributionGroup : results.getRelativeDistributionSingelGroupList()) {
            Assert.assertEquals(expectedResults.get(index)[0], relativeDistributionGroup.getNumberOfSumsObtained());
            Assert.assertEquals(expectedResults.get(index)[1], relativeDistributionGroup.getSumObtainedDuringSimulation());
            Assert.assertEquals(relativeDistributionsExpected[index], relativeDistributionGroup.getRelativeDistribution());
            index++;
        }
    }

    @Test
    public void expectEmptyResultsForRelativeDistribution() {
        // given
        long numberOfAllSimulations = 10;
        int numberOfDices = 3;
        int numberOfSides = 6;

        List<Object[]> expectedResults = new ArrayList<Object[]>(); ;

        Mockito.when(diceRollSimulationRepository.count()).thenReturn(numberOfAllSimulations);
        Mockito.when(diceRollSimulationRepository.getRelativeDistribution(numberOfDices, numberOfSides)).thenReturn(expectedResults);

        // when
        RelativeDistributionDTO results = sut.getRelativeDistribution(numberOfDices, numberOfSides);

        // then
        Assert.assertNotNull(results);
        Assert.assertNotNull(results.getNumberOfAllSimulations());
        Assert.assertEquals(expectedResults.size(), results.getRelativeDistributionSingelGroupList().size());
    }
}
