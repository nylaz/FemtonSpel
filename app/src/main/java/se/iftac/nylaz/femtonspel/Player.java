package se.iftac.nylaz.femtonspel;

import java.io.Serializable;
import java.util.Comparator;

public class Player implements Comparable<Player>,Serializable{

    private String name;
    private int score;

    public Player(String name, int score){
        this.name=name;
        this.score=score;

    }

    public String getName(){
        return name;
    }

    public int getScore(){
        return score;
    }

    @Override
    public int compareTo(Player p) {
        return Comparators.SCORE.compare(this, p);
    }

    public static class Comparators {

        public static Comparator<Player> NAME = new Comparator<Player>() {
            public int compare(Player o1, Player o2) {
                return o1.name.compareTo(o2.name);
            }
        };
        public static Comparator<Player> SCORE = new Comparator<Player>() {
            public int compare(Player o1, Player o2) {
                return o1.score - o2.score;
            }
        };
    }
}
