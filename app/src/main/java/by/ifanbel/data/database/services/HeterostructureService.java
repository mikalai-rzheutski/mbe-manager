package by.ifanbel.data.database.services;

import by.ifanbel.data.cache.Cache;
import by.ifanbel.data.database.dto.JspBeanHeterostructure;
import by.ifanbel.data.database.entities.Heterostructure;
import by.ifanbel.data.database.repositories.HeterostructureRepo;
import by.ifanbel.data.database.services.exceptions.NoSuchHeterostructureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HeterostructureService {

	@Autowired
	HeterostructureRepo heterostructureRepo;

	/**
	 * Creates a new heterostructure or updates the existing heterostructure in the database.
	 *
	 * @param jspBeanHeterostructure The JSP heterostructure object to be created or updated.
	 * @return true if the heterostructure was created or updated successfully.
	 */
	@Transactional
	public boolean createOrUpdate(JspBeanHeterostructure jspBeanHeterostructure) {
		return createOrUpdate(new Heterostructure(jspBeanHeterostructure));
	}

	/**
	 * Creates a new heterostructure or updates the existing heterostructure in the database.
	 *
	 * @param heterostructure The Heterostructure object to be created or updated.
	 * @return true if the heterostructure was created or updated successfully.
	 */
	@Transactional
	public boolean createOrUpdate(Heterostructure heterostructure) {
		String sampleNumber = heterostructure.getSampleNumber();
		if (heterostructureRepo.existsById(sampleNumber)) heterostructureRepo.deleteById(sampleNumber);
		Cache.clear(heterostructure);
		return (heterostructureRepo.saveAndFlush(heterostructure) != null);
	}

	/**
	 * Creates a new heterostructure in the database. It doesn't update an existing object.
	 *
	 * @param jspBeanHeterostructure The JSP heterostructure object to be created.
	 * @return true if the new heterostructure was created successfully.
	 */
	public boolean create(JspBeanHeterostructure jspBeanHeterostructure) {
		return create(new Heterostructure(jspBeanHeterostructure));
	}

	/**
	 * Creates a new heterostructure in the database. It doesn't update an existing object.
	 *
	 * @param heterostructure The Heterostructure object to be created.
	 * @return true if the new heterostructure was created successfully.
	 */
	@Transactional
	public boolean create(Heterostructure heterostructure) {
		String sampleNumber = heterostructure.getSampleNumber();
		if (heterostructureRepo.existsById(sampleNumber)) return false;
		else {
			Cache.clear(heterostructure);
			return (heterostructureRepo.saveAndFlush(heterostructure) != null);
		}
	}

	/**
	 * Deletes a heterostructure in the database by its sampleNumber
	 *
	 * @param sampleNumber The sampleNumber of the heterostructure to be deleted.
	 * @return false if the specified heterostructure was not in the database.
	 */
	@Transactional
	public boolean delete(String sampleNumber) {
		Cache.clear(sampleNumber);
		if (heterostructureRepo.existsById(sampleNumber)) {
			heterostructureRepo.deleteById(sampleNumber);
			return true;
		} else return false;
	}

	/**
	 * Reads the Heterostructure object from the database
	 *
	 * @param sampleNumber The sampleNumber of the heterostructures to be read
	 * @return The read Heterostructure object
	 */
	@Transactional
	public Heterostructure getHeterostructureBySampleNumber(String sampleNumber) throws NoSuchHeterostructureException {
		Heterostructure hs = heterostructureRepo.findOneBySampleNumber(sampleNumber);
		if (hs == null) throw new NoSuchHeterostructureException(sampleNumber);
		else return hs;
	}

	/**
	 * Reads the JspBeanHeterostructure object from the database. The JspBeanHeterostructure object can be directly transferred to JSP
	 *
	 * @param sampleNumber The sampleNumber of the JspBeanHeterostructure to be read
	 * @return The read JspBeanHeterostructure object
	 */
	@Transactional
	public JspBeanHeterostructure getJspHeterostructureBySampleNumber(String sampleNumber) throws NoSuchHeterostructureException {
		Heterostructure hs = heterostructureRepo.findOneBySampleNumber(sampleNumber);
		if (hs == null) throw new NoSuchHeterostructureException(sampleNumber);
		return hs.getJspBean();
	}

	@Transactional
	public List<Object[]> getTable() {
		return heterostructureRepo.getTableOfHeterostructures();
	}

	public List<JspBeanHeterostructure> exportHeterostructuresDump() {
		return heterostructureRepo.findAll()
				.stream()
				.map((hs) -> hs.getJspBean())
				.collect(Collectors.toList());
	}

	@Transactional
	public long importHeterostructuresDump(List<JspBeanHeterostructure> importedData) {
		List<String> listOfAllHeterostructures = heterostructureRepo.findAll().stream().map((hs) -> hs.getSampleNumber()).collect(Collectors.toList());
		List<Heterostructure> listOfNewHeterostructures = importedData.stream().filter((hs) -> !listOfAllHeterostructures.contains(hs.getSampleNumber())).map((jspBeanHs) -> new Heterostructure(jspBeanHs)).collect(Collectors.toList());
	//	heterostructureRepo.saveAll(listOfNewHeterostructures);
		/*return importedData.stream()
				.filter((h) -> !heterostructureRepo.existsById(h.getSampleNumber()))
				.map((h) -> this.create(new Heterostructure(h)))
				.count();*/
		return heterostructureRepo.saveAll(listOfNewHeterostructures).size();
	}

	@Transactional
	public List<Heterostructure> getListOfHeterostructures(Date fromDate, Date  toDate) {
		return heterostructureRepo.findByDateBetweenOrderByDateAscSampleNumberAsc(fromDate, toDate);
	}

}


