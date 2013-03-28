package at.jku.sea.modelanalyzer.parse.ocl.type.lib;

import at.jku.sea.modelanalyzer.parse.ocl.type.Type;

public interface TypeComparableChecker<T> {

	boolean isComparableTo(Type<T> type1, Type<T> type2);
	
}
