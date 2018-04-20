import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

public class EmbeddedJettyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int maxThreads = 100;
		int minThreads = 10;
		int idleTimeout = 120;
		 
		QueuedThreadPool threadPool = new QueuedThreadPool(maxThreads, minThreads, idleTimeout);
		 
		//server = new Server(7070);
		Server server = new Server(threadPool);
		ServletContextHandler handler = new ServletContextHandler(server, "/example");		
		handler.addServlet(ExampleServlet.class, "/");
		handler.addServlet(AsyncServlet.class, "/async");
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
