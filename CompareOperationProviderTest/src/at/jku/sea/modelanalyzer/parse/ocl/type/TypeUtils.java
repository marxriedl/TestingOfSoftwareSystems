package at.jku.sea.modelanalyzer.parse.ocl.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TypeUtils {
	
	private TypeUtils() {
		// prevent instantiation
	}

	public static <T> boolean containsElementWithName(
			Collection<NamedTypeElement<T>> attributes, String name) {
		return getElementByName(attributes, name) != null;
	}

	public static <T> boolean containsOperationWithName(
			Collection<OperationDeclaration<T>> operations, String name) {
		return !getOperationsByName(operations, name).isEmpty();
	}

	public static <T>  Collection<OperationDeclaration<T>> getOperationsByName(
			Collection<OperationDeclaration<T>> operations, String name) {
		List<OperationDeclaration<T>> list = new ArrayList<OperationDeclaration<T>>();
		for (OperationDeclaration<T> operationDeclaration : operations) {
			if(operationDeclaration.getName().equals(name)) {
				list.add(operationDeclaration);
			}
		}
		return list;
	}

	public static <T>  NamedTypeElement<T> getElementByName(
			Collection<NamedTypeElement<T>> attributes, String name) {
		for (NamedTypeElement<T> namedTypeElement : attributes) {
			if(namedTypeElement.getName().equals(name)) {
				return namedTypeElement;
			}
		}
		return null;
	}

}
