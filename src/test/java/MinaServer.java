import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Created by admin on 2016/5/29.
 */
public class MinaServer {
    public static void main(String[] args) {
        NioSocketAcceptor acceptor = new NioSocketAcceptor();
        try {
            acceptor.bind(new InetSocketAddress(10003));
            DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
            chain.addLast("logger", new LoggingFilter());
            acceptor.setHandler(new IoHandler() {
                @Override
                public void sessionCreated(IoSession ioSession) throws Exception {

                }

                @Override
                public void sessionOpened(IoSession ioSession) throws Exception {

                }

                @Override
                public void sessionClosed(IoSession ioSession) throws Exception {

                }

                @Override
                public void sessionIdle(IoSession ioSession, IdleStatus idleStatus) throws Exception {

                }

                @Override
                public void exceptionCaught(IoSession ioSession, Throwable throwable) throws Exception {

                }

                @Override
                public void messageReceived(IoSession ioSession, Object o) throws Exception {
                    System.out.println("object: " + o);
                }

                @Override
                public void messageSent(IoSession ioSession, Object o) throws Exception {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
