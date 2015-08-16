package com.justep.baas.data;

public enum RowState {
	NONE, NEW, EDIT, DELETE;
	
	public static RowState parse(String state) {
		return Util.isEmptyString(state) ? RowState.NONE : RowState.valueOf(state.toUpperCase());
	}
	
	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}

}
