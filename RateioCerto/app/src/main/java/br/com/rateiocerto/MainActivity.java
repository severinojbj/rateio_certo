package br.com.rateiocerto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

import br.com.rateiocerto.businiess.DataRWManager;
import br.com.rateiocerto.businiess.tasks.TaskSetupAptTable;
import br.com.rateiocerto.interfaces.GuiBackendManager;

/**
 * Rateio Certo
 *
 * MainActivity: classe que gerencia os eventos da tela inicial do app.
 * Aqui contém as rotinas iniciais de carregamento dos dados e configurações.
 *
 * @Author: Severino José (biujose@gmail.com)
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Método de inicialização da tela inicial. É inicializada a tela
     * propriamente dita, além do gerenciador principal da camada de negócio.
     * Também são definidas as ações correspondentes aos botões apresentados na tela.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String homePath = getExternalFilesDir("").getPath();
        GuiBackendManager dataRWManager = new DataRWManager (homePath, getApplicationContext());

        //((DataRWManager) dataRWManager).db.ReadDataDao().clearAllReadData();
        //((DataRWManager) dataRWManager).clearAllAptData();

        //List <AptRead> testList = dataRWManager.db.AptReadDao().getListAllReadsCond();

        /**Rotina de carregamento inicial dos dados de apartamentos.
         * O carregamento só ocorre se o número de apartamentos cadastrados
         * for nulo.
         */
        int test = dataRWManager.getTotalApartments ();
        if (test == 0) {
            String condName = dataRWManager.getNameCondominium();
            String fileCSVName = homePath + "/" + condName + "/"
                    + condName + ".csv";
            TaskSetupAptTable t = new TaskSetupAptTable(this,
                    fileCSVName, ((DataRWManager) dataRWManager).db);
            t.execute();
        }

        Button btnHomeInit = (Button) findViewById(R.id.btn_home_init);
        Button btnHomeLastSheet = (Button) findViewById(R.id.btn_home_last_sheet);
        Button btnHomeAbout = (Button) findViewById(R.id.btn_home_about);

        /**
         * Ação do botão de início de leitura, com a inicialização da tela gerenciada
         * pela classe ReadActivity.
         */
        btnHomeInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ReadActivity.class);
                startActivity(intent);

            }
        });

        /**
         * Ação do botão de verificação das úlimas planilhas.
         * Ocorre a abertura de um mini browser com a lista dessas planilhas.
         */
        btnHomeLastSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ReadFileActivity.class);
                startActivity(intent);

            }

        });

        /**
         * Ação do botão sobre. Mostra a mensagem na tela com o nome do app
         * e seu desenvolvedor.
         */
        btnHomeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = "RateioCerto v0.3\n" +
                        "Desenvolvido por Severino José";
                Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
