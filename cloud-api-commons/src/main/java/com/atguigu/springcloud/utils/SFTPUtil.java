package com.atguigu.springcloud.utils;

import com.jcraft.jsch.*;
import org.apache.commons.vfs.FileSystemException;
import org.apache.commons.vfs.FileSystemOptions;
import org.apache.commons.vfs.provider.sftp.SftpClientFactory;
import org.apache.commons.vfs.provider.sftp.SftpFileSystemConfigBuilder;

import java.io.*;
import java.util.Vector;

/**
 * @ClassName: SFTPUtil
 * @Description: SFTP工具类
 * @Author:huangwb
 * @CreateDate:2017-4-13 下午4:48:48
 * @Version:0.1
 * @ModifyLog:2017-4-13 下午4:48:48
 */
public class SFTPUtil {

	private ChannelSftp	channel;

	private Session		session;

	/** sftp用户名 */
	private String		userName;
	/** sftp密码 */
	private String		password;
	/** sftp主机ip */
	private String		ftpHost;
	/** sftp主机端口 */
	private int			ftpPort;

	/**
	 * 默认构造方法
	 */
	public SFTPUtil() {

	}

	/**
	 * @Description: 连接SFTP服务器
	 * @Parmaters: @param userName 用户名
	 * @Parmaters: @param password 密码
	 * @Parmaters: @param ftpHost IP地址
	 * @Parmaters: @param ftpPort 端口
	 * @Parmaters: @throws JSchException
	 * @Parmaters: @throws FileSystemException
	 * @Throws:
	 * @Author: huangwb
	 * @CreateDate:2017-4-13 下午4:49:08
	 * @Version:0.1
	 * @ModifyLog:2017-4-13 下午4:49:08
	 */
	public SFTPUtil(String userName, String password, String ftpHost, int ftpPort) throws JSchException, FileSystemException {
		this.userName = userName;
		this.password = password;
		this.ftpHost = ftpHost;
		this.ftpPort = ftpPort;
		System.out.println("SFTP连接信息: " + "userName=" + userName + ", " + "password=" + password + ", " + "ftpHost=" + ftpHost + ", " + "ftpPort=" + ftpPort);
		connectServer();
	}

	/**
	 * @Title: finalize
	 * @Description: 断开连接 V0.x：变更时间：变更描述
	 * @Parmaters: @throws Throwable
	 * @Return: void
	 * @Throws:
	 * @Author:hengyh
	 * @CreateDate:2018年5月29日 下午2:14:35
	 * @Version:0.1
	 * @ModifyLog:2018年5月29日 下午2:14:35
	 */
	@Override
	protected void finalize() throws Throwable {
		this.disconnect();
		super.finalize();
	}

	/**
	 * @Title: downFile
	 * @Description: 从SFTP上下载文件到本地
	 * @Parmaters: @param sftp sftp工具类
	 * @Parmaters: @param remotePath 远程服务器文件路径
	 * @Parmaters: @param remoteFile sftp服务器文件名
	 * @Parmaters: @param localFile 下载到本地的文件路径和名称
	 * @Parmaters: @param closeFlag true 表示关闭连接 false 表示不关闭连接
	 * @Parmaters: @return flag 下载是否成功, true-下载成功, false-下载失败
	 * @Parmaters: @throws Exception
	 * @Return: boolean
	 */
	public synchronized boolean downFile(SFTPUtil sftp, String remotePath, String remoteFile, String localFile, boolean closeFlag) throws Exception {
		boolean flag = false;
		try {
			this.channel.cd(remotePath);
			InputStream input = this.channel.get(remoteFile);

			// 判断本地目录是否存在, 若不存在就创建文件夹
			if (localFile != null && !"".equals(localFile)) {
				File checkFileTemp = new File(localFile);
				if (!checkFileTemp.getParentFile().exists()) {
					// 创建文件夹, 如：在f盘创建/TXT文件夹/testTXT/两个文件夹
					checkFileTemp.getParentFile().mkdirs();
				}
			}

			FileOutputStream out = new FileOutputStream(new File(localFile));
			byte[] bt = new byte[1024];
			int length = -1;
			while ((length = input.read(bt)) != -1) {
				out.write(bt, 0, length);
			}
			input.close();
			out.close();
			if (closeFlag) {
				sftp.disconnect();
			}
			flag = true;
		} catch (SftpException e) {
			System.out.println("文件下载失败！");
			throw new RuntimeException("文件下载失败！");
		} catch (FileNotFoundException e) {
			System.out.println("下载文件到本地的路径有误！");
			throw new RuntimeException("下载文件到本地的路径有误！");
		} catch (IOException e) {
			System.out.println("文件写入有误！");
			throw new RuntimeException("文件写入有误！");
		}

		return flag;
	}

