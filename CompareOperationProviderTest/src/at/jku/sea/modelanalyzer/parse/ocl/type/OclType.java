package at.jku.sea.modelanalyzer.parse.ocl.type;

public enum OclType {
	OCL_ANY(null, false) {
		@Override
		public boolean conformsTo(OclType t) {
			return t == OCL_ANY || t == GENERIC;
		}
	},
	GENERIC(null, false) {

		@Override
		public boolean conformsTo(OclType t) {
			throw new IllegalStateException();
		}

	},
	CLASSIFIER(OCL_ANY, false), REAL(OCL_ANY, false), INTEGER(REAL, false), BOOLEAN(OCL_ANY, false),
	COLLECTION(OCL_ANY, true), SET(COLLECTION, true), 
	ORDERED_SET(SET, true), BAG(COLLECTION, true),
	SEQUENCE(BAG, true), STRING(OCL_ANY, false), TUPLE(OCL_ANY, false), 
	OCL_VOID(OCL_ANY, false) {

		@Override
		public boolean conformsTo(OclType t) {
			return t != OCL_INVALID;
		}

	},
	OCL_INVALID(OCL_ANY, false) {

		@Override
		public boolean conformsTo(OclType t) {
			return t != OCL_VOID;
		}
	};

	private final boolean isCollection;
	private final OclType superType;

	OclType(OclType superType, boolean isCollection) {
		this.superType = superType;
		this.isCollection = isCollection;
	}

	public boolean isCollection() {
		return isCollection;
	}

	public boolean conformsTo(OclType t) {
		return this == t || superType.conformsTo(t);
	}

	public OclType getSuperType() {
		return superType;
	}

}
