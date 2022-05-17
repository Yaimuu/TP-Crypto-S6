import java.util.*;

/**
 * @author Yanis OULED MOUSSA 12019652
 * @author Iskander FERCHIOU 11809526
 * @project TP Maths discrètes
 *
 * README : Pour exécuter un code, veuillez vous rendre dans le main et décommenter l'exercice que vous souhaitez
 *          tester.
 */
public class Main {

    private Outils outils = new Outils();

    public static void main(String[] args)
    {
        Main main = new Main();

//        main.exo1();
//        main.exo2();
//        main.exo3();
//        main.exo4et5();
//        main.exo6();
//        main.exo7();
//        main.exo8();
//        main.exo9();
//       main.exo10();
//        main.exo11_12_13_14(11);
//        main.exo11_12_13_14(12);
//        main.exo11_12_13_14(13);
//        main.exo11_12_13_14(14);
//        main.questionSubsidiaire();
        main.tp_note();
    }

    public void tp_note()
    {
        long u = 1201;
        long v = 2509;

        // 1 - Calcul r, s et n
        System.out.println("Question 1 :");
        long r = u - 1, s = v - 1, n = 0;
        while (!outils.PrimaliteNaif(r))
        {
            r--;
        }
        while (!outils.PrimaliteNaif(s))
        {
            s--;
        }
        n = r*s;
        System.out.println("r = " + r + "- s = " + s + " - n = " + n);

        // 2 - Calcul Phi(n)
        System.out.println("Question 2 :");
        long phi = (r-1)*(s-1);
        System.out.println("phi = " + phi);

        // 3 - Calcul de e et f
        System.out.println("Question 3 :");
        long e = -1, f = -1;
        for (long i = 2; i < Math.sqrt(phi); i++)
        {
            if(outils.pgcd(i,phi) == 1)
            {
                e = i;
                break;
            }
        }
        for (long i = e+1; i < phi; i++)
        {
            if(outils.pgcd(i,phi) == 1 && outils.pgcd(i,e) == 1)
            {
                f = i;
                break;
            }
        }
        System.out.println("e = " + e + " - f = " + f);
        // 4 -
        System.out.println("Question 4 :");
        long E = outils.ExpMod(53, e, n);
        long F = outils.ExpMod(53, f, n);
        System.out.println("E = " + E + " - F = " + F);

        // 5 - Modifier ExpMod
        System.out.println("Question 5 :");
        System.out.println("Voir code ExpMod !");
        System.out.println(outils.ExpMod(e, -3, f));

        // 6 - EuclideEtendu
        System.out.println("Question 6 :");
        long[] dab = outils.EuclideEtendu(e,f);
        System.out.println(Arrays.toString(dab));

        // 7 - Calcul de (ExpMod(E,a,n) * ExpMod(F,b,n)) mod n
        System.out.println("Question 7 :");
        long res = (outils.ExpMod(E, dab[1], n) * outils.ExpMod(F, dab[2], n)) % n;
        System.out.println(res);

        // 8 - Généralisation
        System.out.println("Question 8 :");

    }

    /** Exo 1
     * Euclide Etendu
     */
    public void exo1()
    {
        System.out.println("---------- Exo 1 ----------");
        System.out.println(
                Arrays.toString(
                        outils.EuclideEtendu(7,6)
                )
        );
    }

    /** Exo 2
     * Exponentiation modulaire
     */
    public void exo2()
    {
        System.out.println("---------- Exo 2 ----------");
        System.out.println( outils.ExpMod(4 , 13, 497) );
    }

    /** Exo 3
     *  Test de primalité naïf
     */
    public void exo3()
    {
        System.out.println("---------- Exo 3 ----------");
        System.out.println( outils.PrimaliteNaif(15) );
    }

