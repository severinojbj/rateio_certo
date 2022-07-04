package br.com.rateiocerto.businiess;

import android.content.Context;

import androidx.room.Room;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import br.com.rateiocerto.basics.Apartment;
import br.com.rateiocerto.basics.AptRead;
import br.com.rateiocerto.basics.ReadData;
import br.com.rateiocerto.interfaces.GuiBackendManager;

/**
 * Rateio Certo
 *
 * DataRWManager: classe que realiza o gerenciamento dos serviços
 * fornecidos para a interface do App. Esses serviços processam e
 * retornam dados referentes as leituras e seus respectivos
 * apartamentos.
 *
 * @Author: Severino José (biujose@gmail.com)
 */
public class DataRWManager implements GuiBackendManager {

    //constante que indica o nome do arquivo de configuração em br.com.rateiocerto/data/files
    static String CONFIG_FILE = "rateio_config.ini";

    private String homePath;
    private String nameCondominium;
    public DatabaseRateio db;
    Context c;

    /**
     * Construtor da classe. É responsável por verificar qual condomínio
     * foi escolhido para ser gerenciado, inicializando o banco de dados
     * correspondente às informações do mesmo.
     * @param homePath string que indica o caminho e nome do arquivo
     *                 com o nome do condomínio
     * @param c contexto da interface gráfica, usado para carregar o BD
     */
    public DataRWManager(String homePath, Context c) {
        this.homePath = homePath + "/";
        this.readNameCondominium ();
        this.c = c;
        this.db = Room.databaseBuilder(c.getApplicationContext(), DatabaseRateio.class,
                "database_" + this.nameCondominium).allowMainThreadQueries().build();
    }

    /**
     * Método que carrega o nome do condomínio, armazenado num arquivo de
     * configuração indicado no construtor. Esse nome será usado para indicar
     * o banco de dados do condomínio e sua planilha gerada com as leituras.
     */
    private void readNameCondominium () {
        try {
            FileReader fr = new FileReader(this.homePath + DataRWManager.CONFIG_FILE);
            BufferedReader bf = new BufferedReader (fr);
            this.nameCondominium = bf.readLine();
            bf.close();
        }
        catch (IOException e) {
            this.writeLog("readNameCondominium: " + e.getMessage());
            //e.printStackTrace();
        }
    }

    /**
     * Método que retorna uma lista de leituras referentes ao mês e ano passados como
     * parâmetros.
     * @param year ano de referência da lista
     * @param month mês de referência da lista
     * @return a lista correspondente ao mês e ano recebidos.
     */
    public List<ReadData> getMonthListReadData (int year, int month) {
        return this.db.ReadDataDao().
                getMonthListReadData("" + year + "",
                        "" + String.format("%02d", month) + "");
    }

    /**
     * Método que retorna uma lista de visualizações de leitura referentes
     * ao mês e ano passados como parâmetros.
     * @param year ano de referência da lista
     * @param month mês de referência da lista
     * @return a lista correspondente ao mês e ano recebidos.
     */
    public List<AptRead> getYearMonthListAptRead (int year, int month) {
        return this.db.AptReadDao().
                getYearMonthListAptRead("" + year + "",
                        "" + String.format("%02d", month) + "");
    }

    /**
     * Método de remoção de todos os dados da tabela.
     */
    public void clearAllAptData () {
        this.db.ApartmentDao().clearAllAptData();
    }

    /**
     * Método que retorna a leitura cadastrada no mês atual de um apartamento,
     * cujo identificador é passado como parâmetro.
     * @param idApt identificador do apartamento ao qual se deseja a leitura atual
     * @return leitura atual do apartamento. Caso contrário, null
     */
    public ReadData getCurrentReadDataById (int idApt) {
        return this.db.ReadDataDao().getCurrentReadDataById(idApt);
    }

