package polymorphs.a301.f17.cs414.thexgame.ui;

import polymorphs.a301.f17.cs414.thexgame.ui.activities.StartupScreenActivity;

/**
 * Created by Miles on 12/2/2017. This is used to cache the google signin result generated in the StartupScreenActivity.
 * Once set the signin result instace can be retrieved later to preform signout.
 */

public class StartScreenWrapper {
    private static StartScreenWrapper wrapperInstance = null;

    StartupScreenActivity startScreenActivity = null;

    private StartScreenWrapper(StartupScreenActivity startScreenActivity) {
        this.startScreenActivity = startScreenActivity;
    }

    /**
     * Used to setup the wrapper, will store the passed StartupScreenActivity in an instance of the wrapper.
     * @param startScreenActivity - the StartupScreenActivity to store for later retrieval
     */
    public static void setStartScreenActivity(StartupScreenActivity startScreenActivity) {
        wrapperInstance = new StartScreenWrapper(startScreenActivity);
    }

    /**
     * Used to get the singleton wrapper instance. Will return null if setStartScreenActivity has not been called.
     * @return the wrapper instance or null if setStartScreenActivity has not been called.
     */
    public static StartScreenWrapper getWrapperInstance() {
        return wrapperInstance;
    }

    /**
     * Used to retrieve the StartupScreenActivity set by a call to setStartScreenActivity.
     * @return StartupScreenActivity if setStartScreenActivity has been called, null if otherwise
     */
    public StartupScreenActivity getStartScreenActivityClient() {
        return startScreenActivity;
    }
}