    /** Exo 4
     *
     * Interpretation : plus la taille de l'intervalle N inter [2^i ; 2^i+1] est grande, plus la proportion de nombre
     * premiers est petite.
     *
     * Exo 5
     *
     * Interpretation : Pour l'intervalle [2,4[, le taux d'erreur est de 50% car le chiffre 2 n'est pas considéré comme
     * premier par notre test de fermat. En effet, 2^1 mod 2 = 0, ce qui est différent de 1. Concernant le reste des
     * intervalles, le taux d'erreur reste nul jusqu'à ce que i = 8 où l'on commence à avoir une valeur supérieur à 0.
     */
    public void exo4et5()
    {
        System.out.println("---------- Exos 4 et 5 ----------");
        for(long i = 1; i < 10; i++)
        {
            long borneInf = (long)Math.pow(2,i);
            long borneSup = (long)Math.pow(2,i+1);
            long size = borneSup - borneInf;

            long tab[] = new long[(int)size];

            Arrays.setAll(tab, x -> x + borneInf);

            System.out.println("--------------- Intervalle [" + borneInf + ", " + borneSup + "[ --------------- ");
            System.out.println(Arrays.toString(tab));

            double p1 = 0; // variable contenant la proportion de nb premiers de PrimalitéNaif
            double p2 = 0; // variable contenant la proportion de nb premiers du test de Fermat
            for(long j = 0; j < tab.length; j++)
            {
                if(outils.PrimaliteNaif(tab[(int)j]))
                {
                    p1++;
                }

                if(outils.TestFermat(tab[(int)(j)]))
                {
                    p2++;
                }

            }

            double proportion = p1/(double)tab.length;
            double tauxErreur = Math.abs(proportion - p2/(double)(tab.length));

            System.out.println("Proportion naïve : " + (p1/(double)tab.length) * 100 + "%");

            System.out.println("Taux d'erreur de Fermat : " + tauxErreur * 100 + "%");
        }
    }

    /** Exo 6
     *  Protocole expérimental montrant que dans le pire des cas, TestFermat est asymptotiquement
     *  plus rapide que PrimaliteNaif.
     *
     *  Pour chaque intervalle N inter [2^i ; 2^i+1], on compare le temps en millisecondes (ms) avant et après
     *  l'execution de chaque code, que ce soit PrimilateNaif ou TestFermat. Etant donné que nous mesurons en ms,
     *  les premières valeurs entre Fermat et PrimaliteNaif ne sont pas signicativement différentes.
     *  Cependant, sur le long terme (donc sur des grands intervalles), on observe que le temps d'exécution de
     *  PrimaliteNaif est bien plus long que celui de Fermat.
     *  Remarque : la valeur totale affichée est la somme de tout les temps d'exécution de chaque intervalle.
     */
    public void exo6()
    {
        System.out.println("---------- Exo 6 ----------");
        long totalSize = 15;

        double timesNaif[] = new double[(int)totalSize];
        double timesFermat[] = new double[(int)totalSize];
        double timeNaif = 0;
        double timeFermat = 0;

        for(long i = 1; i <= totalSize; i++)
        {
            long borneInf = (long)Math.pow(2,i);
            long borneSup = (long)Math.pow(2,i+1);
            long size = borneSup - borneInf;

            long tab[] = new long[(int)size];

            Arrays.setAll(tab, x -> x + borneInf);

            double debut, time;


            debut = System.currentTimeMillis();
            for(long j = 0; j < tab.length; j++)
            {
                outils.PrimaliteNaif(tab[(int)j]);
            }
            time = System.currentTimeMillis() - debut;
            timeNaif = time;

            debut = System.currentTimeMillis();
            for(long j = 0; j < tab.length; j++)
            {
                outils.TestFermat(tab[(int)j]);
            }
            time = System.currentTimeMillis() - debut;
            timeFermat = time;

            timesNaif[(int)(i-1)] = timeNaif;
            timesFermat[(int)(i-1)] = timeFermat;

        }

        long totalNaif = (long)Arrays.stream(timesNaif).sum();
        long totalFermat = (long)Arrays.stream(timesFermat).sum();

        System.out.println("Temps d'execution en millisecondes (pour chaque intervalle N ∩ [2^i ; 2^i+1[ ) : ");
        System.out.println("----------- Naif -----------");
        System.out.println(Arrays.toString(timesNaif));
        System.out.println("Total : " + totalNaif + "ms");
        System.out.println("----------- Fermat -----------");
        System.out.println(Arrays.toString(timesFermat));
        System.out.println("Total : " + totalFermat + "ms");

    }

