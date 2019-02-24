package worker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.SliderUI;

import com.util.Constants;
import com.util.SizeComparator;
import com.vo.FileVO;


public class Worker {

	public static ArrayList<FileVO> liFileVO;
	public static ArrayList<FileVO> liAllFilesVO;
	public static ArrayList<FileVO> liDisplayHtmlFilesVO;
	public static ArrayList<FileVO> liAllHtmlFilesVO;
	public static ArrayList<FileVO> liAllCssFilesVO;
	public static ArrayList<FileVO> liAllJsFilesVO;
	public static ArrayList<FileVO> liAllJsFilesWithUnusedFunctionsVO;
	public static ArrayList<FileVO> liAllJspFilesVO;
	public static Pattern pattern;
	public static List<String> liInstructions= new ArrayList<String>();
	public static Map<File, List<String>> fileMap = new HashMap<File, List<String>>();
	public static Map<String, List<File>> funcMap = new HashMap<String, List<File>>();
		
	public static int counter=0;
	public static int max=1;
	public static int counterHTML=0;
	public static int maxHTML=1;
	public static int counter404=0;
	public static int max404=1;
	public static int counterUnused=0;
	public static int maxUnused=1;
	public static HashMap<String, ArrayList<FileVO>> map = new HashMap<String, ArrayList<FileVO>>();
	public static HashMap<String, ArrayList<FileVO>> mapUnused = new HashMap<String, ArrayList<FileVO>>();
	public static HashMap<String, ArrayList<FileVO>> map404 = new HashMap<String, ArrayList<FileVO>>();
	public static HashMap<String, ArrayList<FileVO>> mapUnused404 = new HashMap<String, ArrayList<FileVO>>();
	public static ArrayList<FileVO> li404Resources=new ArrayList<FileVO>();
	public static HashMap<String, ArrayList<FileVO>>mapDupSel=new HashMap<String, ArrayList<FileVO>>();
	
