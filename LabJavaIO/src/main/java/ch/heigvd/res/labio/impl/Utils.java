package ch.heigvd.res.labio.impl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments. 
   * 
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    String[] result = new String[2];

    String WindowsLR = "\r\n";
    String MacLR = "\r";
    String UnixLR = "\n";
    String delimiter = "";

    if(lines.contains(WindowsLR)) {
      delimiter = WindowsLR;
    } else if(lines.contains(MacLR)) {
      delimiter = MacLR;
      } else if(lines.contains(UnixLR)) {
      delimiter = UnixLR;
    }

    result[0] = lines.substring(0, lines.indexOf(delimiter)+delimiter.length());
    result[1] = lines.substring(lines.indexOf(delimiter)+delimiter.length(), lines.length());

    return result;
  }

}
