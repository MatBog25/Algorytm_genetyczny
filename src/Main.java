import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        int rozmiarPopulacji = 10;
        int liczbaZmiennych = 1;
        double dokladnosc = 0.1;
        double dolnaGranica = -1;
        double gornaGranica = 1;
        int sukcesjaPokolenia = 1000;
        int d = 1;

        List<List<Integer>> populacja = Popbinarna(rozmiarPopulacji, liczbaZmiennych, d, dolnaGranica, gornaGranica);

        Map<Integer, MyPair<List<Integer>, Double>> mapa = new HashMap<>();

        List<OsobnikPoEwolucji> PopulacjaEwo = ocenOsobniki(populacja, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

        for (int i = 0; i < rozmiarPopulacji; i++) {
            OsobnikPoEwolucji osobnikPoEwolucji = PopulacjaEwo.get(i);

            MyPair<List<Integer>, Double> pair = new MyPair<>(osobnikPoEwolucji.osobnik, osobnikPoEwolucji.przystosowanie);

            mapa.put(i, pair);
        }
        for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : mapa.entrySet()) {
            Integer indeks = entry.getKey();
            List<Integer> osobnik = entry.getValue().getLeft();
            Double przystosowanie = entry.getValue().getRight();
            System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnik + ", Przystosowanie: " + przystosowanie);
        }
        System.out.println();
        System.out.println("Populacja po metodzie rankingowej");
        System.out.println();
        // Wywołanie funkcji rankingowej na przystosowaniach z mapy
        Map<Integer, MyPair<List<Integer>, Double>> zaktualizowanaMapa = rankingowa(mapa, rozmiarPopulacji);

        // Wypisanie zaktualizowanych przystosowań
        for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : zaktualizowanaMapa.entrySet()) {
            Integer indeks = entry.getKey();
            List<Integer> osobnik = entry.getValue().getLeft();
            Double przystosowanie = entry.getValue().getRight();
            System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnik + ", Przystosowanie: " + przystosowanie);
        }
        System.out.println();
        // Przekształcenie mapy na listę List<List<Integer>>
        List<List<Integer>> listaOsobnikow = new ArrayList<>();
        for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : zaktualizowanaMapa.entrySet()) {
            List<Integer> osobnik = entry.getValue().getLeft();
            listaOsobnikow.add(osobnik);
        }


        System.out.println("Populacja po metodzie ruletki");
        System.out.println();

        Map<Integer, MyPair<List<Integer>, Double>> MapaRuletki = ruletki(mapa, rozmiarPopulacji);

        // Wypisanie zaktualizowanych przystosowań
        for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : MapaRuletki.entrySet()) {
            Integer indeks = entry.getKey();
            List<Integer> osobnik = entry.getValue().getLeft();
            Double przystosowanie = entry.getValue().getRight();
            System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnik + ", Przystosowanie: " + przystosowanie);
        }
        System.out.println();



        System.out.println("Populacja po metodzie turniejowej");
        System.out.println();
        Map<Integer, MyPair<List<Integer>, Double>> MapaTurniejowa = turniejowa(mapa, rozmiarPopulacji);

        for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : MapaTurniejowa.entrySet()) {
            Integer indeks = entry.getKey();
            List<Integer> osobnik = entry.getValue().getLeft();
            Double przystosowanie = entry.getValue().getRight();
            System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnik + ", Przystosowanie: " + przystosowanie);
        }
        System.out.println();



        List<List<Integer>> mutacja = mutujPopulacje(listaOsobnikow, 0.1);
        System.out.println();
        System.out.println("MUTACJA:");
        Wypisz(mutacja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

        List<List<Integer>> inwersja = inwersjaPopulacji(mutacja, 0.2);
        System.out.println();
        System.out.println("INWERSJA:");
        Wypisz(inwersja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

        List<List<Integer>> krzyzowanie = krzyzowanie_core(inwersja, 0.5, 1);
        System.out.println();
        System.out.println("INWERSJA:");
        Wypisz(krzyzowanie, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
        System.out.println();


    //SUKCESJA TRYWIALNA
//        List<List<Integer>> populacjaSukcesja = krzyzowanie;
//        for (int j=0; j<sukcesjaPokolenia; j++){
//            System.out.println("Pokolenie Sukcesja nr." + j);
//
//            Map<Integer, MyPair<List<Integer>, Double>> mapaSukcesja = new HashMap<>();
//
//            List<OsobnikPoEwolucji> PopulacjaEwoSukcesja = ocenOsobniki(populacjaSukcesja, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
//
//            for (int i = 0; i < rozmiarPopulacji; i++) {
//                OsobnikPoEwolucji osobnikPoEwolucjiSukcesja = PopulacjaEwoSukcesja.get(i);
//
//                MyPair<List<Integer>, Double> pairSukcesja = new MyPair<>(osobnikPoEwolucjiSukcesja.osobnik, osobnikPoEwolucjiSukcesja.przystosowanie);
//
//                mapaSukcesja.put(i, pairSukcesja);
//            }
//
//            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entrySukcesja : mapaSukcesja.entrySet()) {
//                Integer indeks = entrySukcesja.getKey();
//                List<Integer> osobnikSukcesja = entrySukcesja.getValue().getLeft();
//                Double przystosowanie = entrySukcesja.getValue().getRight();
//                System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnikSukcesja + ", Przystosowanie: " + przystosowanie);
//            }
//            System.out.println();
//            System.out.println("Populacja po metodzie rankingowej");
//            System.out.println();
//
//            Map<Integer, MyPair<List<Integer>, Double>> zaktualizowanaMapaSukcesja = rankingowa(mapaSukcesja, rozmiarPopulacji);
//
//
//            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : zaktualizowanaMapaSukcesja.entrySet()) {
//                Integer indeks = entry.getKey();
//                List<Integer> osobnik = entry.getValue().getLeft();
//                Double przystosowanie = entry.getValue().getRight();
//                System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnik + ", Przystosowanie: " + przystosowanie);
//            }
//            System.out.println();
//
//            List<List<Integer>> listaOsobnikowSukcesja = new ArrayList<>();
//            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : zaktualizowanaMapaSukcesja.entrySet()) {
//                List<Integer> osobnik = entry.getValue().getLeft();
//                listaOsobnikowSukcesja.add(osobnik);
//            }
//
//            List<List<Integer>> mutacjaSukcesja = mutujPopulacje(listaOsobnikowSukcesja, 0.1);
//            System.out.println();
//            System.out.println("MUTACJA:");
//            Wypisz(mutacjaSukcesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
//
//            List<List<Integer>> inwersjaSuckesja = inwersjaPopulacji(mutacjaSukcesja, 0.2);
//            System.out.println();
//            System.out.println("INWERSJA:");
//            Wypisz(inwersjaSuckesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
//
//            List<List<Integer>> krzyzowanieSukcesja = krzyzowanie_core(inwersjaSuckesja, 0.5, 1);
//            System.out.println();
//            System.out.println("KRZYZOWANIE:");
//            Wypisz(krzyzowanieSukcesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
//            System.out.println();
//
//            populacjaSukcesja = krzyzowanieSukcesja;
//        }


    //SUKCESJA Z CZESCIOWYM ZASTEPOWANIEM - LOSOWA
//        List<List<Integer>> wybraneElementyMutacja = losoweWybieranie(mutacja, 9);
//        List<List<Integer>> wybraneElementyInwersja = losoweWybieranie(inwersja, 1);
//        List<List<Integer>> wybraneElementyKrzyzowanie = losoweWybieranie(krzyzowanie, 6);
//
//        List<List<Integer>> SumaList = new ArrayList<>();
//        SumaList.addAll(populacja);
//        SumaList.addAll(wybraneElementyMutacja);
//        SumaList.addAll(wybraneElementyInwersja);
//        SumaList.addAll(wybraneElementyKrzyzowanie);
//
//        List<List<Integer>> wybraniOsobnicy = losoweWybieranie(SumaList, 10);
//        List<List<Integer>> populacjaSukcesja = new ArrayList<>();
//        for (int j=0; j<sukcesjaPokolenia; j++){
//            System.out.println("Pokolenie Sukcesja nr." + j);
//
//
//
//            Map<Integer, MyPair<List<Integer>, Double>> mapaSukcesja = new HashMap<>();
//
//            List<OsobnikPoEwolucji> PopulacjaEwoSukcesja = ocenOsobniki(wybraniOsobnicy, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
//
//            for (int i = 0; i < rozmiarPopulacji; i++) {
//                OsobnikPoEwolucji osobnikPoEwolucjiSukcesja = PopulacjaEwoSukcesja.get(i);
//
//                MyPair<List<Integer>, Double> pairSukcesja = new MyPair<>(osobnikPoEwolucjiSukcesja.osobnik, osobnikPoEwolucjiSukcesja.przystosowanie);
//
//                mapaSukcesja.put(i, pairSukcesja);
//            }
//
//            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entrySukcesja : mapaSukcesja.entrySet()) {
//                Integer indeks = entrySukcesja.getKey();
//                List<Integer> osobnikSukcesja = entrySukcesja.getValue().getLeft();
//                Double przystosowanie = entrySukcesja.getValue().getRight();
//                System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnikSukcesja + ", Przystosowanie: " + przystosowanie);
//            }
//            System.out.println();
//            System.out.println("Populacja po metodzie rankingowej");
//            System.out.println();
//
//            Map<Integer, MyPair<List<Integer>, Double>> zaktualizowanaMapaSukcesja = rankingowa(mapaSukcesja, rozmiarPopulacji);
//
//
//            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : zaktualizowanaMapaSukcesja.entrySet()) {
//                Integer indeks = entry.getKey();
//                List<Integer> osobnik = entry.getValue().getLeft();
//                Double przystosowanie = entry.getValue().getRight();
//                System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnik + ", Przystosowanie: " + przystosowanie);
//            }
//            System.out.println();
//
//            List<List<Integer>> listaOsobnikowSukcesja = new ArrayList<>();
//            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : zaktualizowanaMapaSukcesja.entrySet()) {
//                List<Integer> osobnik = entry.getValue().getLeft();
//                listaOsobnikowSukcesja.add(osobnik);
//            }
//
//            List<List<Integer>> mutacjaSukcesja = mutujPopulacje(listaOsobnikowSukcesja, 0.1);
//            System.out.println();
//            System.out.println("MUTACJA:");
//            Wypisz(mutacjaSukcesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
//
//            List<List<Integer>> inwersjaSuckesja = inwersjaPopulacji(mutacjaSukcesja, 0.2);
//            System.out.println();
//            System.out.println("INWERSJA:");
//            Wypisz(inwersjaSuckesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
//
//            List<List<Integer>> krzyzowanieSukcesja = krzyzowanie_core(inwersjaSuckesja, 0.5, 1);
//            System.out.println();
//            System.out.println("KRZYZOWANIE:");
//            Wypisz(krzyzowanieSukcesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
//            System.out.println();
//
//            populacjaSukcesja = wybraniOsobnicy;
//
//            List<List<Integer>> wybraneElementyMutacjaSukcesja = losoweWybieranie(mutacjaSukcesja, 9);
//            List<List<Integer>> wybraneElementyInwersjaSukcesja = losoweWybieranie(inwersjaSuckesja, 1);
//            List<List<Integer>> wybraneElementyKrzyzowanieSukcesja = losoweWybieranie(krzyzowanieSukcesja, 6);
//
//            List<List<Integer>> SumaListSukcesja = new ArrayList<>();
//            SumaListSukcesja.addAll(populacjaSukcesja);
//            SumaListSukcesja.addAll(wybraneElementyMutacjaSukcesja);
//            SumaListSukcesja.addAll(wybraneElementyInwersjaSukcesja);
//            SumaListSukcesja.addAll(wybraneElementyKrzyzowanieSukcesja);
//
//            wybraniOsobnicy = losoweWybieranie(SumaListSukcesja, 10);
//        }



        //SUKCESJA Z CZESCIOWYM ZASTEPOWANIEM - ELITARNA

        List<List<Integer>> wybraneElementyMutacja = losoweWybieranie(mutacja, 9);
        List<List<Integer>> wybraneElementyInwersja = losoweWybieranie(inwersja, 1);
        List<List<Integer>> wybraneElementyKrzyzowanie = losoweWybieranie(krzyzowanie, 6);

        List<List<Integer>> SumaList = new ArrayList<>();
        SumaList.addAll(populacja);
        SumaList.addAll(wybraneElementyMutacja);
        SumaList.addAll(wybraneElementyInwersja);
        SumaList.addAll(wybraneElementyKrzyzowanie);

        Map<Integer, MyPair<List<Integer>, Double>> mapaElitarna = new HashMap<>();

        List<OsobnikPoEwolucji> PopulacjaEwoElitarna = ocenOsobniki(SumaList, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

        for (int i = 0; i < rozmiarPopulacji; i++) {
            OsobnikPoEwolucji osobnikPoEwolucji = PopulacjaEwoElitarna.get(i);

            MyPair<List<Integer>, Double> pair = new MyPair<>(osobnikPoEwolucji.osobnik, osobnikPoEwolucji.przystosowanie);

            mapaElitarna.put(i, pair);
        }

        List<MyPair<List<Integer>, Double>> najlepsiOsobnicy = wybierzNajlepszychOsobnikow(mapaElitarna, 10);

        Map<Integer, MyPair<List<Integer>, Double>> nowaMapa = zapiszDoNowejMapy(najlepsiOsobnicy);
        System.out.println();
        System.out.println("Populacja poczatkowa do Sukcesji czesciowej Elitarnej");
        System.out.println();
        for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : nowaMapa.entrySet()) {
            Integer indeks = entry.getKey();
            MyPair<List<Integer>, Double> pair = entry.getValue();
            System.out.println("Indeks: " + indeks + ", Osobnik: " + pair.getLeft() + ", Przystosowanie: " + pair.getRight());
        }
        List<List<Integer>> PopulacjaSukcesja = new ArrayList<>();
        for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : nowaMapa.entrySet()) {
            List<Integer> osobnik = entry.getValue().getLeft();
            PopulacjaSukcesja.add(osobnik);
        }

        for (int j=0; j<sukcesjaPokolenia; j++){
            System.out.println("Pokolenie Sukcesja nr." + j);


            Map<Integer, MyPair<List<Integer>, Double>> mapaSukcesja = new HashMap<>();

            List<OsobnikPoEwolucji> PopulacjaEwoSukcesja = ocenOsobniki(PopulacjaSukcesja, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

            for (int i = 0; i < rozmiarPopulacji; i++) {
                OsobnikPoEwolucji osobnikPoEwolucjiSukcesja = PopulacjaEwoSukcesja.get(i);

                MyPair<List<Integer>, Double> pairSukcesja = new MyPair<>(osobnikPoEwolucjiSukcesja.osobnik, osobnikPoEwolucjiSukcesja.przystosowanie);

                mapaSukcesja.put(i, pairSukcesja);
            }

            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entrySukcesja : mapaSukcesja.entrySet()) {
                Integer indeks = entrySukcesja.getKey();
                List<Integer> osobnikSukcesja = entrySukcesja.getValue().getLeft();
                Double przystosowanie = entrySukcesja.getValue().getRight();
                System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnikSukcesja + ", Przystosowanie: " + przystosowanie);
            }
            System.out.println();
            System.out.println("Populacja po metodzie rankingowej");
            System.out.println();

            Map<Integer, MyPair<List<Integer>, Double>> zaktualizowanaMapaSukcesja = rankingowa(mapaSukcesja, rozmiarPopulacji);


            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : zaktualizowanaMapaSukcesja.entrySet()) {
                Integer indeks = entry.getKey();
                List<Integer> osobnik = entry.getValue().getLeft();
                Double przystosowanie = entry.getValue().getRight();
                System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnik + ", Przystosowanie: " + przystosowanie);
            }
            System.out.println();

            List<List<Integer>> listaOsobnikowSukcesja = new ArrayList<>();
            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : zaktualizowanaMapaSukcesja.entrySet()) {
                List<Integer> osobnik = entry.getValue().getLeft();
                listaOsobnikowSukcesja.add(osobnik);
            }

            List<List<Integer>> mutacjaSukcesja = mutujPopulacje(listaOsobnikowSukcesja, 0.1);
            System.out.println();
            System.out.println("MUTACJA:");
            Wypisz(mutacjaSukcesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

            List<List<Integer>> inwersjaSuckesja = inwersjaPopulacji(mutacjaSukcesja, 0.2);
            System.out.println();
            System.out.println("INWERSJA:");
            Wypisz(inwersjaSuckesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

            List<List<Integer>> krzyzowanieSukcesja = krzyzowanie_core(inwersjaSuckesja, 0.5, 1);
            System.out.println();
            System.out.println("KRZYZOWANIE:");
            Wypisz(krzyzowanieSukcesja, rozmiarPopulacji, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

            System.out.println();

            List<List<Integer>> wybraneElementyMutacjaSukcesja = losoweWybieranie(mutacjaSukcesja, 9);
            List<List<Integer>> wybraneElementyInwersjaSukcesja = losoweWybieranie(inwersjaSuckesja, 1);
            List<List<Integer>> wybraneElementyKrzyzowanieSukcesja = losoweWybieranie(krzyzowanieSukcesja, 6);

            List<List<Integer>> SumaListSukcesja = new ArrayList<>();
            SumaListSukcesja.addAll(PopulacjaSukcesja);
            SumaListSukcesja.addAll(wybraneElementyMutacjaSukcesja);
            SumaListSukcesja.addAll(wybraneElementyInwersjaSukcesja);
            SumaListSukcesja.addAll(wybraneElementyKrzyzowanieSukcesja);
            PopulacjaSukcesja.clear();
            Map<Integer, MyPair<List<Integer>, Double>> mapaElitarnaSukcesja = new HashMap<>();

            List<OsobnikPoEwolucji> PopulacjaEwoElitarnaSukcesja = ocenOsobniki(SumaListSukcesja, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);

            for (int i = 0; i < rozmiarPopulacji; i++) {
                OsobnikPoEwolucji osobnikPoEwolucji = PopulacjaEwoElitarnaSukcesja.get(i);

                MyPair<List<Integer>, Double> pair = new MyPair<>(osobnikPoEwolucji.osobnik, osobnikPoEwolucji.przystosowanie);

                mapaElitarnaSukcesja.put(i, pair);
            }

            List<MyPair<List<Integer>, Double>> najlepsiOsobnicySukcejsa = wybierzNajlepszychOsobnikow(mapaElitarnaSukcesja, 10);

            Map<Integer, MyPair<List<Integer>, Double>> nowaMapaSukcesja = zapiszDoNowejMapy(najlepsiOsobnicySukcejsa);

            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : nowaMapaSukcesja.entrySet()) {
                List<Integer> osobnik = entry.getValue().getLeft();
                PopulacjaSukcesja.add(osobnik);
            }

        }
    }

    public static void Wypisz(List<List<Integer>> Lista, int rozmiarPopulacji, int liczbaZmiennych, double dolnaGranica, double gornaGranica, double dokladnosc){
        List<OsobnikPoEwolucji> PopulacjaEwo = ocenOsobniki(Lista, liczbaZmiennych, dolnaGranica, gornaGranica, dokladnosc);
        Map<Integer, MyPair<List<Integer>, Double>> mapa = new HashMap<>();
        for (int i = 0; i < Lista.size(); i++) {
            OsobnikPoEwolucji osobnikPoEwolucji = PopulacjaEwo.get(i);

            MyPair<List<Integer>, Double> pair = new MyPair<>(osobnikPoEwolucji.osobnik, osobnikPoEwolucji.przystosowanie);

            mapa.put(i, pair);
        }
        for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : mapa.entrySet()) {
            Integer indeks = entry.getKey();
            List<Integer> osobnik = entry.getValue().getLeft();
            Double przystosowanie = entry.getValue().getRight();
            System.out.println("Indeks: " + indeks + ", Osobnik: " + osobnik + ", Przystosowanie: " + przystosowanie);
        }
    }

    public static List<List<Integer>> losoweWybieranie(List<List<Integer>> listaList, int liczbaElementow) {
        if (listaList.size() <= liczbaElementow) {
            return new ArrayList<>(listaList);
        }

        List<List<Integer>> wybraneElementy = new ArrayList<>();
        List<Integer> indeksy = new ArrayList<>();

        Random random = new Random();

        while (wybraneElementy.size() < liczbaElementow) {
            int losowyIndeks = random.nextInt(listaList.size());

            if (!indeksy.contains(losowyIndeks)) {
                indeksy.add(losowyIndeks);
                wybraneElementy.add(listaList.get(losowyIndeks));
            }
        }

        return wybraneElementy;
    }

    public static List<MyPair<List<Integer>, Double>> wybierzNajlepszychOsobnikow(Map<Integer, MyPair<List<Integer>, Double>> mapa, int liczbaOsobnikow) {
        List<MyPair<List<Integer>, Double>> listaOsobnikow = new ArrayList<>(mapa.values());
        Collections.sort(listaOsobnikow, Comparator.comparingDouble(MyPair::getRight));
        return listaOsobnikow.subList(0, liczbaOsobnikow);
    }

    public static Map<Integer, MyPair<List<Integer>, Double>> zapiszDoNowejMapy(List<MyPair<List<Integer>, Double>> listaOsobnikow) {
        Map<Integer, MyPair<List<Integer>, Double>> nowaMapa = new HashMap<>();

        for (int i = 0; i < listaOsobnikow.size(); i++) {
            MyPair<List<Integer>, Double> pair = listaOsobnikow.get(i);
            nowaMapa.put(i, pair);
        }

        return nowaMapa;
    }
    public static List<List<Integer>> Popbinarna(int rozPopulacji, int liczbaZmiennych, double d, double dolnaGranica, double gornaGranica) {
        List<List<Integer>> populacja2 = new ArrayList<>();
        int dlugoscBinarna = (int) Math.ceil(((gornaGranica - dolnaGranica) * Math.pow(10,d) + 1));

        for (int i = 0; i < rozPopulacji; i++) {
            List<Integer> osobnik = new ArrayList<>();
            for (int j = 0; j < liczbaZmiennych * dlugoscBinarna; j++) {
                osobnik.add(new Random().nextInt(2));
            }
            populacja2.add(osobnik);
        }
        return populacja2;
    }

    public static List<List<Integer>> mutujPopulacje(List<List<Integer>> populacja, double mutacjaProb) {
        List<List<Integer>> nowaPopulacja = new ArrayList<>();

        for (List<Integer> osobnik : populacja) {
            List<Integer> nowyOsobnik = new ArrayList<>();

            for (int gen : osobnik) {
                if (Math.random() < mutacjaProb) {
                    nowyOsobnik.add((gen == 0) ? 1 : 0);
                } else {
                    nowyOsobnik.add(gen);
                }
            }

            nowaPopulacja.add(nowyOsobnik);
        }
        return nowaPopulacja;
    }


    public static List<List<Integer>> inwersjaPopulacji(List<List<Integer>> populacja, double inwersjaProb) {
        Random rand = new Random();

        List<List<Integer>> zmienionaPopulacja = new ArrayList<>();

        for (List<Integer> osobnik : populacja) {
            if (Math.random() < inwersjaProb) {
                int punktStartowy = rand.nextInt(osobnik.size());
                int punktKoncowy = rand.nextInt(osobnik.size());

                if (punktStartowy > punktKoncowy) {
                    int temp = punktStartowy;
                    punktStartowy = punktKoncowy;
                    punktKoncowy = temp;
                }

                List<Integer> zmienionyOsobnik = new ArrayList<>(osobnik);
                while (punktStartowy < punktKoncowy) {
                    int temp = zmienionyOsobnik.get(punktStartowy);
                    zmienionyOsobnik.set(punktStartowy, zmienionyOsobnik.get(punktKoncowy));
                    zmienionyOsobnik.set(punktKoncowy, temp);
                    punktStartowy++;
                    punktKoncowy--;
                }
                zmienionaPopulacja.add(zmienionyOsobnik);
            } else {
                zmienionaPopulacja.add(osobnik);
            }
        }
        return zmienionaPopulacja;
    }

    public static List<List<Integer>> krzyzowanie_core(List<List<Integer>> populacja22, double krzyzowanieProb, int rodzaj){
        Random rand = new Random();
        List<List<Integer>> populacjaKrzyzowanie = populacja22;
        List<List<Integer>> populacjaDoKrzyzowania = new ArrayList<>();
        for (int i = 0; i < populacjaKrzyzowanie.size(); i++) {
            List<Integer> osobnik = populacjaKrzyzowanie.get(i);
            if (new Random().nextDouble(1) < krzyzowanieProb) {
                populacjaDoKrzyzowania.add(osobnik);
                populacjaKrzyzowanie.remove(i);
            }
        }
        if(populacjaDoKrzyzowania.size() < 2){
            return populacjaKrzyzowanie;
        }
        if(populacjaDoKrzyzowania.size()%2 != 0){
            populacjaKrzyzowanie.add(populacjaDoKrzyzowania.remove(rand.nextInt(populacjaDoKrzyzowania.size())));
        }

        List<List<Integer>> wybraneOsoby = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < populacjaDoKrzyzowania.size()/2; i++) {
            List<List<Integer>> pary = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                if (populacjaDoKrzyzowania.isEmpty()) {
                    break;
                }
                int losowyIndeks = random.nextInt(populacjaDoKrzyzowania.size());
                List osobnik = populacjaDoKrzyzowania.get(losowyIndeks);
                pary.add(osobnik);
            }

            if (pary.size() == 2) {
                wybraneOsoby.addAll(pary);
            }
        }
        if(rodzaj == 1) {
            List<List<Integer>> krzyzowanie_wynik = new ArrayList<>();
            for (int i = 0; i < wybraneOsoby.size(); i += 2) {
                List osobnik1 = wybraneOsoby.get(i);
                List osobnik2 = wybraneOsoby.get(i + 1);
                int losowyIndeks = random.nextInt(osobnik1.size());

                List backup1 = new ArrayList();
                List backup2 = new ArrayList();
                for (int k = 0; k < losowyIndeks; k++) {
                    backup1.add(k, osobnik1.get(k));
                    backup2.add(k, osobnik2.get(k));
                }
                for (int j = losowyIndeks; j < osobnik1.size(); j++) {
                    backup1.add(j, osobnik2.get(j));
                    backup2.add(j, osobnik1.get(j));
                }
                krzyzowanie_wynik.add(backup1);
                krzyzowanie_wynik.add(backup2);
                populacjaKrzyzowanie.add(backup1);
                populacjaKrzyzowanie.add(backup2);
            }

            return populacjaKrzyzowanie;
        }

        if(rodzaj == 2){
            List<List<Integer>> krzyzowanie_wynik = new ArrayList<>();
            for (int i = 0; i < wybraneOsoby.size(); i += 2) {
                List osobnik1 = wybraneOsoby.get(i);
                List osobnik2 = wybraneOsoby.get(i + 1);
                int losowyIndeks1 = random.nextInt(osobnik1.size());
                int losowyIndeks2 = random.nextInt(osobnik1.size());

                if(losowyIndeks1 < osobnik1.size()){
                    do {
                        losowyIndeks2 = random.nextInt(osobnik1.size());
                    }while(losowyIndeks2 < losowyIndeks1);
                }
                else{
                    losowyIndeks2 = losowyIndeks1;
                }

                List backup1 = new ArrayList();
                List backup2 = new ArrayList();
                for (int k = 0; k < losowyIndeks1; k++) {
                    backup1.add(k, osobnik1.get(k));
                    backup2.add(k, osobnik2.get(k));
                }
                for (int j = losowyIndeks1; j < losowyIndeks2; j++) {
                    backup1.add(j, osobnik2.get(j));
                    backup2.add(j, osobnik1.get(j));
                }
                for (int l = losowyIndeks2; l < osobnik1.size(); l++){
                    backup1.add(l, osobnik1.get(l));
                    backup2.add(l, osobnik2.get(l));
                }
                krzyzowanie_wynik.add(backup1);
                krzyzowanie_wynik.add(backup2);
                populacjaKrzyzowanie.add(backup1);
                populacjaKrzyzowanie.add(backup2);
            }

            return populacjaKrzyzowanie;
        }
        else
            return populacjaKrzyzowanie;
    }

    public static List<Double> dekod_Binarnego(List<Integer> osobnik, int liczbaZmiennych, double dokladnosc, double dolnaGranica, double gornaGranica) {
        List<Double> zdekodowaneWartosci = new ArrayList<>();
        int dlugoscBinarna = (int) Math.ceil(Math.log((gornaGranica - dolnaGranica) / dokladnosc + 1) / Math.log(2));

        for (int i = 0; i < liczbaZmiennych; i++) {
            int poczatek = i * dlugoscBinarna;
            int koniec = (i + 1) * dlugoscBinarna;
            String ciagBinarny = "";
            for (int j = poczatek; j < koniec; j++) {
                ciagBinarny += osobnik.get(j);
            }
            int Dec = Integer.parseInt(ciagBinarny, 2);
            double zdekodowanaWartosc = dolnaGranica + ((gornaGranica - dolnaGranica) * Dec) / (Math.pow(2, dlugoscBinarna) - 1);
            zdekodowaneWartosci.add(zdekodowanaWartosc);
        }

        return zdekodowaneWartosci;
    }

    public static List<OsobnikPoEwolucji> ocenOsobniki(List<List<Integer>> populacja, int liczbaZmiennych, double dolnaGranica, double gornaGranica, double dokladnosc) {
        List<OsobnikPoEwolucji> Nowa_populacja = new ArrayList<>();

        for (List<Integer> osobnik : populacja) {
            List<Double> zdekodowaneWartosci = dekod_Binarnego(osobnik, liczbaZmiennych, dokladnosc, dolnaGranica, gornaGranica);
            double x = zdekodowaneWartosci.get(0);
            double przystosowanie = 10 + Math.pow(x, 2) - 10 * Math.cos(20 * Math.PI * x);
            Nowa_populacja.add(new OsobnikPoEwolucji(osobnik, przystosowanie));
        }

        return Nowa_populacja;
    }

    public static class OsobnikPoEwolucji {
        List<Integer> osobnik;
        double przystosowanie;

        public OsobnikPoEwolucji(List<Integer> osobnik, double przystosowanie) {
            this.osobnik = osobnik;
            this.przystosowanie = przystosowanie;
        }
    }
    public static class MyPair<L, R> {
        private final L left;
        private final R right;

        public MyPair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public R getRight() {
            return right;
        }
    }
    public static Map<Integer, MyPair<List<Integer>, Double>> rankingowa(Map<Integer, MyPair<List<Integer>, Double>> mapa, int rozmiarPopulacji) {
        Random random = new Random();
        Random random2 = new Random(System.currentTimeMillis());

        // Sortowanie mapy względem wartości przystosowania
        List<Map.Entry<Integer, MyPair<List<Integer>, Double>>> sortedEntries = new ArrayList<>(mapa.entrySet());
        sortedEntries.sort(Map.Entry.comparingByValue(Comparator.comparing(MyPair::getRight)));

        List<Integer> wybraneIndeksy = new ArrayList<>();

        for (int i = 0; i < rozmiarPopulacji; i++) {
            int pierwszyLos = random.nextInt(rozmiarPopulacji);
            wybraneIndeksy.add(sortedEntries.get(pierwszyLos % rozmiarPopulacji).getKey());
        }

        // Aktualizacja kluczy w mapie
        Map<Integer, MyPair<List<Integer>, Double>> nowaMapa = new HashMap<>();
        for (int i = 0; i < wybraneIndeksy.size(); i++) {
            Integer staryIndeks = i;
            Integer nowyIndeks = wybraneIndeksy.get(i);
            MyPair<List<Integer>, Double> wartości = mapa.get(nowyIndeks);
            nowaMapa.put(staryIndeks, wartości);
        }
        return nowaMapa;
    }



    public static Map<Integer, MyPair<List<Integer>, Double>> ruletki(Map<Integer, MyPair<List<Integer>, Double>> mapa, int rozmiarPopulacji) {
        Map<Integer, MyPair<List<Integer>, Double>> nowaMapa = new HashMap<>();
        Random random = new Random();

        double sumaPrzystosowan = 0;
        for (MyPair<List<Integer>, Double> wartości : mapa.values()) {
            sumaPrzystosowan += wartości.getRight();
        }

        double dystrybuanta = 0;

        for (int i = 0; i < rozmiarPopulacji; i++) {
            double losowaLiczba = random.nextDouble();
            dystrybuanta = 0;

            for (Map.Entry<Integer, MyPair<List<Integer>, Double>> entry : mapa.entrySet()) {
                MyPair<List<Integer>, Double> wartości = entry.getValue();
                double przystosowanie = wartości.getRight();
                double prawdopodobieństwo = przystosowanie / sumaPrzystosowan;

                dystrybuanta += prawdopodobieństwo;

                if (losowaLiczba <= dystrybuanta) {
                    nowaMapa.put(i, wartości);
                    break;
                }
            }
        }

        return nowaMapa;
    }

    public static Map<Integer, MyPair<List<Integer>, Double>> turniejowa(Map<Integer, MyPair<List<Integer>, Double>> mapa, int rozmiarPopulacji) {
        Map<Integer, MyPair<List<Integer>, Double>> nowaMapa = new HashMap<>();
        Random random = new Random();

        for (int i = 0; i < rozmiarPopulacji; i++) {

            int indeks1 = random.nextInt(rozmiarPopulacji);
            int indeks2 = random.nextInt(rozmiarPopulacji);

            double przystosowanie1 = mapa.get(indeks1).getRight();
            double przystosowanie2 = mapa.get(indeks2).getRight();

            int indeksZwyciescy = (przystosowanie1 < przystosowanie2) ? indeks1 : indeks2;

            MyPair<List<Integer>, Double> zwyciesca = mapa.get(indeksZwyciescy);
            nowaMapa.put(i, zwyciesca);
        }

        return nowaMapa;
    }
}
