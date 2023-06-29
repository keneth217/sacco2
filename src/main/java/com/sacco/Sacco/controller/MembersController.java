package com.sacco.Sacco.controller;

import com.sacco.Sacco.models.Members;
import com.sacco.Sacco.repository.MembersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/api/members")
@CrossOrigin("*")
public class MembersController {
    private static final String UPLOAD_DIR = "/home/kennh/PROJECTS/Sacco/src/images/";

    @Autowired
    private MembersRepository membersRepository;


    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void addMember(
            @RequestParam("portImage") MultipartFile portImageFile,
            @RequestParam("formImage") MultipartFile formImageFile,
            @RequestParam("fullName") String fullName,
            @RequestParam("idNo") String idNo,
            @RequestParam("kraPin") String kraPin,
            @RequestParam("phoneOne") String phoneOne,
            @RequestParam("phoneTwo") String phoneTwo,
            @RequestParam("email") String email,
            @RequestParam("userName") String userName,
            @RequestParam("occupation") String occupation,
            @RequestParam("dob") String dob,
            @RequestParam("county") String county,
            @RequestParam("constituency") String constituency,
            @RequestParam("ward") String ward,
            @RequestParam("address") String address,
            @RequestParam("gender") String gender,
            @RequestParam("position") String position,
            @RequestParam("kinOne") String kinOne,
            @RequestParam("kinOnePhone") String kinOnePhone,
            @RequestParam("relationshipOne") String relationshipOne,
            @RequestParam("kinTwo") String kinTwo,
            @RequestParam("kinTwoPhone") String kinTwoPhone,
            @RequestParam("relationshipTwo") String relationshipTwo
    ) throws IOException {
        if (portImageFile.isEmpty() || formImageFile.isEmpty()) {
            throw new IllegalArgumentException("Both portImage and formImage must be uploaded");
        } long maxFileSize = 10 * 1024 * 1024; // 10MB in bytes
        if (portImageFile.getSize() > maxFileSize || formImageFile.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds the maximum limit of 10MB");
        }

        // Save portImage file
        String portImageFileName = portImageFile.getOriginalFilename();
        String portImageFilePath = UPLOAD_DIR + portImageFileName;
        File portImageFileObj = new File(portImageFilePath);
        portImageFile.transferTo(portImageFileObj);

        // Save formImage file
        String formImageFileName = formImageFile.getOriginalFilename();
        String formImageFilePath = UPLOAD_DIR + formImageFileName;
        File formImageFileObj = new File(formImageFilePath);
        formImageFile.transferTo(formImageFileObj);

        // Create a new Members object
        Members members = new Members();
        members.setFullName(fullName);
        members.setIdNo(idNo);
        members.setKraPin(kraPin);
        members.setPhoneOne(phoneOne);
        members.setPhoneTwo(phoneTwo);
        members.setEmail(email);
        members.setUserName(userName);
        members.setOccupation(occupation);
        members.setDob(dob);
        members.setCounty(county);
        members.setConstituency(constituency);
        members.setWard(ward);
        members.setAddress(address);
        members.setGender(gender);
        members.setPosition(position);
        members.setKinOne(kinOne);
        members.setKinOnePhone(kinOnePhone);
        members.setRelationshipOne(relationshipOne);
        members.setKinTwo(kinTwo);
        members.setKinTwoPhone(kinTwoPhone);
        members.setRelationshipTwo(relationshipTwo);
        members.setPortImage(portImageFileName);
        members.setFormImage(formImageFileName);

        membersRepository.save(members);
    }

    @GetMapping("/get{id}")
    public Members getMemberById(@PathVariable Long id) {
        return membersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member with ID " + id + " does not exist."));
    }
    @GetMapping("/get")
    public List<Members> getAllMembers() {
        return membersRepository.findAll();
    }

