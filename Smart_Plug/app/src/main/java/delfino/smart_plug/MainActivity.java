package delfino.smart_plug;

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

public class MainActivity extends AppCompatActivity {


    EditText editText;
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
    private static String ip = "192.168.43.238";//73";//IP do Computador na tentativa de conexão estabelecida
    private static int porta = 1856;//Porta do Computador na tentativa de conexão estabelecida
    String message = "";
    String time = "";
    String recurrency = "";
    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText)findViewById(R.id.editText);
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
                socket = new Socket(ip,porta);
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
        time = editText.getText().toString();
        recurrency="";
        if(checkBox0.isChecked()){
            recurrency+="0,";
        }
        if(checkBox1.isChecked()){
            recurrency+="1,";
        }
        if(checkBox2.isChecked()){
            recurrency+="2,";
        }
        if(checkBox3.isChecked()){
            recurrency+="3,";
        }
        if(checkBox4.isChecked()){
            recurrency+="4,";
        }
        if(checkBox5.isChecked()){
            recurrency+="5,";
        }
        if(checkBox6.isChecked()){
            recurrency+="6";
        }
        /*/Removendo inserção da ","
        if (recurrency.charAt(recurrency.length()-1)==',') {
            recurrency = recurrency.replace(recurrency.substring(recurrency.length() - 1), "");
        }*/

        if(switch1.isChecked()){
            flag = "1";
        }else{
            flag = "0";
        }
        //Algoritmo de envio
        message = time + " " + recurrency + " " + flag;
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