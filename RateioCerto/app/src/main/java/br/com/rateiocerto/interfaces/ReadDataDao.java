package br.com.rateiocerto.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.rateiocerto.basics.ReadData;

/**
 * Rateio Certo
 *
 * ReadDataDao: interface que possui os métodos responsáveis pelas consultas
 * SQLite referentes à tabela ReadData.
 *
 * @Author: Severino José (biujose@gmail.com)
 */

@Dao
public interface ReadDataDao {

    /**
     * Método de inserção de uma leitura, comando INSERT do SQLite.
     * @param r objeto referente à nova linha inserida na tabela
     */
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertReadData (ReadData r);

    /**
     * Método de remoção de uma leitura, comando DELETE do SQLite.
     * @param r objeto referente à linha a ser removida na tabela
     */
    @Delete
    void deleteReadData (ReadData r);

    /**
     * Método de atualização de uma leitura, comando UPDATE do SQLite.
     * @param r objeto referente à nova linha a ser atualizada na tabela
     */
    @Update
    void updateReadData (ReadData r);

    /**
     * Método de remoção de todos os dados da tabela.
     */
    @Query ("DELETE FROM ReadData")
    void clearAllReadData ();

    /**
     * Método que remove as leituras referentes ao mês e ano indicados
     * como parâmetros.
     * @param year ano indicado para seleção
     * @param month mês indicado para seleção
     */
    @Query ("DELETE FROM ReadData WHERE STRFTIME ('%Y', data_leitura) = (:year) " +
            "AND STRFTIME ('%m', data_leitura) = (:month)")
    void clearReadList (String year, String month);

    /**
     * Método que busca por uma leitura cadastrada, a qual contenha o mesmo
     * indicador passado como parâmetro.
     * @param idRead indicador de uma possível leitura a ser pesquisada
     * @return a leitura encontrada com o referido indicador. Caso contrário, null
     */
    @Query ("SELECT * FROM ReadData WHERE (id_leitura = :idRead)")
    ReadData getReadData (int idRead);

    /**
     * Método que retorna a soma dos valores de leitura realizados no mês e ano
     * indicados pelos parâmetros.
     * @param year ano de referência para o cálculo
     * @param month mês de referência para o cálculo
     * @return a soma dos valores de leitura, refentes ao mês e ano indicados
     */
    @Query ("SELECT COUNT (1) FROM ReadData WHERE " +
            "(STRFTIME ('%Y', data_leitura) = (:year) " +
            "AND STRFTIME ('%m', data_leitura) = (:month))")
    int getTotalYearMonthReadData (String year, String month);

    /**
     * Método que retorna a leitura cadastrada no mês atual de um apartamento,
     * cujo identificador é passado como parâmetro.
     * @param idApt identificador do apartamento ao qual se deseja a leitura atual
     * @return leitura atual do apartamento. Caso contrário, null
     */
    @Query ("SELECT * FROM ReadData WHERE (id_apt = :idApt) AND " +
            "(STRFTIME('%m', data_leitura) = STRFTIME ('%m','now'))")
    ReadData getCurrentReadDataById (int idApt);

    /**
     * Método que retorna uma lista de leituras referentes ao mês e ano passados como
     * parâmetros.
     * @param year ano de referência da lista
     * @param month mês de referência da lista
     * @return a lista correspondente ao mês e ano recebidos.
     */
    @Query ("SELECT * FROM ReadData WHERE ((STRFTIME ('%Y', data_leitura) = :year) " +
            "AND (STRFTIME('%m', data_leitura) = :month)) ORDER BY id_apt ASC")
    List<ReadData> getMonthListReadData (String year, String month);

    /**
     * Método que retorna uma leitura referente ao mês e ano imediatamente
     * anteriores ao mês e ano passados como parâmetro. Ou seja, retorna uma leitura
     * do mês anterior (ou menos), o qual possui a penúltima lista de leituras
     * realizadas (em relação ao mês e ano indicado nos parâmetros).
     * @param year ano de referência para busca
     * @param month mês de referência para busca
     * @return uma leitura correspondente ao mês (ou ano) anterior.
     */
    @Query ("SELECT * FROM ReadData WHERE ((STRFTIME ('%Y', data_leitura) = :year)" +
            "AND (STRFTIME ('%m', data_leitura) < :month))" +
            "OR (STRFTIME ('%Y', data_leitura) < :year) " +
            "ORDER BY id_leitura DESC LIMIT (1)")
    ReadData getLastReadPastMonth (String year, String month);

}
