package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

public class JspHelper {

    // Web property id UA- needs to be replaced with MO- for mobile.
    private static final String GA_ACCOUNT = "UA-12345678-9";

    // Mapped url
    private static final String GA_PIXEL = "/ga";

    public static String googleAnalyticsGetImageUrl(
        HttpServletRequest request) throws UnsupportedEncodingException {
      StringBuilder url = new StringBuilder();
      url.append(request.getContextPath());
      url.append(GA_PIXEL + "?");
      url.append("utmac=").append(GA_ACCOUNT);
      url.append("&utmn=").append(Integer.toString((int) (Math.random() * 0x7fffffff)));

      String referer = request.getHeader("referer");
      String query = request.getQueryString();
      String path = request.getRequestURI();

      if (referer == null || "".equals(referer)) {
        referer = "-";
      }
      url.append("&utmr=");
      appendEncode(url, referer);

      if (path != null) {
        if (query != null) {
          path += "?" + query;
        }
        url.append("&utmp=");
        appendEncode(url, path);
      }

      url.append("&guid=ON");

      return url.toString().replace("&", "&amp;");
    }

    private static void appendEncode(StringBuilder url, String referer) {
        try {
            url.append(URLEncoder.encode(referer, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