    /**
     * Método utilizado para registrar logs de erros geraods durante a execução
     * da aplicação. Cada condomínio tem seu arquivo de log, de acordo com o
     * nome fornecido do mesmo.
     * @param message string contendo a mensagem de erro
     */
    public void writeLog (String message) {
        String LOG_FILE_NAME = this.homePath + "log/" + this.nameCondominium + ".log";
        FileWriter fLog = null;
        Timestamp ts = new Timestamp (System.currentTimeMillis());

        try {
            fLog = new FileWriter(LOG_FILE_NAME, true);
            fLog.write("["+ ts + "] " + message + "\n");
            fLog.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que retorna o caminho raiz do local de leitura e escrita de arquivos
     * pela aplicação.
     * @return a string com o caminho ou pasta
     */
    public String getHomePath () {
        return this.homePath;
    }

    /**
     * Método que retorna a soma dos valores de leitura realizados no mês e ano
     * indicados pelos parâmetros.
     * @param year ano de referência para o cálculo
     * @param month mês de referência para o cálculo
     * @return a soma dos valores de leitura, refentes ao mês e ano indicados
     */
    public long getSumYearMonthReads (int year, int month) {
        return this.db.AptReadDao().
                getSumYearMonthListAptRead("" + year, String.
                        format("%02d", month));
    }

    /**
     * Método que retorna a quantidade de leituras contidas nas
     * visualizações, de acordo com o mês e ano passados como parâmetros.
     * @param year ano de referência para busca
     * @param month mês de referência para busca
     * @return a soma das leituras correspondentes ao mês e ano recebidos
     */
    public int getTotalYearMonthReads (int year, int month) {
//        int resp = this.db.ReadDataDao().getTotalYearMonthReadData(
//                ""+year, ""+ String.format ("%02d",month));
        int resp = this.db.AptReadDao().
                getTotalYearMonthAptRead(""+year, String.format ("%02d",month));
        return resp;
    }

    /* #########################################################
     * Inicio dos metodos publicos e que implementam a interface
     * #########################################################
     */

    /**
     * Método que retorna a quantidade de apartamentos cadastrados no BD.
     * Caso não haja apartamnto cadastrado, retorna -1.
     * @return a quantidade de apartamentos cadastrados
     */
    @Override
    public int getTotalApartments () {
        int resp = -1;
        resp =  this.db.ApartmentDao().getTotalApartments ();
        return resp;
    }

    /**
     * Método que retorna o nome do condomínio, cujo BD foi carregado.
     * @return a string do noem do condomínio
     */
    @Override
    public String getNameCondominium() {
        return this.nameCondominium;
    }

    /**
     * Método que verifica se um apartamento está cadastrado no sistema, de acordo
     * com as informações passadas como parâmatros.
     * @param block nome do bloco do apartamento
     * @param num número do apartamento
     * @return booleando que retorna true caso o apartamento esteja cadastrado.
     * Caso contrário, false
     */
    @Override
    public boolean existApartment(String block, int num) {
        boolean resp = false;
        Apartment test = this.db.ApartmentDao()
                .getApartment(block, "" + num);
        if (test != null) {
            resp = true;
        }
        return resp;
    }

    /**
     * Método que verifica se um apartamento é o primeiro da relação de
     * cadastrados no sistema. Ou seja, o apartamento de menor identificador.
     * Essa busca é baseada nas informações passadas como parâmatros.
     * @param block nome do bloco do apartamento
     * @param num número do apartamento
     * @return booleando que retorna true caso o apartamento seja o primeiro.
     * Caso contrário, false
     */
    @Override
    public boolean isFirstApartment(String block, int num) {
        boolean resp = false;
        Apartment aptTest = this.getFirstApartment();
        resp = (block.contentEquals(aptTest.getBlock()) &&
                num == aptTest.getNum());
        return resp;
    }

    /**
     * Método que verifica se um apartamento é o últmo da relação de
     * cadastrados no sistema. Ou seja, o apartamento de maior identificador.
     * Essa busca é baseada nas informações passadas como parâmatros.
     * @param block nome do bloco do apartamento
     * @param num número do apartamento
     * @return booleando que retorna true caso o apartamento seja o último.
     * Caso contrário, false
     */
    @Override
    public boolean isLastApartment(String block, int num) {
        boolean resp = false;
        Apartment aptTest = this.db.ApartmentDao().
                getLastApartment();
        resp = (block.contentEquals(aptTest.getBlock()) &&
                num == aptTest.getNum());
        return resp;
    }

    /**
     * Método que retorna o primeiro apartamento cadastrado, com
     * o menor id disponível.
     * @return o apartamento com o menor id
     */
    @Override
    public Apartment getFirstApartment() {
        Apartment resp = this.db.ApartmentDao().
                getFirstApartment();
        return resp;
    }

    /**
     * Método de busca por um apartamento cadastrado, de
     * acordo com o nome do bloco e o número.
     * @param block nome do bloco a ser pesquisado
     * @param num número do apartamento a ser pesquisado
     * @return caso exista o apartamento, retorna o mesmo. Caso contrário, null
     */
    @Override
    public Apartment getApartment(String block, int num) {
        Apartment resp = this.db.ApartmentDao()
                .getApartment(block, "" + num);
        return resp;
    }

    /**
     * Método que retorna o próximo apartamento, cujo identificador
     * é subsequente ao apartamento referente às informações passadas
     * nos parâmetros.
     * @param block nome do bloco de referência
     * @param num número do apartamento de referência
     * @return o próximo apartamento, com id subsequente ao da entrada
     */
    @Override
    public Apartment getNextApartment(String block, int num) {
        Apartment resp;
        Apartment actualApt = this.getApartment(block, num);
        int actualId = actualApt.getId();
        resp = this.db.ApartmentDao()
                .getApartmentById(actualId + 1);
        return resp;
    }

    /**
     * Método que retorna o apartamento anterior, cujo identificador
     * é antecedente ao apartamento referente às informações passadas
     * nos parâmetros.
     * @param block nome do bloco de referência
     * @param num número do apartamento de referência
     * @return o apartamento anterior, com id antecedente ao da entrada
     */
    @Override
    public Apartment getPreviousApartment(String block, int num) {
        Apartment resp;
        Apartment actualApt = this.getApartment(block, num);
        int actualId = actualApt.getId();
        resp = this.db.ApartmentDao()
                .getApartmentById(actualId - 1);
        return resp;
    }

    /**
     * Método que lista os nomes de blocos, os quais existem apartamentos
     * cadastrados.
     * @return a lista dos nomes dos blocos
     */
    @Override
    public Vector<String> listBlocks() {
        Vector<String> resp = new Vector<>();
        List<String> listBlock = this.db.ApartmentDao().listAllBlocks();
        if (listBlock != null) {
            resp.addAll(listBlock);
        }
        return resp;
    }

    /**
     * Método que faz a inserção ou atualização (caso já exista) de uma nova
     * leitura.
     * @param r o objeto que representa a nova leitura
     */
    @Override
    public void insertUpdateReadData(ReadData r) {
        ReadData rTest = this.db.ReadDataDao()
                .getCurrentReadDataById(r.getIdApt());
        if (rTest == null) {
            this.db.ReadDataDao().insertReadData(r);
        }
        else if (r.getReadValue() != rTest.getReadValue()){
            r.setIdRead(rTest.getIdRead());
            this.db.ReadDataDao().updateReadData(r);
        }
    }

    /**
     * Método que retorna o ano e mês da penúltima leitura completa,
     * anterior à leitura atual.
     * Inicialmente, há um carregamento do primeiro apartamento e sua leitura
     * no mês atual, com objetivo de obter qual mês e ano ocorreu a última leitura.
     * Com as informações de data, o próximo passo é buscar a leitura anterior
     * à data encontrada. Assim que o mês e ano dessa leitura anterior é encontrada,
     * as mesmas são preparadas para retorno do método.
     * @return array de string que armazena o ano e mês da penúltima leitura
     */
    @Override
    public String [] getLastYearMonthCompleteRead() {
        String [] resp = new String [2];
        int idFirstApt = this.db.ApartmentDao().getFirstApartment().getId();
        ReadData firstReadCurrent = db.ReadDataDao().getCurrentReadDataById(idFirstApt);
        if (firstReadCurrent != null) {
            String yearCurr =  firstReadCurrent.getDateRead().substring(0,4);
            String monthCurr = firstReadCurrent.getDateRead().substring(5,7);
            ReadData beforeRead = db.ReadDataDao().
                    getLastReadPastMonth(yearCurr, monthCurr);
            if (beforeRead != null) {
                resp [0] = (beforeRead.getDateRead()).substring(0,4); //year
                resp [1] = (beforeRead.getDateRead()).substring(5,7); //month
            }
            else {
                resp [0] = "";
                resp [1] = "";
            }
        }
        else {
            resp [0] = "";
            resp [1] = "";
        }
        return resp;
    }

    /*
    @Override
    public void writeCSVSheet(int prevYear, int prevMonth, int currYear, int currMonth) throws IOException {
        String fileName = this.getHomePath() + "output/" +
                this.getNameCondominium () + "_" + currMonth +
                "_" + currYear + ".csv";
        List<ReadData> prevRead = this.getMonthListReadData (prevYear, prevMonth);
        List<ReadData> currRead = this.getMonthListReadData (currYear, currMonth);

        long sumDiff = 0;
        int totalData = this.getTotalApartments();

        for (int i = 0; i < totalData; i++) {
            sumDiff = sumDiff + (currRead.get(i).getReadValue()
                    - prevRead.get(i).getReadValue());

        }

        String fileHeader = "Condominio:," + this.getNameCondominium() + "\n"
                + "Periodo:," + String.format("%02d", prevMonth) + "/" + prevYear
                + " a " + String.format("%02d", currMonth) + "/" + currYear + "\n"
                + ",,\n"
                + "Volume total (m³):," + sumDiff + "\n"
                + ",,\n"
                + "Bloco,Apartamento,Leitura anterior (m³),Leitura atual (m³),"
                + "Consumo unitário (m³),% rateio\n";
        int valuePrev = 0;
        int valueCurr = 0;
        int valueDiff = 0;
        double percentDiff = 0;
        Apartment a;
        String rowWr = "";

        try {
            RandomAccessFile fr = new RandomAccessFile (new File(fileName), "rw");
            fr.writeChars(fileHeader);
            DecimalFormat df = new DecimalFormat("#0.00");
            for (int i = 1; i <= totalData; i++) {
                valuePrev = prevRead.get(i-1).getReadValue();
                valueCurr = currRead.get(i-1).getReadValue();
                valueDiff = Math.abs (valueCurr - valuePrev);
                percentDiff = ((double)valueDiff/(double)sumDiff)*100;
                a = this.db.ApartmentDao().getApartmentById(currRead.get(i-1).getIdApt());

                rowWr = a.getBlock() + "," + a.getFormatedNum() + ","
                        + valuePrev + "," + valueCurr + "," + valueDiff
                        + "," + df.format(percentDiff).replace(",", ".") + "\n";
                fr.writeChars(rowWr);

            }
            fr.close ();

        }
        catch (IOException e) {
            this.writeLog("writeCSVSheet: " + e.getMessage());
            throw e;

        }
    }
     */

    /**
     * Método responsável por realizar a geração da planilha de consumo,
     * entre os meses e anos indicados nos parâmetros. A planilha contém
     * informações referentes ao nome do condomínio, consumo total dos
     * apartamentos, e o consumo individual de cada um.
     * Inicialmente, é feita o carregamento das leituras dos meses passados
     * como parâmetros. Depois, é feito um cálculo da soma das diferenças
     * entre os valores dessas leituras, com relação a cada apartamento.
     * A partir daí, inica-se a escrita do arquivo em formato CSV, com
     * a geração de um cabeçalho contendo as informações do condomínio,
     * o total de consumo (soma das diferenças), e uma lista contendo
     * informações de cada apartamento, seu consumo individual e a
     * porcentagem desse consumo em relação ao total.
     * @param prevYear ano da leitura anterior a de referẽncia
     * @param prevMonth mês da leitura anterior a de referência
     * @param currYear ano referente à leitura atual ou de referência
     * @param currMonth mês referente à leitura atual ou de referência
     * @throws IOException erro retornado caso haja um problema na geração
     */
    @Override
    public void writeCSVSheet(int prevYear, int prevMonth, int currYear, int currMonth) throws IOException {
        String fileName = this.getHomePath() + "output/" +
                this.getNameCondominium () + "_" + currMonth +
                "_" + currYear + ".csv";
        List<AptRead> prevRead = this.getYearMonthListAptRead (prevYear, prevMonth);
        List<AptRead> currRead = this.getYearMonthListAptRead (currYear, currMonth);

        long sumDiff = 0;
        int totalData = currRead.size(); //this.getTotalApartments();

        long currSumValueAptRead = this.getSumYearMonthReads(currYear, currMonth);
        long prevSumValueAptRead = this.getSumYearMonthReads (prevYear, prevMonth);
        sumDiff = Math.abs (currSumValueAptRead - prevSumValueAptRead);

        String fileHeader = "Condominio:," + this.getNameCondominium() + "\n"
                + "Periodo:," + String.format("%02d", prevMonth) + "/" + prevYear
                + " a " + String.format("%02d", currMonth) + "/" + currYear + "\n"
                + ",,\n"
                + "Volume total (m³):," + sumDiff + "\n"
                + ",,\n"
                + "Bloco,Apartamento,Leitura anterior (m³),Leitura atual (m³),"
                + "Consumo unitário (m³),% rateio\n";
        long valuePrev = 0;
        long valueCurr = 0;
        int valueDiff = 0;
        double percentDiff = 0;
        Apartment a;
        String rowWr = "";

        try {
            RandomAccessFile fr = new RandomAccessFile (new File(fileName), "rw");
            fr.writeChars(fileHeader);
            DecimalFormat df = new DecimalFormat("#0.00");
            for (int i = 1; i <= totalData; i++) {
                valuePrev = prevRead.get(i-1).getReadDataValueRead();
                valueCurr = currRead.get(i-1).getReadDataValueRead();
                valueDiff = (int) Math.abs (valueCurr - valuePrev);
                percentDiff = ((double)valueDiff/(double)sumDiff)*100;

                rowWr = currRead.get(i-1).getBloco() + "," + currRead.get(i-1).getFormatedNumero() + ","
                        + valuePrev + "," + valueCurr + "," + valueDiff
                        + "," + df.format(percentDiff).replace(",", ".") + "\n";
                fr.writeChars(rowWr);

            }
            fr.close ();

        }
        catch (IOException e) {
            this.writeLog("writeCSVSheet: " + e.getMessage());
            throw e;

        }
    }

    /*
    //Todo: em desenvolvimento...
    public void writeXLSSheet(int prevYear, int prevMonth, int currYear, int currMonth) throws IOException{
        String fileName = this.getHomePath() + "output/" +
                this.getNameCondominium () + "_" + currMonth +
                "_" + currYear + ".xlsx";
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new File(fileName));
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        Sheet wrSheet = workbook.createSheet (String.format ("%d_%d", currMonth, currYear));

        //Row wrRow = wrSheet.createRow(0);

        CellStyle cellStyle = workbook.createCellStyle();

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 12);
        font.setBold(false);
        cellStyle.setFont(font);

        String [][] rowHeader = {
                {"Condominio:", this.getNameCondominium()},
                {"Periodo:", String.format("%02d", prevMonth) + "/" + prevYear
                        + " a " + String.format("%02d", currMonth) + "/" + currYear},
                {"Volume Total:"},
                {"Bloco,Apartamento","Leitura anterior (m³)",
                        "Leitura atual (m³)", "Consumo unitário (m³)", "% rateio"}
        };

//        String [] rowInterval = {"Periodo:", String.format("%02d", prevMonth) + "/" + prevYear
//                + " a " + String.format("%02d", currMonth) + "/" + currYear};
//        String rowTotalVol = "Volume Total:";
//        String [] rowLabel = {"Bloco,Apartamento","Leitura anterior (m³)",
//                "Leitura atual (m³)", "Consumo unitário (m³)", "% rateio"};
//

        for (int i = 0; i < rowHeader.length; i++) {
            int colSize = rowHeader [i].length;
            Row wrRow = wrSheet.createRow(colSize);
            for (int j = 0; i < colSize; j++) {
                Cell cell = wrRow.createCell(j);
                cell.setCellValue(rowHeader[i][j]);
                cell.setCellStyle(cellStyle);
            }
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(fileName);
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();
    }
    */
}
