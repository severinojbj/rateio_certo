package br.com.rateiocerto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.rateiocerto.businiess.DataRWManager;
import br.com.rateiocerto.businiess.tasks.TaskWriteCSV;

/**
 * Rateio Certo
 *
 * FinishActivity: classe que gerencia a tela de finalização do App.
 * Nela será iniciada a geração da planilha, a partir do botão correspondente.
 *
 * @Author: Severino José (biujose@gmail.com)
 */
public class FinishActivity extends AppCompatActivity {

    DataRWManager dataRWManager;

    /**
     * Rotina de criação da atividade de finalização. Contém as inicializações
     * da tela propriamente dita, do gerenciador da camada de negócio e das
     * ações dos botões presentes nessa tela.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String homePath = getExternalFilesDir("").getPath();
        this.dataRWManager = new DataRWManager(homePath, getApplicationContext());

        setContentView(R.layout.activity_finish);

        Button btnSaveSheet = findViewById (R.id.btn_finish_save_sheet);
        Button btnBackHome = findViewById(R.id.btn_finish_home);

        /**
         * Ação do botão "salvar planilha".
         * É inicializado o processo de geração de planilha em arquivo,
         * cujo processo roda em separado da interface gráfica.
         * Após a inicialização da escrita, a tela inicial é instanciada
         * para visualização.
         */
        btnSaveSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskWriteCSV t = new TaskWriteCSV(v, dataRWManager);
                t.execute();
                Intent intent = new Intent (getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        /**
         * Ação do botão "voltar ao início".
         * Aqui apenas é instanciada a tela inicial para visualização.
         */
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
