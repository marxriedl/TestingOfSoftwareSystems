package at.jku.sea.modelanalyzer.parse.ocl.model;

import java.util.Collection;
import java.util.List;

import at.jku.sea.modelanalyzer.parse.ocl.type.NamedTypeElement;
import at.jku.sea.modelanalyzer.parse.ocl.type.OclType;
import at.jku.sea.modelanalyzer.parse.ocl.type.OperationDeclaration;
import at.jku.sea.modelanalyzer.parse.ocl.type.Type;

public interface ModelAdapter<T> {

	/**
	 * Returns the type of the corresponding qualified name.
	 * <p>
	 * The name consists of a {@code List<String>} containing all the packages
	 * and subpackages of the type and its name. <br>
	 * The name of the type is the last element of the list.
	 * <p>
	 * 
	 * If no corresponding type exists, this method return {@code null}.
	 * 
	 * @param name
	 *            a list containing the package hierarchy and the name of the
	 *            type
	 * @return type with the corresponding qualified name or {@code null} if no
	 *         such type exists
	 */
	Type<T> findType(List<String> name);

	/**
	 * 
	 * @return super type of the object
	 */
	Collection<Type<T>> getParents(Type<T> type);

	/**
	 * 
	 * @return all declared operations of the type (excluding inherited
	 *         operations)
	 */
	Collection<OperationDeclaration<T>> getOperations(Type<T> type);

	/**
	 * 
	 * @return all declared attributes of the type (excluding inherited
	 *         attributes)
	 */
	Collection<NamedTypeElement<T>> getAttributes(Type<T> type);

	/**
	 * 
	 * @return all operations of the type (including inherited operations)
	 */
	Collection<OperationDeclaration<T>> getAllOperations(Type<T> type);

	/**
	 * 
	 * @return all attributes of the type (including inherited attributes)
	 */
	Collection<NamedTypeElement<T>> getAllAttributes(Type<T> type);

	/**
	 * 
	 * @return true, if modelType1 is comparable to modelType2, i.e. one can
	 *         make a statement whether an instances of modelType1 is greater,
	 *         smaller or equal to an instance of modelType2
	 */
	boolean isComparableTo(T modelType1, T modelType2);

	/**
	 * 
	 * @return true, if instances of oclType can be assigned to a variable of
	 *         type formalModelType
	 */
	boolean conformsTo(OclType oclType, T formalModelType);

	/**
	 * 
	 * @return true, if instances of actualModelType can be assigned to a
	 *         variable of type formalModelType; Normally this is the case if
	 *         actualModelType is a subtype of formalModelType
	 */
	boolean conformsTo(T actualModelType, T formalModelType);

	/**
	 * 
	 * @return the type of the static attribute associated with pathname or
	 *         {@code null} if no such attribute exists
	 */
	Type<T> getStaticAttributeType(List<String> pathname);

	/**
	 * 
	 * @return the type of the an enumeration constant with the name constant
	 *         and the enumeration associated with classifier or {@code null} if
	 *         no such enumeration constant exists
	 */
	Type<T> getEnumConstantType(List<String> classifier, String constant);

	/**
	 * 
	 * @return the name of type
	 */
	String getName(T type);

}