	public static File fileDir;
	public static int intCSSSetting=0;
	public static int intJSSetting=0;
	
	
	public static void findUnusedHtml1() throws IOException{
		int i=0;
		maxHTML=liAllHtmlFilesVO.size();
		for(FileVO liHtmlFile:liAllHtmlFilesVO){
			i++;
			counterHTML=i;
			boolean blFileUsed=false;
			for(FileVO liAllFiles:liAllFilesVO){
				if(liAllFiles.getFile().getPath().endsWith(".jsp") 
						|| liAllFiles.getFile().getPath().endsWith(".html") 
						|| (liAllFiles.getFile().getPath().endsWith(".css") && (!liAllFiles.getFile().getPath().contains(".min") && !liAllFiles.getFile().getPath().contains("jquery")))
						|| (liAllFiles.getFile().getPath().endsWith(".js") && (!liAllFiles.getFile().getPath().contains(".min") && !liAllFiles.getFile().getPath().contains("jquery"))) 
						|| liAllFiles.getFile().getPath().endsWith(".xml") 
						|| liAllFiles.getFile().getPath().endsWith(".properties") 
						|| liAllFiles.getFile().getPath().endsWith(".java")){
					FileReader fr=new FileReader(liAllFiles.getFile().getAbsolutePath());  
					BufferedReader br = new BufferedReader(fr);
					String s=null;
					while((s=br.readLine()) != null) { 
						try{
							String strFileNameOnly=liHtmlFile.getStrFileName().substring(0, liHtmlFile.getStrFileName().lastIndexOf("."));
							if(s.contains(strFileNameOnly)){
								liHtmlFile.setBlUsed(true);
								blFileUsed=true;
								break;
							}
						}catch(Exception e){
						}
					}
					if(blFileUsed){
						break;
					}
				}
				if(blFileUsed){
					continue;
				}
			}
		}
			if(liDisplayHtmlFilesVO==null){
				liDisplayHtmlFilesVO=new ArrayList<FileVO>();
			}
			for(FileVO liHtmlFile:liAllHtmlFilesVO){
				if(liHtmlFile.isBlUsed()==false){
					liDisplayHtmlFilesVO.add(liHtmlFile);
				}
			}
			
	}
	public static void cleanupCSS2(FileVO fileVO) throws IOException{
		Pattern patSel = Pattern.compile(".*(:|>|;|(.+\\.)|,)");
		Pattern space = Pattern.compile("\\S(\\s+)\\S");
		Pattern space1 = Pattern.compile("\\S(\\s+)(\\w)");
		File newDir=new File(fileDir.getAbsolutePath()+"\\CleanedUpCSS\\");
		newDir.mkdir();
		FileWriter fw=new FileWriter(newDir.getAbsolutePath()+"\\"+fileVO.getStrFileName()); 
		BufferedWriter out = new BufferedWriter(fw);
		boolean blWrite=true;
		boolean blContains=false;
		boolean blCheck=true;
		FileReader fr=new FileReader(fileVO.getFile().getAbsolutePath());  
		BufferedReader br = new BufferedReader(fr);
		String s=null;
		String sTrim=null;
		while((s=br.readLine()) != null) { 
			sTrim=s.trim();
			Matcher m = patSel.matcher(sTrim);
			Matcher m1 = space1.matcher(sTrim);
			if(sTrim.startsWith(".") && !m.find() && !m1.find() && blCheck){
				blContains=false;
				String strSel=cleanSelector(sTrim);

				//FileReader fre=new FileReader("D:\\1\\index.html");  
				for(FileVO liAllFiles:liAllFilesVO){
					if(liAllFiles.getFile().getPath().endsWith(".jsp") || liAllFiles.getFile().getPath().endsWith(".html") || liAllFiles.getFile().getPath().endsWith(".js")){

						FileReader fre=new FileReader(liAllFiles.getFile().getAbsolutePath());  
						BufferedReader buf= new BufferedReader(fre);
						String str=null;
						while((str=buf.readLine()) != null) { 
							//if(str.contains("class") && str.contains(strSel)){
							if(str.toLowerCase().contains("class") && str.contains(strSel)){
								blContains=true;
								break;
							}
						}
						if(blContains){
							break;
						}
					}
				}
				if(blContains==false){
					blWrite=false;
				}
			}
			if(blWrite){
				blCheck=false;
				out.write(s);
				out.newLine();
			}
			if(blWrite==false && sTrim.contains("}")){
				blWrite=true;
			}
			if(sTrim.contains("}")){
				blCheck=true;
			}
		}
		out.close();
		br.close();
	}
	
