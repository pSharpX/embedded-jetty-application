import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AsyncServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static String HEAVY_RESOURCE = "This is some heavy resource that will be served in an async way";

	protected void doGet(HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final ByteBuffer content = ByteBuffer.wrap(HEAVY_RESOURCE.getBytes(StandardCharsets.UTF_8));
		final AsyncContext async = request.startAsync();
		final ServletOutputStream out = response.getOutputStream();
		out.setWriteListener(new WriteListener() {
			public void onWritePossible() throws IOException {
				while (out.isReady()) {
					if (!content.hasRemaining()) {
						response.setStatus(200);
						async.complete() ;
						return;
					}
					out.write(content.get());
				}
			}

			public void onError(Throwable t) {
				getServletContext().log("Async Error", t);
				async.complete();
			}
		});
	}
}
