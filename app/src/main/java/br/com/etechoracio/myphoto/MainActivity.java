package br.com.etechoracio.myphoto;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity
{

    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCapturar (View view)
    {
        //Criação do arquivo da imagem
        File diretorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        //Nome da imagem
        String nomeImagem = diretorio.getPath() + "/" + System.currentTimeMillis() + ".jpg";

        //
        uri = Uri.fromFile(new File(nomeImagem));



        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //putExtra = passa informações adicionais aos intents, no caso o uri.
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);



        //Aciona a câmera com o identificador de 84 para saber a foto que foi tirada.
        startActivityForResult(intent, 84);
    }

    //Método para saber que a imagem que o sistema me devolve é a que eu tirei.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (requestCode == 84 && resultCode == RESULT_OK)
        {
            Toast.makeText(this, "Imagem capturada!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

            intent.setData(uri);
            this.sendBroadcast(intent);
        }
        else
        {
            Toast.makeText(this, "Imagem não capturada!", Toast.LENGTH_SHORT).show();
        }
    }
}