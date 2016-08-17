package com.cognifide.slung.component.filter.response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ComponentResponse extends HttpServletResponseWrapper {

  private static final int BUFFER_SIZE = 1024;

  private final ByteArrayOutputStream arrayStream;

  private final ServletOutputStream stream;

  private PrintWriter writer;

  public ComponentResponse(HttpServletResponse response) {
    super(response);
    this.arrayStream = new ByteArrayOutputStream(BUFFER_SIZE);
    this.stream = new ComponentStream(arrayStream);
  }

  @Override
  public ServletOutputStream getOutputStream() {
    return stream;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    if (writer == null) {
      writer = new PrintWriter(new OutputStreamWriter(stream, getCharacterEncoding()), true);
    }
    return writer;
  }

  public ByteArrayOutputStream getContent() {
    return arrayStream;
  }
}
