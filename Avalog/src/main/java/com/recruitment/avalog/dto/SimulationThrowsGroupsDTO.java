package com.recruitment.avalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * DTO class that stores total number of simulations and total rolls made, grouped by all existing dice number–dice side
 * combinations
 *
 * @author  MrDanTan
 */
@Getter
@Setter
@Builder
public class SimulationThrowsGroupsDTO {

    private BigInteger numberSimulations;
    private BigInteger numberRequests;
    private int numbeDice;
    private int numberDiceSides;

}
