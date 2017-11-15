package polymorphs.a301.f17.cs414.thexgame.ui.listeners;

import android.view.View;
import android.widget.TextView;

import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;
import polymorphs.a301.f17.cs414.thexgame.ui.activities.SetUsernameActivity;

/**
 * Created by thenotoriousrog on 10/20/17.
 * Click listener for the submit button!
 */

public class SubmitButtonClickListener implements View.OnClickListener {

    private String name;
    private String email;
    private String username;
    TextView usernameField;
    SetUsernameActivity setUsernameActivity;

    public SubmitButtonClickListener(String name, String email, TextView usernameField, SetUsernameActivity setUsernameActivity)
    {
        this.name = name;
        this.email = email;
        this.usernameField = usernameField;
        this.setUsernameActivity = setUsernameActivity;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public void onClick(View view)
    {
        username = usernameField.getText().toString();
        // TODO: if users enter a number instead of text we must ask them to enter another
        setUsernameActivity.sendUserData(); // send the user data back to the SetUsernameActivity.
    }

}
