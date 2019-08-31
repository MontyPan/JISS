<project xmlns="http://maven.apache.org/POM/4.0.0" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<groupId>${groupName}</groupId>
	<artifactId>${project.name}</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>
	
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<maven.compiler.source>1.7</maven.compiler.source>
		<maven.compiler.target>1.7</maven.compiler.target>
		<webappDirectory>${r"${project.build.directory}"}/${r"${project.build.finalName}"}</webappDirectory>
	</properties>
	
	<dependencies>
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
			<version>3.0.1</version>
			<scope>provided</scope>
		</dependency>

		<dependency>
			<groupId>us.dontcareabout</groupId>
			<artifactId>gf</artifactId>
			<version>0.0.2</version>
		</dependency>
		
		<dependency>
			<groupId>org.apache.maven</groupId>
			<artifactId>maven-model</artifactId>
			<version>3.5.3</version>
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
				<!-- FIXME 目前最新是 2.8.0 但是疑似在 I18N 的部份炸 error，所以只好指定一下目錄 -->
				<version>2.7.0</version>
				<executions>
					<execution>
						<goals>
							<goal>generateAsync</goal>
							<goal>compile</goal>
							<goal>test</goal>
							<goal>i18n</goal>
						</goals>
					</execution>
				</executions>
				<configuration>
					<gwtSdkFirstInClasspath>true</gwtSdkFirstInClasspath>
					<incremental>false</incremental>
					<precompile>false</precompile>
					<hostedWebapp>${r"${webappDirectory}"}</hostedWebapp>
					<i18nMessagesBundles>
						<!-- 新增的 I18N Message 檔要在這裡作對應 -->
					</i18nMessagesBundles>
				</configuration>
			</plugin>

			<plugin>
				<groupId>org.mortbay.jetty</groupId>
				<artifactId>jetty-maven-plugin</artifactId>
				<version>8.1.16.v20140903</version>
			</plugin>
		</plugins>
	</build>	
</project>