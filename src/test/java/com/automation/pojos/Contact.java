package com.automation.pojos;

import com.google.gson.annotations.SerializedName;

/**
 * "contact": {
 *         "contactId": 11671,
 *         "phone": "240-123-1231",
 *         "emailAddress": "james_bond@gmail.com",
 *         "premanentAddress": "7925 Jones Branch Dr"
 *     }
 */

public class Contact {

    @SerializedName("contactId")
    private Integer contactId;
    @SerializedName("phone")
    private String phone;
    @SerializedName("emailAddress")
    private String emailAddress;
    @SerializedName("premanentAddress")
    private String premanentAddress;

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPremanentAddress() {
        return premanentAddress;
    }

    public void setPremanentAddress(String premanentAddress) {
        this.premanentAddress = premanentAddress;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "contactId=" + contactId +
                ", phone='" + phone + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", premanentAddress='" + premanentAddress + '\'' +
                '}';
    }
}
