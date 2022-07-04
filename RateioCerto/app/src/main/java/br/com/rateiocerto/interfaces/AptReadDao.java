package br.com.rateiocerto.interfaces;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import br.com.rateiocerto.basics.AptRead;

/**
 * Rateio Certo
 *
 * AptReadDao: interface que possui os métodos responsáveis pelas consultas
 * SQLite referentes à tabela virtual AptRead.
 *
 * @Author: Severino José (biujose@gmail.com)
 */

@Dao
public interface AptReadDao {

    /**
     * Método que retorna todas as visualizações de leitura dispoíveis
     * dos apartamentos.
     * @return a lista com as visualizações
     */
    @Query("SELECT * FROM AptRead")
    List<AptRead> getListAllReadsCond ();

    /**
     * Método que retorna a quantidade de leituras contidas nas
     * visualizações, de acordo com o mês e ano passados como parâmetros.
     * @param year ano de referência para busca
     * @param month mês de referência para busca
     * @return a soma das leituras correspondentes ao mês e ano recebidos
     */
    @Query ("SELECT COUNT(1) FROM AptRead WHERE " +
            "(STRFTIME ('%Y', readDataDateRead) = (:year) " +
            "AND STRFTIME ('%m', readDataDateRead) = (:month))")
    int getTotalYearMonthAptRead (String year, String month);

    /**
     * Método que busca a linha de visualização, com informações de leitura
     * atual do apartamento, cujo id é passado como parâmetro.
     * @param idApt id do apartamento a ser buscado
     * @return a linha contendo informações da leitura atual do apartamento recebido
     */
    @Query ("SELECT * FROM AptRead WHERE (id_apt = :idApt) AND " +
            "(STRFTIME('%m', readDataDateRead) = STRFTIME ('%m','now'))")
    AptRead getCurrentAptReadById (int idApt);

    /**
     * Método que retorna uma lista de visualizações de leitura referentes
     * ao mês e ano passados como parâmetros.
     * @param year ano de referência da lista
     * @param month mês de referência da lista
     * @return a lista correspondente ao mês e ano recebidos.
     */
    @Query ("SELECT * FROM AptRead WHERE " +
            "(STRFTIME ('%Y', readDataDateRead) = (:year) " +
            "AND STRFTIME ('%m', readDataDateRead) = (:month))")
    List<AptRead> getYearMonthListAptRead (String year, String month);

    /**
     * Método que retorna uma visualização de leitura referente ao mês e
     * ano imediatamente anteriores ao mês e ano passados como parâmetro.
     * Ou seja, retorna uma visualização de leitura do mês anterior
     * (ou menos), o qual possui a penúltima lista de leituras
     * realizadas (em relação ao mês e ano indicado nos parâmetros).
     * @param year ano de referência para busca
     * @param month mês de referência para busca
     * @return uma visualzação de leitura correspondente ao mês (ou ano) anterior.
     */
    @Query ("SELECT * FROM AptRead WHERE ((STRFTIME ('%Y', readDataDateRead) = :year)" +
            "AND (STRFTIME ('%m', readDataDateRead) < :month))" +
            "OR (STRFTIME ('%Y', readDataDateRead) < :year) " +
            "ORDER BY id_apt DESC LIMIT (1)")
    AptRead getLastAptReadPastMonth (String year, String month);

    /**
     * Método que retorna a soma dos valores de leitura realizados no mês e ano
     * indicados pelos parâmetros.
     * @param year ano de referência para o cálculo
     * @param month mês de referência para o cálculo
     * @return a soma dos valores de leitura, refentes ao mês e ano indicados
     */
    @Query ("SELECT SUM (readDataValueRead) FROM AptRead WHERE " +
            "(STRFTIME ('%Y', readDataDateRead) = (:year) " +
            "AND STRFTIME ('%m', readDataDateRead) = (:month))")
    int getSumYearMonthListAptRead (String year, String month);

    //Todo: Desenvolver queries necessarias à geração da planilha, como a soma das diferencas
}
