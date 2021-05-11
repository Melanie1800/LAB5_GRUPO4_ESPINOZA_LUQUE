package edu.pucp.gtics.lab5_gtics_20211.repository;

import edu.pucp.gtics.lab5_gtics_20211.entity.Plataformas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PlataformasRepository extends JpaRepository<Plataformas,Integer> {
}
