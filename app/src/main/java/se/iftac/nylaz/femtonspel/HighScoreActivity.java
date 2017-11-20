package se.iftac.nylaz.femtonspel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HighScoreActivity extends AppCompatActivity{

    private TextView textView;
    private List<Player> players;
    private String highScore="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        textView = (TextView) findViewById(R.id.textView);
        players=readHighscore();
        if(getIntent().hasExtra("HS_NAME")) {
            players.add(new Player(getIntent().getStringExtra("HS_NAME"), getIntent().getIntExtra("HS_TIME", 0)));
        }
        Collections.sort(players,Player.Comparators.SCORE);

        for (int i = 0; i < players.size(); i++){
            highScore=highScore+(i+1 + ". " + "\t" + players.get(i).getName() + " \t\t\t" +
                    players.get(i).getScore() + "s" + "\n");
        }
        textView.setText(highScore);
        writeHighscore(players);
    }

    private void writeHighscore(List<Player> players) {
        try {
            FileOutputStream out = openFileOutput("highscore", MODE_PRIVATE);
            ObjectOutputStream oOut = new ObjectOutputStream(out);
            if(players.size() > 9){
                ArrayList<Player> listOfPlayers = new ArrayList<Player>(players.subList(0,2));
                oOut.writeObject(listOfPlayers);
            }else{
                oOut.writeObject(players);
            }
            oOut.close();
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private List<Player> readHighscore() {
        List<Player> tempList = new ArrayList<>();
        try {
            FileInputStream in = openFileInput("highscore");
            ObjectInputStream oIn = new ObjectInputStream(in);
            tempList = (List<Player>) oIn.readObject();
            oIn.close();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tempList;
    }


}
