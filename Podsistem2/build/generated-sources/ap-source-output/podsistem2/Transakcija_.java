package podsistem2;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import podsistem2.Racun;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-19T23:31:27")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Double> iznos;
    public static volatile SingularAttribute<Transakcija, Date> datumVreme;
    public static volatile SingularAttribute<Transakcija, Integer> idFil;
    public static volatile SingularAttribute<Transakcija, String> tip;
    public static volatile SingularAttribute<Transakcija, Integer> redniBroj;
    public static volatile SingularAttribute<Transakcija, Racun> iDRac;
    public static volatile SingularAttribute<Transakcija, Integer> iDTra;

}