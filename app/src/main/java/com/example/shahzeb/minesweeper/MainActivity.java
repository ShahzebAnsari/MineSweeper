package com.example.shahzeb.minesweeper;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Vibrator vibe;
    int[] data=new int[180];
    int[] flag=new int[180];
    int[] address=new int[40];
    GridLayout textGrid;
    GridLayout buttonGrid;
    Button button;
    int box=140;

    public void makeAlphaZero(int index){
        if(index>10&&index%10!=0&&((Button)buttonGrid.getChildAt(index-11)).getAlpha()!=0){
            ((Button)buttonGrid.getChildAt(index-11)).setAlpha(0);
            box--;
            if(data[index-11]==0)
                makeAlphaZero(index-11);
        }
        if(index>9&&((Button)buttonGrid.getChildAt(index-10)).getAlpha()!=0){
            ((Button)buttonGrid.getChildAt(index-10)).setAlpha(0);
            box--;
            if(data[index-10]==0)
                makeAlphaZero(index-10);
        }
        if(index>9&&index%10!=9&&((Button)buttonGrid.getChildAt(index-9)).getAlpha()!=0){
            ((Button)buttonGrid.getChildAt(index-9)).setAlpha(0);
            box--;
            if(data[index-9]==0)
                makeAlphaZero(index-9);
        }
        if(index%10!=0&&((Button)buttonGrid.getChildAt(index-1)).getAlpha()!=0){
            ((Button)buttonGrid.getChildAt(index-1)).setAlpha(0);
            box--;
            if(data[index-1]==0)
                makeAlphaZero(index-1);
        }
        if(index%10!=9&&((Button)buttonGrid.getChildAt(index+1)).getAlpha()!=0){
            ((Button)buttonGrid.getChildAt(index+1)).setAlpha(0);
            box--;
            if(data[index+1]==0)
                makeAlphaZero(index+1);
        }
        if(index<170&&index%10!=0&&((Button)buttonGrid.getChildAt(index+9)).getAlpha()!=0){
            ((Button)buttonGrid.getChildAt(index+9)).setAlpha(0);
            box--;
            if(data[index+9]==0)
                makeAlphaZero(index+9);
        }
        if(index<170&&((Button)buttonGrid.getChildAt(index+10)).getAlpha()!=0){
            ((Button)buttonGrid.getChildAt(index+10)).setAlpha(0);
            box--;
            if(data[index+10]==0)
                makeAlphaZero(index+10);
        }
        if(index<170&&index%10!=9&&((Button)buttonGrid.getChildAt(index+11)).getAlpha()!=0){
            ((Button)buttonGrid.getChildAt(index+11)).setAlpha(0);
            box--;
            if(data[index+11]==0)
                makeAlphaZero(index+11);
        }
    }
    public void buttonPressed(View view){
        button=(Button)view;
        int index=Integer.parseInt(button.getTag().toString());
        if(flag[index]==1||flag[index]==-1)return;
        flag[index]=-1;
        (button).setAlpha(0);
        box--;
        if(data[index]==-1){
            for(int i=0;i<180;i++){
                if(flag[i]!=-1){
                    ((Button)buttonGrid.getChildAt(i)).setAlpha(0);
                    if(flag[i]==1){
                        TextView textView=(TextView)textGrid.getChildAt(i);
                        textView.setBackgroundResource(0);
                        if(data[i]!=-1&&data[i]!=0){
                            textView.setText(String.valueOf(data[i]));
                        }
                        if(data[i]==-1){
                            textView.setBackgroundResource(R.drawable.bomb);
                        }
                    }
                    flag[i]=-1;
                }
            }
            Toast.makeText(this, "Out", Toast.LENGTH_SHORT).show();
        }
        if(data[index]==0)
            makeAlphaZero(index);
        if(box==0) {
            Toast.makeText(this, "Won", Toast.LENGTH_SHORT).show();
            for(int i=0;i<180;i++){
                if(flag[i]==1){
                    ((TextView)textGrid.getChildAt(i)).setBackgroundResource(R.drawable.bomb);
                }
                flag[i]=-1;
            }
        }
    }


    public boolean isMatch(int a,int index){
        for(int i=0;i<index;i++)
            if(address[i]==a)
                return true;
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGrid=(GridLayout)findViewById(R.id.buttonGrid);
        textGrid=(GridLayout)findViewById(R.id.textGrid);
        vibe=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        for(int i=0;i<180;i++){
            data[i]=0;
            flag[i]=0;
        }
        for(int i=0;i<40;i++){
            int rand=(int)(Math.random()*180);
            while(isMatch(rand,i)){
                rand=(int)(Math.random()*180);
            }
            ((TextView)textGrid.getChildAt(rand)).setBackgroundResource(R.drawable.bomb);
            data[rand]=-1;
            address[i]=rand;
        }
        for(int i=0;i<180;i++){
            if(data[i]==-1)continue;
            if(i>10&&i%10!=0&&data[i-11]==-1)
                data[i]++;
            if(i>9&&data[i-10]==-1)
                data[i]++;
            if(i>9&&i%10!=9&&data[i-9]==-1)
                data[i]++;
            if(i%10!=0&&data[i-1]==-1)
                data[i]++;
            if(i%10!=9&&data[i+1]==-1)
                data[i]++;
            if(i<170&&i%10!=0&&data[i+9]==-1)
                data[i]++;
            if(i<170&&data[i+10]==-1)
                data[i]++;
            if(i<170&&i%10!=9&&data[i+11]==-1)
                data[i]++;
            if(data[i]!=0)
            ((TextView)textGrid.getChildAt(i)).setText(String.valueOf(data[i]));
        }
        for(int i=0;i<180;i++) {
            ((Button) buttonGrid.getChildAt(i)).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int index=Integer.parseInt(((Button)v).getTag().toString());
                    if(flag[index]==-1)return true;
                    vibe.vibrate(50);
                    TextView textView=(TextView)textGrid.getChildAt(index);
                    if(flag[index]==0) {
                        flag[index]=1;
                        ((Button) v).setAlpha(0);
                        textView.setText("");
                        textView.setBackgroundResource(R.drawable.flag);
                    }else{
                        flag[index]=0;
                        ((Button) v).setAlpha(1);
                        textView.setBackgroundResource(0);
                        if(data[index]!=-1&&data[index]!=0){
                            textView.setText(String.valueOf(data[index]));
                        }
                        if(data[index]==-1){
                            textView.setBackgroundResource(R.drawable.bomb);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
