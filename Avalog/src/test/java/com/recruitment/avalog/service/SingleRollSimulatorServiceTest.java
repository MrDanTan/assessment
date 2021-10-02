package com.recruitment.avalog.service;


import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

@RunWith(DataProviderRunner.class)
public class SingleRollSimulatorServiceTest {

    @InjectMocks
    private SingleRollSimulatorService sut;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @UseDataProvider("dataForCorrectResults")
    public void expectCorrectResults(int numberOfDices, int numberOfSides) {
        // when
        int[] results = sut.simulateSingleRoll(numberOfDices, numberOfSides);

        // then
        Assert.assertEquals(numberOfDices, results.length);
        for (int index = 0; index < results.length; index++) {
            Assert.assertTrue(results[index] > 0 && results[index] <= numberOfSides);
        }
    }

    @DataProvider
    public static Object[][] dataForCorrectResults() {
        return new Object[][] {
                {1, 4},
                {2, 5},
                {3, 6},
                {4, 7},
                {5, 8},
                {6, 9},
                {6, 10}
        };
    }

    @Test
    public void expectEmptyResults() {
        // given
        int numberOfDices = 0;
        int numberOfSides = 6;

        // when
        int[] results = sut.simulateSingleRoll(numberOfDices, numberOfSides);

        // then
        Assert.assertEquals(0, results.length);
    }

    @Test(expected = IllegalArgumentException.class)
    public void expectIllegalArgumentException() {
        // given
        int numberOfDices = 3;
        int numberOfSides = -1;

        // when
        sut.simulateSingleRoll(numberOfDices, numberOfSides);
    }
}
