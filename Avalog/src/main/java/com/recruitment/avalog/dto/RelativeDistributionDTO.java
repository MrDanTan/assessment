package com.recruitment.avalog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
