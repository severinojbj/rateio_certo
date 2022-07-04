package br.com.rateiocerto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

import br.com.rateiocerto.basics.Apartment;
import br.com.rateiocerto.basics.ReadData;
import br.com.rateiocerto.businiess.DataRWManager;

/**
 * Rateio Certo
 *
 * ReadActivity: classe que gerencia os eventos da tela de leitura do app.
 * Aqui contém as rotinas as rotinas de inserção de novas leituras, além
 * de algumas verificações referentes aos apartamentos cadastrados.
 *
 * @Author: Severino José (biujose@gmail.com)
 */
public class ReadActivity extends AppCompatActivity {

    DataRWManager dataRWManager;

    public ReadActivity () {
        super ();
    }

    /**
     * Método de inicialização da atividade de leitura. Contém as inicializações do
     * gerenciador da camada de negócio, além de controles de botões e áreas de texto.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_read);

        String homePath = getExternalFilesDir("").getPath();
        this.dataRWManager = new DataRWManager(homePath, getApplicationContext());

        /*
        //Rotina de teste, lembrar de retirar

        this.dataRWManager.db.ReadDataDao().clearAllReadData();

        List<Apartment> list = this.dataRWManager.db.ApartmentDao().listAllApartments ();

        for (int i = 1; i <= 256; i++) {
            int id = list.get(i-1).getId();
            this.dataRWManager.insertUpdateReadData(new ReadData(0, id, "2020-05-13", i));

        }

        for (int i = 1; i <= 256; i++) {
            int id = list.get(i-1).getId();
            String testDate = LocalDate.now().toString();
            this.dataRWManager.insertUpdateReadData(new ReadData(id, testDate, (10 + 2*i)));

        }

        //Fim de rotina de teste
        */

        //Carregamento do primeiro apartamento cadastrado.
        final Apartment firstApt = this.dataRWManager.getFirstApartment();

        final EditText edBlock = findViewById(R.id.ed_txt_block);
        final EditText edApt = findViewById(R.id.ed_txt_read_apt_num);
        final EditText edValue = findViewById(R.id.ed_txt_read_in_read_vol);

        //Carregamento das primeiras informações da tela, com o apartamento
        // e seu valor de leitura (caso já exista) do mês.
        edBlock.setText(firstApt.getBlock());
        edApt.setText(firstApt.getFormatedNum());
        ReadData firstRead = this.dataRWManager.getCurrentReadDataById(firstApt.getId());
        if (firstRead != null) {
            String firstReadApt = "" + firstRead.getReadValue();
            edValue.setText(firstReadApt);
        }

        //Inicio das rotinas de acoes para os elementos de tela

