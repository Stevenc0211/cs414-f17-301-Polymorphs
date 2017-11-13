package polymorphs.a301.f17.cs414.thexgame.persistence;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;

/**
 * Created by Miles on 11/12/2017.
 */

public interface GameSnapshotObserver {
    /**
     * Called when the observed game snapshot is updated in the database
     * @param gs - a game snapshot object containing up to date data for the observed game snapshot
     */
    void snapshotUpdated(GameSnapshot gs);
}
