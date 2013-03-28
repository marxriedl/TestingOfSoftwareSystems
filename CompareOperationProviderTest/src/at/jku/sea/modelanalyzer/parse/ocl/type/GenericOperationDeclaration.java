package at.jku.sea.modelanalyzer.parse.ocl.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GenericOperationDeclaration<T> extends OperationDeclaration<T> {

	private final Object flattenedGenericId;
	private final Object elementGenericId;
	private final Type<T> anyType = new Type<T>(OclType.OCL_ANY, null);

	public GenericOperationDeclaration(String name, Type<T> returnType,
			Type<T>[] parameters, Object elementGenericId,
			Object flattenedGenericId) {
		super(name, returnType, parameters);
		this.elementGenericId = elementGenericId;
		this.flattenedGenericId = flattenedGenericId;
	}

	public GenericOperationDeclaration(String name, Type<T> returnType,
			Type<T>[] parameters) {
		this(name, returnType, parameters, null, null);
	}

	@Override
	public Type<T> getActualReturnType(Type<T> source, List<Type<T>> actualParameters) {
		Type<T> returnType = getReturnType();
		if (returnType.isGeneric()) {
			Object genericId = getGenericId(returnType);
			Type<T> resolvedType = resolveGenericReturnType(source, genericId,
					actualParameters);
			return nestGenericType(returnType, resolvedType);
		} else {
			return super.getActualReturnType(source, actualParameters);
		}
	}

	/**
	 *	genericType: Collection<Set<Bag<T>>>; resolvedType: AClass => Collection<Set<Bag<AClass>>>
	 */
	private Type<T> nestGenericType(Type<T> genericType, Type<T> resolvedType) {
		List<OclType> list = buildNestedTypesList(genericType);
		Type<T> type = resolvedType;
		for (OclType oclType : list) {
			type = new Type<T>(oclType, null, type);
		}
		return type;
	}

	/**
	 * Collection<Set<Bag<T>>> -> (Bag, Set, Collection)
	 */
	private List<OclType> buildNestedTypesList(Type<T> returnType) {
		List<OclType> list = new ArrayList<OclType>();
		Type<T> type = returnType;
		while (type != null && type.getOclType() != OclType.GENERIC) {
			list.add(type.getOclType());
			type = type.getElementType();
		}
		// most inner type should be listed first
		Collections.reverse(list);
		return list;
	}

	
	/**
	 * 
	 */
	private Type<T> resolveGenericReturnType(Type<T> source, Object genericId,
			List<Type<T>> actualParameters) {
		if (flattenedGenericId != null && genericId.equals(flattenedGenericId)) {
			return source.getFlattenedElementType();
		} else if (elementGenericId != null && genericId.equals(elementGenericId)) {
			return source.getElementType();
		}
		List<Type<T>> parameters = getParameters();
		for (int i = 0; i < parameters.size(); i++) {
			Type<T> formalParameter = parameters.get(i);
			if (genericId.equals(getGenericId(formalParameter))) {
				Type<T> actualParameter = actualParameters.get(i);
				while (formalParameter.getOclType() != OclType.GENERIC) {
					formalParameter = formalParameter.getElementType();
					actualParameter = actualParameter.getElementType();
				}
				return actualParameter;
			}
		}
		throw new IllegalArgumentException("no generic type found");
	}
	
	@Override
	public boolean meetsFormalParameters(Type<T> sourceType,
			List<Type<T>> actualParameters, TypeConformanceChecker<T> checker) {
		List<Type<T>> formalParameters = resolveGenericParameters(sourceType);
		return meetsFormalParameters(actualParameters, formalParameters, checker);
	}
	
	public List<Type<T>> resolveGenericParameters(Type<T> source) {
		List<Type<T>> parameters = getParameters();
		ArrayList<Type<T>> resolvedParameters = new ArrayList<Type<T>>(parameters.size());
		for (Type<T> parameter : parameters) {
			if(parameter.isGeneric()) {
				resolvedParameters.add(resolveParameters(source, parameter));
			} else {
				resolvedParameters.add(parameter);
			}
		}
		return resolvedParameters;
	}

	private Type<T> resolveParameters(Type<T> source, Type<T> parameter) {
		Object genericID = getGenericId(parameter);
		Type<T> resolvedType = resolveGenericType(source, genericID, parameter);
		return nestGenericType(parameter, resolvedType);
	}

	private Type<T> resolveGenericType(Type<T> source,  Object genericId, Type<T> parameter) {
		if (flattenedGenericId != null && genericId.equals(flattenedGenericId)) {
			return source.getFlattenedElementType();
		} else if (elementGenericId != null && genericId.equals(elementGenericId)) {
			return source.getElementType();
		} else {
			return anyType; 
		}
	}

	private Object getGenericId(Type<T> genericType) {
		return ((GenericType<T>)genericType.getFlattenedElementType()).getGenericID();
	}

}
