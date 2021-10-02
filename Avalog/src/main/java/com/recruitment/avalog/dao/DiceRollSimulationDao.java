package com.recruitment.avalog.dao;

import com.recruitment.avalog.dto.RelativeDistributionDTO;
import com.recruitment.avalog.dto.RelativeDistributionSingelGroupDTO;
import com.recruitment.avalog.dto.SimulationThrowsGroupsDTO;
import com.recruitment.avalog.dto.SingeRollSimulationResultDTO;
import com.recruitment.avalog.entity.DiceRollSimulationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class allows access to database related to DiceRollSimulationEntity
 *
 * @author MrDanTan
 */
@Service
public class DiceRollSimulationDao {

    DiceRollSimulationRepository diceRollSimulationRepository;

    /**
     * Class constructor with full DI
     *
     * @param diceRollSimulationRepository
     */
    @Autowired
    public DiceRollSimulationDao(DiceRollSimulationRepository diceRollSimulationRepository) {
        this.diceRollSimulationRepository = diceRollSimulationRepository;
    }

    /**
     * Method allows to store to database results of dice distribution simulation
     *
     * @param simulationResults
     * @param numberOfDice
     * @param numberOfSides
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void save(List<SingeRollSimulationResultDTO> simulationResults, Integer numberOfDice, Integer numberOfSides) {
        if (simulationResults == null || simulationResults.isEmpty()) {
            return;
        }

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

    /**
     * Method prepares number of simulations and total rolls made, grouped by all existing dice number–dice side
     * combinations
     *
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public List<SimulationThrowsGroupsDTO> getGroupsForDiceNumberDiceSideNumberCombination() {
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

    /**
     * Method prepares relative distribution data
     *
     * @param numberOfDices
     * @param numberOfSides
     * @return
     */
    @Transactional(isolation = Isolation.SERIALIZABLE)
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