    /** Exo 7
     * Génération d'un nombre premier aléatoire de k bits
     */
    public void exo7()
    {
        System.out.println("---------- Exo 7 ----------");
        long premier = outils.GenPremiers(8);

        System.out.println("Rand premier : " + premier);
        System.out.println("Est premier : " + outils.PrimaliteNaif(premier));

    }

    /** Exo 8
     *  Factorisation de n en un produit de 2 nombres premiers p et q
     *
     *  phi(n) = phi(pq) = (p-1)(q-1). Or, l'algorithme polynomiale A retourne les valeurs de p et q donc s'il n'existe
     *  pas d'algorithme polynomiale de factorisation, il n'existe pas d'algorithme polynomial A tel que A(n) -> phi(n).
     *
     *  Complexité : O(n^2)
     */
    public void exo8()
    {
        System.out.println("---------- Exo 8 ----------");
        System.out.println(
                Arrays.toString(
                        outils.PhiToFact(6)
                )
        );
    }

    /** Exo 9
     * Vérifie que phi(pq) = (p-1)*(q-1)
     */
    public void exo9()
    {
        System.out.println("---------- Exo 9 ----------");
        long[] fact = outils.PhiToFact(6);
        System.out.println(
                        outils.VerifyPhi(fact[0], fact[1])
        );
    }

    /** Exo 10
     *
     * (x^e)^d congru à x mod n.
     *
     * Preuve :
     *
     * (x^e)^d = x^(1 mod phi(n))
     *         = x^(1 + k * phi(n))
     *         = x * x^(k * phi(n))
     *
     * D'après le théorème d'Euler, x^(k * phi(n)) est congru à 1 mod n.
     * Par conséquent, (x^e)^d est congru à x mod n.
     *
     * Protocole expérimentale :
     *
     * Pour vérifier que (x^e)^d est congru à x mod n :
     *      - Etape 1 : on génère deux entiers aléatoires, p et q, de k bits.
     *      - Etape 2 : on calcule n = p*q et on récupère phi(n).
     *      - Etape 3 : on prend un e aléatoire qui n'est pas multiple de n et inversible modulo phi(n).
     *      - Etape 4 : on vérifie que la formule est applicable sur un échantillon (par exemple un intervalle de
     *                  taille 100).
     *       -Etape 5 : Si un nombre x appartenant à l'intervalle est premier avec n, on test si (x^e)^d est congru
     *                  à x mod n. Cette relation doit toujours être vérifiée, ce qui est bien le cas.
     */
    public void exo10()
    {
        System.out.println("---------- Exo 10 ----------");
        boolean res = true;
        long p = outils.GenPremiers(7);
        long q = outils.GenPremiers(7);

        long n = p * q;
//        long n = 21;

        long[] fact = outils.PhiToFact(n);

        System.out.println("Factorisation : " + Arrays.toString(fact));

        long phi = outils.Phi(n);

        Random rand = new Random();
        long e;
        // On génère un e aléatoire qui n'est pas multiple de n et qui est inversible modulo phi
        do
        {
            e = rand.nextInt(100);
        } while(n % e == 0 || outils.InverseMod(e, phi) == -1);

        long d = outils.InverseMod(e, phi);

        System.out.println("n : " + n + " - phi(n) : " + outils.Phi(n) + " - inverse : " + d + " - e : " + e);

        // On teste chaque que la formule fonctionne pour tout x (ici on prend un intervalle de taille 100)
        for (long x = 0; x < 100; x++)
        {
            // x et n doivent être premiers entre-eux
            if(outils.pgcd(x, n) == 1)
            {
                long calcul = outils.ExpMod(x, e * d, n);
                // On verifie que x^(e*d) est congrue à x mod n
                if(calcul != x % n)
                {
                    System.out.println("------ Ne devrait jamais être faux ! ------");
                    System.out.print("calcul : " + calcul + " - ");
                    System.out.print("x : " + x + " - ");
                    System.out.println("e : " + e + " - d : " + d);
                    System.out.println("------------");
                    res = false;
                    break;
                }
            }
        }

        System.out.println("Vérification de la formule : " + res);
    }

