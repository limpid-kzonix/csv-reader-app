package com.balyshyn.app.root;

import com.google.inject.AbstractModule;

public class ApplicationTestModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(TestAppender.class).to(WarningsAppender.class);
	}
}
