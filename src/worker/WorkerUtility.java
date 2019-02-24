package worker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vo.FileVO;

public class WorkerUtility {

	public static Pattern pattern;
	public static void fetchSelectedFileTypes(String directoryName,  Map<String,ArrayList<FileVO>> mpFileTypes){
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
			ArrayList<FileVO> li=new ArrayList<FileVO>();
			for (File file : fList) {
				String strPath = file.getPath();
				if (file.isFile()) {
					
					String strType = strPath.substring(strPath.lastIndexOf('.')+1);
					if(mpFileTypes.get(strType)!=null){
						FileVO objFileVO=new FileVO();
						objFileVO.setFile(file);
						objFileVO.setStrFileName(file.getName());
						int size=(int)file.length()/1024;
						if(size==0){
							objFileVO.setIntFileSize((int)file.length());
							objFileVO.setStrFileSizeType("B");
						}
						else{
							objFileVO.setIntFileSize((int)file.length()/1024);
							objFileVO.setStrFileSizeType("KB");
						}
						mpFileTypes.get(strType).add(objFileVO);
					}
				} else if (file.isDirectory()) {
					if(!strPath.endsWith("classes") && !strPath.endsWith("lib") && !strPath.endsWith(".svn") && !strPath.endsWith("bin")){
						fetchSelectedFileTypes(file.getAbsolutePath(),mpFileTypes);
					}
				}
			}
			//Worker.map.put(entry.getKey(), li);
	}
	public static void fetchSelectedFileTypesNew(String directoryName, String strType,HashMap<String, ArrayList<FileVO>> map){
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
			ArrayList<FileVO> li=new ArrayList<FileVO>();
			for (File file : fList) {
				if (file.isFile()) {
					if(file.getPath().endsWith("."+strType)){//new changes
						FileVO objFileVO=new FileVO();
						objFileVO.setFile(file);
						objFileVO.setStrFileName(file.getName());
						int size=(int)file.length()/1024;
						if(size==0){
							objFileVO.setIntFileSize((int)file.length());
							objFileVO.setStrFileSizeType("B");
						}
						else{
							objFileVO.setIntFileSize((int)file.length()/1024);
							objFileVO.setStrFileSizeType("KB");
						}
						map.get(strType).add(objFileVO);
					}
				} else if (file.isDirectory()) {
					if(!file.getPath().endsWith("classes") && !file.getPath().endsWith("lib") && !file.getPath().endsWith(".svn") && !file.getPath().endsWith("bin")){
						fetchSelectedFileTypesNew(file.getAbsolutePath(),strType,map);
					}
				}
			}
			//Worker.map.put(entry.getKey(), li);
	}
	
	public static void find404References(ArrayList<FileVO> liFileVO, String strType) throws IOException{
		
		//start
		int intLine=0;
		String s=null;
		int count=0;
		Worker.max404=Worker.liAllFilesVO.size();
		for(FileVO liAllFiles:Worker.liAllFilesVO){
			count++;
			Worker.counter404=count;
			
			if(liAllFiles.getFile().getPath().endsWith(".jsp") || liAllFiles.getFile().getPath().endsWith(".html")){
				FileReader fr=new FileReader(liAllFiles.getFile().getAbsolutePath());  
				BufferedReader br = new BufferedReader(fr);
				s=null;
				intLine=0;
				while((s=br.readLine()) != null) { 
					intLine++;
					if(s.contains("http") || s.contains("https") || s.contains("www")){
						continue;
					}
					if(s.contains("."+strType) && s.charAt(s.lastIndexOf("."+strType)-1)!=')'){
						int indexEnd=s.lastIndexOf("."+strType);
						int indexStart=0;
						for(int k=indexEnd;k>=0;k--){
							//if(String.valueOf(s.charAt(k)).matches("\\|/|'|\"")){
								if(String.valueOf(s.charAt(k)).equals("\\") || String.valueOf(s.charAt(k)).equals("/") || String.valueOf(s.charAt(k)).equals("'")
										|| String.valueOf(s.charAt(k)).equals("\"")){
								indexStart=k;
								break;
						}
					}
					String check=s.substring(indexStart+1, indexEnd+1+strType.length());
					if(check.length()>(strType.length()+1)){
					boolean blUsed=false;
					for (Map.Entry<String, ArrayList<FileVO>> entry : Worker.map404.entrySet()){
						if(entry.getKey().equals(strType)){
							for(int i=0;i<Worker.map404.get(entry.getKey()).size();i++){
								if(Worker.map404.get(entry.getKey()).get(i).getFile().getName().equals(check)){
									blUsed=true;
									break;
								}
							}
						}
					}
					if(!blUsed){
						FileVO objFileVO=new FileVO();
						objFileVO.setIntLineNo(intLine);
						objFileVO.setStrFileName(liAllFiles.getFile().getName());
						objFileVO.setFile(liAllFiles.getFile());
						objFileVO.setStrCode(s);
						objFileVO.setStr404File(check);
						Worker.li404Resources.add(objFileVO);
					}
				}
			}
		}
		}
		}
	}
	
