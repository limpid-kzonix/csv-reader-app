libraryDependencies ++= Seq(

  "com.univocity" % "univocity-parsers" % "2.6.0",

  // Commons
  "software.reinvent" % "commons" % "0.3.12",
  "org.zeroturnaround" % "zt-zip" % "1.12",
  "io.reactivex.rxjava2" % "rxjava" % "2.1.10",
  "org.projectlombok" % "lombok" % "1.18.2" % "provided",

  // TEST
  "org.assertj" % "assertj-core" % "3.9.1" % "test",
  "org.assertj" % "assertj-guava" % "3.1.0" % "test" exclude("com.google.guava", "guava"),
  "com.novocode" % "junit-interface" % "0.11" % "test->default",
  "org.jukito" % "jukito" % "1.5" % "test",
  "info.debatty" % "java-string-similarity" % "1.0.1" % "test"
)

dependencyUpdatesFailBuild := true
