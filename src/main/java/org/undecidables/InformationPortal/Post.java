package org.undecidables.InformationPortal;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * Created by Szymon on 01/07/2015.
 */
public class Post {

    @Id
    private String PostID;

    private String Title;
    private String Contents;
    private String AuthorID;
    private LocalDateTime dateTime;

    /**
     * Use in case of post creation
     * @param authorID UserID of poster
     * @param contents Contents of news post
     */
    public Post(String authorID, String Title, String contents)
    {
        this.Contents = contents;
        this.AuthorID = authorID;
        this.dateTime = LocalDateTime.now();
    }
/*  This constructor should not be needed, but I am keeping it here for safety sake
    public Post(String postID, String authorID, String contents, LocalDateTime dateTime)
    {
        this.Contents = contents;
        this.PostID = postID;
        this.AuthorID = authorID;
        this.dateTime = dateTime;
    }
*/
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
