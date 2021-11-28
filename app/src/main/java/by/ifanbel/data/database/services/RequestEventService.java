package by.ifanbel.data.database.services;

import by.ifanbel.data.database.entities.RequestEvent;
import by.ifanbel.data.database.repositories.RequestEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RequestEventService {

	@Autowired
	RequestEventRepo requestEventRepo;

	public void saveRequestEvent(RequestEvent requestEvent) {
		requestEventRepo.saveAndFlush(requestEvent);
	}

	public List<Objects[]> getRequestsHistory() {
		return requestEventRepo.getAllRequests();
	}
}
