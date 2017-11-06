package polymorphs.a301.f17.cs414.thexgame.persistence;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.GameRecord;

/**
 * Created by athai on 11/3/17.
 */

public interface GameRecordListObserver {
    /**
     * Triggered when a new game record is added to the list
     * @param addedRecord - the new record
     * @param precedingRecordKey - the database key of the preceding element in the game record list
     */
    void recordAdded(GameRecord addedRecord,String precedingRecordKey);

    /**
     * Triggered when a record is changed in the database
     * @param changedRecord - the changed record with updated data
     * @param precedingRecordKey - the database key of the preceding element in the game record list
     */
    void recordChanged(GameRecord changedRecord, String precedingRecordKey);

    /**
     * Triggered when a record is removed from the database
     * @param removedRecord - the record that was removed
     */
    void recordRemoved(GameRecord removedRecord);


}
