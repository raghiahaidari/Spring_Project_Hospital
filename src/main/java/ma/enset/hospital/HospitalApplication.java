package ma.enset.hospital;

import ma.enset.hospital.entities.*;
import ma.enset.hospital.repository.ConsultationRepository;
import ma.enset.hospital.repository.MedecinRepository;
import ma.enset.hospital.repository.PatientRepository;
import ma.enset.hospital.repository.RendezVousRepository;
import ma.enset.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;


@SpringBootApplication
public class HospitalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalApplication.class, args);
    }

    @Bean
    CommandLineRunner start(IHospitalService hospitalService,
                            PatientRepository patientRepository,
                            MedecinRepository medecinRepository,
                            ConsultationRepository consultationRepository,
                            RendezVousRepository rendezVousRepository) {
        return args -> {
            Stream.of("Mohammed", "Zineb", "Laila").
                    forEach(name->  {
                        Patient patient = new Patient() ;
                        patient.setNom(name);
                        patient.setDateNaissance(new Date());
                        patient.setMalade(true);
                        hospitalService.savePatient(patient);
                    });
            Stream.of("Dr. Mohammed", "Dr. Zineb", "Dr. Laila").
                    forEach(name->  {
                        Medecin medecin = new Medecin();
                        medecin.setNom(name);
                        medecin.setSpecialite(Math.random()>0.5? "Cardio" : "Dentist");
                        medecin.setEmail(name+"@hospital.ma");
                        hospitalService.saveMedecin(medecin);
                    });

            Patient patient=patientRepository.findByNom("Mohammed");

            Medecin medecin=medecinRepository.findByNom("Dr. Zineb");

            RendezVous rendezVous = new RendezVous();
            rendezVous.setDate(new Date());
            rendezVous.setStatus(StatusRDV.EnCours);
            rendezVous.setMedecin(medecin);
            rendezVous.setPatient(patient);
            rendezVousRepository.save(rendezVous);

            RendezVous rendezVous1 = rendezVousRepository.findById(1L).orElse(null);
            Consultation consultation = new Consultation() ;
            consultation.setRendezVous(rendezVous1);
            consultation.setDateConsultation(new Date());
            consultation.setRapport("Rapport de la 1ere consultation");
            consultationRepository.save(consultation);
        };
    }
}
