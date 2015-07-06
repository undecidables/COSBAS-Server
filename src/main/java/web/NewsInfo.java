package web;

import java.time.LocalDateTime;

/**
 * Class to show the information/news
 * @author Elzahn Botha
 */
public class NewsInfo 
{
    public void addNews(String title, String content, LocalDateTime date, String id)
    {
        //adds the provided news to the newsfeed
    }
    
    public String retrieveNews()
    {
        //gets the stored news
        return "news";
    }
    
    public void displayNews()
    {
        //displays the news if needed
    }
    
    //can't think of any other functions needed here at this time
}
