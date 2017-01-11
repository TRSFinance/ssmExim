package com.trs.common.util.ftp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trs.common.util.DateUtils;

/**
 *　　　　　　　　┏┓　　　┏┓+ +
 *　　　　　　　┏┛┻━━━┛┻┓ + +
 *　　　　　　　┃　　　　　　　┃ 　
 *　　　　　　　┃　　　━　　　┃ ++ + + +
 *　　　　　　 ████━████ ┃+
 *　　　　　　　┃　　　　　　　┃ +
 *　　　　　　　┃　　　┻　　　┃
 *　　　　　　　┃　　　　　　　┃ + +
 *　　　　　　　┗━┓　　　┏━┛
 *　　　　　　　　　┃　　　┃　　　　　　　　　　　
 *　　　　　　　　　┃　　　┃ + + + +
 *　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting　　　　　　　
 *　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug　　
 *　　　　　　　　　┃　　　┃
 *　　　　　　　　　┃　　　┃　　+　　　　　　　　　
 *　　　　　　　　　┃　 　　┗━━━┓ + +
 *　　　　　　　　　┃ 　　　　　　　┣┓
 *　　　　　　　　　┃ 　　　　　　　┏┛
 *　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 *　　　　　　　　　　┃┫┫　┃┫┫
 *　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 */
/**
 * ftp服务器的工具类
 * ftp可能存在连接数限制，所有相关操作都为单线程方式
 * @author wangjian
 * @author 邹许红     2015-10-27
 */
