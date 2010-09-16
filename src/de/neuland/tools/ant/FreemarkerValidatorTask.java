package de.neuland.tools.ant;

import static java.lang.System.out;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;

import freemarker.core.ParseException;
import freemarker.template.Template;

public class FreemarkerValidatorTask extends Task {

	private static final String CHECK_MARK = "\u2714";
	private static final String CROSS 	   = "\u2715";
	
	private FileSet fileSet = new FileSet();
	private boolean verbose = false;

	@Override
	public void execute() throws BuildException {
		out.println("validating freemarker templates");
		
		boolean foundInvalid = false;
		
		@SuppressWarnings("unchecked")
		Iterator<FileResource> fileResourceIterator = fileSet.iterator();

		while (fileResourceIterator.hasNext()) {
			foundInvalid |= ! validate(fileResourceIterator.next());
		}
		
		out.println("processed "+fileSet.size()+" files. finished.");
		
		if (foundInvalid) throw new BuildException("malformed freemarker syntax found");
	}

	private boolean validate(FileResource fileResource) {
		boolean isValid = false;
		String filePath = fileResource.getFile().getAbsolutePath();
		try {
			printIfVerbose("validating "+filePath);
			new Template(filePath, new InputStreamReader(fileResource.getInputStream()), null);
			printIfVerbose("\t" + CHECK_MARK + "\n");
			isValid = true;
		} catch (ParseException e) {
			printIfVerbose("\t" + CROSS + "\n");
			out.println("!!! "+filePath+"\n!!! "+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isValid;
	}

	private void printIfVerbose(String s) {
		if (verbose) out.print(s);
	}

	public void add(FileSet files) {
		this.fileSet = files;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
}
