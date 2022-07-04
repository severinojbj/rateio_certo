package br.com.rateiocerto.basics;

import androidx.room.DatabaseView;

/**
 * Rateio Certo
 *
 * Consulta SQL que cria uma tabela virtual (view), a qual permite o carregamento
 * das informações dos atributos pertencentes às tabelas Apartments e ReadData para
 * a classe AptRead, a qual representa cada linha dessa view.
 */
@DatabaseView ("SELECT Apartment.id_apt, Apartment.bloco, " +
        "Apartment.numero, ReadData.data_leitura AS readDataDateRead, " +
        "ReadData.valor_leitura AS readDataValueRead FROM Apartment INNER JOIN " +
        "ReadData ON (Apartment.id_apt = ReadData.id_apt) ORDER BY Apartment.id_apt ASC")

/**
 * Rateio Certo
 *
 * AptRead: classe que representa uma linha da visualização homônima, e declarada
 * na consulta SQL. Os atributos inclusos fazem parte das classes e tabelas Apartment
 * e ReadData.
 * Os atrbutos de AptRead são:
 * id_apt: intero identificador do apartamento.
 * bloco: string que armazena o nome ou código do bloco o qual pertence o apartamento.
 * numero: inteiro que representa o número do apartamento.
 * readDataDateRead: string contendo a data de leitura da tabela ReadData
 * readDataValueRead: inteiro contendo o valor de leitura da tabela
 *
 * @Author: Severino José (biujose@gmail.com)
 */
public class AptRead {
    public int id_apt;
    public String bloco;
    public int numero;
    public String readDataDateRead;
    public long readDataValueRead;

    /**
     * Método que fornece o identificador do Apartamento.
     * @return o id do apartamento
     */
    public int getId_apt() {
        return id_apt;
    }

    /**
     * Método que fornece o nome do bloco do Apartamento.
     * @return o bloco do apartamento
     */
    public String getBloco() {
        return bloco;
    }

    /**
     * Método que fornece o número do Apartamento.
     * @return o número do apartamento
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Método que fornece a data de leitura.
     * @return a data de leitura
     */
    public String getReadDataDateRead() {
        return readDataDateRead;
    }

    /**
     * Método que fornece o valor de leitura.
     * @return o valor de leitura
     */
    public long getReadDataValueRead() {
        return readDataValueRead;
    }

    /**
     * Método que fornece o número do Apartamento em formato texto.
     * O formato depende da quantidade de algarismos desejada pelo cliente.
     * Caso o número possua menos algarismos que o desejado, insere-se zeros
     * à esquerda como complemento.
     * @return o número formatado (string) do apartamento
     */
    public String getFormatedNumero () {
        return String.format("%03d", this.numero);
    }
}
