package by.ifanbel.data.database.repositories;


import by.ifanbel.data.database.entities.Heterostructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface HeterostructureRepo extends JpaRepository<Heterostructure, String> {

	Heterostructure findOneBySampleNumber(String sampleNumber);

	@Query("SELECT h.date, h.sampleNumber, h.description FROM Heterostructure h ORDER BY h.date DESC, h.sampleNumber DESC")
	List<Object[]> getTableOfHeterostructures();

	List<Heterostructure> findByDateBetweenOrderByDateAscSampleNumberAsc(Date fromDate, Date toDate);

}