	/**
	 * @Title: downFile
	 * @Description:下载文件
	 * @Parmaters: @param sftp sftp工具类
	 * @Parmaters: @param remotePath 远程服务器文件路径
	 * @Parmaters: @param remoteFile sftp服务器文件名
	 * @Parmaters: @param localFilePath 下载到本地的文件路径
	 * @Parmaters: @param localFileName 下载到本地的文件名称
	 * @Parmaters: @param closeFlag true 表示关闭连接 false 表示不关闭连接
	 * @Parmaters: @return 下载是否成功, true-下载成功, false-下载失败
	 * @Parmaters: @throws Exception
	 * @Return: boolean
	 */
	public synchronized boolean downFile(SFTPUtil sftp, String remotePath, String remoteFileName, String localFilePath, String localFileName, boolean closeFlag) throws Exception {
		boolean flag = false;
		try {
			this.channel.cd(remotePath);
			InputStream input = this.channel.get(remoteFileName);
			String localRemoteFile = localFilePath + remoteFileName;
			File checkFileTemp = null;
			// 判断本地目录是否存在, 若不存在就创建文件夹
			if (localRemoteFile != null && !"".equals(localRemoteFile)) {
				checkFileTemp = new File(localRemoteFile);
				if (!checkFileTemp.getParentFile().exists()) {
					// 创建文件夹, 如：在f盘创建/TXT文件夹/testTXT/两个文件夹
					checkFileTemp.getParentFile().mkdirs();
				}
			}

			FileOutputStream out = new FileOutputStream(new File(localRemoteFile));
			byte[] bt = new byte[1024];
			int length = -1;
			while ((length = input.read(bt)) != -1) {
				out.write(bt, 0, length);
			}
			input.close();
			out.close();
			if (closeFlag) {
				sftp.disconnect();
			}
			flag = true;

			// 下载后的文件重命名
			File upSupFile = new File(localFilePath + localFileName);
			checkFileTemp.renameTo(upSupFile);
		} catch (SftpException e) {
			System.out.println("文件下载失败： " + e);
			throw new RuntimeException("文件下载失败！");
		} catch (FileNotFoundException e) {
			System.out.println("下载文件到本地的路径有误！");
			throw new RuntimeException("下载文件到本地的路径有误！");
		} catch (IOException e) {
			System.out.println("文件写入有误！");
			throw new RuntimeException("文件写入有误！");
		}
		return flag;
	}

