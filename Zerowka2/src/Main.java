import java.util.*;
import java.time.LocalDateTime;
 abstract class Transport{
    protected int iloscMiejsc;
    protected double cenaBiletu;
    public Transport(){
        this.iloscMiejsc=0;
        this.cenaBiletu=0.0;
    }
    public void obliczCene(){
        this.cenaBiletu=110.0;
    }
    public double pokazCeneBiletu(){
        return cenaBiletu;
    }
}
class Autobus extends Transport{
    public Autobus(int iloscMiejsc){
        this.iloscMiejsc=iloscMiejsc;
        super.obliczCene();
    }
    @Override
    public String toString(){
        return "Autobus: ilość miejsc= "+ iloscMiejsc + ", cena biletu= " + cenaBiletu;
    }
}
class Pociag extends Transport{
    private int dlugoscTrasy;
    public Pociag(int iloscMiejsc, int dlugoscTrasy){
        this.iloscMiejsc=iloscMiejsc;
        this.dlugoscTrasy=dlugoscTrasy;
        obliczCene();
    }
    @Override
    public void obliczCene(){
        if(dlugoscTrasy>100){
            this.cenaBiletu=dlugoscTrasy*1.45;
        }
        else{
            super.obliczCene();
        }
    }
    @Override
    public String toString(){
        return "Pociag: ilość miejsc= "+ iloscMiejsc + ", cena biletu= " + cenaBiletu;
    }
}

class ZazTrans implements IZarzadzaj{
    private List<Transport> pojazdy;
    public ZazTrans(){
        this.pojazdy=new ArrayList<>();
    }
    @Override
    public void dodajAutobus(int iloscMiejsc){
        pojazdy.add(new Autobus(iloscMiejsc));
    }
    @Override
    public void dodajPociag(int iloscMiejsc,int dlugoscTrasy){
        pojazdy.add(new Pociag(iloscMiejsc, dlugoscTrasy));
    }
    @Override
    public void usunOstatni(){
        if(!pojazdy.isEmpty()){
            pojazdy.remove(pojazdy.size()-1);
        }
        else{
            System.out.println("Lista jest pusta");
        }
    }
    @Override
    public void wyczysc(){
        pojazdy.clear();
    }
    public void wyswietlPojazdy(){
        for(Transport pojazd : pojazdy){
            System.out.println(pojazd);
        }
    }
}
class DataC implements IData{
     private LocalDateTime data;
     @Override
    public void ustawDate(LocalDateTime data){
         this.data=data;
     }
     @Override
    public boolean sprawdzDate(LocalDateTime data){
         if(this.data==null){
             return false;
         }
         return this.data.isEqual(data);
     }
}
class Podroz implements IZarzadzaj, IData{
     private LocalDateTime dataPodrozy;
     private List<Transport> planPodrozy= new ArrayList<>();
     private double koszt=0.0;

     @Override
    public void dodajAutobus(int iloscMiejsc){
         Autobus autobus=new Autobus(iloscMiejsc);
         autobus.obliczCene();
         planPodrozy.add(autobus);
         koszt+=autobus.cenaBiletu;
     }
     @Override
     public void dodajPociag(int iloscMiejsc, int dlugoscTrasy){
         Pociag pociag= new Pociag(iloscMiejsc,dlugoscTrasy);
         planPodrozy.add(pociag);
         koszt+=pociag.cenaBiletu;
     }
     @Override
    public void usunOstatni() {
         if (!planPodrozy.isEmpty()) {
             Transport ostatniPojazd = planPodrozy.remove(planPodrozy.size() - 1);
             koszt -= ostatniPojazd.cenaBiletu;
         }
     }
     @Override
    public void wyczysc(){
         planPodrozy.clear();
         koszt=0.0;
     }
     @Override
    public void ustawDate(LocalDateTime data){
         this.dataPodrozy=data;
     }
     @Override
    public boolean sprawdzDate(LocalDateTime data){
         if(this.dataPodrozy==null){
             return false;
         }
         return this.dataPodrozy.isAfter(LocalDateTime.now());
     }
    @Override
    public String toString(){
        return "Podróż: data podróży= "+ dataPodrozy + ", koszt= " + koszt+", plan podróży= " + planPodrozy;
    }
}
public class Main {
    public static void main(String[] args) {

        Autobus autobus = new Autobus(50);
        System.out.println(autobus);
        Pociag pociag1=new Pociag(200,150);
        Pociag pociag2=new Pociag(200,99);
        System.out.println(pociag1);
        System.out.println(pociag2);
        ZazTrans zarzadzaj = new ZazTrans();
        zarzadzaj.dodajAutobus(50);
        zarzadzaj.dodajPociag(250,233);
        zarzadzaj.wyswietlPojazdy();
        zarzadzaj.usunOstatni();
        zarzadzaj.wyswietlPojazdy();
        zarzadzaj.wyczysc();
        zarzadzaj.wyswietlPojazdy();
        DataC datac= new DataC();
        LocalDateTime data1=LocalDateTime.now();
        LocalDateTime data2=data1.minusSeconds(1);
        datac.ustawDate(data1);
        System.out.println("Czy data1 jest rowna z dzisiejsza?:"+ datac.sprawdzDate(data1));
        System.out.println("Czy data2 jest rowna z dzisiejsza?:"+ datac.sprawdzDate(data2));


    }
}