package polymorphs.a301.f17.cs414.thexgame.persistence;


import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;

/**
 * Created by Miles on 11/14/2017.
 */

public interface GameSnapshotObserver {
    /**
     * Called when the observed user is updated in the database
     * @param u - a user object containing up to date data for the observed user
     */
    void snapshotUpdated(GameSnapshot u);
}
