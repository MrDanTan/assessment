package com.recruitment.avalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class SingeRollSimulationResultDTO {

    private int[] diceResults;
    private long sum;

}
