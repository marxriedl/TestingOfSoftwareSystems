package at.jku.sea.modelanalyzer.parse.ocl.type.lib;

import java.util.List;

import at.jku.sea.modelanalyzer.parse.ocl.type.GenericOperationDeclaration;
import at.jku.sea.modelanalyzer.parse.ocl.type.GenericType;
import at.jku.sea.modelanalyzer.parse.ocl.type.OclType;
import at.jku.sea.modelanalyzer.parse.ocl.type.Type;
import at.jku.sea.modelanalyzer.parse.ocl.type.TypeConformanceChecker;

public class ComparatorDeclaration<T> extends GenericOperationDeclaration<T> {

	private final TypeComparableChecker<T> typeComparableChecker;


	@SuppressWarnings("unchecked")
	public ComparatorDeclaration(String name, TypeComparableChecker<T> typeComparableChecker) {
		super(name, new Type<T>(OclType.BOOLEAN, null), new Type[] {new GenericType<T>(new Object())});
		this.typeComparableChecker = typeComparableChecker;
	}
	
	
	@Override
	public boolean meetsFormalParameters(Type<T> sourceType,
			List<Type<T>> actualParameters, TypeConformanceChecker<T> checker) {
		return super.meetsFormalParameters(sourceType, actualParameters,
				checker) && isComparableTo(sourceType, actualParameters.get(0));
	}


	private boolean isComparableTo(Type<T> type1, Type<T> type2) {
		return typeComparableChecker.isComparableTo(type1, type2);
	}

}
