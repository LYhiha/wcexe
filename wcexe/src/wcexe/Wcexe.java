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
	//��ȡ�ļ�
	File filepath = new File(path);
	//������Ϣ
	public static void help() {
		System.out.println("");
		System.out.println("wc.exe -l �ļ���������·���� ��ȡ����");
		System.out.println("wc.exe -c �ļ���������·������ȡ�ַ���");
		System.out.println("wc.exe -w �ļ���������·���� ��ȡ������");
		System.out.println("wc.exe -a �ļ���������·������ȡ���У�ע���У���������");
		System.out.println("wc.exe -s �ļ��У�����·�������������ļ���");
		System.out.println(" ");
	}
	//�����ļ���
	public void traverseDir(File filepath) {
		File[] files;
		files = filepath.listFiles();
		if(files != null) {
			for(File file : files) {
				if(file.isFile()) {
					 path=file.getAbsolutePath();
					 System.out.println("�ļ�����"+path);
					Wcexe wc1=new Wcexe();
					try {//����������ļ�
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
	
	//������С�ע���С�
	public void countdifLine() throws IOException {
		int blankLine = 0;
		int codeLine = 0;
		int annotateLine = 0;
		FileReader inputReader = new FileReader(filepath);
	        BufferedReader bufReader = new BufferedReader(inputReader);
	        String line;
	        boolean flag = false;//���ڱ��/*     */ע����
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
	        System.out.println("������"+codeLine);
	        System.out.println("ע����"+annotateLine);
	        System.out.println("��   ��"+blankLine);
	}
	//���㵥�ʸ���
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
	        System.out.println("��������"+wordNum);
	        bufReader.close();
	}
	//�����ַ�����
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
	        System.out.println("�ַ�����"+charNum);
	        bufReader.close();
	}
        
	//��������
	public void countLine() throws IOException {
		//��ʼ������
		int lineNum = 0;
		FileReader inputReader = new FileReader(filepath);
	        BufferedReader bufReader = new BufferedReader(inputReader);
	        String line ;
	        while( ( line = bufReader.readLine() ) != null) {
	        	lineNum++;		        	   
        	}	 
	        System.out.println("��������"+lineNum);
	        bufReader.close();
        }

	public static void main(String[] args) {
		try{  
			Wcexe wc = null;
			while(true) {
			System.out.println("���������(����wc.exe help��ȡ����)");
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
        				System.out.println("��������ȷ�������wc.exe help ��ȡ����.............");
        				break;
        			}		
        		}
		}catch(Exception e) {

			System.out.println("���������������ļ�·���Ƿ���ȷ��������������wc.exe help ��ȡ����.............");
		}
	}
}

