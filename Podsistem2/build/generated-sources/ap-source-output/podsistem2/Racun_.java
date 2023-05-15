package podsistem2;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import podsistem2.Komitent;
import podsistem2.Transakcija;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-19T23:31:27")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile SingularAttribute<Racun, Integer> brojTransakcija;
    public static volatile SingularAttribute<Racun, Double> stanje;
    public static volatile SingularAttribute<Racun, Integer> iDMes;
    public static volatile ListAttribute<Racun, Transakcija> transakcijaList;
    public static volatile SingularAttribute<Racun, Komitent> iDKom;
    public static volatile SingularAttribute<Racun, Date> datumVremeOtvaranja;
    public static volatile SingularAttribute<Racun, Double> dozvoljeniMinus;
    public static volatile SingularAttribute<Racun, Integer> iDRac;
    public static volatile SingularAttribute<Racun, String> status;

}