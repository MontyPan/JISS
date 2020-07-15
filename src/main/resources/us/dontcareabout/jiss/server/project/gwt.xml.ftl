<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE module PUBLIC "-//Google Inc.//DTD Google Web Toolkit 2.8.0//EN" "http://www.gwtproject.org/doctype/2.8.0/gwt-module.dtd">
<module rename-to="${project.name}">
	<inherits name="us.dontcareabout.gxt.GXT" />
	<inherits name="com.sencha.gxt.theme.gray.Gray"/>

	<source path="client" />
	<source path="shared" />

	<entry-point class="${project.rootPackage}.client.${project.name}EP" />
	<stylesheet src="reset.css" />
	<add-linker name="xsiframe" />
	<collapse-all-properties />	
	
	<set-configuration-property name="CssResource.enableGss" value="true" />
</module>