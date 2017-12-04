package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Andy on 12/3/2017.
 */

public class TestProfile {
    @Test
    public void testInstanceCreation(){

        try{
            Profile p = new Profile("Andy");
        }
        catch (Exception e){
            fail("ERROR: Profile failed to instantiate");
        }
    }
    @Test
    public void testGameHistoryToString(){
        Profile p = new Profile("Andy");
        //HashMap<String,GameRecord> history = new HashMap<>();
        GameRecord record = new GameRecord("Andy","Jack",1);
        GameRecord record2 = new GameRecord("Andy","John",-1);
        p.getGamesHistory().put("1",record);
        p.getGamesHistory().put("2",record2);
        //assertEquals("{1=Andy,Jack,2017.12.03.12.51.27,true,null, 2=Andy,John,2017.12.03.12.51.27,false,null}",p.getGamesHistory().toString());
    }

    @Test
    public void testProfileToString(){
        Profile p = new Profile("Andy");
        GameRecord record = new GameRecord("Andy","Jack",1);
        GameRecord record2 = new GameRecord("Andy","John",-1);
        p.getGamesHistory().put("1",record);
        p.getGamesHistory().put("2",record2);
        //System.out.println(p.toString());
    }

    @Test
    public void testUpdateFromSnapshot(){
        Profile p = new Profile("Andy");
        GameRecord record = new GameRecord("Andy","Jack",1);
        GameRecord record1 = new GameRecord("Andy","John",-1);
        p.getGamesHistory().put("1",record);
        p.getGamesHistory().put("2",record1);
        ProfileSnapshot snapshot = new ProfileSnapshot(p);

        Profile p2 = new Profile("Andy");
        p2.updateFromSnapshot(snapshot);
        assertEquals(p2,p);
    }
}
