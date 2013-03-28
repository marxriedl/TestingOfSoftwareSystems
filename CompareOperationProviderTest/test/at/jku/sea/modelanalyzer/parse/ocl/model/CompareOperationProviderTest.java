package at.jku.sea.modelanalyzer.parse.ocl.model;

import static org.junit.Assert.*;
import static org.easymock.EasyMock.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import at.jku.sea.modelanalyzer.parse.ocl.type.OclType;
import at.jku.sea.modelanalyzer.parse.ocl.type.OperationDeclaration;
import at.jku.sea.modelanalyzer.parse.ocl.type.Type;

public class CompareOperationProviderTest {

	private static final String STRING = "String";
	private static final Type<String> STRING_TYPE = new Type<String>(
			OclType.STRING, STRING);
	private static final String INTEGER = "Integer";
	private static final Type<String> INTEGER_TYPE = new Type<String>(OclType.INTEGER, INTEGER);
	private CompareOperationProvider<String> compareOperationProvider;
	private ModelAdapter<String> mock;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		mock = createMock(ModelAdapter.class);
		compareOperationProvider = new CompareOperationProvider<String>(mock);
	}
	
	@Test
	public void testInteger() {
		expect(mock.isComparableTo(INTEGER, INTEGER)).andReturn(true);
		replay(mock);
		Collection<OperationDeclaration<String>> operations = compareOperationProvider.getOperations(INTEGER_TYPE);
		verify(mock);
		assertFalse(operations.isEmpty());
	}

	@Test
	public void testNonComparableWithOclType() {
		expect(mock.isComparableTo(STRING, STRING)).andReturn(false);
		replay(mock);
		Collection<OperationDeclaration<String>> operations = compareOperationProvider.getOperations(STRING_TYPE);
		verify(mock);
		for (OperationDeclaration<String> operationDeclaration : operations) {
			System.out.println(operationDeclaration);
		}
		assertTrue(operations.isEmpty());
	}

}
