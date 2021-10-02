package com.recruitment.avalog.dao;

import com.recruitment.avalog.entity.DiceRollSimulationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface DiceRollSimulationRepository extends JpaRepository<DiceRollSimulationEntity, Long>, JpaSpecificationExecutor<DiceRollSimulationEntity> {

    @Query("SELECT coalesce(MAX(d.simulationNumber), 0) FROM DiceRollSimulationEntity d ")
    public long getMaxSimulationId();

    @Query(value = "SELECT Count(id) number_of_simulations, Count(DISTINCT simulation_number) number_of_requests, number_of_dice, number_of_dice_sides " +
                   "FROM dice_roll_simulation " +
                   "GROUP BY number_of_dice, number_of_dice_sides",
           nativeQuery = true)
    public List<Object[]> getSimulationsAndThrowsForDiceNumberDiceSideNumberCombination();

    @Query(value = "Select COUNT(id) mumber_of_sums_obtained, throw_result_sum " +
                   "FROM dice_roll_simulation " +
                   "WHERE number_of_dice = :numberOfDice AND number_of_dice_sides = :numberOfSides " +
                   "GROUP BY throw_result_sum",
           nativeQuery = true)
    public List<Object[]> getRelativeDistribution(@Param("numberOfDice") int numberOfDice, @Param("numberOfSides") int numberOfSides);

}
