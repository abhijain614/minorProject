/*
File pojo
 */
package FTS.pojo;

import java.sql.Date;

/**
 *
 * @author DELL
 */
public class File {
private String referenceNumber;
private int senderID; 
private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public int getCurrentFileholder() {
        return currentFileholder;
    }

    public void setCurrentFileholder(int currentFileholder) {
        this.currentFileholder = currentFileholder;
    }

    public char getFileType() {
        return fileType;
    }

    public void setFileType(char fileType) {
        this.fileType = fileType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    private int creatorID;
    private int currentFileholder;
    private char fileType;
    private String subject;
    private String remark; 
    private int fileID;
    private int receiverID;

    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getFileID() {
        return fileID;
    }

    public void setFileID(int fileID) {
        this.fileID = fileID;
    }
}
