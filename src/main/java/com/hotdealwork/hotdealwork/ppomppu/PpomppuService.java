package com.hotdealwork.hotdealwork.ppomppu;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class PpomppuService {
    public List<Ppomppu> getPpomppuFeed() {
        List<Ppomppu> ppomppus = new ArrayList<>();
        try {
            URL ppomppuURL = new URL("https://www.ppomppu.co.kr/rss.php?id=ppomppu");

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(ppomppuURL));

            for (SyndEntry entry : feed.getEntries()) {
                String title = entry.getTitle();
                String link = entry.getLink();
                String author = entry.getAuthor();
                ppomppus.add(new Ppomppu(title, link, author));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ppomppus;
    }
}
