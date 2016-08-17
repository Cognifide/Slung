package com.cognifide.slung.component.filter.response;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;

class ComponentStream extends ServletOutputStream {

  private final OutputStream outputStream;

  ComponentStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  @Override
  public void write(int value) throws IOException {
    this.outputStream.write(value);
  }

  @Override
  public void write(byte[] value) throws IOException {
    this.outputStream.write(value);
  }

  @Override
  public void write(byte[] b, int off, int len) throws IOException {
    this.outputStream.write(b, off, len);
  }
}
