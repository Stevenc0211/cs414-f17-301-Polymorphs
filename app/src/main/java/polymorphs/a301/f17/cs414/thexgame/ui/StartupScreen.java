package polymorphs.a301.f17.cs414.thexgame.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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

import polymorphs.a301.f17.cs414.thexgame.HomescreenActivity;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.R;

/**
 * Created by Roger Hannagan on 9/13/17.
 *
 * This class will be called after the MainActivity is called and we will start the process for users to create an account or to login
 *** Note: Logging in will be automatic once the database is started. ***
 */

public class StartupScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private View googleSignInButton; // holds the google sign-in button.

    private GoogleApiClient googleClient; // a GoogleApiClient used to allow users to sign in with their credentials.
    private ProgressDialog progressDialog; // TODO: deprecated, change this to something that is more recent!!!

    private static final int RC_SIGN_IN = 9001;


    // This is the first thing called when this class is called and will create the startup screen UI.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_startup); // sets our startup screen as the main content view.

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
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount(); // grabs this users account which can be used to assign the name to accounts in our game!!
            String userGoogleDisplayName = acct.getDisplayName(); // get this user's display name, pretty awesome!
            String email = acct.getEmail(); // get this user's email

            DBIOCore.setCurrentUser(userGoogleDisplayName, email); // Starting DBIOCore, removed for now to follow the general flow of adding users to the database.

            System.out.println("Log in was a success");

            Intent mainGameUIIntent = new Intent(StartupScreen.this, HomescreenActivity.class); // main game ui intent that is sent when the app is started.
            startActivity(mainGameUIIntent);
        }
        else // Signed out, show unauthenticated UI.
        {

            System.out.println("Log in was a failure");
           // Intent mainGameUIIntent = new Intent(StartupScreen.this, HomescreenActivity.class); // main game ui intent that is sent when the app is started.
            //startActivity(mainGameUIIntent);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        System.out.println("Found the user's sign in account here");
    }

    // [START signOut] // TODO: implement this somewhere in our code to be used for users to be able to sign out if they wish.
    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }

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
}
