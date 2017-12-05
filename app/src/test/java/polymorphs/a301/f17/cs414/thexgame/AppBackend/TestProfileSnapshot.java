package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Andy on 12/5/2017.
 */

public class TestProfileSnapshot {
    @Test
    public void testInstanceCreation(){
        Profile profile = new Profile("Andy");
        try{
            ProfileSnapshot snapshot = new ProfileSnapshot(profile);
        }
        catch (Exception e){
            fail("ERROR: Profile snapshot failed to instantiate");
        }
    }
    @Test
    public void testGameHistoryToString(){
        Profile profile = new Profile("Andy");
        GameRecord record = new GameRecord("Andy","John",1);
        profile.getGamesHistory().put("1",record);
        ProfileSnapshot snapshot = new ProfileSnapshot(profile);
        assertEquals(profile.getGamesHistory().toString(),snapshot.getHistString());
    }
    @Test
    public void testNicknameEquals(){
        Profile profile = new Profile("Andy");
        ProfileSnapshot snapshot = new ProfileSnapshot(profile);
        assertEquals(profile.getNickname(),snapshot.getNickname());
    }
    @Test
    public void testWinRatioEquals(){
        Profile profile = new Profile("Andy");
        ProfileSnapshot snapshot = new ProfileSnapshot(profile);
        assertEquals(profile.getWinRatio(),snapshot.getWinRatio(),0);
    }
    @Test
    public void testPicStringEquals(){
        Profile profile = new Profile("Andy");
        ProfileSnapshot snapshot = new ProfileSnapshot(profile);
        assertEquals(profile.getPicString(),snapshot.getPicString());
    }
}
