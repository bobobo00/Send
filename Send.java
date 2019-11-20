package chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable {
	private Socket client;
	private BufferedReader br=null;
	private DataOutputStream dos=null;
	private boolean isRunning;
	public Send(Socket client) {
		this.client = client;
		br=new BufferedReader(new InputStreamReader(System.in));
		isRunning=true;
		try {
			dos=new DataOutputStream(client.getOutputStream());
			this.send();
		} catch (IOException e) {
	      release();
		}
	}
	
	private void send() {
		String msg=getConsole();
		if(!msg.equals("")) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				System.out.println("#######send#######");
			}
			
		}
		
	}
    private String getConsole() {
    	try {
			return br.readLine();
		} catch (IOException e) {
			System.out.println("#######getConsole#######");
		}
    	return "";
    }
    
    private void release() {
    	isRunning=false;
		Utils.close(br,dos);
	}
    public void run() {
    	while(isRunning) {
    		send();
		}
    	release();
	}
}
