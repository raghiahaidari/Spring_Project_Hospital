package ma.enset.hospital.repository;

import ma.enset.hospital.entities.Consultation;
import ma.enset.hospital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation,Long> {
}
