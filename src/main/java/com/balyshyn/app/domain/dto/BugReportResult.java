package com.balyshyn.app.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class BugReportResult {
	private int blocker = 0;
	private int critical = 0;
	private int major = 0;
	private int medium = 0;
	private int minor = 0;
	private int normal = 0;
	private int unresolved = 0;

	public void incrementBlocker() {
		this.blocker++;
	}

	public void incrementCritical() {
		this.critical++;
	}

	public void incrementMajor() {
		this.major++;
	}

	public void incrementMedium() {
		this.medium++;
	}

	public void incrementMinor() {
		this.minor++;
	}

	public void incrementNormal() {
		this.normal++;
	}

	public void incrementUnresolved() {
		this.unresolved++;
	}

	public int getTotal() {
		return blocker + critical + major + minor + normal;
	}
}
