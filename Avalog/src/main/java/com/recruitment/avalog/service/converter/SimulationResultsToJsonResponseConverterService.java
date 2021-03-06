package com.recruitment.avalog.service.converter;

import com.recruitment.avalog.dto.DiceRollsSimulationResultsDTO;
import com.recruitment.avalog.dto.SingeRollSimulationResultDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Class rewrites dice distribution simulation results to json string
 *
 * @author MrDanTan
 */
@Service
public class SimulationResultsToJsonResponseConverterService {

    private static final String SINGLE_THROW_VALUES_KEY = "roll_values";
    private static final String SINGLE_THROW_SUM_KEY = "roll_values_sum";
    private static final String LIST_OF_ROLLS_KEY = "list_of_rolls";

    private static final String SUM_OCCURRENCES_VALUE_KEY = "value";
    private static final String NUMBER_OF_OCCURRENCES_KEY = "occurrences";
    private static final String LIST_OF_SUM_OCCURRENCES_KEY = "sum_occurrences";

    /**
     * Method responsible for rewriting dice distribution simulation results to json string
     *
     * @param diceRollsSimulationResults
     * @return
     */
    public String convertToJson(DiceRollsSimulationResultsDTO diceRollsSimulationResults) {
        JSONObject jsonFormatResults = new JSONObject();

        appendDiceRollsSimulationResult(diceRollsSimulationResults.getSimulationResults(), jsonFormatResults);
        appendListOfSumOccurrences(diceRollsSimulationResults.getNumberOfSumOccurrences(), jsonFormatResults);

        return jsonFormatResults.toString();
    }

    private void appendDiceRollsSimulationResult(List<SingeRollSimulationResultDTO> diceRollsSimulationResults,
                                                 JSONObject jsonFormatResults) {
        if (diceRollsSimulationResults == null || diceRollsSimulationResults.isEmpty()) {
            jsonFormatResults.put(LIST_OF_ROLLS_KEY, new JSONObject[]{});
            return;
        }

        for (SingeRollSimulationResultDTO singeRollSimulationResult : diceRollsSimulationResults) {
            JSONObject singleSumOccurrence = new JSONObject();
            singleSumOccurrence.put(SINGLE_THROW_VALUES_KEY, singeRollSimulationResult.getDiceResults());
            singleSumOccurrence.put(SINGLE_THROW_SUM_KEY, singeRollSimulationResult.getSum());
            jsonFormatResults.append(LIST_OF_ROLLS_KEY, singleSumOccurrence);
        }
    }

    private void appendListOfSumOccurrences(Map<Long, Integer> numberOfSumOccurrences,
                                            JSONObject jsonFormatResults) {
        if (numberOfSumOccurrences == null || numberOfSumOccurrences.isEmpty()) {
            jsonFormatResults.put(LIST_OF_SUM_OCCURRENCES_KEY, new JSONObject[]{});
            return;
        }

        for (Long singleSumValue :  numberOfSumOccurrences.keySet()) {
            JSONObject singleSumOccurrence = new JSONObject();
            singleSumOccurrence.put(SUM_OCCURRENCES_VALUE_KEY, singleSumValue);
            singleSumOccurrence.put(NUMBER_OF_OCCURRENCES_KEY, numberOfSumOccurrences.get(singleSumValue));
            jsonFormatResults.append(LIST_OF_SUM_OCCURRENCES_KEY, singleSumOccurrence);
        }
    }
}
