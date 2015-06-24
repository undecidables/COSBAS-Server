[![Stories in Ready](https://badge.waffle.io/undecidables/COSBAS-Server.png?label=ready&title=Ready)](https://waffle.io/undecidables/COSBAS-Server)

[![Join the chat at https://gitter.im/undecidables/COSBAS-Server](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/undecidables/COSBAS-Server?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

# COSBAS-Server

The Computer Science Biometric Access System's Server Code.

## Running the Server

To launch the server run gradle bootrun or gradlew bootrun in the projects root folder.  

## Notes to the team
* Please look into using git [commit keywords](https://help.github.com/articles/closing-issues-via-commit-messages/).
* Please document all code *immediately* using JavaDoc
* All http routes must be documented, maybe on the wiki somewhere? This gets really confusing to keep track of after a while...
* Branches
    + Create a new branch for a new feature
    + Before you start working on a recently merged branch please make sure to pull from master to update the branch to after the merge.
    + Use branch names with some relation to the feature you are working on.
    + Please use *pull requests* to merge into master -- do **not** merge directly. 
        - This means someone else can review your code if necessary and we have a better history of what was merged when
    + Branches can be grouped into folders by having  a / in the name:
        - inf = infrastructure  (eg. inf/gradle)
        - bug 
