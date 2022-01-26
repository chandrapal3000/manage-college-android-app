package com.chandrapal.manage_college;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Model {

    private String usersId;
    private String usersFullname;
    private String usersRegNum;
    private String usersUsername;
    private String usersPassword;
    private String usersPhone;
    private String usersEmail;
    private String usersBio;
    private String usersType;
    private String usersProfileImage;
    private String usersStatus;

    private String photosId;
    private String photosImage;
    private String photosUploadedBy;
    private String photosUserId;
    private String photosUserProfileImage;
    private String photosCaption;
    private String photosUploadedTime;
    private String photosImageSize;
    private String photosStatus;
    private String photosPinned;

    private String newsId;
    private String newsTitle;
    private String newsNews;
    private String newsUploadedBy;
    private String newsUserId;
    private String newsUserProfileImage;
    private String newsBranch;
    private String newsRelatedDocument;
    private String newsUploadedTime;
    private String newsStatus;
    private String newsPinned;

    private String notesId;
    private String notesTitle;
    private String notesNotesText;
    private String notesUploadedBy;
    private String notesUserId;
    private String notesUserProfileImage;
    private String notesBranch;
    private String notesSubject;
    private String notesChapter;
    private String notesUploadedTime;
    private String notesStatus;
    private String notesPinned;

    public Model(){ }

    public Model(String usersId, String usersFullname, String usersRegNum, String usersUsername, String usersPassword, String usersPhone, String usersEmail, String usersBio, String usersType, String usersProfileImage, String usersStatus, String photosId, String photosImage, String photosUploadedBy, String photosUserId, String photosUserProfileImage, String photosCaption, String photosUploadedTime, String photosImageSize, String photosStatus, String photosPinned, String newsId, String newsTitle, String newsNews, String newsUploadedBy, String newsUserId, String newsUserProfileImage, String newsBranch, String newsRelatedDocument, String newsUploadedTime, String newsStatus, String newsPinned, String notesId, String notesTitle, String notesNotesText, String notesUploadedBy, String notesUserId, String notesUserProfileImage, String notesBranch, String notesSubject, String notesChapter, String notesUploadedTime, String notesStatus, String notesPinned) {
        this.usersId = usersId;
        this.usersFullname = usersFullname;
        this.usersRegNum = usersRegNum;
        this.usersUsername = usersUsername;
        this.usersPassword = usersPassword;
        this.usersPhone = usersPhone;
        this.usersEmail = usersEmail;
        this.usersBio = usersBio;
        this.usersType = usersType;
        this.usersProfileImage = usersProfileImage;
        this.usersStatus = usersStatus;
        this.photosId = photosId;
        this.photosImage = photosImage;
        this.photosUploadedBy = photosUploadedBy;
        this.photosUserId = photosUserId;
        this.photosUserProfileImage = photosUserProfileImage;
        this.photosCaption = photosCaption;
        this.photosUploadedTime = photosUploadedTime;
        this.photosImageSize = photosImageSize;
        this.photosStatus = photosStatus;
        this.photosPinned = photosPinned;
        this.newsId = newsId;
        this.newsTitle = newsTitle;
        this.newsNews = newsNews;
        this.newsUploadedBy = newsUploadedBy;
        this.newsUserId = newsUserId;
        this.newsUserProfileImage = newsUserProfileImage;
        this.newsBranch = newsBranch;
        this.newsRelatedDocument = newsRelatedDocument;
        this.newsUploadedTime = newsUploadedTime;
        this.newsStatus = newsStatus;
        this.newsPinned = newsPinned;
        this.notesId = notesId;
        this.notesTitle = notesTitle;
        this.notesNotesText = notesNotesText;
        this.notesUploadedBy = notesUploadedBy;
        this.notesUserId = notesUserId;
        this.notesUserProfileImage = notesUserProfileImage;
        this.notesBranch = notesBranch;
        this.notesSubject = notesSubject;
        this.notesChapter = notesChapter;
        this.notesUploadedTime = notesUploadedTime;
        this.notesStatus = notesStatus;
        this.notesPinned = notesPinned;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public String getUsersFullname() {
        return usersFullname;
    }

    public void setUsersFullname(String usersFullname) {
        this.usersFullname = usersFullname;
    }

    public String getUsersRegNum() {
        return usersRegNum;
    }

    public void setUsersRegNum(String usersRegNum) {
        this.usersRegNum = usersRegNum;
    }

    public String getUsersUsername() {
        return usersUsername;
    }

    public void setUsersUsername(String usersUsername) {
        this.usersUsername = usersUsername;
    }

    public String getUsersPassword() {
        return usersPassword;
    }

    public void setUsersPassword(String usersPassword) {
        this.usersPassword = usersPassword;
    }

    public String getUsersPhone() {
        return usersPhone;
    }

    public void setUsersPhone(String usersPhone) {
        this.usersPhone = usersPhone;
    }

    public String getUsersEmail() {
        return usersEmail;
    }

    public void setUsersEmail(String usersEmail) {
        this.usersEmail = usersEmail;
    }

    public String getUsersBio() {
        return usersBio;
    }

    public void setUsersBio(String usersBio) {
        this.usersBio = usersBio;
    }

    public String getUsersType() {
        return usersType;
    }

    public void setUsersType(String usersType) {
        this.usersType = usersType;
    }

    public String getUsersProfileImage() {
        return usersProfileImage;
    }

    public void setUsersProfileImage(String usersProfileImage) {
        this.usersProfileImage = usersProfileImage;
    }

    public String getUsersStatus() {
        return usersStatus;
    }

    public void setUsersStatus(String usersStatus) {
        this.usersStatus = usersStatus;
    }

    public String getPhotosId() {
        return photosId;
    }

    public void setPhotosId(String photosId) {
        this.photosId = photosId;
    }

    public String getPhotosImage() {
        return photosImage;
    }

    public void setPhotosImage(String photosImage) {
        this.photosImage = photosImage;
    }

    public String getPhotosUploadedBy() {
        return photosUploadedBy;
    }

    public void setPhotosUploadedBy(String photosUploadedBy) {
        this.photosUploadedBy = photosUploadedBy;
    }

    public String getPhotosUserId() {
        return photosUserId;
    }

    public void setPhotosUserId(String photosUserId) {
        this.photosUserId = photosUserId;
    }

    public String getPhotosUserProfileImage() {
        return photosUserProfileImage;
    }

    public void setPhotosUserProfileImage(String photosUserProfileImage) {
        this.photosUserProfileImage = photosUserProfileImage;
    }

    public String getPhotosCaption() {
        return photosCaption;
    }

    public void setPhotosCaption(String photosCaption) {
        this.photosCaption = photosCaption;
    }

    public String getPhotosUploadedTime() {
        return photosUploadedTime;
    }

    public void setPhotosUploadedTime(String photosUploadedTime) {
        this.photosUploadedTime = photosUploadedTime;
    }

    public String getPhotosImageSize() {
        return photosImageSize;
    }

    public void setPhotosImageSize(String photosImageSize) {
        this.photosImageSize = photosImageSize;
    }

    public String getPhotosStatus() {
        return photosStatus;
    }

    public void setPhotosStatus(String photosStatus) {
        this.photosStatus = photosStatus;
    }

    public String getPhotosPinned() {
        return photosPinned;
    }

    public void setPhotosPinned(String photosPinned) {
        this.photosPinned = photosPinned;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsNews() {
        return newsNews;
    }

    public void setNewsNews(String newsNews) {
        this.newsNews = newsNews;
    }

    public String getNewsUploadedBy() {
        return newsUploadedBy;
    }

    public void setNewsUploadedBy(String newsUploadedBy) {
        this.newsUploadedBy = newsUploadedBy;
    }

    public String getNewsUserId() {
        return newsUserId;
    }

    public void setNewsUserId(String newsUserId) {
        this.newsUserId = newsUserId;
    }

    public String getNewsUserProfileImage() {
        return newsUserProfileImage;
    }

    public void setNewsUserProfileImage(String newsUserProfileImage) {
        this.newsUserProfileImage = newsUserProfileImage;
    }

    public String getNewsBranch() {
        return newsBranch;
    }

    public void setNewsBranch(String newsBranch) {
        this.newsBranch = newsBranch;
    }

    public String getNewsRelatedDocument() {
        return newsRelatedDocument;
    }

    public void setNewsRelatedDocument(String newsRelatedDocument) {
        this.newsRelatedDocument = newsRelatedDocument;
    }

    public String getNewsUploadedTime() {
        return newsUploadedTime;
    }

    public void setNewsUploadedTime(String newsUploadedTime) {
        this.newsUploadedTime = newsUploadedTime;
    }

    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }

    public String getNewsPinned() {
        return newsPinned;
    }

    public void setNewsPinned(String newsPinned) {
        this.newsPinned = newsPinned;
    }

    public String getNotesId() {
        return notesId;
    }

    public void setNotesId(String notesId) {
        this.notesId = notesId;
    }

    public String getNotesTitle() {
        return notesTitle;
    }

    public void setNotesTitle(String notesTitle) {
        this.notesTitle = notesTitle;
    }

    public String getNotesNotesText() {
        return notesNotesText;
    }

    public void setNotesNotesText(String notesNotesText) {
        this.notesNotesText = notesNotesText;
    }

    public String getNotesUploadedBy() {
        return notesUploadedBy;
    }

    public void setNotesUploadedBy(String notesUploadedBy) {
        this.notesUploadedBy = notesUploadedBy;
    }

    public String getNotesUserId() {
        return notesUserId;
    }

    public void setNotesUserId(String notesUserId) {
        this.notesUserId = notesUserId;
    }

    public String getNotesUserProfileImage() {
        return notesUserProfileImage;
    }

    public void setNotesUserProfileImage(String notesUserProfileImage) {
        this.notesUserProfileImage = notesUserProfileImage;
    }

    public String getNotesBranch() {
        return notesBranch;
    }

    public void setNotesBranch(String notesBranch) {
        this.notesBranch = notesBranch;
    }

    public String getNotesSubject() {
        return notesSubject;
    }

    public void setNotesSubject(String notesSubject) {
        this.notesSubject = notesSubject;
    }

    public String getNotesChapter() {
        return notesChapter;
    }

    public void setNotesChapter(String notesChapter) {
        this.notesChapter = notesChapter;
    }

    public String getNotesUploadedTime() {
        return notesUploadedTime;
    }

    public void setNotesUploadedTime(String notesUploadedTime) {
        this.notesUploadedTime = notesUploadedTime;
    }

    public String getNotesStatus() {
        return notesStatus;
    }

    public void setNotesStatus(String notesStatus) {
        this.notesStatus = notesStatus;
    }

    public String getNotesPinned() {
        return notesPinned;
    }

    public void setNotesPinned(String notesPinned) {
        this.notesPinned = notesPinned;
    }

    public String cleanString01(String messyString){
        return messyString.toLowerCase().trim().replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    }

}
