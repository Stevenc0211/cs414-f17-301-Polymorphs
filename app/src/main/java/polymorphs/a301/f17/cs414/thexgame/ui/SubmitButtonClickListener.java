package polymorphs.a301.f17.cs414.thexgame.ui;

import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import polymorphs.a301.f17.cs414.thexgame.persistence.DBIOCore;

/**
 * Created by thenotoriousrog on 10/20/17.
 */

public class SubmitButtonClickListener implements View.OnClickListener {

    private String name;
    private String email;
    private String username;
    TextView usernameField;
    MainGameUI mainGameUI;

    public SubmitButtonClickListener(String name, String email, TextView usernameField, MainGameUI mainGameUI)
    {
        this.name = name;
        this.email = email;
        this.usernameField = usernameField;
        this.mainGameUI = mainGameUI;
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
        mainGameUI.createMainGameUI();
    }

}
