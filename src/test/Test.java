package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李浩铭
 * @since 2017年2月8日 下午3:41:51
 */
public class Test {
	public static void main(String[] args) {
		String cssPath = "C:\\Users\\Administrator\\Desktop\\插件注册\\插件\\天降红包雨_1\\css\\style.css";
		String path = "C:\\Users\\Administrator\\Desktop\\插件注册\\插件\\天降红包雨_1\\index.html";
		runResolver(path, cssPath);
	}

	static List<String> htmlList = null;
	static List<String> cssList = null;

	public static void runResolver(String path, String cssPath) {
		htmlList = readFile(path);
		cssList = readFile(cssPath);
		resolverBody(getBody(htmlList));

	}

	/**
	 * 在html截取Body中的内容
	 */
	private static void resolverBody(List<String> list) {
		for (String string : list) {
			// System.out.println(string);
		}
		for (int i = 0; i < list.size(); i++) {
			String str = list.get(i);
			int w = str.indexOf("<div");
			if (w != -1) {
				//判断是否这个div内是否包含其它元素
				if(str.indexOf("</div>")!=-1){
					
				}
				
				
				
				
//				// 得到的clsss[]数组 如editor bg
//				String classs[] = getClassList(str);
//				//得到该class数组中的所有样式
//				List<String> css=getCssByName(classs);
			}
		}
	}

	/**
	 * 传入<div class="editor bg"></div> 返回数组[editor,bg];
	 */
	private static String[] getClassList(String str) {
		int i = str.indexOf("class=");
		if (i != -1) {
			str = str.substring(i + 7, str.length());
			str = str.substring(0, str.indexOf("\""));
			if (!str.equals("")) {
				return str.split(" ");
			}
		}
		return null;
	}

	/**
	 * 通过class名查询所有的样式
	 * @return list集合
	 */
	private  static List<String> getCssByName(String str[]) {
		List<String> list=new ArrayList<String>();
		int beginIndex=0;
		int endIndex=0;
		if (str != null) {
			for(int k=0;k<str.length;k++){
				String className="."+str[k].trim()+"{";
				for (int i = 0; i < cssList.size(); i++) {
					//查找class名
					if(className.equals(cssList.get(i))){
						beginIndex=i+1;
						for (int r = i; r < cssList.size(); r++) {
							//查找class名
							if("}".equals(cssList.get(r))){
								endIndex=r;
								break;
							}
						}
						break;
					}
				}
				for(int i=beginIndex;i<endIndex;i++){
					list.add(cssList.get(i));
				}
			}
		}
		return list;
	}
	
	/**
	 * 解析样式
	 * */


	/**
	 * 截取html文本body区域
	 * 
	 * @return list集合
	 */
	private static List<String> getBody(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			list.remove(0);
			if (list.get(0).trim().equals("<body>")) {
				list.remove(0);
				break;
			}
		}
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).trim().equals("</body>")) {
				for (int k = i; k < list.size(); k++) {
					list.remove(k);
				}
				break;
			}
		}
		return list;
	}

	/**
	 * 读取html或css文件
	 * 
	 * @return List<Stirng>
	 */
	private static List<String> readFile(String filePath) {
		BufferedReader bufferedReader = null;
		InputStreamReader read = null;
		List<String> text = new ArrayList<String>();
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					if (!lineTxt.trim().equals("")) {
						text.add(lineTxt);
					}
				}
				return text;

			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			try {
				read.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if (read != null) {
				try {
					read.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
