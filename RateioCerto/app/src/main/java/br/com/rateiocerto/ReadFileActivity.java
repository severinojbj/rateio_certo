package br.com.rateiocerto;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

/**
 * Rateio Certo
 *
 * ReadFileActivity: classe que gerencia a tela de planilhas geradas. Permite ao usuário
 * ver a lista de arquivos, abrir uma planilha, entre outros serviços relacionados.
 *
 * @Author: Severino José (biujose@gmail.com)
 */

public class ReadFileActivity extends AppCompatActivity {

    public String filePath;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    /**
     * Método de criação da tela de gerenciamento das planilhas. Aqui é definida qual
     * pasta estão as planilhas a serem listadas. Também são inicializados e configurados
     * todos os elementos de tela responsáveis por essa listagem.
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file);
        this.recyclerView = findViewById(R.id.rv_read_file);

        filePath = getExternalFilesDir("output").getPath();

        setTitle(R.string.title_save_sheet);
        File dir = new File(filePath);
        String [] list = dir.list();

        this.recyclerView.setHasFixedSize(true);

        this.layoutManager = new LinearLayoutManager(getApplicationContext());
        this.recyclerView.setLayoutManager(this.layoutManager);
        this.mAdapter = new ReadFileAdapter(list);
        this.recyclerView.setAdapter(this.mAdapter);
    }

}

