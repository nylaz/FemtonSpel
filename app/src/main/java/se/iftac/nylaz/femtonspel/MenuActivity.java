package se.iftac.nylaz.femtonspel;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    static final String PICTURE = "pic";
    private static final String ATLEAST_ONE_BOX = "Atleast one of the boxes must be checked";
    private static final String JUST_ONE_BOX = "One box is enough";
    private Intent newGame;
    private Intent highScore;
    private Button buttonNewGame;
    private Button buttonHighScore;
    private Button buttonExitGame;
    private Button buttonPlay;
    private Dialog dialog;
    private CheckBox boxNum;
    private CheckBox boxPic;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        buttonNewGame = (Button) findViewById(R.id.buttonNew);
        buttonHighScore = (Button) findViewById(R.id.buttonHigh);
        buttonExitGame = (Button) findViewById(R.id.buttonExit);
        buttonNewGame.setOnClickListener(this);
        buttonHighScore.setOnClickListener(this);
        buttonExitGame.setOnClickListener(this);
        newGame = new Intent(this, MainActivity.class);
        highScore = new Intent(this, HighScoreActivity.class);
    }

    public void onClick(View view){
        if (view.getId() == buttonNewGame.getId()){
            dialog = new Dialog(MenuActivity.this);
            dialog.setContentView(R.layout.activity_menu_popup);
            boxNum = (CheckBox) dialog.findViewById(R.id.checkBox3);
            boxPic = (CheckBox) dialog.findViewById(R.id.checkBox4);
            buttonPlay = (Button) dialog.findViewById(R.id.button);
            buttonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (boxNum.isChecked() && !boxPic.isChecked()){
                        dialog.dismiss();
                        startActivity(newGame);
                    }if (boxPic.isChecked() && !boxNum.isChecked()){
                        dialog.dismiss();
                        newGame.putExtra(PICTURE,true);
                        startActivity(newGame);
                    }if (boxNum.isChecked() && boxPic.isChecked()){
                        stupid(JUST_ONE_BOX);
                    }if (!boxNum.isChecked() && !boxPic.isChecked()){
                        stupid(ATLEAST_ONE_BOX);
                    }
                }
            });
            dialog.show();
        }
        if (view.getId() == buttonHighScore.getId()){
            startActivity(highScore);
        }
        if (view.getId() == buttonExitGame.getId()){
            finish();
        }
    }

    private void stupid(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT).show();
    }
}
