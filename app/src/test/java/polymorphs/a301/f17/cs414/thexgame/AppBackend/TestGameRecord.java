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
            GameRecord record = new GameRecord("Andy","Jack",1);
        }
        catch (Exception e){
            fail("ERROR: Game record failed to instantiate");
        }
    }
    @Test
    public void testToString(){
        //unable to fully test due to changing date/time when game record is created
        GameRecord record = new GameRecord("Andy","Jack",1);
        System.out.println(record.toString());
    }
}