	/**
	 * @Title: upFile
	 * @Description: 上传文件到SFTP服务器
	 * @Parmaters: @param remotePath sftp服务器路径
	 * @Parmaters: @param fileName sftp服务器文件名
	 * @Parmaters: @param localFile 本地文件路径和名称字符串
	 * @Parmaters: @param closeFlag 是否要关闭本次SFTP连接: true-关闭, false-不关闭
	 * @Parmaters: @param filePathFlag 是否要创建远程的指定目录: true-创建, false-不创建
	 * @Parmaters: @return
	 */
	public synchronized boolean upFile(String remotePath, String fileName, String localFile, boolean closeFlag, boolean filePathFlag) throws Exception {
		boolean flag = false;
		InputStream input = null;
		try {
			input = new FileInputStream(localFile);
			String now = null;
			// 判断是否要在远程目录上创建对应的目录
			if (filePathFlag) {
				String[] dirs = remotePath.split("\\/");
				if (dirs == null || dirs.length < 1) {
					dirs = remotePath.split("\\\\");
				}

				now = this.channel.pwd();// 获取当前目录
				for (int i = 0; i < dirs.length; i++) {
					if (dirs[i] != null && !"".equals(dirs[i])) {
						boolean dirExists = this.openDirs(dirs[i]);
						if (!dirExists) {
							this.channel.mkdir(dirs[i]);// 创建目录
							this.channel.cd(dirs[i]);// 切换到对应目录
						}
					}
				}
				this.channel.cd(now);// 重新切换到上文当前目录
			}
			this.channel.cd(now + remotePath);// 切换到sftp服务器路径

			this.channel.put(input, fileName);
			this.channel.chmod(444, fileName);// 设置文件权限
			flag = true;
		} catch (SftpException e) {
			System.out.println("文件上传失败:"+ e);
//			System.out.println(e.getMessage());
			throw new RuntimeException("文件上传失败！");
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException 上传文件找不到: " + e);
			throw new RuntimeException("上传文件路径有误！");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception localException1) {
					System.out.println("输入流关闭失败: " + localException1);
				}
			}
			// 判断是否要关闭本次SFTP连接
			if (closeFlag) {
				disconnect();
			}
		}

		return flag;
	}

	/**
	 * @Title: connectServer
	 * @Description: 连接SFTP服务器
	 * @Parmaters: @throws JSchException
	 * @Parmaters: @throws FileSystemException
	 * @Return: void
	 */
	private synchronized void connectServer() throws JSchException, FileSystemException {
		if (this.channel != null) {
			disconnect();
		}
		FileSystemOptions fso = new FileSystemOptions();
		SftpFileSystemConfigBuilder.getInstance().setStrictHostKeyChecking(fso, "no");

		System.out.println("SFTP连接正在创建Session... ...");
		this.session = SftpClientFactory.createConnection(this.ftpHost, this.ftpPort, this.userName.toCharArray(), this.password.toCharArray(), fso);
		System.out.println("SFTP连接Session创建成功");

		System.out.println("SFTP连接正在打开SFTP通道... ...");
		Channel _channel = this.session.openChannel("sftp");
		System.out.println("SFTP连接通道打开成功");

		System.out.println("SFTP连接中... ...");
		_channel.connect();
		System.out.println("SFTP连接成功");

		this.channel = ((ChannelSftp) _channel);
	}

	/**
	 * @Title: disconnect
	 * @Description: 关闭连接,原来是私有方法,由于不同产品关闭连接的时机是不同的,需要在类外面调用此方法
	 * @Return: void
	 */
	public synchronized void disconnect() {
		if (this.channel != null) {
			this.channel.exit();
		}
		if (this.session != null) {
			this.session.disconnect();
		}
		this.channel = null;
	}

	/**
	 * @Title: downFile
	 * @Description: 下载文件
	 * @Parmaters: @param remotePath sftp服务器路径
	 * @Parmaters: @param remoteFile sftp服务器文件名
	 * @Parmaters: @return
	 * @Parmaters: @throws Exception
	 * @Return: InputStream
	 */
	public synchronized InputStream downFile(String remotePath, String remoteFile) throws Exception {
		try {
			this.channel.cd(remotePath);
			return this.channel.get(remoteFile);
		} catch (SftpException e) {
			System.out.println("文件下载失败: " + e);
			throw new Exception("文件下载失败", e);
		}
	}

	/**
	 * @Title: openDirs
	 * @Description: 打开指定目录
	 * @Parmaters: @param directory
	 * @Parmaters: @return
	 * @Return: boolean 是否打开目录
	 */
	public synchronized boolean openDirs(String directory) {
		try {
			this.channel.cd(directory);
			return true;
		} catch (SftpException e) {
			return false;
		}
	}

	/**
	 * @Title: ls
	 * @Description: 查看指定目录下文件<br>
	 *               V0.x：变更时间：变更描述
	 * @Parmaters: @param directory
	 * @Parmaters: @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized Vector<ChannelSftp.LsEntry> ls(String directory) throws Exception {
		try {
			return this.channel.ls(directory);
		} catch (SftpException e) {
			System.out.println("查看指定目录" + directory + "下文件失败");
			throw new Exception(e.getMessage(), e);
		}
	}

	/**
	 * @throws JSchException
	 * @throws FileSystemException
	 * @Title: isNew
	 * @Description: 根据用户名,主机名判断是否已经存在连接<br>
	 *               V0.x：变更时间：变更描述
	 * @Parmaters: @param userName
	 * @Parmaters: @param ftpHost
	 * @Parmaters: @param ftpPort
	 * @Parmaters: @return
	 * @Return: boolean
	 */
	public boolean isNew(String userName, String ftpHost) throws FileSystemException, JSchException {
		if (userName.equals(this.userName) && ftpHost.equals(this.ftpHost)) {
			if (this.channel == null || this.channel.isClosed()) {// 如果已经关闭连接,则重新连接
				this.connectServer();
			}
			return true;
		} else {
			return false;
		}
	}
	public synchronized boolean disconnectSftp(SFTPUtil sftp) {
		sftp.disconnect();
		return true;
	}

	/**
	 * 测试下载文件
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
//		SFTPUtil sftpUtil = new SFTPUtil("fbapp", "ABab12", "10.20.37.127", 22);
//		boolean downResult = sftpUtil.downFile(sftpUtil, "/home/fbapp/app/logs", "yusp-app-single.log", "D:\\fubon\\工作记录\\", "test.log", true);
//		if (downResult == true) {
//			System.out.println("下载成功");
//		}else {
//			System.out.println("下载失败");
//		}

		SFTPUtil sftpUtil = new SFTPUtil("mysftp", "123456", "192.168.254.20", 22);
		boolean downResult = sftpUtil.downFile(sftpUtil, "/upload", "yusp-app-single-3.log", "D:\\fubon\\工作记录\\", "test3.log", true);
		if (downResult == true) {
			System.out.println("下载成功");
		}else {
			System.out.println("下载失败");
		}

//		boolean uploadResult = sftpUtil.upFile("/upload", "yusp-app-single-4.log", "D:\\fubon\\工作记录\\test.log", true, true);
//		if (uploadResult == true) {
//			System.out.println("上传成功");
//		}else {
//			System.out.println("上传失败");
//		}
	}
}

