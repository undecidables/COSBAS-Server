package InformationPortal;

import java.time.LocalDateTime;

/**
 * Created by Szymon on 01/07/2015.
 */
public class Post {
    private String contents;
    private String authorID;
    private String postID;
    private LocalDateTime dateTime;

    public Post(String postID, String authorID, String contents, LocalDateTime dateTime)
    {
        this.contents = contents;
        this.postID = postID;
        this.authorID = authorID;
        this.dateTime = dateTime;
    }

    public String getPostID()
    {
        return this.postID;
    }

    public String getContents()
    {
        return this.contents;
    }

    public String getAuthorID()
    {
        return this.authorID;
    }

    public LocalDateTime getDateTime()
    {
        return this.dateTime;
    }


}
