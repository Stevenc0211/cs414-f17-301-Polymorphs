package polymorphs.a301.f17.cs414.thexgame.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.HashMap;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.Driver;
import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.R;
import polymorphs.a301.f17.cs414.thexgame.persistence.UserObserver;
import polymorphs.a301.f17.cs414.thexgame.persistence.UsernameListObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.StartScreenWrapper;

/**
 * Created by Roger Hannagan on 9/13/17.
 *
 * This class will be called after the MainActivity is called and we will start the process for users to create an account or to login
 *** Note: Logging in will be automatic once the database is started. ***
 */

public class StartupScreenActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, UserObserver, UsernameListObserver  {

    private View googleSignInButton; // holds the google sign-in button.

    private GoogleApiClient googleClient; // a GoogleApiClient used to allow users to sign in with their credentials.
    private ProgressDialog progressDialog;

    private static final int RC_SIGN_IN = 9001;
    private final int SET_USERNAME = 9002; // details what we are doing for the username.


    private String displayName;
    private String name;
    private String email;
    private String username;
    HashMap<String, String> usernames; // holds the list of people to invite keyed by the previous usernames database key

    private Activity activityTriggeringSignout;

    // This is the first thing called when this class is called and will create the startup screen UI.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_startup); // sets our startup screen as the main content view.
        usernames = new HashMap<>();

        googleSignInButton = (View) findViewById(R.id.googlebutton); // sign in with the google button here for us to be able to sign_in and register with the game!
        googleSignInButton.setOnClickListener(new View.OnClickListener() {

            // start the sign in process when the button is clicked.
            @Override
            public void onClick(View view)
            {
                signIn(); // start the sign in behavior.
            }
        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        googleClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // register to the lists here to add the new usernames, very important!
        StartScreenWrapper.setStartScreenActivity(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            // Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    // When the app is reopened
    @Override
    protected void onResume() {
        super.onResume();
        hideProgressDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if(result.isSuccess())
            {
                System.out.println("We have successfully added this account to the app!");
            }

            handleSignInResult(result);
        }
        else if(requestCode == SET_USERNAME) // if the request code came from SetUsernameActivity.
        {
            Bundle d = data.getBundleExtra("results");
            username = d.getString("username"); // this comes back fine.

            DBIOCore.getInstance().setCurrentUserUsername(username); // set the username grabbed from the activity.
            delayHandler.post(transferToHomescreen);
        }
    }

    Handler delayHandler = new Handler();

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount(); // grabs this users account which can be used to assign the name to accounts in our game!!
            displayName = acct.getDisplayName(); // get this user's display name, pretty awesome!
            email = acct.getEmail(); // get this user's email

            delayHandler.post(setupCore);
            delayHandler.post(setupDriver);
        }
        else // Signed out, show unauthenticated UI.
        {}
    }

    private Runnable setupCore = new Runnable() {
        @Override
        public void run() {
            DBIOCore.getInstance().setCurrentUser(displayName, email);
        }
    };


    /**
     * This class just starts the homescreen. It is used to let the DBIOCore populate the current user before the homescreen loads
     */
    private Runnable setupDriver = new Runnable() {
        public void run() {
            if (DBIOCore.getInstance().getCurrentUserUsername() == null)
            {
                System.out.println("The currentUserUsername is null, waiting for dbio to start...");
                delayHandler.postDelayed(setupDriver, 100);
            } else if (DBIOCore.getInstance().getCurrentUserUsername().equals("")) {
                // TODO: handle opening the set username screen

                System.out.println("asking to create username now!!");
                Intent setUsernameIntent = new Intent(StartupScreenActivity.this, SetUsernameActivity.class);
                startActivityForResult(setUsernameIntent, SET_USERNAME); // start the activity for intent!
            } else {
                System.out.println("Everything running smoothely, loading homescreen now...");
                Driver.getInstance();

                DBIOCore.getInstance().registerToUsernameList(StartupScreenActivity.this);
                DBIOCore.getInstance().registerToCurrentUser(StartupScreenActivity.this);
                DBIOCore.getInstance().setupProfile();
                delayHandler.post(transferToHomescreen);
            }

        }
    };

    /**
     * This class just starts the homescreen. It is used to let the DBIOCore populate the current user before the homescreen loads
     */
    private Runnable transferToHomescreen = new Runnable() {
        public void run() {
            if (Driver.getInstance().isSetup()) {
//                System.out.println("The name of the current user according to the database is: " + Driver.getInstance().getCurrentPlayerNickname());
                System.out.println("the username of the current user is in StartupScreen: " + username);
                Intent mainGameUIIntent = new Intent(StartupScreenActivity.this, HomescreenActivity.class); // main game ui intent that is sent when the app is started.
                startActivity(mainGameUIIntent);
            } else {
                delayHandler.postDelayed(transferToHomescreen, 100);
            }

        }
    };

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        System.out.println("Found the user's sign in account here");
    }

    // [START signOut] // TODO: implement this somewhere in our code to be used for users to be able to sign out if they wish.
    public void signOut(Activity currentActivity) {
        activityTriggeringSignout = currentActivity;
        googleClient.connect();
        delayHandler.post(waitForConnection);
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.

    }

    private Runnable waitForConnection = new Runnable() {
        public void run() {
            if (googleClient.isConnected()) {
                Auth.GoogleSignInApi.signOut(googleClient);
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                activityTriggeringSignout.finish();
            } else {
                delayHandler.postDelayed(waitForConnection, 1000);
            }
        }
    };

     // TODO: this came with the design, there are cases for needing to revoke access, but probably not for the scope of our application.
    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(googleClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

    // when a connection fails or gets interrupted for some reason.
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        //Log.d(TAG, "onConnectionFailed:" + connectionResult);
        // TODO: should start a log for this to figure out what exactly is going on, also display a user name.
    }


    // TODO: the progress dialog is not working, I need to change this into something that is not deprecated.
    @Override
    protected void onStop() {
        super.onStop();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    private void updateUI(boolean signedIn) {
        if (signedIn)
        {
            googleSignInButton.setVisibility(View.GONE); // make the sign in button go away if they are already signed in, if signed, in an new activity should arise though.
        }
        else // user has not signed it yet.
        {
            googleSignInButton.setVisibility(View.VISIBLE); // make the sign in button visible.
        }
    }

    @Override
    public void userUpdated(User u) {

        System.out.println("User was added to the database, was it null? " + u);

        //currentUser = u;
        name = u.getName();
        email = u.getEmail();
        username = u.getNickname();
    }

    @Override
    public void usernameAdded(String addedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, addedUsername);
    }

    @Override
    public void usernameChanged(String changedUsername, String precedingUsernameKey) {
        usernames.put(precedingUsernameKey, changedUsername);
    }

    @Override
    public void usernameRemoved(String removedUsername) {
        String rmKey = "";
        for (String key : usernames.keySet()) {
            if (usernames.get(key).equals(removedUsername)) {
                rmKey = key;
            }
        }
        if (rmKey != "") {
            usernames.remove(rmKey);
        }
    }
}
