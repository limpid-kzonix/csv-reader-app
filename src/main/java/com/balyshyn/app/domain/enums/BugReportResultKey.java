package com.balyshyn.app.domain.enums;

public enum BugReportResultKey {

	BLOCKER("Blocker"),
	CRITICAL("Critical"),
	MAJOR("Major"),
	MINOR("Minor"),
	MEDIUM("Medium"),
	NORMAL("Normal"),
	TOTAL("Total"),
	UNRESOLVED("Unresolved");

	private String name;

	BugReportResultKey(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
