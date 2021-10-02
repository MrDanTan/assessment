package com.recruitment.avalog.controller;

import com.recruitment.avalog.dto.DiceRollsSimulationResultsDTO;
import com.recruitment.avalog.service.RollSimulatorService;
import com.recruitment.avalog.service.converter.SimulationResultsToJsonResponseConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiceRollController {

    private static final int DEFAULT_NUMBER_OF_ROLLS = 100;
    private static final int DEFAULT_NUMBER_OF_DICES = 3;
    private static final int DEFAULT_NUMBER_OF_SIDES = 6;

    private RollSimulatorService rollSimulatorService;
    private SimulationResultsToJsonResponseConverterService simulationResultsToJsonResponseConverterService;

    @Autowired
    public DiceRollController (RollSimulatorService rollSimulatorService,
                               SimulationResultsToJsonResponseConverterService simulationResultsToJsonResponseConverterService) {
        this.rollSimulatorService = rollSimulatorService;
        this.simulationResultsToJsonResponseConverterService = simulationResultsToJsonResponseConverterService;
    }

    @GetMapping(value = "/get/dice-rolls", produces = "text/html")
    public String defaultDiceRoll() {
        DiceRollsSimulationResultsDTO diceRollsSimulationResultsDTO = rollSimulatorService.simulate(DEFAULT_NUMBER_OF_ROLLS,
                                                                                                    DEFAULT_NUMBER_OF_DICES,
                                                                                                    DEFAULT_NUMBER_OF_SIDES);

        return simulationResultsToJsonResponseConverterService.convertToJson(diceRollsSimulationResultsDTO);
    }

}
