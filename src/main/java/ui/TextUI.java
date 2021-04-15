package ui;

import java.util.List;

import domain.Sovellus;
import io.IO;

public class TextUI {
    private IO io;
    private Sovellus sovellus;

    public TextUI(IO io, Sovellus sovellus) {
        this.io = io;
        this.sovellus = sovellus;
    }

    public void suorita() {
        this.io.print("Valitse toiminto syöttämällä numero:");
        this.printInfo();

        while (true) {
            var komento = this.io.nextInt("Komento: ");

            if (komento == -1) {
                this.io.print("Hei hei!");
                break;
            }

            switch (komento) {
            case -2:
                this.io.error("Ei numero! Syötä numero komentovalikosta");
                printInfo();
                break;
            case 0:
                printInfo();
                break;
            case 1:
                selaaVinkkeja();
                break;
            case 2:
                lisaaVinkki();
                break;
            case 3:
                    poistaVinkki();
                    break;
            case 4:
                haeTagilla();
                break;
            default:
                this.io.error("Komentoa ei löydy!");
                printInfo();
                break;
            }
        }
    }

    private void selaaVinkkeja() {
        for (String vinkki : sovellus.selaaVinkkeja())
            this.io.print(vinkki);
    }

    private void poistaVinkki() {
        List<String> otsikot = sovellus.listaaOtsikotIdlla();
        if (otsikot.isEmpty()) {
            this.io.print("Ei poistettavia vinkkejä!");
        } else {
            for (String otsikko : otsikot) {
                this.io.print(otsikko);
            }
            var poistettava = this.io.nextInt("Anna poistettavan vinkin id-numero:");
            if (poistettava > otsikot.size() || poistettava < 1) {
                this.io.print("Virheellinen id-numero");
            } else {
                var vastaus = this.io
                        .nextLine("Haluatko varmasti poistaa vinkin " + otsikot.get(poistettava - 1) + " (kyllä/ei)");
                if (vastaus.equals("kyllä")) {
                    sovellus.poistaVinkki(poistettava);
                    this.io.print("Vinkki poistettu");
                } else {
                    this.io.print("Vinkkiä ei poistettu");
                }
            }
        }
    }

    private void lisaaVinkki() {
        var otsikko = this.io.nextLine("Anna lukuvinkin otsikko: ");
        while (sovellus.tarkistaOtsikko(otsikko)) {
            this.io.error("Syöttämälläsi otsikolla löytyy jo vinkki. Syötä uniikki otsikko");
            otsikko = this.io.nextLine("Anna lukuvinkin otsikko: ");
        }
        var URL = this.io.nextLine("Anna lukuvinkin URL: ");
        String tagit = this.io.nextLine("Lisää tägejä pilkulla erotettuna: ");
        sovellus.lisaaVinkki(otsikko, URL, tagit);
    }

    private void haeTagilla() {
        String haettavaTagi = this.io.nextLine("Syötä etsittävä tagi: ");

        List<String> vinkit = sovellus.etsiVinkkejaTagilla(haettavaTagi);

        if (vinkit.size() == 0) {
            this.io.print("Yhtään vinkkiä ei löytynyt tagilla \"" + haettavaTagi + "\".");
            return;
        }

        this.io.print("" + vinkit.size() + " vinkki(ä) löytyi tagilla \"" + haettavaTagi + "\".");
        for (String vinkki : vinkit)
            this.io.print(vinkki);
    }

    private void testaus() {
        String output = "#";
        String tagit = "aa,bb,cc,dd";
        String[] tagiLista = tagit.split(",");
        output += String.join(", #", tagiLista);

        this.io.print(output);
    }

    private void printInfo() {
        this.io.print(" 0: Info");
        this.io.print(" 1: Selaa vinkkejä");
        this.io.print(" 2: Lisää uusi vinkki");
        this.io.print(" 3: Poista vinkki");
        this.io.print(" 4: Etsi tagilla");
        this.io.print("-1: Poistu");
    }
}
