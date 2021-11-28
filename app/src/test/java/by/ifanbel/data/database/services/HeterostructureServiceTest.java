package by.ifanbel.data.database.services;


import by.ifanbel.data.database.repositories.HeterostructureRepo;
import by.ifanbel.data.database.services.exceptions.NoSuchHeterostructureException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;

/**
 * The class tests only one method from HeterostructureService
 */
public class HeterostructureServiceTest {

	@Mock
	private HeterostructureRepo heterostructureRepo;

	@InjectMocks
	private HeterostructureService heterostructureService;

	@BeforeMethod
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expectedExceptions = NoSuchHeterostructureException.class)
	public void getHeterostructureBySampleNumberThrowsException() throws NoSuchHeterostructureException {
		heterostructureRepo = Mockito.mock(HeterostructureRepo.class);
		Mockito.when(heterostructureRepo.findOneBySampleNumber(any())).thenReturn(null);
		heterostructureService.getJspHeterostructureBySampleNumber("someSampleNumber");
	}

}