    /** Exos 11 à 14
     *
     *  Exercice 11 :
     *
     *  La complexité de cette fonction est O(2^n). Par conséquent, il ne s'exécute donc pas en temps polynomial.
     *
     */
    public void exo11_12_13_14(long exo)
    {
        System.out.println("---------- Exo " + exo + " ----------");
        // Nb bits, k > 6 car il doit exister au moins 6 nombres premiers entre 2^k et 2^(k-1)
        long k = 6;
        long e, n;

        List<Long> ensembleP = new ArrayList<>();

        ensembleP.add(outils.GenPremiers(k));
        long tmp;

        if(exo == 12)
        {
            for (long i = 1; i < 6; i++) {
                if(i % 2 != 0 || i == 1)
                {
                    do
                    {
                        tmp = outils.GenPremiers(k);
                    } while ( ensembleP.contains(tmp) );
                }
                else
                {
                    tmp = ensembleP.get((int)(i-2));
                }

                ensembleP.add(tmp);
            }
        }
        else
        {
            for (long i = 1; i < 6; i++) {
                do
                {
                    tmp = outils.GenPremiers(k);
                } while ( ensembleP.contains(tmp) );
                ensembleP.add(tmp);
            }
        }

        System.out.println("Ensemble p : " + Arrays.toString(ensembleP.toArray()));

        long[] ensembleN = new long[3];
        ensembleN[0] = ensembleP.get(0) * ensembleP.get(1);
        ensembleN[1] = ensembleP.get(2) * ensembleP.get(3);
        ensembleN[2] = ensembleP.get(4) * ensembleP.get(5);

        System.out.println("Ensemble n : " + Arrays.toString(ensembleN));

        n = Arrays.stream(ensembleN)
                .min()
                .getAsLong();

        System.out.println("n : " + n);

        Random rand = new Random();

        long mTemoin;
        long nb = 0;
        do {
            mTemoin = 1 + (rand.nextInt((int)((n-1) - 1)));
            if(exo==14)
            {
                String binary = Integer.toBinaryString((int)mTemoin);
                nb = binary.chars().filter(ch -> ch == '1').count();
            }

        } while ( outils.pgcd(mTemoin, ensembleN[0]) != 1 || outils.pgcd(mTemoin, ensembleN[1]) != 1 || outils.pgcd(mTemoin, ensembleN[2]) != 1 ||
                    nb > 3);

        System.out.println("m témoin binaire : " + Integer.toBinaryString((int)mTemoin) + " - nb bits : " + Integer.toBinaryString((int)mTemoin).chars().count());

        System.out.println("m témoin : " + mTemoin);

        if(exo == 13)
        {
            e = 3;
        }
        else
        {
            do {
                e = 1 + (rand.nextInt((int)((n-1) - 1)));
            } while ( outils.pgcd(e, outils.Phi(ensembleN[0])) != 1 || outils.pgcd(e, outils.Phi(ensembleN[1])) != 1 || outils.pgcd(e, outils.Phi(ensembleN[2])) != 1 );
        }

        System.out.println("e : " + e);

        long[] ensembleM = new long[3];
        // Mi = m^e mod ni
        ensembleM[0] = outils.ExpMod(mTemoin, e, ensembleN[0]);
        ensembleM[1] = outils.ExpMod(mTemoin, e, ensembleN[1]);
        ensembleM[2] = outils.ExpMod(mTemoin, e, ensembleN[2]);

        long m = -1;

        switch ((int)exo)
        {
            case 11:
                m = outils.A1(ensembleN[0], ensembleN[1], ensembleN[2], e, ensembleM[0], ensembleM[1], ensembleM[2]);
                break;
            case 12:
                m = outils.A2(ensembleN[0], ensembleN[1], ensembleN[2], e, ensembleM[0], ensembleM[1], ensembleM[2]);
                break;
            case 13:
                m = outils.A3(ensembleN[0], ensembleN[1], ensembleN[2], e, ensembleM[0], ensembleM[1], ensembleM[2]);
                break;
            case 14:
                m = outils.A4(ensembleN[0], ensembleN[1], ensembleN[2], e, ensembleM[0], ensembleM[1], ensembleM[2]);
                break;
        }

        System.out.println("Ensemble M : " + Arrays.toString(ensembleM));

        System.out.println("m : " + m);
    }

