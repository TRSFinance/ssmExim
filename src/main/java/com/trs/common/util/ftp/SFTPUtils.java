package com.trs.common.util.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(SFTPUtils.class);
    
	private ChannelSftp sftp = null;
	private Session sshSession =null;
	private Channel channel=null;
    public  SFTPUtils(){
    	
    }
	/**
	 * 连接sftp服务器
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public SFTPUtils(String host, int port, String username,
			String password){
		
		sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			sshSession = jsch.getSession(username, host, port);
			LOGGER.info("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.setTimeout(1000*60*30);
			sshSession.connect();
			LOGGER.info("Session connected.");
			LOGGER.info("Opening Channel.");
		
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("创建SFTP通道出现异常");
		}
		
		
		
	}
	
	
	
	/**
	 * 连接sftp服务器
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return
	 */
	public synchronized  ChannelSftp connect(String host, int port, String username,
			String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			sshSession = jsch.getSession(username, host, port);
			LOGGER.info("Session created.");
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.setTimeout(1000*60*30);
			sshSession.connect();
			LOGGER.info("Session connected.");
			LOGGER.info("Opening Channel.");
		
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			LOGGER.error("创建SFTP通道出现异常");
		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 *            SFTP通道
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFile
	 *            存在本地的路径
	 * @param sftp
	 */
	public void download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    /**
     * 
     * @param dataFileprefix
     *        文件前缀
     * @param dataFileSuffix
     *        文件后缀
     * @param directory
     *        目录
     * @param sftp
     *        SFTP通道
     * @return
     */
	public  List<String>   listFiles(String  dataFileprefix,String dataFileSuffix,
			String directory,ChannelSftp sftp){
		List<String>  list=new ArrayList<String>();
		String dataFileRexSufFix="";
		if(dataFileSuffix.startsWith(".")){
			dataFileRexSufFix="\\"+dataFileSuffix;
		}else {
			dataFileRexSufFix=dataFileSuffix;
		}
		String dataFileRegex="^"+dataFileprefix+".*"+dataFileRexSufFix;
		try {
			Vector<?> lsList=sftp.ls(directory);
			for (int i = 0; i < lsList.size(); i++) {
				LsEntry lsEntry=(LsEntry) lsList.get(i);
				String  fileName=lsEntry.getFilename();
				if(fileName.matches(dataFileRegex))
					list.add(fileName);
			}
			
		} catch (SftpException e) {
			e.printStackTrace();
			LOGGER.error("SFTP查询目录出现异常");
			
		}
		return list;
		
	}
	/**
     * 
     * @param dataFileprefix
     *        文件前缀
     * @param dataFileSuffix
     *        文件后缀
     * @param directory
     *        目录
     * @param sftp
     *        SFTP通道
     * @return
     */
	public static List<String>   listFileStatic(String  dataFileprefix,String dataFileSuffix,
			String directory,ChannelSftp sftp){
		List<String>  list=new ArrayList<String>();
		String dataFileRexSufFix="";
		if(dataFileSuffix.startsWith(".")){
			dataFileRexSufFix="\\"+dataFileSuffix;
		}else {
			dataFileRexSufFix=dataFileSuffix;
		}
		String dataFileRegex="^"+dataFileprefix+".*"+dataFileRexSufFix;
		try {
			Vector<?> lsList=sftp.ls(directory);
			for (int i = 0; i < lsList.size(); i++) {
				LsEntry lsEntry=(LsEntry) lsList.get(i);
				String  fileName=lsEntry.getFilename();
				if(fileName.matches(dataFileRegex))
					list.add(fileName);
			}
			
		} catch (SftpException e) {
			e.printStackTrace();
			LOGGER.error("SFTP查询目录出现异常");
			
		}
		return list;
		
	}
	
	/**
     * 获取
     * @param dataFileprefix
     *        文件前缀
     * @param dataFileSuffix
     *        文件后缀
     * @param directory
     *        目录
     * @param dateString
     *        日期
     * @param sftp
     *        SFTP通道
     * @return
     */
	public static List<String>   listFileStatic(String  dataFileprefix,String dataFileSuffix,
			String directory,String dateString,ChannelSftp sftp){
		List<String>  list=new ArrayList<String>();
		String dataFileRexSufFix="";
		if(dataFileSuffix.startsWith(".")){
			dataFileRexSufFix="\\"+dataFileSuffix;
		}else {
			dataFileRexSufFix=dataFileSuffix;
		}
		String dataFileRegex="^"+dataFileprefix+dateString+".*"+dataFileRexSufFix;
		try {
			Vector<?> lsList=sftp.ls(directory);
			for (int i = 0; i < lsList.size(); i++) {
				LsEntry lsEntry=(LsEntry) lsList.get(i);
				String  fileName=lsEntry.getFilename();
				if(fileName.matches(dataFileRegex))
					list.add(fileName);
			}
			
		} catch (SftpException e) {
			e.printStackTrace();
			LOGGER.error("SFTP查询目录出现异常");
			
		}
		return list;
		
	}
	
	
	/**
     * 删除符合条件的文件
     * @param dataFileprefix
     *        文件前缀
     * @param dataFileSuffix
     *        文件后缀
     * @param directory
     *        目录
     * @param dateString
     *        日期
     * @param sftp
     *        SFTP通道
     * @return
     */
	public static void   removeFileStatic(String  dataFileprefix,String dataFileSuffix,
			String directory,String dateString,ChannelSftp sftp){
		String dataFileRexSufFix="";
		if(dataFileSuffix.startsWith(".")){
			dataFileRexSufFix="\\"+dataFileSuffix;
		}else {
			dataFileRexSufFix=dataFileSuffix;
		}
		String dataFileRegex="^"+dataFileprefix+dateString+".*"+dataFileRexSufFix;
		try {
			Vector<?> lsList=sftp.ls(directory);
			sftp.cd(directory);
			
			for (int i = 0; i < lsList.size(); i++) {
				LsEntry lsEntry=(LsEntry) lsList.get(i);
				String  fileName=lsEntry.getFilename();
				if(fileName.matches(dataFileRegex))
					sftp.rm(fileName);
					
			}
			
		} catch (SftpException e) {
			e.printStackTrace();
			LOGGER.error("SFTP查询目录出现异常");
			
		}
		
		
	}
	
	
	
	
	
	/**
	 * 列出目录下的文件及目录
	 * @param directory
	 *        要列出的目录
	 * @param sftp
	 *        SFTP通道
	 * @return
	 * @throws SftpException
	 */
	public Vector<?> listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}
	
	/** 
     * 关闭连接 
     */  
    public void  disconnect(ChannelSftp sftp ,Session sshSession,Channel channel)  {  
        try{  
            if (sftp!=null&&sftp.isConnected()) {  
                sftp.disconnect();  
            }  
            if (channel!=null&&channel.isConnected()) {  
                channel.disconnect();  
            }  
            if (sshSession!=null&&sshSession.isConnected()) {  
            	sshSession.disconnect();  
            }  
        }catch(Exception e){  
        	LOGGER.error("关闭服务器连接出现异常");
        }  
     }  
       
	
	//---------------------------------------------------------------------
	public ChannelSftp getSftp() {
		return sftp;
	}



	public void setSftp(ChannelSftp sftp) {
		this.sftp = sftp;
	}



	public Session getSshSession() {
		return sshSession;
	}



	public void setSshSession(Session sshSession) {
		this.sshSession = sshSession;
	}



	public Channel getChannel() {
		return channel;
	}



	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	
	



	
	
	
	
	

}
