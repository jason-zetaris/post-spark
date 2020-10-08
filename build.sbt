
import sbt.Keys.{parallelExecution, sourceGenerators, _}
import sbtbuildinfo.Plugin.buildInfoPackage

val sparkVersion = "3.0.0"
val hadoopVersion = "2.7.2"
val buildTag = "0.1-on-going"
val buildTarget = "evaluation"
val buildTime = {
  new java.text.SimpleDateFormat().format(new java.util.Date())
}

lazy val commonSettings = Seq(
  organization := "com.jj",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.12.11",
  parallelExecution in ThisBuild := false,
  isSnapshot := true,
  parallelExecution in Test := false
)

lazy val commonDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.1.1"  % "test",
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided"
)

lazy val sparkSqlDependencies = Seq(
  "org.apache.hadoop" % "hadoop-common" % hadoopVersion % "provided" force(),
  "org.apache.hadoop" % "hadoop-mapreduce-client-core" % hadoopVersion % "provided" force(),

  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided" withSources(),
  "org.apache.spark" %% "spark-catalyst" % sparkVersion % "provided" withSources(),
  "org.apache.spark" %% "spark-hive" % sparkVersion % "provided" withSources(),
  "org.apache.spark" %% "spark-hive-thriftserver" % sparkVersion % "provided" withSources(),

  "org.apache.spark" %% "spark-core" % sparkVersion % "test" classifier "tests" withSources(),
  "org.apache.spark" %% "spark-sql" % sparkVersion % "test" classifier "tests" withSources(),
  "org.apache.spark" %% "spark-hive" % sparkVersion % "test" classifier "tests" withSources(),
  "org.apache.spark" %% "spark-catalyst" % sparkVersion % "test" classifier "tests" withSources(),
  "org.apache.derby" % "derby" % "10.12.1.1" % "provided",
  "org.apache.derby" % "derbynet" % "10.12.1.1" % "provided",
  "org.apache.derby" % "derbyclient" % "10.12.1.1" % "provided"
)

lazy val springHibernateDependencies = Seq(
  "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final",
  "org.springframework" % "spring-context" % "4.1.7.RELEASE",
  "org.springframework" % "spring-orm" % "4.1.7.RELEASE",
  "com.h2database" % "h2" % "1.4.191",
  "org.springframework" % "spring-test" % "4.1.7.RELEASE" % "test"
)

lazy val postSpark = project.in(file("post-spark")).
  settings(commonSettings: _*).
  settings(assemblySettings: _*).
  settings(publishSettings: _*).
  settings(
    name := "post-spark",
    libraryDependencies ++= commonDependencies,
    libraryDependencies ++= sparkSqlDependencies,
    libraryDependencies ++= springHibernateDependencies
  ).
  settings (
    buildInfoSettings ++
      Seq(
        sourceGenerators in Compile <+= buildInfo,
        buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion,
          "buildTag" -> buildTag,
          "buildTarget" -> buildTarget,
          BuildInfoKey.action("buildTime") {
            buildTime
          }
        ),
        buildInfoPackage := "com.jj.postspark"
      )
  )

lazy val root = (project in file(".")).
  aggregate(postSpark).
  settings(commonSettings: _*).
  settings(assemblySettings: _*).
  settings(publishSettings: _*).
  settings(
    name := "jj-postspark",
    aggregate in update := false,
    libraryDependencies ++= Seq(
      "com.jj" %% "post-spark" % version.value
    )
  ).
  dependsOn(postSpark)

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := s"${name.value}-${version.value}.jar",
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false),
  test in assembly := {}
)

resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  Resolver.mavenLocal
)

lazy val publishSettings = Seq(
  publishTo := {
    val publishTo:String = sys.props.getOrElse("publishTo", "MAVEN")
    publishTo match {
      case "MAVEN" => Some(Resolver.mavenLocal)
      case "NEXUS" =>   {
        throw new RuntimeException("Release to Nexus is not supported")
      }
    }
  }
)

enablePlugins(JavaAppPackaging)



