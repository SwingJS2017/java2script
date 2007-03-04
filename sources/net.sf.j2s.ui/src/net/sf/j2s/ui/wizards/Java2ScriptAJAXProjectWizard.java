/*******************************************************************************
 * Java2Script Pacemaker (http://j2s.sourceforge.net)
 *
 * Copyright (c) 2006 ognize.com and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     ognize.com - initial API and implementation
 *******************************************************************************/

package net.sf.j2s.ui.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import net.sf.j2s.ui.launching.J2SLaunchingUtil;
import net.sf.j2s.ui.property.J2SClasspathModel;

/**
 * @author zhou renjian
 *
 * 2007-3-4
 */
public class Java2ScriptAJAXProjectWizard extends Java2ScriptProjectWizard {
	public Java2ScriptAJAXProjectWizard() {
		super();
	}
	
	protected void updateJava2ScriptWizardTitle() {
		setWindowTitle(getWindowTitle() + " with Java2Script AJAX Enabled");
	}

	protected void updateJava2ScriptLibraries(J2SClasspathModel classpathModel, String j2sLibPath) {
	}

	protected void updateJava2ScriptProject(String prjFolder, String binRelative) {
		try {
			File cpFile = new File(prjFolder, ".classpath");
			FileInputStream fis = new FileInputStream(cpFile);
			String classpath = J2SLaunchingUtil.readAFile(fis);
			if (classpath != null) {
				boolean needUpdate = false;
				if (classpath.indexOf("AJAX_CORE") == -1 && classpath.indexOf("ajaxcore.jar") == -1) {
					int idx = classpath.lastIndexOf("<");
					classpath = classpath.substring(0, idx)
							+ "\t<classpathentry sourcepath=\"AJAX_CORE_SRC\" kind=\"var\" path=\"AJAX_CORE\"/>\r\n"
							+ classpath.substring(idx);
					needUpdate = true;
				}
				if (classpath.indexOf("AJAX_RPC") == -1 && classpath.indexOf("ajaxrpc.jar") == -1) {
					int idx = classpath.lastIndexOf("<");
					classpath = classpath.substring(0, idx)
					+ "\t<classpathentry sourcepath=\"AJAX_RPC_SRC\" kind=\"var\" path=\"AJAX_RPC\"/>\r\n"
					+ classpath.substring(idx);
					needUpdate = true;
				}
				if (needUpdate) {
					try {
						FileOutputStream fos = new FileOutputStream(cpFile);
						//fos.write(new byte[] {(byte) 0xef, (byte) 0xbb, (byte) 0xbf}); // UTF-8 header!
						fos.write(classpath.getBytes("utf-8"));
						fos.close();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