    /** Question subsidiaire
     *
     * Génération d'un nombre premier aléatoire de k bits en prenant en compte le produit des i premiers facteurs
     * premiers.
     *
     * Protocole expérimental :
     *
     * Comme effectué precédemment (exo 6), on relève le temps d'éxecution des deux versions de GenPremiers pour k
     * allant de 1 à 16 et on compare le résultat. Avant chaque execution, on génère le produit des k premiers facteurs
     * premiers.
     *
     * Interprétation :
     *
     * Etant donné que notre première version de GenPremiers s'appuie sur PrimaliteNaif, il est logique de voir que le
     * temps d'éxecution de la variante est inférieur sur long terme puisque GenPremiers(k, S) fait appel au test de
     * Fermat. En effet, nous avons montré auparavant (exo 6) que le test de Fermat est asymptotiquement (dans le pire
     * des cas) plus rapide que PrimaliteNaif.
     *
     */
    public void questionSubsidiaire()
    {
        long nbFacteursPremiers = 1;
        long S = 1; // produit des i premiers facteurs premiers
        long i = 2; // premier compteur
        long j = 0; // deuxième compteur
        long kInit = 1; // nombre de bits initiaux

        long totalSize = 17;

        double timesGenPremiers[] = new double[(int)totalSize];
        double timesVariante[] = new double[(int)totalSize];

        double debut, time;

        for(long k = kInit; k < totalSize; k++)
        {
            while(j < k)
            {
                if(outils.PrimaliteNaif(i))
                {
                    S*=i;
                    j++;
                }
                i++;
            }

            debut = System.nanoTime();
            outils.GenPremiers(k);
            time = System.nanoTime() - debut;
            timesGenPremiers[(int)k] = time;
        }

        System.out.println("Naïf terminé");
        i = 2;
        j = 0;
        for(long k = kInit; k < totalSize; k++)
        {
            while(j < k)
            {
                if(outils.PrimaliteNaif(i))
                {
                    S*=i;
                    j++;
                }
                i++;
            }

            debut = System.nanoTime();
            long test = outils.GenPremiers(k, S);
//            System.out.println(test);
            time = System.nanoTime() - debut;
            timesVariante[(int)k] = time;
        }

        System.out.println("Variante terminé");

        double totalGenPremiers = Arrays.stream(timesGenPremiers).sum();
        double totalVariante = Arrays.stream(timesVariante).sum();

        System.out.println("Temps d'execution en nanosecondes : ");
        System.out.println("----------- Gen Premiers -----------");
        System.out.println(Arrays.toString(timesGenPremiers));
        System.out.println("Total (en ms) : " + totalGenPremiers/1000000 + "ms");
        System.out.println("----------- Variante -----------");
        System.out.println(Arrays.toString(timesVariante));
        System.out.println("Total (en ms) : " + totalVariante/1000000 + "ms");
    }



    /** --------------------------- Outils
     * Classe répertoriant les outils de maths discrète
     *
     */
    private class Outils {

        /** Euclide étendu
         *
         * res[0] -> notations : pgcd
         * res[1] ->
         * res[2] ->
         *
         * @param a
         * @param b
         * @return
         */
        public long[] EuclideEtendu(long a, long b)
        {
            if(b == 0)
                return new long[]{a, 1, 0};
            long[] tmp = EuclideEtendu(b, a % b);
            long[] res = new long[3];
            res[0] = tmp[0];
            res[1] = tmp[2];
            res[2] = tmp[1] - a/b * tmp[2];
            return res;
        }

