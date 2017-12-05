package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
        //Unable to test fully due to changing game record date/time
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
        //Unable to test fully due to changing game record date/time
        Profile p = new Profile("Andy");
        GameRecord record = new GameRecord("Andy","Jack",1);
        GameRecord record2 = new GameRecord("Andy","John",-1);
        p.getGamesHistory().put("1",record);
        p.getGamesHistory().put("2",record2);
        //System.out.println(p.toString());
    }

    @Test
    public void testUpdateFromSnapshotTrue(){
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

    @Test
    public void testUpdateFromSnapshotFalse(){
        Profile p = new Profile("Andy");
        GameRecord record = new GameRecord("Andy","Jack",1);
        GameRecord record1 = new GameRecord("Andy","John",-1);
        p.getGamesHistory().put("1",record);
        p.getGamesHistory().put("2",record1);
        ProfileSnapshot snapshot = new ProfileSnapshot(p);

        Profile p2 = new Profile("Andy");
        p2.updateFromSnapshot(snapshot);
        p2.setNickname("Jack");
        assertNotEquals(p2,p);
    }

    @Test
    public void testEqualsMethodTrue(){
        Profile p = new Profile("Andy");
        GameRecord record = new GameRecord("Andy","John",1);
        Profile p2 = new Profile("Andy");
        p.getGamesHistory().put("1",record);
        p2.getGamesHistory().put("1",record);
        assertEquals(p,p2);
    }

    @Test
    public void testEqualsMethodFalse(){
        Profile p = new Profile("Andy");
        GameRecord record = new GameRecord("Andy","John",1);
        Profile p2 = new Profile("Andy");
        p.getGamesHistory().put("1",record);
        assertNotEquals(p,p2);
    }

    @Test
    public void testSetWinRatio(){
        Profile p = new Profile("Andy");
        GameRecord record1 = new GameRecord("Andy","John",1);
        GameRecord record2 = new GameRecord("Andy","Jack",-1);
        p.getGamesHistory().put("1",record1);
        p.getGamesHistory().put("2",record2);
        p.setWinRatio();
        assertEquals(.5,p.getWinRatio(),0);
    }
}
