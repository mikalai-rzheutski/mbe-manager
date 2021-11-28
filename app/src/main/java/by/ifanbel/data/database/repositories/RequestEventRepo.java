package by.ifanbel.data.database.repositories;

import by.ifanbel.data.database.entities.RequestEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Objects;

public interface RequestEventRepo extends JpaRepository<RequestEvent, Long> {

	@Query("SELECT rqEv.id, rqEv.date, rqEv.principalName, rqEv.principalRole, rqEv.url FROM RequestEvent rqEv ORDER BY rqEv.id ASC")
	public List<Objects[]> getAllRequests();
}