	//this functions populates JS files which have unused functions
	public static void populateJSFiles() throws IOException{
		Pattern patternJS = Pattern
				.compile("([\\s\\t]*function[\\s\\t]*)([a-zA-Z_0-9]+)(\\()(.*)(\\))"); // This pattern looks for all the javascript functions
		 List<File> lstJSFiles = new ArrayList<File>();
		 List<String> listOfFunctions = new ArrayList<String>();
		 Integer countOfBrackets = new Integer(0);
		 		
		 for(int i=0;i<Worker.liAllHtmlFilesVO.size();i++){
			 lstJSFiles.add(Worker.liAllHtmlFilesVO.get(i).getFile());
		 }
		 for(int i=0;i<Worker.liAllJsFilesVO.size();i++){
			 lstJSFiles.add(Worker.liAllJsFilesVO.get(i).getFile());
		 }
		for(int i=0;i<Worker.liAllJspFilesVO.size();i++){
			 lstJSFiles.add(Worker.liAllJspFilesVO.get(i).getFile());
		 }
		for (File file : lstJSFiles) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String strIterLine = null;
			while ((strIterLine = br.readLine()) != null) {
				Matcher matcher = patternJS.matcher(strIterLine);
				if (matcher.find()) {
					listOfFunctions.add(matcher.group(2));
				}
			}
		}
		int i=0;
		max=listOfFunctions.size();
		for (String funcName : listOfFunctions) {
			i++;
			counter=i;
			int count=0;
			funcMap.put(funcName, new ArrayList<File>());
			for (File files : lstJSFiles) {
				BufferedReader br2 = new BufferedReader(new FileReader(
						files));
				String strIterLine2 = null;
				while ((strIterLine2 = br2.readLine()) != null) {
					if (strIterLine2.contains(funcName)) {
						funcMap.get(funcName).add(files); 		//This map has all the function names as key, list of all the files where the function names can be found as its value
						count++;										
						if(count==2){
							break;
						}
					}
				}
				if(count==2){
					break;
				}
			}
		}
		for (Entry<String, List<File>> entry : funcMap.entrySet()) {
			if (entry.getValue().size() == 1) {
				if(null != fileMap.get(entry.getValue().get(0))){
					fileMap.get(entry.getValue().get(0)).add(entry.getKey());
				}else{
					List<String> liFileFunctions = new ArrayList<String>();
					liFileFunctions.add(entry.getKey());
					fileMap.put(entry.getValue().get(0), liFileFunctions);  /*This map has only those files as key which files contain unused functions, list 
																			of unused functions in those files as its value*/
					FileVO fileVO=new FileVO();
					fileVO.setFile(entry.getValue().get(0));
					if(fileVO.getFile().getPath().endsWith(".js")){
						fileVO.setStrFileName(fileVO.getFile().getName());
						int size=(int)fileVO.getFile().length()/1024;
						if(size==0){
							fileVO.setIntFileSize((int)fileVO.getFile().length());
							fileVO.setStrFileSizeType("B");
						}
						else{
							fileVO.setIntFileSize((int)fileVO.getFile().length()/1024);
							fileVO.setStrFileSizeType("KB");
						}
						liAllJsFilesWithUnusedFunctionsVO.add(fileVO);
					}
				}
			}
		}		
		Collections.sort(Worker.liAllJsFilesWithUnusedFunctionsVO, new SizeComparator());
	}
	
	
	public static void cleanupJS(FileVO fileVO) throws IOException{
		try {
			 Integer countOfBrackets = new Integer(0);
			for (Entry<File, List<String>> entry3 : fileMap.entrySet()) {
				BufferedWriter bufferedWriter=null;
				if(entry3.getKey().getName().endsWith(".js") && fileVO.getFile().getName().equals(entry3.getKey().getName())){
					BufferedReader br2 = new BufferedReader(
							new FileReader(entry3.getKey()));
					String strIterLine2 = null;
					// Path of the folder where the new files will be written
					
					File newFile = new File(fileDir.getAbsolutePath()+"\\CleanedUpJS\\"+ entry3.getKey().getName());
					newFile.getParentFile().mkdirs();
					newFile.createNewFile();
					bufferedWriter = new BufferedWriter(
							new FileWriter(newFile));
					countOfBrackets = 0;
					while ((strIterLine2 = br2.readLine()) != null) {
						boolean validFlag = true;
						for (String func : entry3.getValue()){
							if(strIterLine2.contains(func)){		// To bypass the unused functions, we are using this flag
								validFlag = false;
								//Start of Comment Fix
								if(strIterLine2.contains("/*")){	
									validFlag = true;
								}
								break;
								//End Of Comment Fix							
								}
						}
						if (validFlag) {
							bufferedWriter.write(strIterLine2);		// Writing the files removing the unused functions
							bufferedWriter.newLine();
							bufferedWriter.flush();
						}
						else{
							if(strIterLine2.contains("{")){
								for (char ch : strIterLine2.toCharArray()){
									if (ch == '{'){
										countOfBrackets++;
									}
								}
							}
							if (strIterLine2.contains("}")){
								for (char ch : strIterLine2.toCharArray()){
									if (ch == '}'){
										countOfBrackets--;
									}
								}
							}							
							String strIterLineSub = null;
							while ((strIterLineSub = br2.readLine()) != null) {
								if(strIterLineSub.contains("{")){
									for (char ch : strIterLineSub.toCharArray()){
										if (ch == '{'){
											countOfBrackets++;
										}
									}
								}
								if (strIterLineSub.contains("}")){
									for (char ch : strIterLineSub.toCharArray()){
										if (ch == '}'){
											countOfBrackets--;
										}
									}
								}
								if (countOfBrackets==0){
									if (!(strIterLineSub.contains("}") && strIterLineSub.trim().length()==1)) {
									bufferedWriter.write(strIterLineSub);
									bufferedWriter.newLine();
									bufferedWriter.flush();
									}
									break;
								}
							}
						}
					}
					bufferedWriter.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static String cleanSelector(String strSelector){
		if(strSelector.contains("{")){
			strSelector=strSelector.substring(0,strSelector.indexOf("{"));
		}
		if(strSelector.contains(".")){
			strSelector=strSelector.substring(strSelector.indexOf(".")+1);
		}
		return strSelector.trim();
	}
	public static String cleanType(String strType){
		if(strType.contains(".")){
			strType=strType.substring(strType.indexOf(".")+1);
		}
		return strType.trim();
	}
	
	public static void refreshValues(){
		counter=0;
		max=1;
		counterHTML=0;
		maxHTML=0;
		//liDisplayHtmlFilesVO=new ArrayList<FileVO>();
		if(liAllFilesVO==null){
			liAllFilesVO=new ArrayList<FileVO>();
		}
		if(liAllHtmlFilesVO==null){
			liAllHtmlFilesVO=new ArrayList<FileVO>();
		}
		if(liAllCssFilesVO==null){
			liAllCssFilesVO=new ArrayList<FileVO>();
		}
		if(liAllJsFilesVO==null){
			liAllJsFilesVO=new ArrayList<FileVO>();
		}
		if(liAllJspFilesVO==null){
			liAllJspFilesVO=new ArrayList<FileVO>();
		}
		if(liFileVO==null){
			liFileVO=new ArrayList<FileVO>();
		}
		if(liAllJsFilesWithUnusedFunctionsVO==null){
			liAllJsFilesWithUnusedFunctionsVO=new ArrayList<FileVO>();
		}
	}
	public static void removeDupSelectors(File objFile) throws IOException{
		Pattern patSel = Pattern.compile(".*(:|>|;|(.+\\.)|,)");
		Pattern space = Pattern.compile("\\S(\\s+)(\\w)");
		File newDir=new File(fileDir.getAbsolutePath()+"\\CleanedUpCSS\\");
		FileReader fr=new FileReader(objFile.getAbsolutePath()); 
		FileReader frNested=new FileReader(objFile.getAbsolutePath());  
		BufferedReader br = new BufferedReader(fr);
		BufferedReader brNested = new BufferedReader(frNested);
		String s=null;
		String sTrim=null;
		String sNested=null;
		String sTrimNested=null;
		int i=0;
		int iNested=0;
		while((s=br.readLine()) != null) { 
			i++;
			sTrim=s.trim();
			ArrayList<FileVO> arFileVO=new ArrayList<FileVO>();
			FileVO fileOuterVO=new FileVO();
			fileOuterVO.setIntLineNo(i);
			fileOuterVO.setStrCode(s);
			arFileVO.add(fileOuterVO);
			Matcher m = patSel.matcher(sTrim);
			Matcher m1 = space.matcher(sTrim);
			if(sTrim.startsWith(".") && !m.find() && !m1.find() && !Worker.mapDupSel.containsKey(cleanSelector(sTrim))){	
				String strSel=cleanSelector(sTrim);
				Worker.mapDupSel.put(strSel, arFileVO);
				iNested=0;
				frNested=new FileReader(objFile.getAbsolutePath());  
				brNested = new BufferedReader(frNested);
				
				while((sNested=brNested.readLine()) != null) { 
					iNested++;
					if(iNested>i){
					sTrimNested=sNested.trim();
					Matcher mNested = patSel.matcher(sTrimNested);
					Matcher m1Nested = space.matcher(sTrimNested);
					if(sTrimNested.startsWith(".") && !mNested.find() && !m1Nested.find()){	
						String strSelNested=cleanSelector(sTrimNested);
						if(strSel.equals(strSelNested)){
							FileVO fileInnerVO=new FileVO();
							fileInnerVO.setIntLineNo(iNested);
							fileInnerVO.setStrCode(sNested);
							arFileVO.add(fileInnerVO);
						}
					}
					}
				}
				if(arFileVO.size()==1){
					Worker.mapDupSel.remove(strSel);
				}
				brNested.close();
			}
		}
		br.close();
	}
	
	public static void resetValues(){

		liFileVO=new ArrayList<FileVO>();
		liAllFilesVO=new ArrayList<FileVO>();
		liDisplayHtmlFilesVO=new ArrayList<FileVO>();
		liAllHtmlFilesVO=new ArrayList<FileVO>();
		liAllCssFilesVO=new ArrayList<FileVO>();
		liAllJsFilesVO=new ArrayList<FileVO>();
		liAllJspFilesVO=new ArrayList<FileVO>();
		liAllJsFilesWithUnusedFunctionsVO=new ArrayList<FileVO>();
		fileMap = new HashMap<File, List<String>>();
		funcMap = new HashMap<String, List<File>>();
		map = new HashMap<String, ArrayList<FileVO>>();
		mapUnused = new HashMap<String, ArrayList<FileVO>>();
		map404 = new HashMap<String, ArrayList<FileVO>>();
		mapUnused404 = new HashMap<String, ArrayList<FileVO>>();
		li404Resources=new ArrayList<FileVO>();
		mapDupSel=new HashMap<String, ArrayList<FileVO>>();
		counter=0;
		max=1;
		counterHTML=0;
		maxHTML=1;
		counter404=0;
		max404=1;
		counterUnused=0;
		maxUnused=1;
	}
	public static void populateInstructions(){
		liInstructions=new ArrayList<String>();
		liInstructions.add("How to Use?");
		liInstructions.add("1. Provide workspace directory path, you can provide the path of any other directory also.");
		liInstructions.add("2. Provide the path of Beyond Compare directory. It is optional. If you provide it, beyond compare will be launched automatically after cleanup is done.");
		liInstructions.add("3. Left hand side will be the original file, on the right will be the cleaned up file.");
		liInstructions.add("4. After \"Crawl\" is done, other tabs will become active.");
		liInstructions.add("5. You can cleanup css/js and mminify css/js.");
		liInstructions.add("");
		liInstructions.add("A new folder will be created 'Cleaned Up Files' alongside the jar which will contain the exported files");
		liInstructions.add("");
		liInstructions.add("Unused Resources:");
		liInstructions.add("1. Here you can find all the unused html files within the project.");
		liInstructions.add("2. Also you can find out any given file type which is not being used by giving jpg, png in the free textfield.");
		liInstructions.add("");
		liInstructions.add("Clean Up CSS Section:");
		liInstructions.add("1. Here you can see all the CSS Files in the workspace.");
		liInstructions.add("2. Click on cleanup to clean up that particular CSS.");
		liInstructions.add("3. If Beyond Compare's path was provided it will be launched automatically.");
		liInstructions.add("");
		liInstructions.add("Clean Up JS Section:");
		liInstructions.add("1. Here you can only see those JS Files which require cleanup.");
		liInstructions.add("2. Click on cleanup to clean up that particular JS.");
		liInstructions.add("3. If Beyond Compare's path was provided it will be launched automatically.");
		liInstructions.add("");
		liInstructions.add("Minify Section:");
		liInstructions.add("1. Here you can see all the CSS & JS Files which can be minified.");
		liInstructions.add("2. LineBreak Settings: Values that can be set in the popup for CSS/JS.");
		liInstructions.add("(a). -1 to compress whole file in a single line.");
		liInstructions.add("(b). 0 for every new css selector/js function or statement on new line.");
		liInstructions.add("(c). if we give any larger no. say 1000, then it will try to accommodate 1000 characters on a single line, the larger the number the more minified the file will be.");
		liInstructions.add("");
		liInstructions.add("404 Resources:");
		liInstructions.add("1.Enter comma separated file type eg. pdf, png to find those file types which are being referred but are not present in the workspace.");
	}
}