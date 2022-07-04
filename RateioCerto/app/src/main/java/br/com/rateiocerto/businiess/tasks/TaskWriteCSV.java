package br.com.rateiocerto.businiess.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.time.LocalDate;

import br.com.rateiocerto.businiess.DataRWManager;

/**
 * Rateio Certo
 *
 * TaskWriteCSV: classe que implementa rotinas de execução em background
 * com o objetivo de gerar uma planilha com as leituras dos apartamentos.
 * Essa execução roda independente do processamento da interface gráfica.
 *
 * @Author: Severino José (biujose@gmail.com)
 */
public class TaskWriteCSV extends AsyncTask <Void, Void, String> {

    private int lastYear;
    private int lastMonth;
    private int currYear;
    private int currMonth;

    public View v;
    public DataRWManager drw;

    /**
     * Constutor da classe. Recebe parâmetros vindos da interface gráfica
     * e necessários para operação prinipal.
     * @param v Atvidade referência da interface gráfica
     * @param drw referência de uma instância DataRWManager inicializada
     */
    public TaskWriteCSV (View v, DataRWManager drw) {
        this.v = v;
        this.drw = drw;
    }

    /**
     * Reimplementação do método da classe AsyncTask. Aqui ocorre o
     * carregamento das variáveis necessárias para geração da planilha,
     * com a conversão dos inteiros dos meses e anos em string.
     * Também temos a criação de um aviso na tela de que o processamento
     * de geração da planilha iniciará.
     */
    @Override
    protected void onPreExecute() {
        String [] lastDateRead = this.drw.getLastYearMonthCompleteRead();
        this.lastYear = Integer.parseInt(lastDateRead [0]);
        this.lastMonth = Integer.parseInt(lastDateRead [1]);
        String currDateRead = LocalDate.now().toString();
        this.currYear = Integer.parseInt(currDateRead.substring(0,4));
        this.currMonth = Integer.parseInt(currDateRead.substring(5,7));

        String text = "Criando a planilha, aguarde...";
        Toast.makeText(this.v.getContext(), text, Toast.LENGTH_SHORT).show();

    }

    /**
     * Reimplementação do método da classe AsyncTask. Nele temos a chamada
     * do método pertencente à instância do DataRWManager, indicando como
     * parâmetros as variáveis inicializadas na pre-execução.
     */
    @Override
    protected String doInBackground(Void... voids) {
        String resp = "";
        try {
            this.drw.writeCSVSheet(this.lastYear, this.lastMonth, this.currYear, this.currMonth);
            resp = "Planilha salva com sucesso.";

        }
        catch (IOException e) {
            resp = "Ocorreu um erro: " + e.getMessage();

        }
        return resp;
    }

    /**
     * Reimplementação do método da classe AsyncTask. Nele temos a criação
     * de um aviso na tela de que o processamento de geração da planilha
     * foi concluido.
     */
    @Override
    protected void onPostExecute(String s) {
        Toast.makeText(this.v.getContext(), s, Toast.LENGTH_SHORT).show();
    }
}
