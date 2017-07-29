package team.liutian.chating.server;

/**
* Copyright (C), 2006-2010, Name of Company (e.g. : ChengDu Lovo info. Co., Ltd.)

* FileName: Server.java

* Class Description should be committed here....

*

* @author  LiuTian 
* @Date    7.29

* @version 1.00
*/

//�����������  http://blog.csdn.net/sunhuaqiang1/article/details/52823513

import java.io.BufferedReader;  
import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.io.InputStreamReader;  
import java.net.ServerSocket;
import java.net.Socket;  

public class Server {
    public static final int PORT = 12345;//�����Ķ˿ں�

    public static void main(String[] args) {
        System.out.println("����������...\n");
        Server server = new Server();
        server.init();
    }    

    public void init() {
        try {    
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                // һ���ж���, ���ʾ��������ͻ��˻��������
                Socket client = serverSocket.accept();
                // ����һ���µ��̴߳����������
                new HandlerThread(client);
            }
        } catch (Exception e) {
            System.out.println("�������쳣: " + e.getMessage());
        }
    }

    //  ����һ���µ��̴߳�������
    private class HandlerThread implements Runnable {
        private Socket socket;    
        public HandlerThread(Socket client) {
            socket = client;
            new Thread(this).start();
        }

        public void run() {
            try {
                // ��ȡ�ͻ�������    
                DataInputStream input = new 
DataInputStream(socket.getInputStream());  
//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFException  
                String clientInputStr = input.readUTF();
                // ����ͻ�������
                System.out.println("�ͻ��˷�����������:" + clientInputStr);    
                // ��ͻ��˻ظ���Ϣ
                DataOutputStream out = new 
                                   DataOutputStream(socket.getOutputStream());
                System.out.print("������:\t");
                // ���ͼ��������һ��
                String s = new BufferedReader(new 
 InputStreamReader(System.in)).readLine();
                out.writeUTF(s);

                out.close();    
                input.close();    
            } catch (Exception e) {    
                System.out.println("������ run �쳣: " + e.getMessage());    
            } finally {    
                if (socket != null) {    
                    try {    
                        socket.close();    
                    } catch (Exception e) {
                        socket = null;    
                        System.out.println("����� finally �쳣:" + 
e.getMessage());    
                    }    
                }    
            }   
        }    
    }    
}   