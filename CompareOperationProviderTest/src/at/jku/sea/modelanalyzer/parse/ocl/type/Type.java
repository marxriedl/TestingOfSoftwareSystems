package at.jku.sea.modelanalyzer.parse.ocl.type;

public class Type<T> {

	private final OclType oclType;
	private final T modelType;
	private final Type<T> elementType;

	public Type(OclType oclType, T modelType) {
		this(oclType, modelType, null);
	}

	public Type(OclType oclType, T modelType, Type<T> elementType) {
		if (oclType.isCollection() && elementType == null) {
			throw new IllegalArgumentException(
					"Collection types need an element type");
		}
		if (oclType == OclType.CLASSIFIER && elementType == null) {
			throw new IllegalArgumentException("Classifier types need a type, which they are presenting, as element type");
		}
		this.oclType = oclType;
		this.modelType = modelType;
		this.elementType = elementType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elementType == null) ? 0 : elementType.hashCode());
		result = prime * result
				+ ((modelType == null) ? 0 : modelType.hashCode());
		result = prime * result + ((oclType == null) ? 0 : oclType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Type<?> other = (Type<?>) obj;
		if (elementType == null) {
			if (other.elementType != null)
				return false;
		} else if (!elementType.equals(other.elementType))
			return false;
		if (modelType == null) {
			if (other.modelType != null)
				return false;
		} else if (!modelType.equals(other.modelType))
			return false;
		if (oclType != other.oclType)
			return false;
		return true;
	}

	public OclType getOclType() {
		return oclType;
	}

	public T getModelType() {
		return modelType;
	}

	public Type<T> getElementType() {
		return elementType;
	}

	public boolean isGeneric() {
		return oclType == OclType.GENERIC
				|| (elementType != null ? elementType.isGeneric() : false);
	}

	public Type<T> getFlattenedElementType() {
		if (elementType == null) {
			return this;
		} else {
			return elementType.getFlattenedElementType();
		}
	}
	
	@Override
	public String toString() {
		return oclType + " -> " + modelType + (elementType == null ? "" : "( " + elementType + " )");
	}

}
