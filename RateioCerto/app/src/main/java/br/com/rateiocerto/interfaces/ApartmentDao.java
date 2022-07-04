package br.com.rateiocerto.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.rateiocerto.basics.Apartment;

/**
 * Rateio Certo
 *
 * ApartmentDao: interface que possui os métodos responsáveis pelas consultas
 * SQLite referentes à tabela Apartments.
 *
 * @Author: Severino José (biujose@gmail.com)
 */

@Dao
public interface ApartmentDao {

    /**
     * Método de inserção de um apartamento, comando INSERT do SQLite.
     * @param apt objeto referente à nova linha inserida na tabela
     */
    @Insert
    void insertApt (Apartment apt);

    /**
     * Método de remoção de um apartamento, comando DELETE do SQLite.
     * @param apt objeto referente à linha a ser removida na tabela
     */
    @Delete
    void deleteApt (Apartment apt);

    /**
     * Método de atualização de um apartamento, comando UPDATE do SQLite.
     * @param apt objeto referente à linha a ser atualizada na tabela.
     */
    @Update
    void updateApt (Apartment apt);

    /**
     * Método de remoção de todos os dados da tabela.
     */
    @Query ("DELETE FROM Apartment")
    void clearAllAptData ();

    /**
     * Método de busca por um apartamento cadastrado, de
     * acordo com o nome do bloco e o número.
     * @param block nome do bloco a ser pesquisado
     * @param formatNum número convertido em string do apartamento a ser pesquisado
     * @return caso exista o apartamento, retorna o mesmo. Caso contrário, null
     */
    @Query ("SELECT * FROM Apartment WHERE " +
            "(bloco = :block) " +
            "AND (numero = :formatNum)")
    Apartment getApartment (String block, String formatNum);

    /**
     * Método que lista todos os apartametos cadastrados.
     * @return a lista de apartamentos cadastrados
     */
    @Query ("SELECT * FROM Apartment")
    List<Apartment> listAllApartments ();

    /**
     * Método que lista os nomes de blocos, os quais existem apartamentos
     * cadastrados.
     * @return a lista dos nomes dos blocos
     */
    @Query ("SELECT DISTINCT bloco FROM Apartment")
    List<String> listAllBlocks ();

    /**
     * Método que busca um apartamento, de acordo com o id passado
     * como parâmetro.
     * @param idApt o id referente ao apartamento a ser buscado.
     * @return caso exista o apartamento, retorna o mesmo. Caso contrário, null
     */
    @Query ("SELECT * FROM Apartment WHERE (id_apt = :idApt)")
    Apartment getApartmentById(int idApt);

    /**
     * Método que retorna a quantidade de apartamentos cadastrados no BD.
     * @return a quantidade de apartamentos cadastrados
     */
    @Query ("SELECT COUNT (1) FROM Apartment")
    int getTotalApartments ();

    /**
     * Método que retorna o primeiro apartamento cadastrado, com
     * o menor id disponível.
     * @return o apartamento com o menor id
     */
    @Query ("SELECT * FROM Apartment ORDER BY id_apt ASC LIMIT (1)")
    Apartment getFirstApartment ();

    /**
     * Método que retorna o último apartamento cadastrado, com
     * o maior id disponível.
     * @return o apartamento com o maior id
     */
    @Query ("SELECT * FROM Apartment ORDER BY id_apt DESC LIMIT (1)")
    Apartment getLastApartment ();

}
