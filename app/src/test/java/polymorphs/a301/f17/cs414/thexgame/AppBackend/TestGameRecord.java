package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by Andy on 12/3/2017.
 */

public class TestGameRecord {
    @Test
    public void testInstanceCreation(){
        try{
            GameRecord record = new GameRecord("Andy","Jack",true);
        }
        catch (Exception e){
            fail("ERROR: Game record failed to instantiate");
        }
    }
    @Test
    public void testToString(){
        GameRecord record = new GameRecord("Andy","Jack",true);
        System.out.println(record.toString());
    }
}
