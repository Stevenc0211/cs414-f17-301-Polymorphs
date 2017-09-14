package polymorphs.a301.f17.cs414.thexgame;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Roger Hannagan on 9/13/17.
 *
 * This class will be called after the MainActivity is called and we will start the process for users to create an account or to login
 *** Note: Logging in will be automatic once the database is started. ***
 */

public class StartupScreen extends Activity {

    private Button loginButton; // holds the login button.
    private Button createAccountButton; // holds the create account button.

    private String email = ""; // holds a user's email.
    private String username = ""; // holds a user's username.
    private String password = ""; // holds a user's password

    // NOTE: these will be set once we save the current user's data on the phone itself, this will grabbed by the intent itself.

    // This is the first thing called when this class is called and will create the startup screen UI.
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_startup); // sets our startup screen as the main content view.

        Intent mainActivityIntent = getIntent(); // grabs the intent sent in by our MainActivity. This is what will hold the user's current login info, as of right now there is nothing so ignore it.

        loginButton = (Button) findViewById(R.id.loginButton); // grab our login button here.
        loginButton.setOnClickListener(new View.OnClickListener() {

            // This action will control what happens when a user clicks one of the buttons.
            @Override
            public void onClick(View view)
            {
                final Dialog loginDialog = new Dialog(view.getContext()); // create a dialog message for allowing users to login.
                loginDialog.setContentView(R.layout.login_dialog); // set the main ui for the dialog for the user to interact with.
                loginDialog.setTitle("Login");
                loginDialog.show(); // show the dialog display.

                Button loginButton = (Button) loginDialog.findViewById(R.id.loginDialogButton); // grabs the button the confirm button!
                final EditText emailField = (EditText) loginDialog.findViewById(R.id.loginEmail); // text field for email
                final EditText usernameField = (EditText) loginDialog.findViewById(R.id.loginUsername); // text field for username
                final EditText passwordField = (EditText) loginDialog.findViewById(R.id.loginPassword); // text field for password.

                // create a click listener for the login button now.
                loginButton.setOnClickListener(new View.OnClickListener() {

                    // controls the behavior for when the login button is clicked.
                    @Override
                    public void onClick(View view)
                    {
                        email = emailField.getText().toString(); // grabs the data out of the email field
                        username = usernameField.getText().toString(); // grabs the data out of the username field.
                        password = passwordField.getText().toString(); // grabs the data out of the password field.

                        if(!email.isEmpty() && !username.isEmpty() && !password.isEmpty()) // make sure none of the fields are empty!
                        {
                            // TODO: take the three fields and send off to check with our database to login the player into the game, we also want to build the main UI now.

                            // TODO: we also want to check that the email and username is unique for this game. Very important!

                            loginDialog.dismiss(); // close the dialog now.
                        }
                        else // user left one of the fields blank we must now
                        {
                            Toast.makeText(getApplicationContext(), "All fields must be filled out!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        createAccountButton = (Button) findViewById(R.id.createAccountButton); // grab our create account button here.
        createAccountButton.setOnClickListener(new View.OnClickListener() {

            // sets the behavior for when the create account is created.
            @Override
            public void onClick(View view)
            {
                final Dialog createAccountDialog = new Dialog(view.getContext()); // create a dialog message for allowing users to login.
                createAccountDialog.setContentView(R.layout.create_account_dialog); // set the main ui for the dialog for the user to interact with.
                createAccountDialog.setTitle("Create Account");
                createAccountDialog.show(); // show the dialog.

                Button createAccountButton = (Button) createAccountDialog.findViewById(R.id.createAccountDialogButton); // grabs the button the confirm button!
                final EditText emailField = (EditText) createAccountDialog.findViewById(R.id.createEmail); // text field for email
                final EditText usernameField = (EditText) createAccountDialog.findViewById(R.id.createUsername); // text field for username
                final EditText passwordField = (EditText) createAccountDialog.findViewById(R.id.createPassword); // text field for password.

                // create a click listener for the login button now.
                createAccountButton.setOnClickListener(new View.OnClickListener() {

                    // controls the behavior for when the login button is clicked.
                    @Override
                    public void onClick(View view)
                    {
                        email = emailField.getText().toString(); // grabs the data out of the email field
                        username = usernameField.getText().toString(); // grabs the data out of the username field.
                        password = passwordField.getText().toString(); // grabs the data out of the password field.

                        if(!email.isEmpty() && !username.isEmpty() && !password.isEmpty()) // make sure none of the fields are empty!
                        {
                            // TODO: take the three fields and send off to check with our database to login the player into the game, we also want to build the main UI now.
                            System.out.println("testing the click notification");
                            // TODO: we also want to check that the email and username is unique for this game. Very important!

                            createAccountDialog.dismiss(); // close the dialog now.
                        }
                        else // user left one of the fields blank we must now
                        {
                            Toast.makeText(getApplicationContext(), "All fields must be filled out!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}
