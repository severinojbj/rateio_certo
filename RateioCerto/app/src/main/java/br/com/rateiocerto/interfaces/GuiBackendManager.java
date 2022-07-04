package br.com.rateiocerto.interfaces;

import java.io.IOException;
import java.util.Vector;

import br.com.rateiocerto.basics.Apartment;
import br.com.rateiocerto.basics.ReadData;

/**
 * Rateio Certo
 *
 * GuiBackendManager: interface de integração entre a camada de serviços ou negócio
 * e a camada de gerenciamento da GUI. É responsável por definir a API de métodos
 * os quais a interface gráfica usará para comunicação com o backend.
 *
 * @Author: Severino José (biujose@gmail.com)
 */

public interface GuiBackendManager {

    /**
     * Método que retorna a quantidade de apartamentos cadastrados no BD.
     * Caso não haja apartamnto cadastrado, retorna -1.
     * @return a quantidade de apartamentos cadastrados
     */
    int getTotalApartments ();

    /**
     * Método que retorna o nome do condomínio, cujo BD foi carregado.
     * @return a string do noem do condomínio
     */
    String getNameCondominium();

    /**
     * Método que verifica se um apartamento está cadastrado no sistema, de acordo
     * com as informações passadas como parâmatros.
     * @param block nome do bloco do apartamento
     * @param num número do apartamento
     * @return booleando que retorna true caso o apartamento esteja cadastrado.
     * Caso contrário, false
     */
    boolean existApartment(String block, int num);

    /**
     * Método que verifica se um apartamento é o primeiro da relação de
     * cadastrados no sistema. Ou seja, o apartamento de menor identificador.
     * Essa busca é baseada nas informações passadas como parâmatros.
     * @param block nome do bloco do apartamento
     * @param num número do apartamento
     * @return booleando que retorna true caso o apartamento seja o primeiro.
     * Caso contrário, false
     */
    boolean isFirstApartment(String block, int num);

    /**
     * Método que verifica se um apartamento é o últmo da relação de
     * cadastrados no sistema. Ou seja, o apartamento de maior identificador.
     * Essa busca é baseada nas informações passadas como parâmatros.
     * @param block nome do bloco do apartamento
     * @param num número do apartamento
     * @return booleando que retorna true caso o apartamento seja o último.
     * Caso contrário, false
     */
    boolean isLastApartment(String block, int num);

    /**
     * Método que retorna o primeiro apartamento cadastrado, com
     * o menor id disponível.
     * @return o apartamento com o menor id
     */
    Apartment getFirstApartment();

    /**
     * Método de busca por um apartamento cadastrado, de
     * acordo com o nome do bloco e o número.
     * @param block nome do bloco a ser pesquisado
     * @param num número do apartamento a ser pesquisado
     * @return caso exista o apartamento, retorna o mesmo. Caso contrário, null
     */
    Apartment getApartment(String block, int num);

    /**
     * Método que retorna o próximo apartamento, cujo identificador
     * é subsequente ao apartamento referente às informações passadas
     * nos parâmetros.
     * @param block nome do bloco de referência
     * @param num número do apartamento de referência
     * @return o próximo apartamento, com id subsequente ao da entrada
     */
    Apartment getNextApartment(String block, int num);

    /**
     * Método que retorna o apartamento anterior, cujo identificador
     * é antecedente ao apartamento referente às informações passadas
     * nos parâmetros.
     * @param block nome do bloco de referência
     * @param num número do apartamento de referência
     * @return o apartamento anterior, com id antecedente ao da entrada
     */
    Apartment getPreviousApartment(String block, int num);

    /**
     * Método que lista os nomes de blocos, os quais existem apartamentos
     * cadastrados.
     * @return a lista dos nomes dos blocos
     */
    Vector<String> listBlocks();

    /**
     * Método que faz a inserção ou atualização (caso já exista) de uma nova
     * leitura.
     * @param r o objeto que representa a nova leitura
     */
    void insertUpdateReadData (ReadData r);

    /**
     * Método que retorna o ano e mês da penúltima leitura completa,
     * anterior à leitura atual.
     * @return array de string que armazena o ano e mês da penúltima leitura
     */
    String [] getLastYearMonthCompleteRead();

    /**
     * Método responsável por realizar a geração da planilha de consumo,
     * entre os meses e anos indicados nos parâmetros. A planilha contém
     * informações referentes ao nome do condomínio, consumo total dos
     * apartamentos, e o consumo individual de cada um.
     * @param prevYear ano da leitura anterior a de referẽncia
     * @param prevMonth mês da leitura anterior a de referência
     * @param currYear ano referente à leitura atual ou de referência
     * @param currMonth mês referente à leitura atual ou de referência
     * @throws IOException erro retornado caso haja um problema na geração
     */
    void writeCSVSheet(int prevYear, int prevMonth, int currYear, int currMonth) throws IOException;
}
