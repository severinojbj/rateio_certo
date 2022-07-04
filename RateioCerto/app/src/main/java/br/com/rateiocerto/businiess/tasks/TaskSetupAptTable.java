package br.com.rateiocerto.businiess.tasks;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import br.com.rateiocerto.basics.Apartment;
import br.com.rateiocerto.businiess.DatabaseRateio;

/**
 * Rateio Certo
 *
 * TaskSetupAptTable: classe que implementa rotinas de execução em background
 * com o objetivo de cadastrar todos os apartamentos contidos num arquivo de
 * entrada. Essa execução roda independente do processamento da interface gráfica.
 *
 * @Author: Severino José (biujose@gmail.com)
 */
public class TaskSetupAptTable extends AsyncTask <Void, Void, String> {

    public AppCompatActivity a;
    public String filePath;
    public DatabaseRateio db;

    /**
     * Constutor da classe. Recebe parâmetros vindos da interface gráfica
     * e necessários para operação prinipal.
     * @param a Atvidade referência da interface gráfica
     * @param filePath caminho da pasta a qual o app lê e escreve os arquivos.
     * @param db referência do banco de dados do condomínio
     */
    public TaskSetupAptTable (AppCompatActivity a, String filePath, DatabaseRateio db) {
        this.a = a;
        this.filePath = filePath;
        this.db = db;
    }

    /**
     * Reimplementação do método da classe AsyncTask. Nele temos a criação
     * de um aviso na tela de que o carregamento dos apartamentos iniciará.
     */
    @Override
    protected void onPreExecute() {
        String text = "Carregando dados iniciais, aguarde...";
        Toast.makeText(this.a.getBaseContext(), text, Toast.LENGTH_SHORT).show();

    }

    /**
     * Reimplementação do método da classe AsyncTask. Nele temos a rotina de
     * leitura de cada linha do arquivo e carregamento das informações, no
     * BD, do apartamento referente a essa linha. Quando todas as linhas
     * são lidas, a execução termina.
     */
    @Override
    protected String doInBackground(Void... voids) {
        String resp = "";
        FileReader aptFile;
        BufferedReader bf;
        String lineData;
        String [] aptData;

        try {
            aptFile = new FileReader (this.filePath);
            bf = new BufferedReader(aptFile);
            lineData = bf.readLine();
            while (lineData != null) {
                aptData = lineData.split(",");
                Apartment a = new Apartment (aptData[0], Integer.parseInt(aptData[1]));
                this.db.ApartmentDao().insertApt(a);
                lineData = bf.readLine();
            }
            bf.close();
            aptFile.close();
            resp = "Dados iniciais carregados.";
        }
        catch (IOException e) {
            resp = "Ocorreu um erro: " + e.getMessage();
        }
        return resp;

    }

    /**
     * Reimplementação do método da classe AsyncTask. Nele temos a criação
     * de um aviso na tela de que o carregamento dos apartamentos foi concluído.
     */
    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(this.a.getBaseContext(), s, Toast.LENGTH_SHORT).show();

    }
}
