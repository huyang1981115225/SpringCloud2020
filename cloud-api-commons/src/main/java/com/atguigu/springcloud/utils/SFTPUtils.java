package com.atguigu.springcloud.utils;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.*;

/**
 * @author huyang
 * @date 2020/7/16 15:21
 */
public class SFTPUtils {

    private String host;//服务器连接ip
    private String username;//用户名
    private String password;//密码
    private int port = 22;//端口号
    private ChannelSftp sftp = null;
    private Session sshSession = null;

    public SFTPUtils() {
    }

    public SFTPUtils(String host, int port, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public SFTPUtils(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    /**
     * 通过SFTP连接服务器
     */
    public void connect() {
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);

            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();

            Channel channel = sshSession.openChannel("sftp");
            channel.connect();

            sftp = (ChannelSftp) channel;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();

            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();

            }
        }
    }

    /**
     * 批量下载文件
     *
     * @param remotPath：远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
     * @param localPath：本地保存目录(以路径符号结束,D:\Duansha\sftp\)
     * @param fileFormat：下载文件格式(以特定字符开头,为空不做检验)
     * @param fileEndFormat：下载文件格式(文件格式)
     * @param del：下载后是否删除sftp文件
     * @return
     */
    public List<String> batchDownLoadFile(String remotePath, String localPath,
                                          String fileFormat, String fileEndFormat, boolean del) {
        List<String> filenames = new ArrayList<String>();
        try {
            // connect();
            Vector v = listFiles(remotePath);
            // sftp.cd(remotePath);
            if (v.size() > 0) {
                System.out.println("本次处理文件个数不为零,开始下载...fileSize=" + v.size());
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        boolean flag = false;
                        String localFileName = localPath + filename;
                        fileFormat = fileFormat == null ? "" : fileFormat
                                .trim();
                        fileEndFormat = fileEndFormat == null ? ""
                                : fileEndFormat.trim();
                        // 三种情况
                        if (fileFormat.length() > 0 && fileEndFormat.length() > 0) {
                            if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat)) {
                                flag = downloadFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else if (fileFormat.length() > 0 && "".equals(fileEndFormat)) {
                            if (filename.startsWith(fileFormat)) {
                                flag = downloadFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else if (fileEndFormat.length() > 0 && "".equals(fileFormat)) {
                            if (filename.endsWith(fileEndFormat)) {
                                flag = downloadFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else {
                            flag = downloadFile(remotePath, filename, localPath, filename);
                            if (flag) {
                                filenames.add(localFileName);
                                if (flag && del) {
                                    deleteSFTP(remotePath, filename);
                                }
                            }
                        }
                    }
                }
            }

            System.out.println("download file is success:remotePath=" + remotePath
                    + "and localPath=" + localPath + ",file size is"
                    + v.size());

        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            // this.disconnect();
        }
        return filenames;
    }

    /**
     * 下载单个文件
     *
     * @param remotPath：远程下载目录(以路径符号结束)
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        FileOutputStream fieloutput = null;
        try {
            // sftp.cd(remotePath);
            File file = new File(localPath + localFileName);
            // mkdirs(localPath + localFileName);
            fieloutput = new FileOutputStream(file);
            sftp.get(remotePath + remoteFileName, fieloutput);
            System.out.println("===DownloadFile:" + remoteFileName + " success from sftp.");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            if (null != fieloutput) {
                try {
                    fieloutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 上传单个文件
     *
     * @param remotePath：远程保存目录
     * @param remoteFileName：保存文件名
     * @param localPath：本地上传目录(以路径符号结束)
     * @param localFileName：上传的文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        FileInputStream in = null;
        try {
            createDir(remotePath);
            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SftpException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 批量上传文件
     *
     * @param remotePath：远程保存目录
     * @param localPath：本地上传目录(以路径符号结束)
     * @param del：上传后是否删除本地文件
     * @return
     */
    public boolean bacthUploadFile(String remotePath, String localPath,
                                   boolean del) {
        try {
            connect();
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()
                        && files[i].getName().indexOf("bak") == -1) {
                    if (this.uploadFile(remotePath, files[i].getName(),
                            localPath, files[i].getName())
                            && del) {
                        deleteFile(localPath + files[i].getName());
                    }
                }
            }

            System.out.println("upload file is success:remotePath=" + remotePath
                    + "and localPath=" + localPath + ",file size is "
                    + files.length);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.disconnect();
        }
        return false;

    }

    /**
     * 删除本地文件
     *
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        if (!file.isFile()) {
            return false;
        }
        boolean rs = file.delete();

        System.out.println("delete file success from local.");

        return rs;
    }

    /**
     * 创建目录
     *
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath) {
        try {
            if (isDirExist(createpath)) {
                this.sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }

            }
            this.sftp.cd(createpath);
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断目录是否存在
     *
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 删除stfp文件
     *
     * @param directory：要删除文件所在目录
     * @param deleteFile：要删除的文件
     * @param sftp
     */
    public void deleteSFTP(String directory, String deleteFile) {
        try {
            // sftp.cd(directory);
            sftp.rm(directory + deleteFile);

            System.out.println("delete file success from sftp.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 如果目录不存在就创建目录
     *
     * @param path
     */
    public void mkdirs(String path) {
        File f = new File(path);

        String fs = f.getParent();

        f = new File(fs);

        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory：要列出的目录
     * @param sftp
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException {
        return sftp.ls(directory);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }

    /**
     * 测试
     */
    public static void main(String[] args) {
        SFTPUtils sftp = null;
        // 本地存放地址
        String localPath = "D:/fubon/工作记录/";
        // Sftp下载路径
        String sftpPath = "/upload/";
        List<String> filePathList = new ArrayList<String>();
        try {
            sftp = new SFTPUtils("192.168.254.20", "mysftp", "123456");
            sftp.connect();
            // 下载
//            boolean flag = sftp.downloadFile(sftpPath, "yusp-app-single.log", localPath, "test1.log");
            boolean flag = sftp.uploadFile(sftpPath,"yusp-app-single-1.log",localPath,"test1.log");
            if (flag) {
                System.out.println("成功");
            } else {
                System.out.println("失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sftp.disconnect();
        }
    }
}


