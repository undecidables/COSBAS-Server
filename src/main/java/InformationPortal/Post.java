package InformationPortal;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * Created by Szymon on 01/07/2015.
 */
public class Post {

    @Id
    private String PostID;

    private String Contents;
    private String AuthorID;
    private LocalDateTime dateTime;

    public Post(String authorID, String contents, LocalDateTime dateTime)
    {
        this.Contents = contents;
        this.AuthorID = authorID;
        this.dateTime = dateTime;
    }

    public Post(String postID, String authorID, String contents, LocalDateTime dateTime)
    {
        this.Contents = contents;
        this.PostID = postID;
        this.AuthorID = authorID;
        this.dateTime = dateTime;
    }

    public String getPostID()
    {
        return this.PostID;
    }

    public String getContents()
    {
        return this.Contents;
    }

    public String getAuthorID()
    {
        return this.AuthorID;
    }

    public LocalDateTime getDateTime()
    {
        return this.dateTime;
    }


}
