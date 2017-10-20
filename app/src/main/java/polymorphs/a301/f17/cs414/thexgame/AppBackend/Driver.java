package polymorphs.a301.f17.cs414.thexgame.AppBackend;

import java.util.ArrayList;

/**
 * Created by athai on 10/18/17.
 */

public class Driver {
    private ArrayList users;    //list of registered users
    private ArrayList games;

    public Driver(){
        this.users = new ArrayList<User>();
        this.games = new ArrayList<Game>();
    }

    //Creates a game
    public void createGame(User player1,User player2){
        //must check if both players are registered
        if(isRegistered(player1) && isRegistered(player2)) {
            Game game = new Game(player1, player2);
            games.add(game);
        }
        else{
            System.out.println("ERROR: BOTH PLAYERS MUST BE REGISTERED");
        }
    }

    //Create new User and register them
    public void registerUser(String name,String email,String nickname){
        User user = new User(name,email,nickname);
        users.add(user);
    }

    public boolean isRegistered(User user){
        if(users.contains(user)){
            return true;
        }
        else{
            return false;
        }
    }
}
