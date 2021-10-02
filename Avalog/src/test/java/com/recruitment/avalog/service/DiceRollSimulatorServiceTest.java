package com.recruitment.avalog.service;


import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class DiceRollSimulatorServiceTest {

    @InjectMocks
    private RollSimulatorService sut;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


}
