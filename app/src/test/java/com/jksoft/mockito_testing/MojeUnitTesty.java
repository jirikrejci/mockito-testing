package com.jksoft.mockito_testing;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class MojeUnitTesty {

    @Before
    public void initTests () {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void firstMock_working() throws Exception {
        // mock creation
        List mockedList = mock (List.class);

        ArrayList standardList = new ArrayList();
        standardList.add("one");
        standardList.add("two");


        // using mock object
        mockedList.add("one");
        mockedList.add("two");
        mockedList.clear();

        //selective, explicit, highly readable verification
        verify(mockedList).add("one");
        verify(mockedList).clear();

/*
        verify(standardList).add("one");   // tohle hlásí chybyu, že standardList není mock
        verify(standardList).clear();
*/

    }

    @Test
    public void firstStubMethodCall_working () throws Exception {
        //you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        when(mockedList.get(0)).thenReturn("first");

        // the following prints first
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

    }


    @Mock
    private Flower flowerMock;

    @Test
    public void mockFlower_working () throws Exception {
        String helpStr;

        Flower flowerMock2 = mock(Flower.class);

        flowerMock2.setColor("Cervena");
        helpStr = flowerMock2.sayColor();

        flowerMock.setColor("Cervena");
        helpStr = flowerMock.sayColor();


        Flower flowerReal = new Flower("Červená", "Fiala") ;
        flowerReal.setColor("Zelena");
        helpStr = flowerReal.sayColor();


    }

    private static final int TEST_NUMBER_OF_LEAFS = 5;
    @Test
    public void flowerStubbing_working () {
        Flower flowerMocked = mock(Flower.class);
        when(flowerMocked.getNumberOfLeafs()).thenReturn(TEST_NUMBER_OF_LEAFS);

        int result = flowerMocked.getNumberOfLeafs();
        assertEquals(TEST_NUMBER_OF_LEAFS, flowerMocked.getNumberOfLeafs());

    }

    @Test
    public void shouldMatchSingleArgument() throws ParseException, Exception {
        Date testDate = new SimpleDateFormat( "yyyy/MM/dd", Locale.US ).parse( "2016/10/19" );
        Date anotherDate = new SimpleDateFormat( "yyyy/MM/dd", Locale.US ).parse( "2016/12/05" );



        WateringScheduler shedulerMock = mock(WateringScheduler.class);
        given(shedulerMock.getNumberOfPlantsSheduledOnDate(testDate)).willReturn(99);

        int numberForWantedArgument = shedulerMock.getNumberOfPlantsSheduledOnDate(testDate);
        assertEquals(99, numberForWantedArgument);

        int numberForAnyOtherArgument = shedulerMock.getNumberOfPlantsSheduledOnDate(anotherDate);
        assertEquals(0, numberForAnyOtherArgument);  // default value for int


        // real object test - sheduler returns month number
        WateringScheduler shedulerReal = new WateringScheduler() ;
        numberForWantedArgument = shedulerReal.getNumberOfPlantsSheduledOnDate(testDate);
        assertEquals(10, numberForWantedArgument);
        numberForAnyOtherArgument = shedulerReal.getNumberOfPlantsSheduledOnDate(anotherDate);
        assertEquals(12, numberForAnyOtherArgument);

    }

    @Test
    public void shouldMatchMultipleArgument () throws Exception {
        // mock object
        Flower flowerMock = mock (Flower.class);
        given(flowerMock.smellsEstimate(anyInt(), contains("asparag"), eq("red"))).willReturn(true);  // false = bool default

        boolean resultSmells;
        resultSmells = flowerMock.smellsEstimate(9, "asparagus cestiaris", "red");
        assertEquals(true, resultSmells);

        resultSmells = flowerMock.smellsEstimate(15, "asparagus cestiaris", "red");
        assertEquals(true, resultSmells);

        resultSmells = flowerMock.smellsEstimate(9, "asparagus cestiaris", "redee");
        assertEquals(false, resultSmells);

        resultSmells = flowerMock.smellsEstimate(9, "asp cestiaris", "redee");
        assertEquals(false, resultSmells);

        //real object - reálný objek narozil od mockovbeho vyhodnocuje i pocet listu
        Flower flowerReal = new Flower("red", "Cersia Asparagus", 15);
        resultSmells = flowerReal.smellsEstimate(15, "Cersia Asparagus", "red");
        assertEquals(true, resultSmells);
        resultSmells = flowerReal.smellsEstimate(9, "Aea Confialis", "green");
        assertEquals(false, resultSmells);
    }

    @Test
    public void stubbingVoidMethodWorks () throws Exception {
        //Reálný objekt
        Flower flower = new Flower("red", "Adéla");
        flower.sleep(); // shall print Adeéla is sleeping

        // mock object

        Flower flowerMock = mock(Flower.class);

        verify(flowerMock, never()).sleep(); // sleep ještě nebyl volán

        flowerMock.sleep();  // volání sleep ještě před nastavením doNothing - asi také spouští
        verify(flowerMock).sleep();   // test - voláno právě jednou

        doNothing().when(flowerMock).sleep();  // does nothing

        flowerMock.sleep();
        verify(flowerMock, atLeastOnce()).sleep();  // alespoň jednou

        doCallRealMethod().when(flowerMock).sleep(); // call Real method

        flowerMock.sleep();
        verify(flowerMock, atLeast(2)).sleep();  // alespoň 2x
        verify(flowerMock, times(3)).sleep();    // přesně 3x
        verify(flowerMock, atMost(10)).sleep();    // maximálně 10x



/*   noAnswer  jsme zde nepochopil ani se mi nepodaril zprovoznit
        doAnswer(mockSleep()).when(flowerMock).sleep();
        flowerMock.sleep();
*/
    }

    private Answer mockSleep() {
        System.out.println("Mock flover is sleeping");
        return null;
    }


    public static final int NEW_NUMBER_OF_LEAFS = 10;
    public static final int ORIGINAL_NUMBER_OF_LEAFS = 5;
    @Test
    public void flowerSpyWorks () throws Exception {

        Flower flower = new Flower("red","orchidej", ORIGINAL_NUMBER_OF_LEAFS);
        Flower flowerSpy = spy(flower);

        willDoNothing().given(flowerSpy).setNumberOfLeafs(anyInt()); // nastavené žádná akce

        flowerSpy.setNumberOfLeafs(NEW_NUMBER_OF_LEAFS);        // volani, ktere, ale nema zadnou reakci
        verify(flowerSpy).setNumberOfLeafs(NEW_NUMBER_OF_LEAFS);  // kontrola, že proběhlo volani
        assertEquals(flowerSpy.getNumberOfLeafs(), ORIGINAL_NUMBER_OF_LEAFS); // hodnota nezmenena

    }


}