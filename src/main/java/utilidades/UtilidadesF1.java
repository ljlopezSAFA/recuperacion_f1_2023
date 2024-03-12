package utilidades;

import modelos.*;

import java.util.*;
import java.util.stream.Collectors;

public class UtilidadesF1 {

    public UtilidadesF1() {
    }





    /**
     * Devuelve la lista de pilotos cuya escudería tiene la marca que se pasa como parámetro.
     *
     * @param pilotos
     * @param marca
     * @return
     */
    public static List<Piloto> getPilotosPorMarcaEscuderia(List<Piloto> pilotos, Marca marca){

        return pilotos
                .stream()
                .filter(p-> p.getEscuderia().getMarca().equals(marca))
                .toList();
    }


    /**
     * Devuelve los pilotos agrupados por escudería
     *
     * @param pilotos
     * @return
     */
    public static Map<Escuderia, List<Piloto>>  pilotosPorEscuderia(List<Piloto> pilotos){
        return pilotos
                .stream()
                .collect(Collectors.groupingBy(Piloto::getEscuderia));
    }


    /**
     * Devuelvo los coches cuya suma de puntuacion -> (velocidadPunta + aceleracion - tiempoMedioParadaBoxes - probabilidadAveria )
     * es mayor a la que se pasa , ORDENADOR POR PUNTUACIÓN DE MAYOR A MENOR
     *
     * @param coches
     * @param minimoPuntuacionRequerida
     * @return
     */
    public static List<Coche> topMejoresCoches(List<Coche> coches, Double minimoPuntuacionRequerida){

        Comparator<Coche> comparador = Comparator.comparing(Coche::puntuacionCoche);

        List<Coche> cochesFiltro = coches
                .stream()
                .filter(c-> minimoPuntuacionRequerida <= c.puntuacionCoche())
                .sorted(comparador.reversed())
                .toList();

//        cochesFiltro.sort(comparador.reversed());


        return cochesFiltro;
    }




    /**
     * Devuelve el porcentaje de victoria de un piloto , que se calcula con la siguiente fórmula:
     *
     * -> puntuación total coche del su escuderia  (velocidadPunta + aceleracion - tiempoMedioParadaBoxes - probabilidadAveria)
     * -> +
     * -> puntosRanking de su escuderia
     * -> +
     * -> puntosRanking piloto
     *
     * @param piloto
     * @return
     */
    public static Double porcentajeVictoriaPiloto(Piloto piloto){
        return piloto.getEscuderia().getCoche().puntuacionCoche() + piloto.getEscuderia().getPuntosRanking() + piloto.getPuntosRanking();
    }


    /**
     * Devuelve el piloto con mayor porcentaje de victoria de los dos que se pasa,
     * el porcentaje de victoria se calcula del ejercicio anterior.
     *
     */
     public static Piloto getMejorPiloto(Piloto piloto1, Piloto piloto2){

//         if(porcentajeVictoriaPiloto(piloto1) > porcentajeVictoriaPiloto(piloto2)){
//             return piloto1;
//         }else{
//             return piloto2;
//         }

         //CONDICIONAL TRIPLE
         // CONDICION  "?" VALOR SI SE CUMPLE ":" VALOR SI NO SE CUMPLE ";"
        return porcentajeVictoriaPiloto(piloto1) > porcentajeVictoriaPiloto(piloto2)? piloto1: piloto2 ;
    }


    /**
     * Devuelve el mapa de las posiciones obtenidas por las escuderías del ranking de la temporada que se pasa como parámetro,
     * teniendo en cuenta que sólo hay un ranking por temporada.
     *
     * Las claves del mapa son el orden obtenido de mayor a menor , tras ordenar las temporadas del ranking por su "posicionEnRanking"
     *
     * @param rankingEscuderias
     * @param temporada
     * @return
     */
    public static Map<Integer,Escuderia> getRankigPorEscuderias(List<RankingEscuderias> rankingEscuderias, Integer temporada){

        Map<Integer, Escuderia> mapaFinal = new HashMap<>();

        for(RankingEscuderias r: rankingEscuderias){
            if(r.getTemporada().equals(temporada)){
                for(Escuderia e: r.getEscuderias()){
                    mapaFinal.put(e.getPosicionEnRanking(), e);
                }
            }
        }

        return mapaFinal;


//        return rankingEscuderias
//                .stream()
//                .filter(e-> e.getTemporada().equals(temporada))
//                .findFirst()
//                .get().getEscuderias()
//                    .stream()
//                    .collect(Collectors.toMap(Escuderia::getPosicionEnRanking, e->e));

    }


    /**
     * Devuelve un mapa de los pilotos con la suma de puntos que tengan de las carreras que se pasa como parámetro.
     * Los puntos se obtienen sacando la posición en la que queda el piloto del mapa de "posiciones" y de los puntos
     * que correspondan a esa posición en el mapa "puntosPorPosicion"
     *
     * @param carreras
     * @return
     */
    public static Map<Piloto, Double> totalPuntuacion(List<Carrera> carreras){

        Map<Piloto, Double> puntuaciones = new HashMap<>();

        for(Carrera c: carreras){

            for(Integer posicionEnCarrera : c.getPosiciones().keySet()){

                Piloto pilotoEnPosicion = c.getPosiciones().get(posicionEnCarrera);
                Double puntosPiloto = c.getPuntosPorPosicion().get(posicionEnCarrera);

                if(puntuaciones.containsKey(pilotoEnPosicion)){
                    puntuaciones.put(pilotoEnPosicion, puntuaciones.get(pilotoEnPosicion) +puntosPiloto );
                }else{
                    puntuaciones.put(pilotoEnPosicion, puntosPiloto);
                }

            }
        }


       return puntuaciones;
    }

}
