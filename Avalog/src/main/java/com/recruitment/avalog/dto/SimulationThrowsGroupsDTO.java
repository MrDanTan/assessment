package com.recruitment.avalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Builder
public class SimulationThrowsGroupsDTO {

    private BigInteger numberSimulations;
    private BigInteger numberRequests;
    private int numbeDice;
    private int numberDiceSides;

}
