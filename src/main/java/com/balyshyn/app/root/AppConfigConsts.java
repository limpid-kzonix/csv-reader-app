package com.balyshyn.app.root;

import com.google.common.collect.Sets;

import java.util.Set;

public class AppConfigConsts {

	public static final String APPLICATION_NAME = "application.name";
	public static final String CSV_FILENAME = "application.csv.fileName";
	public static final String CSV_RES_FILENAME = "application.csv.resultFileName";
	public static final String CSV_ITEM_SEPARATOR = "application.csv.itemSeparator";
	public static final String CSV_FILEPATH = "application.csv.path";

	public static final Set<String> LABELS = Sets.newHashSet("TEAM_BEAUJOLAIS", "TEAM_REGSERV", "TEAM_LOIRE",
					"TEAM_RHONE", "TEAM_TECH",
					"TEAM_ALSACE", "MISC");

	private AppConfigConsts() {
	}
}
