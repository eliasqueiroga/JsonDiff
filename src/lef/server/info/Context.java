package lef.server.info;

public class Context {
	
	private String host;
	
	private String protocol;
	
	private int port;
	
	private String method;

	public Context(){
	}
	
	public Context(String host, String protocol, int port, String method){
		this.host = host;
		this.protocol = protocol;
		this.port = port;
		this.method = method;
	}
	
	public String getMethod() {
		return method;
	}


	public void setMethod(String method) {
		this.method = method;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}	
}
