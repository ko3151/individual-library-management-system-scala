name := "Library Management System"
version := "0.1"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.192-R14"

libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.5"

libraryDependencies += "org.apache.derby" % "derby" % "10.12.1.1"

libraryDependencies ++= Seq(
  "org.scalikejdbc" %% "scalikejdbc"               % "2.5.2",
  "com.h2database"  %  "h2"                        % "1.4.200",
  "ch.qos.logback"  %  "logback-classic"           % "1.2.3"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

fork := true