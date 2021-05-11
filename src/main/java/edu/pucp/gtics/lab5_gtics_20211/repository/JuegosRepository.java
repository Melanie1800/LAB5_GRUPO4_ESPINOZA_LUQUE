package edu.pucp.gtics.lab5_gtics_20211.repository;

import edu.pucp.gtics.lab5_gtics_20211.entity.Juegos;
import edu.pucp.gtics.lab5_gtics_20211.entity.JuegosUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository

public interface JuegosRepository extends JpaRepository<Juegos, Integer> {
    /**
     * Completar
     */
    @Query(value = "select j.nombre, j.descripcion, j.image" +
            " FROM juegosxusuario jxu \n" +
            " inner join juegos j on (jxu.idjuego = j.idjuego)\n" +
            " inner join usuarios u on (jxu.idusuario = u.idusuario)\n" +
            " where u.idusuario=?1 order by  j.nombre desc;", nativeQuery = true)
    List<JuegosUserDto> obtenerJuegosPorUser(int idusuario);

    List<Juegos> findAllByOrderByNombreDesc();

    @Query(value = "SELECT * FROM gameshop3.juegos j inner join plataformas p on (j.idplataforma = p.idplataforma) order by j.precio ASC;",
            nativeQuery = true)
    List<Juegos> findAllByOrdOrderByPrecioAsc();

}
