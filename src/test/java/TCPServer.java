import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by admin on 2016/5/28.
 */
public class TCPServer {
    private static int port = 10000;
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(port)) {
            System.out.println("准备阻塞...");
            Socket client = server.accept();
            System.out.println("阻塞完成...");
            InputStream is = client.getInputStream();
            OutputStream os = client.getOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer)) != -1) {
                System.out.println(new String(buffer,0,len));
                os.write("收到你的请求".getBytes());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
