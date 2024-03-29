<project xmlns="http://maven.apache.org/POM/4.0.0" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>${groupName}</groupId>
	<artifactId>${project.name}</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

<#if serverMode>	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.4.4</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

</#if>
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>
		<webappDirectory>${r"${project.build.directory}"}/${r"${project.build.finalName}"}</webappDirectory>
	</properties>

	<dependencies><#if serverMode>
		<!-- server side -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<!-- client side --></#if>
		<dependency>
			<groupId>us.dontcareabout</groupId>
			<artifactId>gf</artifactId>
			<version>0.1.1-SNAPSHOT</version>
		</dependency>
	</dependencies>

	<build>
		<!-- Generate compiled stuff in the folder used for developing mode -->
		<outputDirectory>${r"${webappDirectory}"}/WEB-INF/classes</outputDirectory>

		<plugins>
			<!-- GWT Maven Plugin -->
			<plugin>
				<groupId>org.codehaus.mojo</groupId>
				<artifactId>gwt-maven-plugin</artifactId>
				<version>2.8.2</version>
				<executions>
					<execution>
						<goals>
							<goal>compile</goal>
							<goal>test</goal>
							<goal>i18n</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<gwtSdkFirstInClasspath>true</gwtSdkFirstInClasspath>
					<extraJvmArgs>-Xmx1024m</extraJvmArgs>
					<incremental>false</incremental>
					<precompile>false</precompile>
					<hostedWebapp>${r"${webappDirectory}"}</hostedWebapp>
					<i18nMessagesBundles>
						<!-- 新增的 I18N Message 檔要在這裡作對應 -->
					</i18nMessagesBundles>
				</configuration>
			</plugin>
		</plugins>
	</build>	
</project>