package ar.edu.utn.frc.tup.lciii.repositories;

import ar.edu.utn.frc.tup.lciii.entities.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    List<MateriaEntity> findByMateria(String s);

    List<MateriaEntity> findByMateriaIgnoreCase(String s);
}
