/*******************************************************************************
 * Copyright (c) 2012 java2script.org and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Zhou Renjian - initial API and implementation
 *******************************************************************************/

package net.sf.j2s.ajax;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sf.j2s.ajax.SimpleSerializable;

public class SimpleSource4Java {
	
	static String folder = "Project";
	static String author = "Author";
	static String company = "Company";

	@SuppressWarnings("deprecation")
	public static String generateSourceFromInterface(Class<?> interfaceClazz) {
		StringBuffer source = new StringBuffer();
		Date date = new Date();
		source.append("/**\r\n");
		source.append(" * Generated by Java2Script.\r\n");
		source.append(" * Copyright (c) ");
		source.append(date.getYear() + 1900);
		source.append(" ");
		source.append(company);
		source.append(". All rights reserved.\r\n");
		source.append(" */\r\n");
		source.append("\r\n");

		source.append("//+$0+\r\n//-$0-\r\n\r\n");

		String clazzName = interfaceClazz.getName();
		String simpleClazzName = clazzName;
		int idx = clazzName.lastIndexOf('.');
		if (idx != -1) {
			source.append("package ");
			source.append(simpleClazzName.substring(0, idx));
			source.append(";\r\n");
			source.append("\r\n");
			
			simpleClazzName = clazzName.substring(idx + 1);
		}

		source.append("//+$1+\r\n//-$1-\r\n\r\n");

		Class<?> superClazz = interfaceClazz.getSuperclass();
		if (superClazz != null) {
			String superClazzName = superClazz.getName();
			source.append("import ");
			source.append(superClazzName);
			source.append(";\r\n");
			source.append("\r\n");
			
			idx = superClazzName.lastIndexOf('.');
			if (idx != -1) {
				superClazzName = superClazzName.substring(idx + 1);
			}

			source.append("//+$2+\r\n//-$2-\r\n\r\n");
			
			source.append("public interface ");
			source.append(simpleClazzName);
			source.append(" extends ");
			source.append(superClazzName);
			source.append(" /*+$11+*/  /*-$11-*/");
			source.append(" {\r\n");
		} else {
			source.append("//+$2+\r\n//-$2-\r\n\r\n");
			
			source.append("public interface ");
			source.append(simpleClazzName);
			source.append(" /*+$11+*/  /*-$11-*/");
			source.append(" {\r\n");
		}
		source.append("\r\n");
		source.append("\t//+$3+\r\n\t//-$3-\r\n\r\n");

		boolean gotStaticFinalFields = false;
		Field[] clazzFields = interfaceClazz.getDeclaredFields();
		
		for (int i = 0; i < clazzFields.length; i++) {
			Field f = clazzFields[i];
			int modifiers = f.getModifiers();
			if ((modifiers & (Modifier.PUBLIC | Modifier.PROTECTED)) != 0
					&& (modifiers & Modifier.STATIC) != 0 && (modifiers & Modifier.FINAL) != 0) {
				Class<?> type = f.getType();
				if (type == int.class || type == long.class || type == short.class 
						|| type == byte.class || type == char.class || type == double.class
						|| type == float.class || type == boolean.class || type == String.class) {
					source.append("\tpublic ");
					source.append(type.getSimpleName());
					source.append(" ");
					source.append(f.getName());
					source.append(" = ");
					try {
						if (type == int.class) {
							source.append("" + f.getInt(interfaceClazz));
						} else if (type == long.class) {
							source.append(f.getLong(interfaceClazz) + "L");
						} else if (type == short.class) {
							source.append("" + f.getShort(interfaceClazz));
						} else if (type == byte.class) {
							source.append("" + f.getByte(interfaceClazz));
						} else if (type == char.class) {
							source.append("\'" + f.getChar(interfaceClazz) + "\'");
						} else if (type == float.class) {
							source.append("" + f.getFloat(interfaceClazz));
						} else if (type == double.class) {
							source.append("" + f.getDouble(interfaceClazz));
						} else if (type == boolean.class) {
							if (f.getBoolean(interfaceClazz)) {
								source.append("true");
							} else {
								source.append("false");
							}
						} else if (type == String.class) {
							source.append("\"" + f.get(interfaceClazz) + "\"");
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
					source.append(";\r\n");
					gotStaticFinalFields = true;
				} else {
					System.out.println("Not supporting type " + type + " for field " + f.getName());
				}
			}
		}
		
		if (gotStaticFinalFields) {
			source.append("\r\n");
		}
		source.append("\t//+$4+\r\n\t//-$4-\r\n\r\n");

		source.append("}\r\n");
		return source.toString();
	}

	@SuppressWarnings("deprecation")
	public static String generateSourceFromObject(SimpleSerializable s) {
		StringBuffer source = new StringBuffer();
		Date date = new Date();
		source.append("/**\r\n");
		source.append(" * Generated by Java2Script.\r\n");
		source.append(" * Copyright (c) ");
		source.append(date.getYear() + 1900);
		source.append(" ");
		source.append(company);
		source.append(". All rights reserved.\r\n");
		source.append(" */\r\n");
		source.append("\r\n");
		
		source.append("//+$0+\r\n//-$0-\r\n\r\n");

		Class<?> clazz = s.getClass();
		String clazzName = clazz.getName();
		String simpleClazzName = clazzName;
		int idx = clazzName.lastIndexOf('.');
		if (idx != -1) {
			source.append("package ");
			source.append(simpleClazzName.substring(0, idx));
			source.append(";\r\n");
			source.append("\r\n");
			
			simpleClazzName = clazzName.substring(idx + 1);
		}

		source.append("//+$1+\r\n//-$1-\r\n\r\n");
		
		boolean hasMoreImports = false;
		Set<String> importedClasses = new HashSet<String>();

		Type[] interfaces = s.getClass().getGenericInterfaces();
		if (interfaces != null && interfaces.length > 0) {
			for (int i = 0; i < interfaces.length; i++) {
				Class<?> t = (Class<?>) interfaces[i];
				if (!SimpleSerializable.isSubInterfaceOf(t, ISimpleConstant.class)) {
					continue;
				}
				String typeName = t.getName();
				hasMoreImports = true;
				if (!importedClasses.contains(typeName)) {
					String simpleTypeName = typeName;
					source.append("import ");
					source.append(simpleTypeName);
					source.append(";\r\n");
					importedClasses.add(typeName);
				}
			}
		}


		boolean gotStaticFinalFields = false;
		Field[] clazzFields = clazz.getDeclaredFields();
		
		Map<String, Field> fields = new HashMap<String, Field>();
		for (int i = 0; i < clazzFields.length; i++) {
			Field f = clazzFields[i];
			int modifiers = f.getModifiers();
			if ((modifiers & (Modifier.PUBLIC | Modifier.PROTECTED)) != 0
					&& (modifiers & (Modifier.TRANSIENT | Modifier.STATIC)) == 0) {
				fields.put(f.getName(), f);
			}
		}

		for (Iterator<Field> itr = fields.values().iterator(); itr.hasNext();) {
			Field field = (Field) itr.next();
			Class<?> type = field.getType();
			
			if (SimpleSerializable.isSubclassOf(type, SimpleSerializable.class)
					|| SimpleSerializable.isSubclassOf(type, SimpleSerializable[].class)) {
				hasMoreImports = true;
				String typeName = type.isArray() ? type.getComponentType().getName() : type.getName();
				if (!importedClasses.contains(typeName)) {
					source.append("import ");
					source.append(typeName);
					source.append(";\r\n");
					importedClasses.add(typeName);
				}
			}
		}

		Class<?> superClazz = s.getClass().getSuperclass();
		if (superClazz != null) {
			String superClazzName = superClazz.getName();
			source.append("import ");
			source.append(superClazzName);
			source.append(";\r\n");
			if (SimpleSerializable.isSubclassOf(superClazz, SimplePipeRunnable.class)) {
				Method[] methods = s.getClass().getMethods();
				if (methods != null) {
					for (int i = 0; i < methods.length; i++) {
						Method m = methods[i];
						if ("deal".equals(m.getName())) {
							Class<?>[] params = m.getParameterTypes();
							if (params != null && params.length == 1) {
								Class<?> paramType = params[0];
								String paramClazzName = paramType.getName();
								source.append("import ");
								source.append(paramClazzName);
								source.append(";\r\n");
							}
						}
					}
				}
			}

			idx = superClazzName.lastIndexOf('.');
			if (idx != -1) {
				superClazzName = superClazzName.substring(idx + 1);
			}
			
			source.append("\r\n");
			source.append("//+$2+\r\n//-$2-\r\n\r\n");
			
			source.append("public class ");
			source.append(simpleClazzName);
			source.append(" extends ");
			source.append(superClazzName);
		} else {
			if (hasMoreImports) {
				source.append("\r\n");
			}
			source.append("//+$2+\r\n//-$2-\r\n\r\n");
			source.append("public class ");
			source.append(simpleClazzName);
		}

		if (interfaces != null && interfaces.length > 0) {
			boolean keywordAppended = false;
			for (int i = 0; i < interfaces.length; i++) {
				Class<?> t = (Class<?>) interfaces[i];
				if (!SimpleSerializable.isSubInterfaceOf(t, ISimpleConstant.class)) {
					continue;
				}
				if (!keywordAppended) {
					source.append(" implements ");
					keywordAppended = true;
				}
				String typeName = t.getName();
				String simpleTypeName = typeName;
				idx = simpleTypeName.lastIndexOf('.');
				if (idx != -1) {
					simpleTypeName = simpleTypeName.substring(idx + 1);
				}
				
				source.append(simpleTypeName);
				if (i != interfaces.length -1) {
					source.append(", ");
				}
			}
		}

		source.append(" /*+$11+*/  /*-$11-*/");
		source.append(" {\r\n\r\n");
		source.append("\t//+$3+\r\n\t//-$3-\r\n\r\n");
		
		for (int i = 0; i < clazzFields.length; i++) {
			Field f = clazzFields[i];
			int modifiers = f.getModifiers();
			if ((modifiers & (Modifier.PUBLIC | Modifier.PROTECTED)) != 0
					&& (modifiers & Modifier.STATIC) != 0 && (modifiers & Modifier.FINAL) != 0) {
				Class<?> type = f.getType();
				if (type == int.class || type == long.class || type == short.class 
						|| type == byte.class || type == char.class || type == double.class
						|| type == float.class || type == boolean.class || type == String.class) {
					source.append("\tpublic static final ");
					source.append(type.getSimpleName());
					source.append(" ");
					source.append(f.getName());
					source.append(" = ");
					try {
						if (type == int.class) {
							source.append("" + f.getInt(s.getClass()));
						} else if (type == long.class) {
							source.append(f.getLong(s.getClass()) + "L");
						} else if (type == short.class) {
							source.append("" + f.getShort(s.getClass()));
						} else if (type == byte.class) {
							source.append("" + f.getByte(s.getClass()));
						} else if (type == char.class) {
							source.append("\'" + f.getChar(s.getClass()) + "\'");
						} else if (type == float.class) {
							source.append("" + f.getFloat(s.getClass()));
						} else if (type == double.class) {
							source.append("" + f.getDouble(s.getClass()));
						} else if (type == boolean.class) {
							if (f.getBoolean(s.getClass())) {
								source.append("true");
							} else {
								source.append("false");
							}
						} else if (type == String.class) {
							source.append("\"" + f.get(s.getClass()) + "\"");
						}
					} catch (Throwable e) {
						e.printStackTrace();
					}
					source.append(";\r\n");
					gotStaticFinalFields = true;
				} else {
					System.out.println("Not supporting type " + type + " for field " + f.getName());
				}
			}
		}
		
		if (gotStaticFinalFields) {
			source.append("\r\n");
		}

		for (Iterator<Field> itr = fields.values().iterator(); itr.hasNext();) {
			Field field = (Field) itr.next();
			String name = field.getName();
			Class<?> type = field.getType();
			
			if (type == int.class || type == long.class || type == short.class 
					|| type == byte.class || type == char.class || type == double.class
					|| type == float.class || type == boolean.class || type == String.class
					|| SimpleSerializable.isSubclassOf(type, SimpleSerializable.class)
					|| type == int[].class || type == long[].class || type == double[].class
					|| type == byte[].class || type == short[].class || type == char[].class
					|| type == float[].class || type == boolean[].class || type == String[].class
					|| SimpleSerializable.isSubclassOf(type, SimpleSerializable[].class)) {
				source.append("\tpublic ");
				source.append(type.getSimpleName());
				source.append(" ");
				source.append(name);
				source.append(";\r\n");
			} else {
				System.out.println("Unsupported type " + type);
			}
		}
		
		source.append("\r\n");
		source.append("\t//+$4+\r\n\t//-$4-\r\n\r\n");
		if (s.bytesCompactMode()) {
			source.append("\tpublic boolean bytesCompactMode() {\r\n");
			source.append("\t\treturn true;\r\n");
			source.append("\t}\r\n");
			source.append("\r\n");
		}
		if (s instanceof SimplePipeRunnable) {
			source.append("\t@Override\r\n");
			source.append("\tpublic boolean pipeSetup() {\r\n");
			source.append("\t\treturn true;\r\n");
			source.append("\t}\r\n");
			source.append("\r\n");
			source.append("\t@Override\r\n");
			source.append("\tpublic SimpleSerializable[] through(Object... args) {\r\n");
			source.append("\t\treturn null;\r\n");
			source.append("\t}\r\n");
			source.append("\r\n");
			
			Method[] methods = s.getClass().getMethods();
			if (methods != null) {
				for (int i = 0; i < methods.length; i++) {
					Method m = methods[i];
					if ("deal".equals(m.getName())) {
						Class<?>[] params = m.getParameterTypes();
						if (params != null && params.length == 1) {
							Class<?> paramType = params[0];
							String paramClazzName = paramType.getName();
							if ("net.sf.j2s.ajax.SimpleSerializable".equals(paramClazzName)) {
								continue;
							}
							int paramIdx = paramClazzName.lastIndexOf('.');
							if (paramIdx != -1) {
								paramClazzName = paramClazzName.substring(paramIdx + 1);
							}
							source.append("\tpublic boolean deal(");
							source.append(paramClazzName);
							source.append(" e) {\r\n");
							source.append("\t\treturn true;\r\n");
							source.append("\t}\r\n");
							source.append("\r\n");
						}
					}
				}
			}
		} else if (s instanceof SimpleRPCRunnable) {
			source.append("\t@Override\r\n");
			source.append("\tpublic void ajaxRun() {\r\n");
			source.append("\t}\r\n");
			source.append("\r\n");
		}
		source.append("\t//+$5+\r\n\t//-$5-\r\n\r\n");
		source.append("}\r\n");

		return source.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args == null || args.length < 3) {
			System.out.println("Usage: " + SimpleSource4Java.class.getName() + " <sources folder> <author> <orgization or company> <class> [class ...]");
			return;
		}
		String targetFolder = args[0];
		File f = new File(targetFolder);
		if (f.exists()) {
			if (!f.isDirectory()) {
				System.out.println("Target folder " + f.getAbsolutePath() + " is not a folder.");
				return;
			}
		} else {
			boolean ok = f.mkdirs();
			if (!ok) {
				System.out.println("Failed to create target folder " + f.getAbsolutePath() + ".");
				return;
			}
		}
		folder = f.getName();
		author = args[1];
		company = args[2];
		
		for (int i = 1 + 2; i < args.length; i++) {
			String j2sSimpleClazz = args[i];
			try {
				Class<?> clazz = Class.forName(j2sSimpleClazz);
				if (clazz.isInterface()) {
					String simpleName = j2sSimpleClazz;
					String packageName = null;
					int idx = j2sSimpleClazz.lastIndexOf('.');
					if (idx != -1) {
						packageName = j2sSimpleClazz.substring(0, idx);
						packageName = packageName.replace('.', File.separatorChar);
						simpleName = j2sSimpleClazz.substring(idx + 1);
					}
					String javaSource = generateSourceFromInterface(clazz);
					String targetPath = targetFolder;
					if (packageName != null) {
						if (targetPath.endsWith(File.separator)) {
							targetPath = targetPath + packageName;
						} else {
							targetPath = targetPath + File.separator + packageName;
						}
						File folder = new File(targetPath);
						if (!folder.exists()) {
							folder.mkdirs();
						}
					}
					SourceUtils.updateSourceContent(new File(targetPath, simpleName + ".java"), javaSource);
					System.out.println(new File(targetFolder, simpleName + ".java").getAbsolutePath());
					continue;
				}
				Object inst = clazz.newInstance();
				if (inst instanceof SimpleSerializable) {
					SimpleSerializable s = (SimpleSerializable) inst;
					
					String simpleName = j2sSimpleClazz;
					String packageName = null;
					int idx = j2sSimpleClazz.lastIndexOf('.');
					if (idx != -1) {
						packageName = j2sSimpleClazz.substring(0, idx);
						packageName = packageName.replace('.', File.separatorChar);
						simpleName = j2sSimpleClazz.substring(idx + 1);
					}
					String javaSource = generateSourceFromObject(s);
					String targetPath = targetFolder;
					if (packageName != null) {
						if (targetPath.endsWith(File.separator)) {
							targetPath = targetPath + packageName;
						} else {
							targetPath = targetPath + File.separator + packageName;
						}
						File folder = new File(targetPath);
						if (!folder.exists()) {
							folder.mkdirs();
						}
					}
					File javaFile = new File(targetPath, simpleName + ".java");
					SourceUtils.updateSourceContent(javaFile, javaSource); 
					System.out.println(javaFile.getAbsolutePath());
				}
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}

	}

}
