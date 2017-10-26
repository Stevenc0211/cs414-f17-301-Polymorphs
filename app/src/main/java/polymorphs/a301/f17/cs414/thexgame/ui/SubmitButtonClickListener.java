package polymorphs.a301.f17.cs414.thexgame.ui;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import polymorphs.a301.f17.cs414.thexgame.HomescreenActivity;
import polymorphs.a301.f17.cs414.thexgame.SetUsernameActivity;
import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;

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
        // Adds the username to the user object of the new user
        DBIOCore.setCurrentUserUsername(username);
        setUsernameActivity.sendUserData(); // send the user data back to the SetUsernameActivity.
    }

}