        public long pgcd(long a, long b)
        {
            return EuclideEtendu(a,b)[0];
        }


        /** Exponentiation modulaire
         * a^k mod n congru à p
         * @param a
         * @param k
         * @param n
         * @return
         */
        public long ExpMod(long a, long k, long n)
        {
            long p = 1;
            if(k > 0)
            {
                for ( ; k > 0; k = k/2)
                {
                    if (k % 2 != 0)
                        p = (p * a) % n;
                    a = (a * a) % n;
                }
            }
            else
            {
                return InverseMod((long)Math.pow(a,-k), n);
            }
            return p;
        }

        /** TestFermat
         * Recherche optimale de nombre premier
         * @param n
         * @return
         */
        public boolean TestFermat(long n)
        {
            return ExpMod(2, n-1, n) == 1;
        }

        /** Test de primalité naïf
         *
         * @param n
         * @return
         */
        public boolean PrimaliteNaif(long n)
        {
            for(long i = 2; i < n; i++)
            {
                if(n % i == 0)
                {
                    return false;
                }
            }
            return true;
        }

        /** GenPremiers
         *
         * @param k nombre de bits
         * @return nombre premier de k bits
         */
        public long GenPremiers(long k)
        {
            Random rand = new Random();

            long low = (long)Math.pow(2, k-1);
            long high = (long)Math.pow(2, k);
            long randNb = low + ((long)rand.nextInt((int)(high - low)));

            while (!PrimaliteNaif(randNb))
            {
                randNb = low + ((long)rand.nextInt((int)(high - low)));
            }

            return randNb;
        }

        /** PhiToFact
         *
         * phi(n) = phi(pq) = (p-1)(q-1).
         * Complexité : O(n^2)
         *
         * @param n entier naturel
         * @return la décomposition en nombres premiers de n
         */
        public long[] PhiToFact(long n)
        {
            long[] decomposition = {0,0};

            for(long i = 0; i < n; i++)
            {
                for(long j = 0; j < n; j++)
                {
//                    System.out.println("j : " + j + " - i : " + i + " - j*i = " + (j*i) + " - decomposition : " + (TestFermat(i) ));
                    if( j*i == n && PrimaliteNaif(i) && PrimaliteNaif(j))
                    {
                        decomposition[0] = i;
                        decomposition[1] = j;
                        break;
                    }
                }
            }

            return decomposition;
        }

        /**
         * Calcul de phi(n)
         * @param n
         * @return le nombre de nombres
         */
        public long Phi(long n)
        {
            long phi = 0;
            for(long i = 0; i < n; i++)
            {
                long[] euclide = EuclideEtendu(i, n);
                if(euclide[0] == 1)
                {
                    phi++;
                }
            }

            return phi;
        }

        /** VerifyPhi
         *
         * @param p
         * @param q
         * @return
         */
        public boolean VerifyPhi(long p, long q)
        {
            return Phi(p*q) == (p-1)*(q-1);
        }

        /** Inverse Modulaire
         *
         * @param n
         * @param phi
         * @return l'inverse modulaire entre n et phi
         */
        public long InverseMod(long n, long phi)
        {
            long[] res = EuclideEtendu(n, phi);
            long pgcd = res[0];
            long inverse = res[1];

            if( pgcd == 1 )
            {
                while (inverse < 0)
                {
                    inverse += phi ;
                }

                return inverse;
            }

            return -1;
        }

        /** Fonction A1
         *
         * La complexité de cette fonction est O(2^n).
         * Il ne s'exécute donc pas en temps polynomial.
         *
         * @param n1
         * @param n2
         * @param n3
         * @param e
         * @param M1
         * @param M2
         * @param M3
         * @return
         */
        public long A1(long n1, long n2, long n3, long e, long M1, long M2, long M3)
        {
            long m;

            long n = Math.min(n1, n2);
            n = Math.min(n, n3);

            Random rand = new Random();
            do {
                m = 1 + (rand.nextInt((int)((n-1) - 1)));
            } while ( outils.pgcd(m, n1) != 1 || outils.pgcd(m, n2) != 1 || outils.pgcd(m, n3) != 1 ||
                    ExpMod(m, e, n1) != M1 || ExpMod(m, e, n2) != M2 || ExpMod(m, e, n3) != M3 );

            return m;
        }

