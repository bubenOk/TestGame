package controllers.filters;
 
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
 
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
 
public class ResponseWrapper extends HttpServletResponseWrapper{
    private StringWriter stringWriter =  new StringWriter();;
 
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
    }
 
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return super.getOutputStream();
    }
 
    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(this.stringWriter);
    }
 
    public String getResponseContent() {
        return this.stringWriter.toString();
    }
}