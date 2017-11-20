package se.iftac.nylaz.femtonspel;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Bitmap src;
    private Bitmap[][] pictures;
    private Button[][] buttons;
    private Board board;
    private Intent sendHighScore;
    private Dialog dialog;
    private EditText mText;
    private Button mOk;
    private TextView timer;
    private int counter = 0;
    private Handler handler = new Handler();
    public Runnable runnable =new Runnable(){
        public void run() {
            updateTimer();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendHighScore = new Intent(MainActivity.this, HighScoreActivity.class);
        buttons = new Button[4][4];
        int[] buttonId = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4
                , R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9
                , R.id.button10, R.id.button11, R.id.button12, R.id.button13, R.id.button14
                , R.id.button15};
        int counter = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                buttons[i][j] = (Button) findViewById(buttonId[counter++]);
                buttons[i][j].setOnClickListener(this);
            }
        }
        board = new Board();
        board.shuffleBoard();
        updateButtons(board,buttons);
        timer = (TextView) findViewById(R.id.textView3);
        runnable.run();
    }

    public void onClick(View view){
        for(int i = 0; i< 4; i++){
            for ( int j = 0; j < 4; j++){
                if(view.getId() == buttons[i][j].getId()){
                    board.moveTile(i,j);
                    updateButtons(board,buttons);
                    if (board.isSolved()){
                        handler.removeCallbacks(runnable);
                        dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.activity_popup);
                        mText = (EditText) dialog.findViewById(R.id.editText);
                        mOk = (Button) dialog.findViewById(R.id.buttonPopupOk);
                        mOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendHighScore.putExtra("HS_TIME", counter-1);
                                sendHighScore.putExtra("HS_NAME",mText.getText().toString());
                                dialog.dismiss();
                                startActivity(sendHighScore);
                                finish();
                            }
                        });
                        dialog.show();
                    }
                }
            }
        }
    }

    public void updateButtons(Board board, Button[][] buttons){
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (board.getTile(i,j).isEmpty()){
                    buttons[i][j].setVisibility(View.INVISIBLE);
                } else {
                    buttons[i][j].setVisibility(View.VISIBLE);
                    buttons[i][j].setText(board.getTile(i,j).getValue()+"");
                }
            }
        }
    }

    public void updateTimer()
    {
            timer.setText("Time: " + counter++);
            handler.postDelayed(runnable, 1000);
    }

    public Bitmap[][] cutPicture(Bitmap source) {
        Bitmap[][] bmp = new Bitmap[4][4];
        int width = source.getWidth();
        int height = source.getHeight();
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
                bmp[i][j]=Bitmap.createBitmap(source,(width*j)/4,(i*height)/4,width/4,height/4);
            }
        }
        return bmp;
    }
}
