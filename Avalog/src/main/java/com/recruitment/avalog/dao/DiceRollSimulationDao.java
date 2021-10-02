package com.recruitment.avalog.dao;

import com.recruitment.avalog.dto.RelativeDistributionDTO;
import com.recruitment.avalog.dto.RelativeDistributionSingelGroupDTO;
import com.recruitment.avalog.dto.SimulationThrowsGroupsDTO;
import com.recruitment.avalog.dto.SingeRollSimulationResultDTO;
import com.recruitment.avalog.entity.DiceRollSimulationEntity;
import com.recruitment.avalog.repository.DiceRollSimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiceRollSimulationDao {

    DiceRollSimulationRepository diceRollSimulationRepository;

    @Autowired
    public DiceRollSimulationDao(DiceRollSimulationRepository diceRollSimulationRepository) {
        this.diceRollSimulationRepository = diceRollSimulationRepository;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void save(List<SingeRollSimulationResultDTO> simulationResults, Integer numberOfDice, Integer numberOfSides) {

        long currentNumberOfSimulation = diceRollSimulationRepository.getMaxSimulationId() + 1;

        for (SingeRollSimulationResultDTO singleSimulation : simulationResults) {

            DiceRollSimulationEntity diceRollSimulationEntity =  DiceRollSimulationEntity
                                                                    .builder()
                                                                    .simulationNumber(currentNumberOfSimulation)
                                                                    .numberOfDice(numberOfDice)
                                                                    .numberOfDiceSides(numberOfSides)
                                                                    .throwResult(Arrays.stream(singleSimulation.getDiceResults())
                                                                                       .mapToObj(String::valueOf)
                                                                                       .collect(Collectors.joining(",")))
                                                                    .throwSum(singleSimulation.getSum())
                                                                    .build();
            diceRollSimulationRepository.save(diceRollSimulationEntity);
        }

    }

    public List<SimulationThrowsGroupsDTO> getSimulationsAndThrowsForDiceNumberDiceSideNumberCombination() {
        List<Object[]> results = diceRollSimulationRepository.getSimulationsAndThrowsForDiceNumberDiceSideNumberCombination();
        return results.stream()
                      .map(objectArray -> SimulationThrowsGroupsDTO.builder()
                                                                   .numberSimulations((BigInteger)objectArray[0])
                                                                   .numberRequests((BigInteger)objectArray[1])
                                                                   .numbeDice((int)objectArray[2])
                                                                   .numberDiceSides((int)objectArray[3])
                                                                   .build()
                      ).collect(Collectors.toList());
    }

    public RelativeDistributionDTO getRelativeDistribution(int numberOfDices, int numberOfSides) {
        long allSiumulationCount = diceRollSimulationRepository.count();
        List<Object[]> results = diceRollSimulationRepository.getRelativeDistribution(numberOfDices, numberOfSides);

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(4);

        RelativeDistributionDTO relativeDistributionDTO = new RelativeDistributionDTO();
        relativeDistributionDTO.setNumberOfAllSimulations(allSiumulationCount);

        for (Object[] singleRow : results) {
            BigInteger numberOfSumsObtained = (BigInteger) singleRow[0];
            double relativeDistribution = numberOfSumsObtained.doubleValue() / allSiumulationCount;

            RelativeDistributionSingelGroupDTO singleGroup = RelativeDistributionSingelGroupDTO
                                                                    .builder()
                                                                    .sumObtainedDuringSimulation((BigInteger) singleRow[1])
                                                                    .numberOfSumsObtained(numberOfSumsObtained)
                                                                    .relativeDistribution(decimalFormat.format(relativeDistribution))
                                                                    .build();
            relativeDistributionDTO.getRelativeDistributionSingelGroupList().add(singleGroup);
        }

        return relativeDistributionDTO;
    }

}
