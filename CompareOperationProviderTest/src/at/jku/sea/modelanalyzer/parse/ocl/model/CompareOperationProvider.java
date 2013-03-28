package at.jku.sea.modelanalyzer.parse.ocl.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import at.jku.sea.modelanalyzer.parse.ocl.type.OclType;
import at.jku.sea.modelanalyzer.parse.ocl.type.OperationDeclaration;
import at.jku.sea.modelanalyzer.parse.ocl.type.Type;
import at.jku.sea.modelanalyzer.parse.ocl.type.TypeUtils;
import at.jku.sea.modelanalyzer.parse.ocl.type.lib.ComparatorDeclaration;
import at.jku.sea.modelanalyzer.parse.ocl.type.lib.TypeComparableChecker;

public class CompareOperationProvider<T> {

	private final ModelAdapter<T> modelAdapter;
	private final List<String> comparators = Arrays.asList("<", ">", "<=", ">=");
	private final List<OperationDeclaration<T>> operationList;

	public CompareOperationProvider(final ModelAdapter<T> modelAdapter) {
		this.modelAdapter = modelAdapter;
		TypeComparableChecker<T> typeComparableChecker = new TypeComparableChecker<T>() {

			@Override
			public boolean isComparableTo(Type<T> type1, Type<T> type2) {
				if(type1.getModelType() == null || type2.getModelType() == null) {
					return (type1.getOclType() == OclType.INTEGER || type1
							.getOclType() == OclType.REAL)
							&& (type2.getOclType() == OclType.REAL || type2
									.getOclType() == OclType.REAL);
				}
				return modelAdapter.isComparableTo(type1.getModelType(), type2.getModelType());
			}
		};
		List<OperationDeclaration<T>> operationList = new ArrayList<OperationDeclaration<T>>(4);
		for (String comparator : comparators) {			
			operationList.add(new ComparatorDeclaration<T>(comparator, typeComparableChecker));
		}
		this.operationList = Collections.unmodifiableList(operationList);
	}

	public Collection<OperationDeclaration<T>> getOperations(Type<T> type,
			String name) {
		Collection<OperationDeclaration<T>> operations = getOperations(type);
		return TypeUtils.getOperationsByName(operations, name);
	}

	public Collection<OperationDeclaration<T>> getOperations(Type<T> type) {
		if (type.getModelType() != null
				&& modelAdapter.isComparableTo(type.getModelType(),
						type.getModelType())) {
			return operationList;
		} else {
			return Collections.emptyList();
		}
	}

}