        /**
         * Ação da área de texto editável, correspondente ao número do apartamento.
         * Nela ocorre uma verificação do conteúdo de leitura quando ocorre o toque
         * na área do valor de leitura. Caso esse conteúdo seja uma string vazia,
         * ocorre uma verificação se o apartamento já possui uma leitura cadastrada
         * no mês. Caso essa leitura exista, a mesma é atualizada no campo de leitura.
         * Caso não, esse campo será preenchido com zero.
         */
        edApt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String currBlock = String.valueOf(edBlock.getText());
                String strNum = String.valueOf(edApt.getText());
                if (!strNum.contentEquals("")) {
                    int currNum = Integer.parseInt(strNum);
                    Apartment currApt = dataRWManager.getApartment(currBlock, currNum);
                    if (currApt != null) {
                        ReadData r = dataRWManager.getCurrentReadDataById(currApt.getId());
                        if (r != null) {
                            edValue.setText("" + r.getReadValue());
                        }
                        else {
                            String contentText = String.valueOf(edValue.getText());
                            if (contentText.contentEquals("")) {
                                edValue.setText("0");

                            }
                        }
                    }
                    else {
                        edValue.setText("0");

                    }
                }
            }
        });

        /**
         * Ação da área de texto editável, correspondente ao valor de leitura.
         * Caso o texto atual seja uma string vazia, quando ocorre o toque nesse
         * campo, ocorre o preenchimento inicial dessa área com zero.
         */
        edValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contentText = String.valueOf(edValue.getText());
                if (contentText.contentEquals("0")) {
                    edValue.setText("");

                }
            }
        });

        Button btnReadNext = (Button) findViewById(R.id.btn_read_next);
        Button btnReadPrev = (Button) findViewById(R.id.btn_read_previous);
        Button btnReadFinish = (Button) findViewById(R.id.btn_read_finish);

        /**
         * Ação do botão "avançar" da tela de leitura.
         * Nele temos a leitura dos valores do bloco, número do apartamento e valor
         * de leitura inserido no campo correspondente.
         * Inicialmente há um conjunto de verificações dos valores inseridos na
         * interface, e busca pelas informações do apartamento.
         * Quando esses dados são validados, inicia-se o cadastro dessa leitura.
         * Após o cadastro, há uma verificação para carregar os dados do próximo
         * apartamento, a fim de preparar a próxima leitura na tela.
         */
        btnReadNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aptBlock = String.valueOf(edBlock.getText());
                String valueTest = String.valueOf(edApt.getText());
                int aptNum = 0;
                int readValue = 0;
                if (!valueTest.contentEquals("")) {
                    aptNum = Integer.parseInt(valueTest);

                }
                valueTest = String.valueOf(edValue.getText());
                if (!valueTest.contentEquals("")) {
                    readValue = Integer.parseInt(valueTest);

                }
                Apartment aptTarget = dataRWManager.getApartment(aptBlock, aptNum);

                if (aptTarget != null) {
                    String dateString = LocalDate.now().toString();
                    ReadData newRead = new ReadData(aptTarget.getId(),
                            dateString, readValue);
                    dataRWManager.insertUpdateReadData(newRead);

                    if (!dataRWManager.isLastApartment(aptBlock, aptNum)) {
                        aptTarget = dataRWManager.getNextApartment(aptBlock, aptNum);
                        edBlock.setText(aptTarget.getBlock());
                        edApt.setText(aptTarget.getFormatedNum());
                        newRead = dataRWManager.getCurrentReadDataById (aptTarget.getId());

                        if (newRead != null) {
                            edValue.setText(""+newRead.getReadValue());

                        }
                        else {
                            edValue.setText ("0");

                        }
                    }
                }
                else {
                    String text = "Bloco ou número de apartamento inexistente.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();

                }
            }
        });

        /**
         * Ação do botão "anterior" da tela de leitura.
         * Nele temos a leitura dos valores do bloco, número do apartamento e valor
         * de leitura inserido no campo correspondente.
         * Inicialmente há um conjunto de verificações dos valores inseridos na
         * interface, e busca pelas informações do apartamento.
         * Quando esses dados são validados, inicia-se o cadastro dessa leitura.
         * Após o cadastro, há uma verificação para carregar os dados do
         * apartamento anterior, a fim de preparar a próxima leitura na tela.
         */
        btnReadPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aptBlock = String.valueOf(edBlock.getText());
                String valueTest = String.valueOf(edApt.getText());
                int aptNum = 0;
                int readValue = 0;
                if (!valueTest.contentEquals("")) {
                    aptNum = Integer.parseInt(valueTest);
                }
                valueTest = String.valueOf(edValue.getText());
                if (!valueTest.contentEquals("")) {
                    readValue = Integer.parseInt(valueTest);
                }
                Apartment aptTarget = dataRWManager.getApartment(aptBlock, aptNum);

                if (aptTarget != null) {
                    String dateString = LocalDate.now().toString();
                    ReadData newRead = new ReadData(aptTarget.getId(),
                            dateString, readValue);
                    dataRWManager.insertUpdateReadData(newRead);
                    boolean isFirstApt = (aptBlock.contentEquals(firstApt.getBlock()) &&
                            aptNum == firstApt.getNum());

                    if (!isFirstApt) {
                        aptTarget = dataRWManager.getPreviousApartment(aptBlock, aptNum);
                        edBlock.setText(aptTarget.getBlock());
                        edApt.setText(aptTarget.getFormatedNum());
                        newRead = dataRWManager.getCurrentReadDataById (aptTarget.getId());

                        if (newRead != null) {
                            edValue.setText(""+newRead.getReadValue());

                        }
                        else {
                            edValue.setText ("0");

                        }
                    }
                }
                else {
                    String text = "Bloco ou número de apartamento inexistente.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();

                }
            }
        });

        /**
         * Ação do botão "finalizar" da tela de leitura.
         * Há uma verificação da quantidade de leituras cadastradas no
         * mês. Caso essa quantidade seja inferior à quantidade de
         * apartamentos, uma mensagem de "leituras insuficientes"
         * aparece na tela. Caso contrário, aparecerá a tela de
         * geração da planilha.
         */
        btnReadFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateString = LocalDate.now().toString();
                int currYear = Integer.parseInt(dateString.substring(0,4));
                int currMonth = Integer.parseInt(dateString.substring(5,7));
                int totalCurrReads = dataRWManager.getTotalYearMonthReads
                        (currYear, currMonth);
                int totalApts = dataRWManager.getTotalApartments();

                if (totalCurrReads == totalApts)  {
                    Intent intent = new Intent(getBaseContext(), FinishActivity.class);
                    startActivity(intent);

                }
                else {
                    String text = "Um ou mais apartamentos \n" +
                            "estão sem leitura.";
                    Toast.makeText(getBaseContext(), text, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
