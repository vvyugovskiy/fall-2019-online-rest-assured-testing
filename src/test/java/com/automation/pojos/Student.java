package com.automation.pojos;

import java.util.List;

/**
 * {
 *     "studentId": 11651,
 *     "firstName": "James",
 *     "lastName": "Bond",
 *     "batch": 15,
 *     "joinDate": "01/01/3321",
 *     "birthDate": "01/01/1890",
 *     "password": "1234",
 *     "subject": "Math",
 *     "gender": "Males",
 *     "admissionNo": "1234",
 *     "major": "CS",
 *     "section": "101010",
 *     "contact": {
 *         "contactId": 11671,
 *         "phone": "240-123-1231",
 *         "emailAddress": "james_bond@gmail.com",
 *         "premanentAddress": "7925 Jones Branch Dr"
 *     },
 *     "company": {
 *         "companyId": 11591,
 *         "companyName": "Cybertek",
 *         "title": "SDET",
 *         "startDate": "02/02/2020",
 *         "address": {
 *             "addressId": 11611,
 *             "street": "7925 Jones Branch Dr",
 *             "city": "McLean",
 *             "state": "Virginia",
 *             "zipCode": 22102
 *         }
 *     }
 * }
 */
public class Student {

    private int studentId;
    private String firstName;
    private String lastName;
    private int batch;
    private String joinDate;
    private String birthDate;
    private String password;
    private String subject;
    private String gender;
    private String admissionNo;
    private String major;
    private String section;
    private Contact contact;
    private Company company;
    private Address address;

    public int getStudentId() {
        return studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdmissionNo() {
        return admissionNo;
    }

    public void setAdmissionNo(String admissionNo) {
        this.admissionNo = admissionNo;
    }
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", batch=" + batch +
                ", joinDate='" + joinDate + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", password='" + password + '\'' +
                ", subject='" + subject + '\'' +
                ", gender='" + gender + '\'' +
                ", admissionNo='" + admissionNo + '\'' +
                ", major='" + major + '\'' +
                ", section='" + section + '\'' +
                ", contact=" + contact +
                ", company=" + company +
                ", address=" + address +
                '}';
    }
}