    @PutMapping("/edit/{id}")
    public Members editMember(@PathVariable Long id, @RequestBody Members updatedMember) {
        Members member = membersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member with ID number " + id + " does not exist."));

        member.setFullName(updatedMember.getFullName());
        member.setIdNo(updatedMember.getIdNo());
        member.setKraPin(updatedMember.getKraPin());
        member.setPhoneOne(updatedMember.getPhoneOne());
        member.setPhoneTwo(updatedMember.getPhoneTwo());
        member.setEmail(updatedMember.getEmail());
        member.setUserName(updatedMember.getUserName());
        member.setOccupation(updatedMember.getOccupation());
        member.setDob(updatedMember.getDob());
        member.setCounty(updatedMember.getCounty());
        member.setConstituency(updatedMember.getConstituency());
        member.setWard(updatedMember.getWard());
        member.setAddress(updatedMember.getAddress());
        member.setGender(updatedMember.getGender());
        member.setPosition(updatedMember.getPosition());
        member.setKinOne(updatedMember.getKinOne());
        member.setKinOnePhone(updatedMember.getKinOnePhone());
        member.setRelationshipOne(updatedMember.getRelationshipOne());
        member.setKinTwo(updatedMember.getKinTwo());
        member.setKinTwoPhone(updatedMember.getKinTwoPhone());
        member.setRelationshipTwo(updatedMember.getRelationshipTwo());

        return membersRepository.save(member);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteMember(@PathVariable Long id) {
        Members member = membersRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member with ID " + id + " does not exist."));

        membersRepository.delete(member);
    }
//search by id
//    @GetMapping("/search")
//    public Members searchMember(@RequestParam("id") Long id) {
//        Members member = membersRepository.findById(id);
//        if (member == null) {
//            throw new IllegalArgumentException("Member with ID number " + id + " does not exist.");
//        }
//        return member;
//    }
        //search by fullname
//    @GetMapping("/search")
//    public Members searchMember(@RequestParam("fullName") String fullName) {
//        Members member = membersRepository.findByFullName(fullName);
//        if (member == null) {
//            throw new IllegalArgumentException("Member with full name " + fullName + " does not exist.");
//        }
//        return member;
//    }
   // search by both fullname and idno
@GetMapping("/search")
public Members searchMember(@RequestParam(value = "fullName", required = false) String fullName,
                            @RequestParam(value = "idNo", required = false) String idNo) {
    if (fullName != null && idNo != null) {
        Members member = membersRepository.findByFullNameAndIdNo(fullName, idNo);
        if (member == null) {
            throw new IllegalArgumentException("Member with full name " + fullName + " and ID number " + idNo + " does not exist.");
        }
        return member;
    } else if (fullName != null) {
        Members member = membersRepository.findByFullName(fullName);
        if (member == null) {
            throw new IllegalArgumentException("Member with full name " + fullName + " does not exist.");
        }
        return member;
    } else if (idNo != null) {
        Members member = membersRepository.findByIdNo(idNo);
        if (member == null) {
            throw new IllegalArgumentException("Member with ID number " + idNo + " does not exist.");
        }
        return member;
    } else {
        throw new IllegalArgumentException("Please provide either full name or ID number for the search.");
    }
    }

    @PostMapping("/import")
    public void importMembers(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Empty file uploaded.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;
            List<Members> membersList = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the header line
                }

                String[] fields = line.trim().split(","); // Assuming each cell in the CSV is separated by a comma (,)

                if (fields.length != 23) {
                    throw new IllegalArgumentException("Invalid number of fields in the CSV file.");
                }

                // Create a new Members object
                Members members = new Members();
                members.setFullName(fields[0]);
                members.setIdNo(fields[1]);
                members.setKraPin(fields[2]);
                members.setPhoneOne(fields[3]);
                members.setPhoneTwo(fields[4]);
                members.setEmail(fields[5]);
                members.setUserName(fields[6]);
                members.setOccupation(fields[7]);
                members.setDob(fields[8]);
                members.setCounty(fields[9]);
                members.setConstituency(fields[10]);
                members.setWard(fields[11]);
                members.setAddress(fields[12]);
                members.setGender(fields[13]);
                members.setPosition(fields[14]);
                members.setKinOne(fields[15]);
                members.setKinOnePhone(fields[16]);
                members.setRelationshipOne(fields[17]);
                members.setKinTwo(fields[18]);
                members.setKinTwoPhone(fields[19]);
                members.setRelationshipTwo(fields[20]);
                members.setPortImage(fields[21]);
                members.setFormImage(fields[22]);

                membersList.add(members);
            }

            membersRepository.saveAll(membersList);
        }
    }
}