public class FTPUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(FTPUtils.class);
	/**
	 * 连接测试次数
	 */
	public int connectTestTime= 3;
	/**
	 * 获取文件名重试次数
	 */
	public int fileNameGetRetryTime=3;
	/**
	 * 每个文件下载重试的次数
	 */
	public int fileDowmRetryTime=3;
	/**
	 * 创建连接代理
	 * @throws IOException
	 */
	public static FTPClient creatClient(String host,int port,String userName,String passWord){
		try {
		FTPClient ftpClient  = new FTPClient();				
			if(port>0){
				ftpClient.connect(host, port);
			}else{
				ftpClient.connect(host);
			}
			ftpClient.login(userName, passWord);
		return ftpClient;
		}catch(IOException e){
			LOGGER.info("创建ftpClient:"+host+"异常");
			return null;
		}
	}
	/**
	 * 关闭代理连接
	 * @param ftpClient
	 */
	public static void closeConnect(FTPClient ftpClient)  {
		if(ftpClient.isConnected()) {
		try {
			ftpClient.disconnect();
		} catch (IOException e) {
			LOGGER.info("关闭FTP连接错误");
			e.printStackTrace();
		}
  	   }
	}

	/**
	 * 将整个ftp的处理封装在本方法中，逻辑流程如下：
	 * 1.创建ftp代理
	 * 2.连接测试
	 * 3.获取指定目录下所有数据文件
	 * 4.分析出需要进行下载的文件列表
	 * 5.进行下载
	 * 6.删除ftp数据目录下所有文件
	 * 7.关闭连接
	 */
	public String errorMesage="";
	public List<String> ftpGetFileProcess(String host,int port,String userName,String passWord,String ftpDateSaveDir,String localSaveDir,
			String dataFileprefix,String dataFileSuffix){
		LOGGER.info("下载ftp服务器参数，host:"+host+"port:"+port+"userName:"+userName+"passWord:"+passWord+"ftpDateSaveDir:"+ftpDateSaveDir);
		FTPClient ftpClient =creatClient( host, port, userName, passWord);
		if(null==ftpClient){
			errorMesage="创建ftpClient异常";
			return null;
		}
		try{
		boolean connectTest=connectTest(ftpClient);
		if(!connectTest){
			errorMesage="无法连接ftp服务器";
			return null;
		}
		Set<String> ftpFileSet =getFtpFileNameSet(ftpClient,ftpDateSaveDir);
		if(null==ftpFileSet||ftpFileSet.size()<=0){
			errorMesage="ftp数据目录下没有数据文件存在";
			return null;
		}
		Map<String,List<String>> processFileMap =
		generateNeedProcessFiles(ftpFileSet,dataFileprefix,dataFileSuffix);
		List<String> downFiles = processFileMap.get(NEEDDOWNFIL);
		if(null==downFiles||downFiles.size()<=0){
			errorMesage="没有符合条件的数据文件";
			return null;
		}
		List<String>completeDownFiles=downFtpFileList( ftpClient,downFiles,ftpDateSaveDir,localSaveDir);
		if(null==completeDownFiles||completeDownFiles.size()<=0){
			errorMesage="下载数据文件出现异常";
			return null;
		}
		for(String deleteFileName:ftpFileSet){
			LOGGER.info("FTP上删除文件："+deleteFileName);
			//deleteFTPFile(ftpClient,ftpDateSaveDir,deleteFileName);//测试时注释此行，以便进行多次测试
		}
		return completeDownFiles;
		}finally{
			closeConnect(ftpClient);
		}
	}

	/**
	 * 向ftp服务器提交数据文件
	 * @param ftpClient
	 * @param ftpSaveDir
	 * @param ftpFileName
	 * @param localDir
	 * @param localFileName
	 * @return
	 */
	
	public boolean submitFtpFile(String host,int port,String userName,String passWord,
			String ftpSaveDir,String ftpDateFileName,String ftpTagFileName,String localDir,String localDateFileName,String localTagFileName){
		LOGGER.info("上传ftp服务器参数，host:"+host+"port:"+port+"userName:"+userName+"passWord:"+passWord+"ftpSaveDir:"+ftpSaveDir
				+"ftpDateFileName:"+ftpDateFileName+"ftpTagFileName:"+ftpTagFileName+"localDir:"+localDir+"localDateFileName:"+localDateFileName
				+"localTagFileName"+localTagFileName);
		FTPClient ftpClient =creatClient( host, port, userName, passWord);
		if(null==ftpClient){
			errorMesage="创建ftpClient异常";
			return false;
		}
		boolean connectTest=connectTest(ftpClient);
		if(!connectTest){
			errorMesage="无法连接ftp服务器";
			return false;
		}
		File localDataFile =new File(localDir+File.separatorChar+localDateFileName);
		File localTagFile =new File(localDir+File.separatorChar+localTagFileName);
		InputStream inputDate;
		InputStream inputTag;
		try {
			inputDate=new FileInputStream(localDataFile);
			ftpClient.enterLocalPassiveMode();
			ftpClient.changeWorkingDirectory(ftpSaveDir);
			ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK" ); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.storeFile(ftpDateFileName, inputDate);
			inputDate.close();
			LOGGER.info("ftp数据文件上传成功");
			//创建标记文件
			inputTag = new FileInputStream(localTagFile);
			ftpClient.storeFile(ftpTagFileName, inputTag);
			inputTag.close();
			LOGGER.info("ftp标识文件上传成功");
			ftpClient.logout();
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			errorMesage="上传ftp服务器文件出现错误";
			LOGGER.info("上传ftp服务器文件出现错误");
			return false;	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMesage="上传ftp服务器文件出现错误";
			LOGGER.info("上传ftp服务器文件出现错误");
			return false;
		}finally {  
            if (ftpClient.isConnected()) {   
                try {   
                	ftpClient.disconnect();   
                } catch (IOException ioe) {   
                }   
            }   
        }  
		
	}
	
	
	
	/**
	 * FTP连接测试
	 */
	private  boolean connectTest(FTPClient ftpClient) {
		for(int retryIndex=0;retryIndex<connectTestTime;retryIndex++){
			int reply = ftpClient.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)) {
			   closeConnect(ftpClient);
			   LOGGER.info("FTP服务器第"+(retryIndex+1)+"次拒绝连接");
			   if(retryIndex>=connectTestTime){
			    	return false;
			    	}
			     }else{
			    	 LOGGER.info("FTP服务器:"+ftpClient.getRemoteAddress()+"第"+(retryIndex+1)+"次连接成功");
			    	 return true;
			      }
			}
		return false;
	}


	
	/**
	 * 获取ftp指定目录下的所有文件的名称
	 * @param ftpDateSaveDir ftp数据存储目录
	 */
	private Set<String> getFtpFileNameSet(FTPClient ftpClient,String ftpDateSaveDir){
		Set<String>fileNameSet = new HashSet<String>();
		for(int retryIndex=1;retryIndex<fileNameGetRetryTime;retryIndex++){
			try {
				ftpClient.enterLocalPassiveMode();
				FTPFile[] files = ftpClient.listFiles(ftpDateSaveDir);
				for(FTPFile tempFtpFile :files){
					//String filname = new String(tempFtpFile.getName().getBytes("iso-8859-1"),"gbk");
					String filname = tempFtpFile.getName();
					fileNameSet.add(filname);
					}
				return fileNameSet;
			} catch (IOException e) {
				LOGGER.info("第"+(retryIndex+1)+"次获取ftp目录下文件列表名称错误");
				if(retryIndex>=fileNameGetRetryTime-1){//最后一次将异常打印，以便检查错误
					e.printStackTrace();
				}
			}
		}
		return fileNameSet;
	}
	
	private String NEEDDOWNFIL= "needdownfiles";//标记需要下载的文件名列表
	/**
	 * 根据文件逻辑，生成需要进行处理的文件列表 
	 * （如文件命名规范及处理逻辑改变，更改此方法即可）
	 * 为便于扩展，返回值设为Map<String,List<String>>类型，以便应对将来多种返回文件的境况，string用以标记下一步需要进行的处理流程
	 * @param fileNameSet  //所有的文件
	 * @param dataFileprefix //数据文件前缀
	 * @param dataFileSuffix //数据文件后缀
	 */
	private Map<String,List<String>> generateNeedProcessFiles(Set<String> fileNameSet,String dataFileprefix,String dataFileSuffix){
		List<String> needDownList= new ArrayList<String>();
		String dataFileRexSufFix="";
		if(dataFileSuffix.startsWith(".")){
			dataFileRexSufFix="\\"+dataFileSuffix;
		}else {
			dataFileRexSufFix=dataFileSuffix;
		}
		String dataFileRegex="^"+dataFileprefix+".*"+dataFileRexSufFix;
		for(String tempFileName:fileNameSet){
			//数据文件名符合规范并且存在其对应的成功标记文件即可
			if(tempFileName.matches(dataFileRegex)){//符合命名规范
				LOGGER.info("待下载文件:"+tempFileName);
				needDownList.add(tempFileName);	
			}
		}
		Map<String,List<String>> returnMap = new HashMap<String,List<String>>();
		returnMap.put(NEEDDOWNFIL, needDownList);
		return returnMap;
	}
	/**
	 * 下载ftp目录下指定文件到本地指定目录
	 * @param downFiles  需要下载的文件名列表
	 * @param ftpSaveDir  ftp保存目录
	 * @param localSaveDir  本地存储目录
	 * @return   本地存储的文件名列表
	 */
	private List<String> downFtpFileList(FTPClient ftpClient,List<String> downFiles,String ftpSaveDir,String localSaveDir){
		List<String>localSaveFiles= new ArrayList<String>();
		for(String ftpFileName:downFiles){
			boolean downSuccess = downSingleFTPFile(ftpClient,ftpSaveDir,localSaveDir,ftpFileName,ftpFileName);//采用与ftp相同的文件名
			if(downSuccess){
				localSaveFiles.add(ftpFileName); //即使有个别文件下载失败，并不影响其他文件的下载
			}
		}
		return localSaveFiles;
	}
	

	/**
	 * 实现一个文件的下载
	 * @param ftpClient 
	 * @param ftpSaveDir  
	 * @param localSaveDir
	 * @param ftpFileName
	 * @param localSavefileName  本地存储的文件名
	 */
	private boolean downSingleFTPFile(FTPClient ftpClient,String ftpSaveDir,String localSaveDir,String ftpFileName,String localSavefileName){
		LOGGER.info("开始下载文件:"+ftpFileName);
		for(int retryIndex=0;retryIndex<fileDowmRetryTime;retryIndex++){
		try {
			File saveFileDir =new File(localSaveDir);
			if(!saveFileDir.exists()){
				FileUtils.forceMkdir(saveFileDir);
			}
			File saveFile =new File(localSaveDir+File.separatorChar+localSavefileName);
			if(saveFile.exists()){
				saveFile.delete(); //如果文件已存在，则删除
			}
			OutputStream os = new FileOutputStream(saveFile);
		    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		    //如果对名称不进行转换的话会将中文的文件下下来后大小变为0kb
		    ftpClient.retrieveFile(new String((ftpSaveDir+File.separatorChar+ftpFileName).getBytes("gbk"),"iso-8859-1"), os);
		    os.close();
		    return true;
		} catch (IOException e) {
			LOGGER.info("文件:"+ftpFileName+"第"+(retryIndex+1)+"次下载错误");
			if(retryIndex>=fileDowmRetryTime-1){
				e.printStackTrace();
			}
		}
		}
		return false;
	}
	/**
	 * ftp服务器上删除文件
	 * @param ftpSaveDir
	 * @param ftpFileName
	 * @throws FTPTransferException
	 */
	public static void deleteFTPFile(FTPClient ftpClient,String ftpSaveDir,String ftpFileName){
		try {			
			boolean result = ftpClient.deleteFile(ftpSaveDir+File.separatorChar+ftpFileName);
			if(result){
				LOGGER.info("FTP服务器中进行删除"+ftpFileName+"文件成功");
			}else{
				LOGGER.info("FTP服务器中进行删除"+ftpFileName+"文件失败");
			}
		} catch (IOException e) {
			LOGGER.info("FTP服务器中删除"+ftpFileName+"文件出现错误");
			e.printStackTrace();
		}
	}
	/**
	 * 获取FTP特定目录下符合条件的文件名
	 * @param ftpClient
	 *         连接信息
	 * @param ftpSaveDir
	 *         保存地址
	 * @param ftpFileprefix
	 *         文件前缀
	 * @param ftpFileSuffix
	 *         文件后缀
	 * @return 符合条件的文件名列表
	 * @author 邹许红
	 */
	public  static  List<String>  getFileNames(FTPClient ftpClient,String ftpSaveDir,
			String  ftpFileprefix,String ftpFileSuffix){
		List<String>  list=new ArrayList<String>();
		String dataFileRexSufFix="";
		if(ftpFileSuffix.startsWith(".")){
			dataFileRexSufFix="\\"+ftpFileSuffix;
		}else {
			dataFileRexSufFix=ftpFileSuffix;
		}
		String dataFileRegex="^"+ftpFileprefix+".*"+dataFileRexSufFix;
		try {
			ftpClient.enterLocalPassiveMode();
			String[] listName=ftpClient.listNames(ftpSaveDir);
			if(listName==null)
				return list;
			for (int i = 0; i < listName.length; i++) {
				String fileName = listName[i];
				if(fileName.matches(dataFileRegex))
					list.add(fileName);
				
			}
		} catch (IOException e) {
			return list;
		}
		
		return list;
	}
	
	/**
	 * 获取FTP特定目录下N天前的文件名
	 * 
	 * @param ftpClient
	 *         连接信息
	 * @param ftpSaveDir
	 *         保存地址
	 * @param  days
	 *         保留天数
	 * @return 符合条件的文件名列表
	 * @author 邹许红
	 */
	public  static  List<String>  getFileNames(FTPClient ftpClient,String ftpSaveDir,int days){
		List<String>  list=new ArrayList<String>();
	    Date   beginDate=DateUtils.getDate(-days);
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");	
		String  dateString  =df.format(beginDate);
		
		try {
			
		    beginDate=df.parse(dateString);
		    long   beginLong=beginDate.getTime();
		    long   endLong=beginLong+86400000;
			ftpClient.enterLocalPassiveMode();
			 FTPFile[] listFiles = ftpClient.listFiles(ftpSaveDir);
			 if(listFiles==null)
				 return  list;
			 for (int i = 0; i < listFiles.length; i++) {
				FTPFile ftpFile = listFiles[i];
				long    ftpFileTime=ftpFile.getTimestamp().getTime().getTime();
				if(ftpFileTime<=endLong&&ftpFileTime>=beginLong)
					list.add(ftpFile.getName());
				
			}
			 
			 
			
		} catch (IOException e) {
			return list;
		}catch (ParseException e) {
			
			return list;
		}
		
		return list;
	}
	
	
	
	/**
	 * 获取FTP特定目录下符合条件的文件名
	 * @param ftpClient
	 *         连接信息
	 * @param ftpSaveDir
	 *         保存地址
	 * @param ftpFileprefix
	 *         文件前缀
	 * @param dateString
	 *        日期
	 * @param ftpFileSuffix
	 *         文件后缀
	 * @return 符合条件的文件名列表
	 * @author 邹许红
	 */
	public  static  List<String>  getFileNames(FTPClient ftpClient,String ftpSaveDir,
			String  ftpFileprefix,String dateString,String ftpFileSuffix){
		List<String>  list=new ArrayList<String>();
		String dataFileRexSufFix="";
		if(ftpFileSuffix.startsWith(".")){
			dataFileRexSufFix="\\"+ftpFileSuffix;
		}else {
			dataFileRexSufFix=ftpFileSuffix;
		}
		String dataFileRegex="^"+ftpFileprefix+dateString+".*"+dataFileRexSufFix;
		try {
			ftpClient.enterLocalPassiveMode();
			String[] listName=ftpClient.listNames(ftpSaveDir);
			if(listName==null)
				return list;
			for (int i = 0; i < listName.length; i++) {
				String fileName = listName[i];
				if(fileName.matches(dataFileRegex))
					list.add(fileName);
				
			}
		} catch (IOException e) {
			return list;
		}
		
		return list;
	}
	/**
	 * 删除FTP特定目录下符合条件的文件
	 * @param ftpClient
	 *         连接信息
	 * @param ftpSaveDir
	 *         保存地址
	 * @param ftpFileprefix
	 *         文件前缀
	 * @param dateString
	 *        日期
	 * @param ftpFileSuffix
	 *         文件后缀
	 * @return 符合条件的文件名列表
	 * @author 邹许红
	 */
	public  static  List<String>  removeFile(FTPClient ftpClient,String ftpSaveDir,
			String  ftpFileprefix,String dateString,String ftpFileSuffix){
		List<String>  list=new ArrayList<String>();
		String dataFileRexSufFix="";
		if(ftpFileSuffix.startsWith(".")){
			dataFileRexSufFix="\\"+ftpFileSuffix;
		}else {
			dataFileRexSufFix=ftpFileSuffix;
		}
		String dataFileRegex="^"+ftpFileprefix+dateString+".*"+dataFileRexSufFix;
		try {
			ftpClient.enterLocalPassiveMode();
			String[] listName=ftpClient.listNames(ftpSaveDir);
			if(listName==null)
				return list;
			for (int i = 0; i < listName.length; i++) {
				String fileName = listName[i];
				if(fileName.matches(dataFileRegex))
					deleteFTPFile(ftpClient, ftpSaveDir, fileName);
				
			}
		} catch (IOException e) {
			return list;
		}
		
		return list;
	}
	
	public static void main(String[] args) throws ParseException {
	
	System.out.println(24*60*60*1000);
	}
}


