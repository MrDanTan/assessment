package com.recruitment.avalog.controller;

import com.recruitment.avalog.dto.DiceRollsSimulationResultsDTO;
import com.recruitment.avalog.service.RollSimulatorService;
import com.recruitment.avalog.service.converter.SimulationResultsToJsonResponseConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiceRollController {

    private static final String DEFAULT_NUMBER_OF_ROLLS = "100";
    private static final String DEFAULT_NUMBER_OF_DICES = "3";
    private static final String DEFAULT_NUMBER_OF_SIDES = "6";

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
            @RequestParam(name = "number_of_rolls", defaultValue = DEFAULT_NUMBER_OF_ROLLS) int numberOfRolls,
            @RequestParam(name = "number_of_dices", defaultValue = DEFAULT_NUMBER_OF_DICES) int numberOfDices,
            @RequestParam(name = "number_of_sides", defaultValue = DEFAULT_NUMBER_OF_SIDES) int numberOfSides
    ) {
        DiceRollsSimulationResultsDTO diceRollsSimulationResultsDTO = rollSimulatorService.simulate(numberOfRolls,
                                                                                                    numberOfDices,
                                                                                                    numberOfSides);

        return simulationResultsToJsonResponseConverterService.convertToJson(diceRollsSimulationResultsDTO);
    }

}
