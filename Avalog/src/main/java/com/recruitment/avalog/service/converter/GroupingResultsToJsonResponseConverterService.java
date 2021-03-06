package com.recruitment.avalog.service.converter;


import com.recruitment.avalog.dto.RelativeDistributionDTO;
import com.recruitment.avalog.dto.RelativeDistributionSingelGroupDTO;
import com.recruitment.avalog.dto.SimulationThrowsGroupsDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class responsible for rewriting data stored in dto's to json results
 *
 * @author  MrDanTan
 */
@Service
public class GroupingResultsToJsonResponseConverterService {

    private static final String NUMBER_OF_DICE_IN_SIMULATION_KEY = "number_of_dice_in_simulation";
    private static final String NUMBER_OF_SIDES_IN_DICE_KEY = "number_of_sides_in_dice";
    private static final String NUMBER_OF_REQUESTS_KEY = "number_of_requests";
    private static final String NUMBER_SIMULATIONS_KEY = "number_of_simulations";
    private static final String GROUPS_KEY = "groups";

    private static final String NUMBER_OF_DICE_KEY = "number_of_dice";
    private static final String NUMBER_OF_ALL_SIMULATIONS_KEY = "number_of_all_simulations";
    private static final String SUM_VALUE_KEY = "sum_value";
    private static final String NUMBER_OF_SUMS_OBTAINED_KEY = "number_of_sums_obtained";
    private static final String RELATIVE_DISTRIBUTION_KEY = "relative_distribution";
    private static final String RELATIVE_DISTRIBUTION_LIST_KEY = "relative_distribution_list";

    /**
     * Method rewrites results of simulations and total rolls made, grouped by all existing dice number–dice side
     * combinations to json string
     *
     * @param simulationThrowsGroupsList
     * @return
     */
    public String groupedDataForDiceNumberAndDiceSidesCombinationJsonResponse(List<SimulationThrowsGroupsDTO> simulationThrowsGroupsList) {
        JSONObject jsonFormatResults = new JSONObject();
        if (simulationThrowsGroupsList == null || simulationThrowsGroupsList.isEmpty()) {
            jsonFormatResults.put(GROUPS_KEY, new JSONObject[]{});
            return jsonFormatResults.toString();
        }

        for(SimulationThrowsGroupsDTO singleSimulationGroup: simulationThrowsGroupsList) {
            JSONObject simulationGroup = new JSONObject();
            simulationGroup.put(NUMBER_OF_DICE_IN_SIMULATION_KEY, singleSimulationGroup.getNumbeDice());
            simulationGroup.put(NUMBER_OF_SIDES_IN_DICE_KEY, singleSimulationGroup.getNumberDiceSides());
            simulationGroup.put(NUMBER_OF_REQUESTS_KEY, singleSimulationGroup.getNumberRequests());
            simulationGroup.put(NUMBER_SIMULATIONS_KEY, singleSimulationGroup.getNumberSimulations());
            jsonFormatResults.append(GROUPS_KEY, simulationGroup);
        }
        return jsonFormatResults.toString();
    }

    /**
     * Method rewrites relative distribution results to json string
     *
     * @param relativeDistribution
     * @param numberOfDice
     * @param numberOfSides
     * @return
     */
    public String relativeDistributionJsonResponse(RelativeDistributionDTO relativeDistribution,
                                                   Integer numberOfDice,
                                                   Integer numberOfSides) {
        JSONObject jsonFormatResults = new JSONObject();
        jsonFormatResults.put(NUMBER_OF_DICE_KEY, numberOfDice);
        jsonFormatResults.put(NUMBER_OF_SIDES_IN_DICE_KEY, numberOfSides);

        if (relativeDistribution == null) {
            return appendResultForNullResult(jsonFormatResults);
        }

        if (relativeDistribution.getRelativeDistributionSingelGroupList().isEmpty()) {
            return appendResultForEmptyResult(jsonFormatResults, relativeDistribution);
        }

        jsonFormatResults.put(NUMBER_OF_ALL_SIMULATIONS_KEY, relativeDistribution.getNumberOfAllSimulations());

        for(RelativeDistributionSingelGroupDTO singleGroup: relativeDistribution.getRelativeDistributionSingelGroupList()) {
            JSONObject simulationGroup = new JSONObject();
            simulationGroup.put(SUM_VALUE_KEY, singleGroup.getSumObtainedDuringSimulation());
            simulationGroup.put(NUMBER_OF_SUMS_OBTAINED_KEY, singleGroup.getNumberOfSumsObtained());
            simulationGroup.put(RELATIVE_DISTRIBUTION_KEY, singleGroup.getRelativeDistribution());
            jsonFormatResults.append(RELATIVE_DISTRIBUTION_LIST_KEY, simulationGroup);
        }
        return jsonFormatResults.toString();
    }

    private String appendResultForNullResult(JSONObject jsonFormatResults) {
        jsonFormatResults.put(GROUPS_KEY, new JSONObject[]{});
        jsonFormatResults.put(NUMBER_OF_ALL_SIMULATIONS_KEY, 0);
        return jsonFormatResults.toString();
    }

    private String appendResultForEmptyResult(JSONObject jsonFormatResults, RelativeDistributionDTO relativeDistribution) {
        jsonFormatResults.put(GROUPS_KEY, new JSONObject[]{});
        jsonFormatResults.put(NUMBER_OF_ALL_SIMULATIONS_KEY, relativeDistribution.getNumberOfAllSimulations());
        return jsonFormatResults.toString();
    }
}
