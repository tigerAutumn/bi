package com.pinting.core.redis;

/**
 * 地址配置
 * @author jackin
 *
 */
public class JedisSharedInfo {
	private String serverName;
	private String ip;
	private int port;
	private String password;
	public JedisSharedInfo(String serverName,String ip,int port,String password){
		this.serverName = serverName;
		this.ip = ip;
		this.port = port;
		this.password = password;
	}
	
	public String getServerName(){
		return serverName;
	}
	
	public String getIp(){
		return ip;
	}
	
	public int getPort(){
		return port;
	}

	public String getPassword() {
		return password;
	}
	
	
	
}
