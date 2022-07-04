package br.com.rateiocerto.businiess;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.rateiocerto.basics.Apartment;
import br.com.rateiocerto.basics.AptRead;
import br.com.rateiocerto.basics.ReadData;
import br.com.rateiocerto.interfaces.ApartmentDao;
import br.com.rateiocerto.interfaces.AptReadDao;
import br.com.rateiocerto.interfaces.ReadDataDao;


/**
 * Rateio Certo
 *
 * DatabaseRateio: Classe abstrata responsável por instanciar as representações das tabelas
 * reais e virtuais, e suas consultas SQLite, de acordo com a metodologia do frameweork Room.
 * Para ter acesso ao banco de dados SQLite gerado internamente pelo Room, basta instanciar
 * essa classe.
 *
 * @Author: Severino José (biujose@gmail.com)
 */

@Database(entities = {Apartment.class, ReadData.class}, views = {AptRead.class}, version = 1)
public abstract class DatabaseRateio extends RoomDatabase {
    public abstract ApartmentDao ApartmentDao ();
    public abstract ReadDataDao ReadDataDao ();
    public abstract AptReadDao AptReadDao ();
}
