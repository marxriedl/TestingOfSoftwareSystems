package at.jku.sea.modelanalyzer.parse.ocl.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;



public class OperationDeclaration<T> {

	private final String name;
	private final Type<T> returnType;
	private final List<Type<T>> parameters;
	
	public OperationDeclaration(String name, Type<T> returnType, Type<T> ... parameters) {
		this.name = name;
		this.returnType = returnType;
		this.parameters = Arrays.asList(parameters);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		// operation signature equality (name + parameters; without return type)
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OperationDeclaration<?> other = (OperationDeclaration<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}

	public Type<T> getReturnType() {
		return returnType;
	}

	public String getName() {
		return name;
	}
	
	public boolean meetsFormalParameters(Type<T> sourceType, List<Type<T>> actualParameters, TypeConformanceChecker<T> checker) {
		List<Type<T>> formalParameters = getParameters();
		return meetsFormalParameters(actualParameters, formalParameters, checker);
	}

	protected boolean meetsFormalParameters(List<Type<T>> actualParameters, List<Type<T>> formalParameters,
			TypeConformanceChecker<T> checker) {
		if (formalParameters.size() != actualParameters.size()) {
			return false;
		}
		for (Iterator<Type<T>> actualIterator = actualParameters.iterator(), formalIterator = formalParameters
				.iterator(); formalIterator.hasNext()
				&& actualIterator.hasNext();) {
			Type<T> formalType = formalIterator.next();
			Type<T> actualType = actualIterator.next();
			if (!checker.conformsTo(actualType, formalType)) {
				return false;
			}
		}
		return true;
	}

	public List<Type<T>> getParameters() {
		return Collections.unmodifiableList(parameters);
	}

	public Type<T> getActualReturnType(Type<T> source, List<Type<T>> actualParameters) {
		return getReturnType();
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(name);
		stringBuilder.append("(");
		stringBuilder.append(parameters);
		stringBuilder.append(") : ");
		stringBuilder.append(returnType);
		return stringBuilder.toString();
	}
	
}
