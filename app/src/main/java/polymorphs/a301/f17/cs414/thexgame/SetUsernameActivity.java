package polymorphs.a301.f17.cs414.thexgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import polymorphs.a301.f17.cs414.thexgame.AppBackend.User;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.persistence.UserObserver;
import polymorphs.a301.f17.cs414.thexgame.ui.SubmitButtonClickListener;

/**
 * Created by thenotoriousrog on 10/26/17. This activity is for our users to be able to sign in with. Pretty important!
 */

public class SetUsernameActivity extends Activity implements UserObserver{

    // These will be populated by the shared preferences.
    private String email; // email of the user.
    private String name; // name of the user.
    private String username; // username of the user
    private User currentUser; // this is our main user which is created as soon as they start the app.
    private final int SET_USERNAME = 9001;


    private SubmitButtonClickListener submitClickListener;

    public void sendUserData()
    {
        name = submitClickListener.getName();
        email = submitClickListener.getEmail();
        username = submitClickListener.getUsername();

        System.out.println("name we want to send = " + name);
        System.out.println("email we want to send = " + email);
        System.out.println("username we want to send = " + username);

        // Send the user's data now.
        Intent resultIntent = new Intent();
        Bundle data = new Bundle();
        data.putString("name", name);
        data.putString("email", email);
        data.putString("username", username);
        resultIntent.putExtra("results", data);
        setResult(SET_USERNAME, resultIntent); // send the result back to the HomescreenActivity.
        finish(); // end the activity after setting all of the information needed for the game.
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setusername); // the layout for us to be able to send our information with.

        DBIOCore.registerToCurrentUser(this);

        final EditText textField = (EditText) findViewById(R.id.usernameField); // grab the edit text username field.
        final Button submitButton = (Button) findViewById(R.id.submitButton); // grab the submit button.


        submitClickListener = new SubmitButtonClickListener(name, email, textField, SetUsernameActivity.this);
        submitButton.setOnClickListener(submitClickListener);

        System.out.println("name from button: " + submitClickListener.getName());
        System.out.println("name from button: " + submitClickListener.getEmail());
        System.out.println("name from button: " + submitClickListener.getUsername());

    }

    @Override
    public void userUpdated(polymorphs.a301.f17.cs414.thexgame.AppBackend.User u) {
        System.out.println("User in SetUsernameActivity is " + u);
        currentUser = u;
    }

}
