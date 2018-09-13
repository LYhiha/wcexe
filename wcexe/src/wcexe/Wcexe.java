package wcexe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Wcexe {
	
	static String path;
	//获取文件
	File filepath = new File(path);
	//帮助信息
	public static void help() {
		System.out.println("");
		System.out.println("wc.exe -l 文件名（完整路径） 获取行数");
		System.out.println("wc.exe -c 文件名（完整路径）获取字符数");
		System.out.println("wc.exe -w 文件名（完整路径） 获取单词数");
		System.out.println("wc.exe -a 文件名（完整路径）获取空行，注释行，代码行数");
		System.out.println("wc.exe -s 文件夹（完整路径）遍历处理文件夹");
		System.out.println(" ");
	}
	//遍历文件夹
	public void traverseDir(File filepath) {
		File[] files;
		files = filepath.listFiles();
		if(files != null) {
			for(File file : files) {
				if(file.isFile()) {
					 path=file.getAbsolutePath();
					 System.out.println("文件名："+path);
					Wcexe wc1=new Wcexe();
					try {//处理遍历的文件
						wc1.countChar();
						wc1.countWord();
						wc1.countLine();
						wc1.countdifLine();						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else if(file.isDirectory()) {
					traverseDir(file.getAbsoluteFile());
				}
			}
		}
	}
	
	//计算空行、注释行、
	public void countdifLine() throws IOException {
		int blankLine = 0;
		int codeLine = 0;
		int annotateLine = 0;
		FileReader inputReader = new FileReader(filepath);
	        BufferedReader bufReader = new BufferedReader(inputReader);
	        String line;
	        boolean flag = false;//用于标记/*     */注释行
	        while( ( line = bufReader.readLine() ) != null) {
	        	if(line.trim().startsWith("/*") && line.trim().endsWith("*/")) {
	        		annotateLine++;
	        	}else if(line.trim().startsWith("/*") && !line.trim().endsWith("*/")) {
	        		annotateLine++;
	        		flag = true;
	        	}else if(flag == true&&!line.trim().endsWith("*/")) {
	        		annotateLine++;
	        	}else if(flag == true && line.trim().endsWith("*/")) {
	        		annotateLine++;
	        		flag = false;
	        	}else if(line.trim().startsWith("//") || line.trim().startsWith("}//")) {
	        		annotateLine++;
	        	}else if(line.trim().matches("")) {
		        		blankLine++;
		        }else{
	        		codeLine++;
	        	}
	        }
	        bufReader.close();
	        System.out.println("代码行"+codeLine);
	        System.out.println("注释行"+annotateLine);
	        System.out.println("空   行"+blankLine);
	}
	//计算单词个数
	public void countWord() throws IOException {
		int wordNum = 0;
		FileReader inputReader = new FileReader(filepath);
	        BufferedReader bufReader = new BufferedReader(inputReader);
	        String line ;
	        while( ( line = bufReader.readLine() ) != null) {
	        	String[] str1=null;
	        	if(!"".equals(line)) {
	        		str1 = line.replaceAll("[^A-Za-z]"," ").trim().split("\\s+");
	        		for(int j = 0;j<str1.length;j++) {
	        			if(str1[j].matches("[A-Za-z]+")){
	        			wordNum ++;;	
	        			}
	        		}	        		
	        	}
	        }
	        System.out.println("单词数："+wordNum);
	        bufReader.close();
	}
	//计算字符个数
	public void countChar() throws IOException {
		int charNum = 0;
		FileReader inputReader = new FileReader(filepath);
	        BufferedReader bufReader = new BufferedReader(inputReader);
	        String line ;
	        while( ( line = bufReader.readLine() ) != null) {
	        	String [ ] str = line.split(" ");        	
	        	for(int i=0;i<str.length;i++) {
	        		charNum +=str[i].length();
	              	}	   
        	}
	        System.out.println("字符数："+charNum);
	        bufReader.close();
	}
        
	//计算行数
	public void countLine() throws IOException {
		//初始化属性
		int lineNum = 0;
		FileReader inputReader = new FileReader(filepath);
	        BufferedReader bufReader = new BufferedReader(inputReader);
	        String line ;
	        while( ( line = bufReader.readLine() ) != null) {
	        	lineNum++;		        	   
        	}	 
	        System.out.println("总行数："+lineNum);
	        bufReader.close();
        }

	public static void main(String[] args) {
		try{  
			Wcexe wc = null;
			while(true) {
			System.out.println("请输入命令：(输入wc.exe help获取帮助)");
			BufferedReader keyReader = new BufferedReader(new InputStreamReader(System.in));
			String keyInput = keyReader.readLine();
			if("-q".equals(keyInput)) {
				break;
				
			}
			String[] command = keyInput.trim().split(" ");
			if(command.length<3) {
				
			}else {
				path = command[2];
				wc = new Wcexe();  
			}       		 		
        			switch (command[1]) {
        			case "-c":
        				wc.countChar();     			
        				break;
        			case "-w":
        				wc.countWord();
        				break;
        			case "-l":
        				wc.countLine();
        				break;
        			case"-a":
        				wc.countdifLine();
        				break;
        			case"-s":
        				wc.traverseDir(wc.filepath);
        				break;    	
        			case"help":
        				Wcexe.help();
        				break;
        			default:
        				System.out.println("请输入正确命令，输入wc.exe help 获取帮助.............");
        				break;
        			}		
        		}
		}catch(Exception e) {

			System.out.println("请检查输入命令与文件路径是否正确，重启程序输入wc.exe help 获取帮助.............");
		}
	}
}

