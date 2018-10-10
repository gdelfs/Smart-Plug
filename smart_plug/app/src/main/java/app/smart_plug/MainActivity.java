package app.smart_plug;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private static Socket socket;
    private static PrintWriter printwriter;
    String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public class connectTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                // Conectando ao servidor
                socket = new Socket(ServerData.ip, ServerData.port);
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

    public void schedule(View view) {
        startActivity(new Intent(MainActivity.this, Schedule.class));
    }

    public void erase(View view) {
        startActivity(new Intent(MainActivity.this, Schedule.class));
    }

    public void turn_on(View view){
        message = "1 1";
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
    public void turn_off(View view){
        message = "1 0";
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

