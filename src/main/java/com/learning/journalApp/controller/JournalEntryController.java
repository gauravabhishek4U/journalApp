package com.learning.journalApp.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.entity.User;
import com.learning.journalApp.service.JournalEntryService;
import com.learning.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournals(){
        List<JournalEntry> allEntries = journalEntryService.getAllEntries();
        if(allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myId}")
    public ResponseEntity <JournalEntry> getJournalById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry = Optional.ofNullable(journalEntryService.getEntryById(myId));
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("{userName}")
    public ResponseEntity <JournalEntry> createJournal(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try{
            journalEntryService.createJournalEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalById(@PathVariable String userName, @PathVariable ObjectId myId){
        journalEntryService.deleteEntryById(myId, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{userName}/{myId}")
    public ResponseEntity<?> updateJournalById(
            @PathVariable ObjectId myId,
             @RequestBody JournalEntry newEntry,
             @PathVariable String userName){
        JournalEntry existingEntry = journalEntryService.getEntryById(myId);
        if(existingEntry != null){
            existingEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : existingEntry.getContent());
            journalEntryService.updateEntryById(existingEntry);
            return new ResponseEntity<>(existingEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if(allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
