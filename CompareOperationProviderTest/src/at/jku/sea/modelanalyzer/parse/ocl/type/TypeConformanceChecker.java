package at.jku.sea.modelanalyzer.parse.ocl.type;

public interface TypeConformanceChecker<T> {
	
	boolean conformsTo(Type<T> type1, Type<T> type2);

}
