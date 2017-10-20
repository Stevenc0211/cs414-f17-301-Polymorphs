package polymorphs.a301.f17.cs414.thexgame;

/**
 * Created by Miles on 10/12/2017.
 * This preforms unit test for the DBIOCore class
 */

import org.junit.Test;

import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;

import static org.junit.Assert.fail;


public class TestDBIOCore {
    @Test
    public void testInstanceCreation() {
        try {
            DBIOCore testCore = new DBIOCore();
        } catch (Exception e) {
            fail("ERROR: DBIOCore failed to instantiate");
        }
    }
}
