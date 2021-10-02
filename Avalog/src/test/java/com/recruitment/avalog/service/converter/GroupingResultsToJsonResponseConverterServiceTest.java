package com.recruitment.avalog.service.converter;

import com.recruitment.avalog.dto.RelativeDistributionDTO;
import com.recruitment.avalog.dto.RelativeDistributionSingelGroupDTO;
import com.recruitment.avalog.dto.SimulationThrowsGroupsDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GroupingResultsToJsonResponseConverterServiceTest {

    @InjectMocks
    private GroupingResultsToJsonResponseConverterService sut;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void expectCorrectResultsForGroupingQuery() {
        // given
        List<SimulationThrowsGroupsDTO> simulationThrowsGroupsList = new ArrayList<SimulationThrowsGroupsDTO>();
        simulationThrowsGroupsList.add(
                SimulationThrowsGroupsDTO.builder()
                                         .numbeDice(7)
                                         .numberDiceSides(8)
                                         .numberRequests(new BigInteger("9"))
                                         .numberSimulations(new BigInteger("2"))
                                         .build()
        );
        simulationThrowsGroupsList.add(
                SimulationThrowsGroupsDTO.builder()
                        .numbeDice(81)
                        .numberDiceSides(22)
                        .numberRequests(new BigInteger("12"))
                        .numberSimulations(new BigInteger("12"))
                        .build()
        );

        // when
        String result = sut.groupedDataForDiceNumberAndDiceSidesCombinationJsonResponse(simulationThrowsGroupsList);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"number_of_simulations\":2"));
        Assert.assertTrue(result.contains("\"number_of_requests\":9"));
        Assert.assertTrue(result.contains("\"number_of_dice_in_simulation\":7"));
        Assert.assertTrue(result.contains("\"number_of_sides_in_dice\":8"));
        Assert.assertTrue(result.contains("\"number_of_simulations\":12"));
        Assert.assertTrue(result.contains("\"number_of_requests\":12"));
        Assert.assertTrue(result.contains("\"number_of_dice_in_simulation\":81"));
        Assert.assertTrue(result.contains("\"number_of_sides_in_dice\":22"));
    }

    @Test
    public void expectEmptyResultsForGroupingQuery() {
        // given
        List<SimulationThrowsGroupsDTO> simulationThrowsGroupsList = new ArrayList<SimulationThrowsGroupsDTO>();

        // when
        String result = sut.groupedDataForDiceNumberAndDiceSidesCombinationJsonResponse(simulationThrowsGroupsList);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"groups\":[]}"));
    }

    @Test
    public void expectCorrectResultsForRelativeDistribution() {
        // given
        long numberOfAllSimulations = 10;
        int numberOfDice = 7;
        int numberOfSides = 8;

        List<RelativeDistributionSingelGroupDTO> relativeDistributionSingelGroupList = new ArrayList<RelativeDistributionSingelGroupDTO>();
        relativeDistributionSingelGroupList.add(
                RelativeDistributionSingelGroupDTO
                        .builder()
                        .numberOfSumsObtained(new BigInteger("22"))
                        .relativeDistribution("0.23222123132")
                        .sumObtainedDuringSimulation(new BigInteger("33"))
                        .build()
        );

        RelativeDistributionDTO relativeDistribution = new RelativeDistributionDTO();
        relativeDistribution.setNumberOfAllSimulations(numberOfAllSimulations);
        relativeDistribution.setRelativeDistributionSingelGroupList(relativeDistributionSingelGroupList);

        // when
        String result = sut.relativeDistributionJsonResponse(relativeDistribution, numberOfDice, numberOfSides);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"relative_distribution\":\"0.23222123132\""));
        Assert.assertTrue(result.contains("\"sum_value\":33"));
        Assert.assertTrue(result.contains("\"number_of_sums_obtained\":22"));
        Assert.assertTrue(result.contains("\"number_of_dice\":7"));
        Assert.assertTrue(result.contains("\"number_of_sides_in_dice\":8"));
        Assert.assertTrue(result.contains("\"number_of_all_simulations\":10"));
    }

    @Test
    public void expectEmptyResultsForRelativeDistribution() {
        // given
        long numberOfAllSimulations = 10;
        int numberOfDice = 7;
        int numberOfSides = 8;

        List<RelativeDistributionSingelGroupDTO> relativeDistributionSingelGroupList = new ArrayList<RelativeDistributionSingelGroupDTO>();
        RelativeDistributionDTO relativeDistribution = new RelativeDistributionDTO();
        relativeDistribution.setNumberOfAllSimulations(numberOfAllSimulations);
        relativeDistribution.setRelativeDistributionSingelGroupList(relativeDistributionSingelGroupList);

        // when
        String result = sut.relativeDistributionJsonResponse(relativeDistribution, numberOfDice, numberOfSides);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"number_of_dice\":7"));
        Assert.assertTrue(result.contains("\"number_of_sides_in_dice\":8"));
        Assert.assertTrue(result.contains("\"groups\":[]"));
        Assert.assertTrue(result.contains("\"number_of_all_simulations\":10"));
    }

    @Test
    public void expectEmptyResultsForRelativeDistributionWitNullResults() {
        // given
        long numberOfAllSimulations = 10;
        int numberOfDice = 7;
        int numberOfSides = 8;

        RelativeDistributionDTO relativeDistribution = null;


        // when
        String result = sut.relativeDistributionJsonResponse(relativeDistribution, numberOfDice, numberOfSides);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result.contains("\"number_of_dice\":7"));
        Assert.assertTrue(result.contains("\"number_of_sides_in_dice\":8"));
        Assert.assertTrue(result.contains("\"groups\":[]"));
        Assert.assertTrue(result.contains("\"number_of_all_simulations\":0"));
    }
}
