package com.recruitment.avalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * DTO class that stores dice distribution simulation results
 *
 * @author  MrDanTan
 */
@Getter
@Setter
@Builder
public class DiceRollsSimulationResultsDTO {

    private List<SingeRollSimulationResultDTO> simulationResults;

    private Map<Long, Integer> numberOfSumOccurrences;

}
