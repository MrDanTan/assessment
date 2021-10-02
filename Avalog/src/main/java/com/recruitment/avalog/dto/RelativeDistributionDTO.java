package com.recruitment.avalog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO class that stores dice relative distribution results
 *
 * @author  MrDanTan
 */
@Setter
@Getter
public class RelativeDistributionDTO {

    public RelativeDistributionDTO() {
        relativeDistributionSingelGroupList = new ArrayList<RelativeDistributionSingelGroupDTO>();
        numberOfAllSimulations = 0;
    }

    List<RelativeDistributionSingelGroupDTO> relativeDistributionSingelGroupList;
    long numberOfAllSimulations;

}
