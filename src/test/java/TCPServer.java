import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by admin on 2016/5/28.
 */
public class TCPServer {
    private static int port = 10000;

    // private static List<Socket> clients = new LinkedList<>();
    private static Map<String, Socket> clients = new LinkedHashMap<>();

    public static void main(String[] args) {

        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                System.out.println("准备阻塞...");
                final Socket client = server.accept();
                System.out.println("阻塞完成...");
                // clients.add(client);


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = null;
                        try {
                            is = client.getInputStream();
                            OutputStream os = client.getOutputStream();
                            byte[] buffer = new byte[1024];
                            int len = -1;
                            while ((len = is.read(buffer)) != -1) {
                                String text = new String(buffer, 0, len);
                                System.out.println(text);
                                Map<String, String> map = new Gson().fromJson(text, new TypeToken<Map<String, String>>() {
                                }.getType());
                                String type = map.get("type");
                                if ("request".equals(type)) {
                                    String action = map.get("action");
                                    if ("auth".equals(action)) {
                                        String sender = map.get("sender");
                                        System.out.println(sender + "认证");
                                        clients.put(sender, client);
                                    } else if ("text".equals(action)) {
                                        String sender = map.get("sender");
                                        String receiver = map.get("receiver");
                                        String content = map.get("content");

                                        Socket s = clients.get(receiver);
                                        if (s != null) {
                                            OutputStream output = s.getOutputStream();
                                            output.write(content.getBytes());
                                        } else {

                                        }
                                    }
                                } else {

                                }
                              /*  if (text.startsWith("#")) {
                                    clients.put(text, client);
                                    os.write("认证成功".getBytes());
                                } else {
                                    os.write("收到你的请求".getBytes());
                                    String[] split = text.split(":");
                                    String key = "#" + split[0];
                                    String content = split[1];
                                    Socket s = clients.get(key);
                                    if (s != null) {
                                        OutputStream output = s.getOutputStream();
                                        output.write(content.getBytes());
                                    }
                                }*/
                               /* for (Socket s : clients) {
                                    if (s != client) {
                                        OutputStream output = s.getOutputStream();
                                        output.write(text.getBytes());
                                    }
                                }*/
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
