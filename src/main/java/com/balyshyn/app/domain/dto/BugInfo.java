package com.balyshyn.app.domain.dto;

import com.balyshyn.app.domain.enums.BugPriority;
import com.univocity.parsers.annotations.EnumOptions;
import com.univocity.parsers.annotations.Parsed;
import lombok.Data;

@Data
public class BugInfo {

	@Parsed(field = "Key")
	private String key;

	@Parsed(field = "Labels")
	private String label;

	@Parsed(field = "Status")
	private String status;

	@Parsed(field = "Priority")
	@EnumOptions(customElement = "name")
	private BugPriority priority;

}
