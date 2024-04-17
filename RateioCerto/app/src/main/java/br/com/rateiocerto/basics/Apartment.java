package br.com.rateiocerto.basics;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Rateio Certo
 *
 * Apartment: classe que representa cada linha da tabela de Apartamentos.
 * É usada para registrar uma tabela de mesmo nome dentro do Banco de dados.
 * Cada Apartamento possui atributo (id_apt) que o torna único dentro do sistema.
 * Os atrbutos de um Apartamento são:
 * id: identificador inteiro único que define e diferencia cada apartamento registrado.
 * O número identificador é gerado automaticamente para registro no BD.
 * block: string que armazena o nome ou código do bloco o qual pertence o apartamento.
 * num: inteiro que representa o número do apartamento.
 *
 * @Author: Severino José (biujose@gmail.com)
 */

@Entity
public class Apartment {

	@PrimaryKey (autoGenerate = true)
	@ColumnInfo (name = "id_apt")
	int id;

	@ColumnInfo (name = "bloco")
	String block;

	@ColumnInfo (name = "numero")
	int num;

	/**
	 * Constrututor padrão da classe, quando não há informações prévias do apartamento.
	 */

	public Apartment () {
		this.id = 0;
		this.block = "Z";
		this.num = 0;
	}

	/**
	 * Construtor mais usual da classe. São passados o nome do bloco como primeiro parâmetro
	 * e o número do apartamento como segundo. O atributo id é gerado automaticamente no BD.
	 * @param block nome ou código do bloco
	 * @param num número do apartamento
	 */
	@Ignore
	public Apartment (String block, int num) {
		this.block = block;
		this.num = num;
	}

	/**
	 * Construtor mais customizável da classe. Nele é possivel inserir um id personalizado,
	 * dispensando geração automática. É preciso ter cuidado para não inserir um código já
	 * existente no BD. Os outros parâmetros são o nome do bloco, e o número do apartamento.
	 * @param id código identificador do apartamento.
	 * @param block nome ou código do bloco
	 * @param num número do apartamento
	 */

	public Apartment (int id, String block, int num) {
		this.id = id;
		this.block = block;
		this.num = num;
	}

	/**
	 * Método que fornece o identificador do Apartamento.
	 * @return o id do apartamento
	 */
	public int getId() {
		return id;
	}

	/**
	 * Método que permite alterar o identificador do Apartamento.
	 * @param id o novo identificador a ser carregado
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Método que fornece o bloco do Apartamento.
	 * @return o bloco do apartamento
	 */
	public String getBlock() {
		return block;
	}

	/**
	 * Método que permite alterar o bloco do Apartamento.
	 * @param block o novo bloco a ser carregado
	 */
	public void setBlock(String block) {
		this.block = block;
	}

	/**
	 * Método que fornece o número do Apartamento.
	 * @return o número do apartamento
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Método que permite alterar o número do Apartamento.
	 * @param num o novo número a ser carregado
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * Método que fornece o número do Apartamento em formato texto.
	 * O formato depende da quantidade de algarismos desejada pelo cliente.
	 * Caso o número possua menos algarismos que o desejado, insere-se zeros
	 * à esquerda como complemento.
	 * @return o número formatado (string) do apartamento
	 */
	public String getFormatedNum () {
		return String.format("%03d", this.num);
	}
}
