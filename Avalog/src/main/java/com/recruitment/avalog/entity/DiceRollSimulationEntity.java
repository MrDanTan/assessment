package com.recruitment.avalog.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@Table(name = "dice_roll_simulation")
public class DiceRollSimulationEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "simulation_number")
    private Long simulationNumber;

    @Column(name = "number_of_dice")
    private Integer numberOfDice;

    @Column(name = "number_of_dice_sides")
    private Integer numberOfDiceSides;

    @Column(name = "throw_result_sum")
    private Long throwSum;

    @Column(name = "throw_result")
    private String throwResult;

}
