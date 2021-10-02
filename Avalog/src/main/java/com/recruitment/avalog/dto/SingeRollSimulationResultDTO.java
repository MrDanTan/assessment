package com.recruitment.avalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO class that stores single simulation results
 *
 * @author  MrDanTan
 */
@Setter
@Getter
@Builder
public class SingeRollSimulationResultDTO {

    private int[] diceResults;
    private long sum;

}
