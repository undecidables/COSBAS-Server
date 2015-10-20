package cosbas.reporting;

import java.time.LocalDateTime;

/**
 *  {@author Szymon}
 */
public class AccessRecordDataReport {
    private String userId;
    private LocalDateTime sDate;
    private LocalDateTime eDate;

    public AccessRecordDataReport(LocalDateTime sDate, LocalDateTime eDate) {
        this.sDate = sDate;
        this.eDate = eDate;
    }

    public AccessRecordDataReport(String userId) {

        this.userId = userId;
    }

    public AccessRecordDataReport(String userId, LocalDateTime sDate, LocalDateTime eDate) {

        this.userId = userId;
        this.sDate = sDate;
        this.eDate = eDate;
    }

    public String getUserId() {

        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getsDate() {
        return sDate;
    }

    public void setsDate(LocalDateTime sDate) {
        this.sDate = sDate;
    }

    public LocalDateTime geteDate() {
        return eDate;
    }

    public void seteDate(LocalDateTime eDate) {
        this.eDate = eDate;
    }
}
