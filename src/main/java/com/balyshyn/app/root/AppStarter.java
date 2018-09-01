package com.balyshyn.app.root;

import com.balyshyn.app.module.AppModule;
import com.google.inject.Guice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AppStarter {

	private static final Logger LOG = LoggerFactory.getLogger(AppStarter.class);

	public static void main(String[] args) {
		final AppStarter app = new AppStarter();
		LOG.info("Application is running.");
		app.run();
	}

	private void run() {
		LOG.info("Will start the application with {}.", this);
		Guice.createInjector(new AppModule())
						.getInstance(Application.class)
						.start();
	}
}
