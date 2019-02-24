package worker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vo.FileVO;

public class Sample {
	static List<FileVO> li=new ArrayList<FileVO>();
	
	public static void fetchSelectedFileTypes(String directoryName, String strType){
		File directory = new File(directoryName);
		File[] fList = directory.listFiles();
			for (File file : fList) {
				if (file.isFile()) {
					if(file.getPath().endsWith(strType)){
						FileVO objFileVO=new FileVO();
						objFileVO.setFile(file);
						objFileVO.setStrFileName(file.getName());
						System.out.println(file.getName());
						int size=(int)file.length()/1024;
						if(size==0){
							objFileVO.setIntFileSize((int)file.length());
							objFileVO.setStrFileSizeType("B");
						}
						else{
							objFileVO.setIntFileSize((int)file.length()/1024);
							objFileVO.setStrFileSizeType("KB");
						}
						li.add(objFileVO);
						//Worker.map.get(strType).add(objFileVO);
					}
				} else if (file.isDirectory()) {
					
						fetchSelectedFileTypes(file.getAbsolutePath(),strType);
					
				}
			}
			//Worker.map.put(entry.getKey(), li);
	}
	
	public static void main(String[] args) throws IOException {
		FileVO fileVO=new FileVO();
		fileVO.setStrFileName("style_exp.css");
		fetchSelectedFileTypes("D:\\Neon\\workspace\\FallOS", ".png");
		System.out.println("Size :"+li.size());
		for(FileVO l:li){
			if(l.getFile().getName().equals("2.png")){
				System.out.println("here");
			}
		}
		ProcessBuilder builder = new ProcessBuilder(//G:\Downloads\Programs\Beyond Compare
	            "cmd.exe", "cd \"G:\\Downloads\\Programs\\Beyond Compare\\", "g:","dir");
		Process runtime = Runtime.getRuntime().exec("cmd /c \"G:\\Downloads\\Programs\\Beyond Compare\\BComp.exe\" ReadMe.txt 12.txt");
	        builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	        }
	        
		//cleanupCSS(fileVO);
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
	
	public static void cleanupCSS(FileVO fileVO) throws IOException{
		Pattern patSel = Pattern.compile(".*(:|>|;|(.+\\.)|,)");
		Pattern space = Pattern.compile("\\S(\\s+)\\S");
		Pattern space1 = Pattern.compile("\\S(\\s+)(\\w)");
		FileWriter fw=new FileWriter("D:\\exp\\"+fileVO.getStrFileName()); 
		BufferedWriter out = new BufferedWriter(fw);
		boolean blWrite=true;
		boolean blContains=false;
		FileReader fr=new FileReader("D:\\1\\style.css");  
		BufferedReader br = new BufferedReader(fr);
		String s=null;
		String sTrim=null;
		while((s=br.readLine()) != null) { 
			sTrim=s.trim();
			Matcher m = patSel.matcher(sTrim);
			Matcher m1 = space1.matcher(sTrim);
			if(sTrim.startsWith(".") && !m.find() && !m1.find()){	//second regex for 
				/*.valid,
					.invalid {
					line-height: 24px;
					font-weight: 400;
				}
				.controls.inline,
				.controls.inline input[type="text"],
				.controls.inline select,
				.inline,
				.inline .cell {
					display: inline;
					float: left;
					width: auto;
				}
				.clear {
					position: relative;
					clear: right;
				}
				*/
				System.out.println(sTrim);
				blContains=false;
				String strSel=cleanSelector(sTrim);
				
				FileReader fre=new FileReader("D:\\1\\index.html");  
				BufferedReader buf= new BufferedReader(fre);
				String str=null;
				while((str=buf.readLine()) != null) { 
					if(str.contains("class=") && str.contains(strSel)){
						blContains=true;
						break;
					}
				}
				if(blContains==false){
					blWrite=false;
				}
			}
			if(blWrite){
				out.write(s);
				out.newLine();
			}
			if(blWrite==false && sTrim.contains("}")){
				blWrite=true;
			}
		}

			out.close();
		}
}
