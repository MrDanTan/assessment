package com.recruitment.avalog.controller;

import com.recruitment.avalog.dto.DiceRollsSimulationResultsDTO;
import com.recruitment.avalog.service.RollSimulatorService;
import com.recruitment.avalog.service.converter.SimulationResultsToJsonResponseConverterService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;

@RestController
@Validated
@Slf4j
public class DiceRollController {

    private static final Logger LOG = LoggerFactory.getLogger(DiceRollController.class);

    private static final String DEFAULT_NUMBER_OF_ROLLS = "100";
    private static final String DEFAULT_NUMBER_OF_DICES = "3";
    private static final String DEFAULT_NUMBER_OF_SIDES = "6";

    private static final String NUMBER_OF_ROLLS_QUERY_KEY = "number_of_rolls";
    private static final String NUMBER_OF_DICES_QUERY_KEY = "number_of_dices";
    private static final String NUMBER_OF_SIDES_QUERY_KEY = "number_of_sides";

    private RollSimulatorService rollSimulatorService;
    private SimulationResultsToJsonResponseConverterService simulationResultsToJsonResponseConverterService;

    @Autowired
    public DiceRollController (RollSimulatorService rollSimulatorService,
                               SimulationResultsToJsonResponseConverterService simulationResultsToJsonResponseConverterService) {
        this.rollSimulatorService = rollSimulatorService;
        this.simulationResultsToJsonResponseConverterService = simulationResultsToJsonResponseConverterService;
    }

    @GetMapping(value = "/get/dice-rolls", produces = MediaType.APPLICATION_JSON_VALUE)
    public String customDiceRoll(
            @RequestParam(name = NUMBER_OF_ROLLS_QUERY_KEY, defaultValue = DEFAULT_NUMBER_OF_ROLLS) @Min(1) Integer numberOfRolls,
            @RequestParam(name = NUMBER_OF_DICES_QUERY_KEY, defaultValue = DEFAULT_NUMBER_OF_DICES) @Min(1) Integer numberOfDices,
            @RequestParam(name = NUMBER_OF_SIDES_QUERY_KEY, defaultValue = DEFAULT_NUMBER_OF_SIDES) @Min(4) Integer numberOfSides
    ) {
        DiceRollsSimulationResultsDTO diceRollsSimulationResultsDTO = rollSimulatorService.simulate(numberOfRolls,
                                                                                                    numberOfDices,
                                                                                                    numberOfSides);
        return simulationResultsToJsonResponseConverterService.convertToJson(diceRollsSimulationResultsDTO);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public String constraintViolationException(HttpServletRequest request, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);

        JSONObject exceptionResponse = new JSONObject();
        exceptionResponse.put("query_string_contraits",
                              String.format("{0} min value is 1, {1} min value is 1, {2} min value is 4",
                                            NUMBER_OF_ROLLS_QUERY_KEY,
                                            NUMBER_OF_DICES_QUERY_KEY,
                                            NUMBER_OF_SIDES_QUERY_KEY
                              )
        );
        exceptionResponse.put("query_string_sent", request.getQueryString());
        exceptionResponse.put("description", "Wrong query string parameter value");
        exceptionResponse.put("status", "Error");

        return exceptionResponse.toString();
    }
}
