package cnnnews.inclassfive.inclassfive;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ojasv on 2/13/17.
 */
public class NewsUtil {
    static public class NewsPullParser {
        static ArrayList<News> parseNews(InputStream in) throws XmlPullParserException, IOException {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in, "UTF-8");
            News news = null;
            ArrayList<News> newsList = new ArrayList<News>();
            int event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("item")) {
                            news = new News();
                        } else if (parser.getName().equals("link")) {
                            String data = parser.nextText();
                            if (data != null && news != null)
                                news.setNewsFeedLink(data);
                        } else if (parser.getName().equals("title")) {
                            String data = parser.nextText();
                            if (data != null && news != null)
                                news.setTitle(data);
                        } else if (parser.getName().equals("description")) {
                            String data = parser.nextText();
                            if (data != null && news != null) {
                                //To skip html tags from description
                                String[] text = data.split("<");
                                if (text[0].length() == 0) {
                                    news.setNewsDescription("No Description available");
                                } else {
                                    news.setNewsDescription(text[0].trim());
                                }
                            }
                        } else if (parser.getName().equals("pubDate")) {
                            String data = parser.nextText();
                            if (data != null && news != null) {
                                DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss z", Locale.US);
                                try {
                                    Date date = formatter.parse(data.trim());
                                    news.setPubDate(date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else if (parser.getName().equals("media:content")) {
                            int tempRes = 0;
                            while (parser.getName().equals("media:content")) {
                                int widthValue = Integer.parseInt(parser.getAttributeValue(null, "width"));
                                int heightValue = Integer.parseInt(parser.getAttributeValue(null, "height"));
                                String data = parser.getAttributeValue(null, "url");
                                if (widthValue == heightValue) {
                                    if (data != null && news != null)
                                        news.setThumbnailImageUrl(data);
                                } else if (widthValue * heightValue > tempRes) {
                                    news.setLargeImageUrl(data);
                                    tempRes = widthValue * heightValue;
                                }
                                parser.next();
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            newsList.add(news);
                            news = null;
                        }
                        break;
                }
                event = parser.next();
            }
            Collections.sort(newsList);
            return newsList;
        }
    }
}