        /** Fonction A2
         *
         *  Dans ce cas, p1 = p2 = p3 donc pgcd(n1,n2) = pgcd(n2,n3) = pgcd(n1,n3)
         *  Il suffit donc de se servir de seulement 2 des n_i
         *
         *  Le calcul du pgcd(n1, n2) = p1 et n1 / p1 = p2
         *  Nous trouvons ensuite rapidement phi(pq) = (p-1)*(q-1)
         *  Finalement, nous nous servons de la formule des M_i pour trouver m en nous servant de l'inverse modulaire de
         *  e par rapport à phi.
         *  Cela permet de trouver m en temps polynomial.
         *
         * @param n1
         * @param n2
         * @param n3
         * @param e
         * @param M1
         * @param M2
         * @param M3
         * @return
         */
        public long A2(long n1, long n2, long n3, long e, long M1, long M2, long M3)
        {
            long m;

            long p1 = pgcd(n1, n2);
            long p2 = n1 / p1;
            long phi = (p1 - 1) * (p2 - 1);
            long d = InverseMod(e, phi);
            m = ExpMod(M1, d, n1);

            return m;
        }

        /** Fonction A3 (Attaque de Hastad)
         *
         * Lorsque e=3, il est possible d'appliquer le Théorème des restes chinois pour obtenir un algorithme
         * qui s'exécute en temps polynomial.
         *
         * @param n1
         * @param n2
         * @param n3
         * @param e vaut 3
         * @param M1
         * @param M2
         * @param M3
         * @return
         */
        public long A3(long n1, long n2, long n3, long e, long M1, long M2, long M3)
        {
            long m;

            long d1 = InverseMod(n2 * n3, n1);
            long d2 = InverseMod(n1 * n3, n2);
            long d3 = InverseMod(n1 * n2, n3);

            long m1 = M1 * d1 * n2 * n3;
            long m2 = M2 * d2 * n1 * n3;
            long m3 = M3 * d3 * n1 * n2;
            long N = (long)n1 * (long)n2 * (long)n3;

            m = (long)Math.cbrt( (m1 + m2 + m3) % N );

            return m;
        }

        /** Fonction A4
         *
         * Utilisation du brute-force pour trouver m
         *
         * @param n1
         * @param n2
         * @param n3
         * @param e
         * @param M1
         * @param M2
         * @param M3
         * @return
         */
        public long A4(long n1, long n2, long n3, long e, long M1, long M2, long M3)
        {
            long m = 0;

            long n = Math.min(n1, n2);
            n = Math.min(n, n3);
            long nbBits = Integer.toBinaryString((int)n).chars().count();

            for(long i = 0; i < Math.pow(2, nbBits) - 1; i++)
            {
                if(outils.pgcd(i, n1) == 1 && outils.pgcd(i, n2) == 1 && outils.pgcd(i, n3) == 1 &&
                        ExpMod(i, e, n1) == M1 && ExpMod(i, e, n2) == M2 && ExpMod(i, e, n3) == M3)
                {
                    m = i;
                    break;
                }
            }

            return m;
        }

        /** Variante de GenPremiers
         *
         * @param k nombre de bits
         * @param S produit des i premiers facteurs premiers
         * @return nombre premier de k bits
         */
        public long GenPremiers(long k, long S)
        {
            Random rand = new Random();

            long low = (long)Math.pow(2, k-1);
            long high = (long)Math.pow(2, k);
            long n = 0;

            boolean b = false;
            while (!b)
            {
                n = low + ((long)rand.nextInt((int)(high - low)));

                if(pgcd(n,S) != 1)
                    b = false;
                else
                    b = outils.TestFermat(n);
            }

            return n;
        }

    }
}
