package ch.heigvd.res.labio.impl.filters;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private char lastChar;
  private int lineNumber = 1;
  private boolean isNewLine = true;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    for(int i = 0; i < len; ++i) {
      if(off + i > str.length()) {
        break;
      }

      write(str.charAt(off + i));
    }
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    write(String.valueOf(cbuf), off, len);
  }

  @Override
  public void write(int c) throws IOException {
    char currentChar = (char) c;

    if(isNewLine) {
      out.write(String.valueOf(lineNumber++) + '\t');
      isNewLine = false;
    }

    if (currentChar == '\n') {
      if (lastChar == '\r') {
        out.write("\r\n");
      } else {
        out.write("\n");
      }

      out.write(String.valueOf(lineNumber++) + '\t');
    } else if (currentChar != '\r') {
      if(lastChar == '\r') {
        out.write('\r');
        out.write(String.valueOf(lineNumber++) + '\t');
      }

      out.write(currentChar);
    }


    lastChar = currentChar;
  }

}
