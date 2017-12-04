package polymorphs.a301.f17.cs414.thexgame.persistence;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.ProfileSnapshot;

/**
 * Created by Andy on 12/2/2017.
 */

public interface ProfileSnapshotObserver {
    void snapshotUpdated(ProfileSnapshot u);
}
