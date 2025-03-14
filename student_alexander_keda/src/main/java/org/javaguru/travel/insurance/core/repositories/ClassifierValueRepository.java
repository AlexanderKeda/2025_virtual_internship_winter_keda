package org.javaguru.travel.insurance.core.repositories;

import org.javaguru.travel.insurance.core.domain.ClassifierValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClassifierValueRepository extends JpaRepository<ClassifierValue, Long> {

    @Query("""
            SELECT cv FROM ClassifierValue cv
            LEFT JOIN cv.classifier c
            WHERE c.title = :ClassifierTitle
            AND cv.ic = :ic
            """)
    Optional<ClassifierValue> findByClassifierTitleAndIc(
            @Param("ClassifierTitle") String classifierTitle,
            @Param("ic") String ic
    );
}