	public static void findUnusedResources(List<FileVO> liFileVO) throws IOException{
		for(FileVO liAllFiles:Worker.liAllFilesVO){
			Worker.counterUnused+=1;			
			if(liAllFiles.getFile().getPath().endsWith(".jsp") 
					|| liAllFiles.getFile().getPath().endsWith(".html") 
					|| (liAllFiles.getFile().getPath().endsWith(".css") && (!liAllFiles.getFile().getPath().contains(".min")))
					|| (liAllFiles.getFile().getPath().endsWith(".js") && (!liAllFiles.getFile().getPath().contains(".min"))) 
					|| liAllFiles.getFile().getPath().endsWith(".xml") 
					|| liAllFiles.getFile().getPath().endsWith(".properties") 
					|| liAllFiles.getFile().getPath().endsWith(".java")){
				try(FileReader fr=new FileReader(liAllFiles.getFile().getAbsolutePath());  
						BufferedReader br = new BufferedReader(fr);)
				{
					String s=null;
					while((s=br.readLine()) != null) {
						for(FileVO objFileVO:liFileVO){
							if(!objFileVO.isBlUsed()){
								try{
									String strFileNameOnly=objFileVO.getStrFileName().substring(0, objFileVO.getStrFileName().lastIndexOf("."));
									if(s.contains(strFileNameOnly)){
										objFileVO.setBlUsed(true);
									}
								}catch(Exception e){
								}
							}
						}
					}
				}
				catch(Exception e){
				}
			}
		}
		for(FileVO objFileVO:liFileVO){
			String strPath = objFileVO.getFile().getPath();
			String strType = strPath.substring(strPath.lastIndexOf('.')+1);
			if(!objFileVO.isBlUsed()){
				Worker.mapUnused.get(strType).add(objFileVO);
			}
		}
	}
	public static void writeDupSelector(File f) throws IOException {
		FileWriter fw=new FileWriter(Worker.fileDir.getAbsolutePath()+"\\CleanedUpCSS\\"+f.getName()+"_DupSelector.txt"); 
		BufferedWriter out = new BufferedWriter(fw);
		int count=0;
		for (Map.Entry<String, ArrayList<FileVO>> entry : Worker.mapDupSel.entrySet()){
			count++;
			out.write(count +". "+ "Selector : "+ entry.getKey());
			out.newLine();
			out.write("Occurs at : Line No. ");
			for(int i=0;i<Worker.mapDupSel.get(entry.getKey()).size();i++){
				out.write(Worker.mapDupSel.get(entry.getKey()).get(i).getIntLineNo()+"");
				if(i<Worker.mapDupSel.get(entry.getKey()).size()-1){
					out.write(", ");
				}
			}
			out.newLine();
			out.newLine();
		}
		out.close();
	}
}
