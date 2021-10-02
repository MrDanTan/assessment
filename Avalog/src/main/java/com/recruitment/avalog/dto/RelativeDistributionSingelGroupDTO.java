package com.recruitment.avalog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

/**
 * DTO class that stores dice relative distribution single group result
 *
 * @author  MrDanTan
 */
@Setter
@Getter
@Builder
public class RelativeDistributionSingelGroupDTO {

    private BigInteger sumObtainedDuringSimulation;
    private BigInteger numberOfSumsObtained;
    private String relativeDistribution;

}
