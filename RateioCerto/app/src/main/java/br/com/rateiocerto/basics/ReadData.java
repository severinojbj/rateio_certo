package br.com.rateiocerto.basics;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.Relation;


/**
 * Rateio Certo
 *
 * ReadData: Classe que representa uma leitura realizada de um determinado apartamento.
 * É usada para registrar uma tabela de mesmo nome dentro do Banco de dados.
 * Os atributos de um ReadData são:
 * idRead: identificador inteiro que torna a leitura única dentro do sistema.
 * Por padrão, é gerada automaticamente.
 * idApt: identificador inteiro do apartamento vinculado e previamente cadastrado.
 * dateRead: string da data de leitura realizada.
 * valueRead: número que representa o valor de uma leitura realizada.
 *
 * @Author: Severino José (biujose@gmail.com)
 */
@Entity
public class ReadData {

	@PrimaryKey (autoGenerate = true)
	@ColumnInfo (name = "id_leitura")
	int idRead;

//	@ForeignKey (entity = Apartment.class,
//			parentColumns = "id_apt",
//			childColumns = "id_apt",
//			onDelete = ForeignKey.CASCADE)
//	@Relation(
//			entity = Apartment.class,
//			parentColumn = "id_apt",
//			entityColumn = "id_apt"
//	)
	@ColumnInfo (name = "id_apt")
	int idApt;

	@ColumnInfo (name = "data_leitura")
	String dateRead;

	@ColumnInfo (name = "valor_leitura")
	int readValue;

	/**
	 * Constrututor padrão da classe, quando não há informações prévias da leitura.
	 */
	public ReadData () {
		this.idRead = 0;
		this.idApt = 0;
		this.dateRead = "2019-01-01";
		this.readValue = 0;
	}

	/**
	 * Construtor mais usual da classe. São passados o id do apartamento como primeiro parâmetro,
	 * a data de leitura como segundo e o valor de leitura como terceiro. O atributo idRead é
	 * gerado automaticamente no BD.
	 * @param idApt identificador do apartamento
	 * @param dateRead data de leitura
	 * @param readValue valor de leitura
	 */
	public ReadData (int idApt, String dateRead, int readValue) {
		this.idApt = idApt;
		this.dateRead = dateRead;
		this.readValue = readValue;
	}

	/**
	 * Construtor mais customizável da classe. Nele é possivel inserir um id personalizado,
	 * dispensando geração automática. É preciso ter cuidado para não inserir um código já
	 * existente no BD. Os outros parâmetros são o identificador do apartamento, a data de
	 * leitura e o valor de leitura.
	 * @param idRead identificador da leitura
	 * @param idApt identificador do apartamento
	 * @param dateRead data de leitura
	 * @param readValue valor de leitura
	 */
	public ReadData (int idRead, int idApt, String dateRead, int readValue) {

		this.idRead = idRead;
		this.idApt = idApt;
		this.dateRead = dateRead;
		this.readValue = readValue;
	}

	/**
	 * Método que fornece o identificador da leitura.
	 * @return o id da leitura
	 */
	public int getIdRead() {
		return idRead;
	}

	/**
	 * Método que permite alterar o identificador da leitura.
	 * @param idRead o novo identificador a ser carregado
	 */
	public void setIdRead(int idRead) {
		this.idRead = idRead;
	}

	/**
	 * Método que fornece o identificador do apartamento vinculdo.
	 * @return o id do apartamento
	 */
	public int getIdApt() {
		return idApt;
	}

	/**
	 * Método que permite alterar o identificador do apartamento vinculado.
	 * @param idApt o novo identificador a ser carregado
	 */
	public void setIdApt(int idApt) {
		this.idApt = idApt;
	}

	/**
	 * Método que fornece o data de realização da leitura.
	 * @return a string da data de leitura
	 */
	public String getDateRead() {
		return dateRead;
	}

	/**
	 * Método que permite alterar a data de realização da leitura.
	 * @param dateRead o nova data a ser carregada
	 */
	public void setDateRead(String dateRead) {
		this.dateRead = dateRead;
	}

	/**
	 * Método que fornece o valor da leitura.
	 * @return inteiro valor da leitura
	 */
	public int getReadValue() {
		return readValue;
	}

	/**
	 * Método que permite alterar o valor da leitura.
	 * @param readValue o novo valor (inteiro) a ser carregada
	 */
	public void setReadValue(int readValue) {
		this.readValue = readValue;
	}
}