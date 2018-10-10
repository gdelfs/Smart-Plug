package app.smart_plug;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Schedule extends AppCompatActivity {
    EditText hours, minutes;
    CheckBox checkBox0;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    Switch switch1;
    Button button;
    private static Socket socket;
    private static PrintWriter printwriter;
    String message = "";
    Integer m = 0, h = 0;
    String recurrency = "";
    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        hours = (EditText)findViewById(R.id.hours);
        minutes = (EditText)findViewById(R.id.minutes);
        checkBox0 = (CheckBox)findViewById(R.id.checkBox0);
        checkBox1 = (CheckBox)findViewById(R.id.checkBox1);
        checkBox2 = (CheckBox)findViewById(R.id.checkBox2);
        checkBox3 = (CheckBox)findViewById(R.id.checkBox3);
        checkBox4 = (CheckBox)findViewById(R.id.checkBox4);
        checkBox5 = (CheckBox)findViewById(R.id.checkBox5);
        checkBox6 = (CheckBox)findViewById(R.id.checkBox6);
        switch1 = (Switch)findViewById(R.id.switch1);
        button = (Button)findViewById(R.id.button);

    }

    public class connectTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Conectando ao servidor
                socket = new Socket(ServerData.ip,ServerData.port);
                printwriter = new PrintWriter(socket.getOutputStream());
                //Enviando para o servidor a foto mostrada
                printwriter.write(message);
                printwriter.flush();

                //Fechando o servidor, apenas um envio.
                printwriter.close();
                socket.close();

            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public void Fazer_Upload(View view){
        //Checando se o código foi inserido/é válido
        m = Integer.parseInt(minutes.getText().toString());
        h = Integer.parseInt(hours.getText().toString());

        recurrency="";

        boolean ok = false;
        if(checkBox0.isChecked()){
            if(ok) recurrency+=",";
            recurrency+="0";
            ok = true;
        }
        if(checkBox1.isChecked()){
            if(ok) recurrency+=",";
            recurrency+="1";
            ok = true;
        }
        if(checkBox2.isChecked()){
            if(ok) recurrency+=",";
            recurrency+="2";
            ok = true;
        }
        if(checkBox3.isChecked()){
            if(ok) recurrency+=",";
            recurrency+="3";
            ok = true;
        }
        if(checkBox4.isChecked()){
            if(ok) recurrency+=",";
            recurrency+="4";
            ok = true;
        }
        if(checkBox5.isChecked()){
            if(ok) recurrency+=",";
            recurrency+="5";
            ok = true;
        }
        if(checkBox6.isChecked()){
            if(ok) recurrency+=",";
            recurrency+="6";
        }

        if(switch1.isChecked()){
            flag = "1";
        }else{
            flag = "0";
        }
        //Algoritmo de envio
        message = "0 " + Integer.toString(m) + " " + Integer.toString(h) + " * * " + recurrency + " " + flag + " 1";
        connectTask myTask = new connectTask();
        myTask .execute();
        try{
            synchronized(this){
                wait(1000);
            }
        }catch(InterruptedException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Servidor está inacessível.", Toast.LENGTH_SHORT);
            toast.show();
        }
        Toast toast = Toast.makeText(getApplicationContext(), "Upload feito com sucesso!", Toast.LENGTH_SHORT);
        toast.show();
    }
}
