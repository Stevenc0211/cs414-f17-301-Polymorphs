package polymorphs.a301.f17.cs414.thexgame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import polymorphs.a301.f17.cs414.thexgame.ui.StartupScreen;

/**
 * Created by Roger Hannagan on 9/13/17.
 *
 * This class is responsible literally for setting up the initial startup of the app. This is a lot like the main class in standard java, but this is responsible for the initial setup.
 * This class will simply take a user's login information and check it in the data base and will also save the user's login to main memory so they will not have to have to type it in everytime.
 */

public class MainActivity extends Activity {

    public final int REQUEST_READ_STORAGE_PERMISSION = 4000; // needed for when activity returns after checking if the app is allowed to save data to main memory.


    // This class is what is first called when the app is started and is responsible for creating the UI and starting the necessary activities for the user to use the app.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Check if the permission to read the internal memory has been granted have been granted by the user.
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            System.out.println("Permission has not yet been granted.");
            // Should we show an explanation here?
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                // we have the option of explaining the point of needing this permission here.
            }
            else // no explanation needed, just request the permission.
            {
                System.out.println("requesting permissions for read storage now");
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE_PERMISSION);
            }
        }

    }

    // This is the method that controls what happens after a user decides on a permission at runtime.
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_STORAGE_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent startupActivity = new Intent(MainActivity.this, StartupScreen.class); // create the intent that will be needed for the starting the login activity!
                    startActivity(startupActivity); // start the activity now.

                } else {
                    // permission denied, boo! the app is going to crash now. Toast message tells the user that this permission is needed for the app to run.

                    // app will gracefully start, but the user will have to manually reenter all of their login info each time, how lame!

                    Intent startupActivity = new Intent(MainActivity.this, StartupScreen.class); // create the intent that will be needed for the starting the login activity!
                    startActivity(startupActivity); // start the activity now.
                }

                return; // none of the above possibilities happened, force a quit out of this method.
            }
        }
    }

    // this app will being showing the startup animation here because the permissions have been granted or denied.
    protected void onResume()
    {
        super.onResume();

        Intent startupActivity = new Intent(MainActivity.this, StartupScreen.class); // create the intent that will be needed for the starting the login activity!
        startActivity(startupActivity); // start the activity now.
    }
}