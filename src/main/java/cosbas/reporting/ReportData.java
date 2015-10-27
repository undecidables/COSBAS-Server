package cosbas.reporting;

import java.time.LocalDateTime;

/**
 *  {@author Szymon}
 */
public class ReportData {
    private String staffID;
    private LocalDateTime sDate;
    private LocalDateTime eDate;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public ReportData(LocalDateTime sDate, LocalDateTime eDate) {
        this.sDate = sDate;
        this.eDate = eDate;
    }

    public ReportData()
    {

    }

    public ReportData(String staffID) {

        this.staffID = staffID;
    }

    public ReportData(String staffID, LocalDateTime sDate, LocalDateTime eDate) {

        this.staffID = staffID;
        this.sDate = sDate;
        this.eDate = eDate;
    }

    public String getStaffID() {

        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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
