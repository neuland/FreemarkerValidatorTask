package de.neuland.tools.ant;

import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import static java.util.Arrays.asList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class FreemarkerValidatorTaskTest {

	private FreemarkerValidatorTask task = new FreemarkerValidatorTask();
	
	@Mock private FileSet fileSet;
	@Mock private FileResource invalidFtlRessource;
	@Mock private File invalidFtlFile;
	@Mock private File validFtlFile;
	@Mock private FileResource validFtlRessource;
	
	private static final String VALID_FTL = 
		"<ul>" +
			"<#list rs as r>" +
				"<li>${r}</li>" +
			"</#list>" +
		"</ul>";
	
	private static final String INVALID_FTL = 
		"<ul>" +
			"<#list rs as r>" +
				"<li>${r}</li>" +
			"</#list" +
		"</ul>";
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		task.add(fileSet);
		task.setVerbose(true);
		
		when(validFtlFile.getAbsolutePath()).thenReturn("/test/view/valid.ftl");
		when(validFtlRessource.getFile()).thenReturn(invalidFtlFile);
		when(validFtlRessource.getInputStream()).thenReturn(toInputStream(VALID_FTL));
		
		when(invalidFtlFile.getAbsolutePath()).thenReturn("/test/view/invalid.ftl");
		when(invalidFtlRessource.getFile()).thenReturn(invalidFtlFile);
		when(invalidFtlRessource.getInputStream()).thenReturn(toInputStream(INVALID_FTL));
	}
	
	@Test(expected = BuildException.class)
	public void shouldThrowBuildExceptionForInvalidFreemarkerSource() throws Exception {
		when(fileSet.iterator()).thenReturn(asList(invalidFtlRessource).iterator());
		task.execute();
	}
	
	@Test
	public void shouldNotThrowBuildExceptionForValidFreemarkerSource() throws Exception {
		when(fileSet.iterator()).thenReturn(asList(validFtlRessource).iterator());
		task.execute();
	}

	@Test(expected = BuildException.class)
	public void shouldThrowBuildExceptionWhenFirstOfTwoFreemarkerSourcesIsInvalid() throws Exception {
		when(fileSet.iterator()).thenReturn(asList(validFtlRessource, invalidFtlRessource).iterator());
		task.execute();
	}
	
	@Test(expected = BuildException.class)
	public void shouldThrowBuildExceptionWhenSecondOfTwoFreemarkerSourcesIsInvalid() throws Exception {
		when(fileSet.iterator()).thenReturn(asList(invalidFtlRessource, validFtlRessource).iterator());
		task.execute();
	}
	

	private static ByteArrayInputStream toInputStream(String string)
			throws UnsupportedEncodingException {
		return new ByteArrayInputStream(string.getBytes("UTF-8"));
	}
}
