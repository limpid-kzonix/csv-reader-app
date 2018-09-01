package com.balyshyn.app.domain.enums;

public enum BugPriority {

	BLOCKER("Blocker"),
	CRITICAL("Critical"),
	MAJOR("Major"),
	MINOR("Minor"),
	MEDIUM("Medium"),
	NORMAL("Normal");

	private String name;

	BugPriority(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}
