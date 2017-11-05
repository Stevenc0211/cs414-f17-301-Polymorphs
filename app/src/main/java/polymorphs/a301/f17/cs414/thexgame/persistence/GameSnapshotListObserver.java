package polymorphs.a301.f17.cs414.thexgame.persistence;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameSnapshot;

/**
 * Created by athai on 11/3/17.
 */

public interface GameSnapshotListObserver {
    /**
     * Triggered when a new game snapshot is added to the list
     * @param addedSnapshot - the new game snapshot
     * @param precedingSnapshotKey - the database key of the preceding element in the game snapshot list
     */
    void snapshotAdded(GameSnapshot addedSnapshot, String precedingSnapshotKey);

    /**
     * Triggered when a game snapshot is changed in the database
     * @param changedSnapshot - the changed game snapshot with updated data
     * @param precedingSnapshotKey - the database key of the preceding element in the game snapshot list
     */
    void snapshotChanged(GameSnapshot changedSnapshot, String precedingSnapshotKey);

    /**
     * Triggered when a game snapshot is removed from the database
     * @param removedSnapshot - the game snapshot that was removed
     */
    void snapshotRemoved(GameSnapshot removedSnapshot);

}
