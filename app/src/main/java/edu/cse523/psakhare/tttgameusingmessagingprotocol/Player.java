package edu.cse523.psakhare.tttgameusingmessagingprotocol;

/**
 * Created by Priyanka on 11/11/2017.
 */
public class Player {
    private String playerName;
    private String playerSymbol;
    private String phone;

    public Player(String Name, String Symbol, String phone) {
        this.playerName=Name;
        this.playerSymbol=Symbol;
        this.phone=phone;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPlayerName(){
        return playerName;
    }
    public void setPlayerName(String player){
        this.playerName = player;
    }
    public String getPlayerSymbol(){
        return playerSymbol;
    }
    public void setPlayerSymbol(String symbol){
        this.playerSymbol = symbol;
    }
}