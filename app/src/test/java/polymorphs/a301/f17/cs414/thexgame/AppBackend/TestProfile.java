package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.Profile;

/**
 * Created by steve-0 on 11/3/17.
 */

public class TestProfile {

    @Test
    public void testNickname()
    {
        Profile prof = new Profile("Steve");

        String check = "Steve";
        AssertEquals(check,prof.getNickname());
    }
    @Test
    public void testGetGamesHistoryUI()
    {
        Profile prof = new Profile("Testing");
        ArrayList<String> history = new ArrayList<String>();
        history.add("steveVSrog");
        history.add("JGvsRusso");


        String answer = "steveVsrog"+"\n"+"JGvsRusso"+"\n";
        AssertEquals(answer,prof.getGamesHistoryUI());


    }
