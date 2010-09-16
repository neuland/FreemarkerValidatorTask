

## FreemarkerValidationTask

.. validates your Freemarker templates with ant

### getting started

	git clone git@github.com:neuland/FreemarkerValidatorTask.git freemarker_validation_ant_task
	cd freemarker_validation_ant_task/
	ant
  
Then copy the built freemarker-validator-task.jar in your projects lib directory.

Download the current [freemarker.jar](http://freemarker.sourceforge.net/freemarkerdownload.html) and save it to your projects lib directory, too.

Now create a build file named freemarkervalidation.xml like this:

	<?xml version="1.0"?>
	
	<project name="validate freemarker " default="validate">
		
		<taskdef name="freemarkervalidator" classname="de.neuland.tools.ant.FreemarkerValidatorTask">
			<classpath>
				<fileset dir="lib/">
					<include name="freemarker-validator-task.jar"/>
					<include name="freemarker*.jar"/>
				</fileset>
			</classpath>	
		</taskdef>
	
		<target name="validate">
			<freemarkervalidator verbose="true">
				<fileset dir="web/webroot/WEB-INF/views">
					<include name="**/*.ftl"/>
				</fileset>
			</freemarkervalidator>
		</target>
	
	</project>

And run it with:
	
	ant -f freemarkervalidation.xml
