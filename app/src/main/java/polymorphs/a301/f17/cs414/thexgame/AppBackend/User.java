package polymorphs.a301.f17.cs414.thexgame.AppBackend;

/**
 * Created by athai on 10/18/17.
 */

public class User {
    private String email;
    private String name;
    private String nickname;
    private Profile profile;

    public User(String name,String email,String nickname){
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        profile = new Profile(nickname);
    }

    public String getEmail(){
        return email;
    }

    public String getName(){
        return name;
    }

    public String getNickname(){
        return nickname;
    }

    public boolean equals(Object o) {
        if (!(o instanceof User)) return false;
        User otherUser = (User)o;
        return (email.equals(otherUser.email) && nickname.equals(otherUser.nickname));
    }
}
