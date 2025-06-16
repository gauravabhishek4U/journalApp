package com.learning.journalApp.controller;

import com.learning.journalApp.entity.JournalEntry;
import com.learning.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAllJournals(){
        return journalEntryService.getAllEntries();
    }

    @GetMapping("/id/{myId}")
    public JournalEntry getJournalById(@PathVariable ObjectId myId){
        return journalEntryService.getEntryById(myId);
    }

    @PostMapping
    public JournalEntry createJournal(@RequestBody JournalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.createJournalEntry(myEntry);
        return myEntry;
    }

    @DeleteMapping("/deleteEntryById/{myId}")
    public boolean deleteJournalById(@PathVariable ObjectId myId){
        journalEntryService.deleteEntryById(myId);
        return true;
    }

    @PutMapping("/updateEntry/{myId}")
    public JournalEntry updateJournalById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        JournalEntry existingEntry = journalEntryService.getEntryById(myId);
        if(existingEntry != null){
            existingEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : existingEntry.getTitle());
            existingEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : existingEntry.getContent());
        }
        return journalEntryService.updateEntryById(existingEntry);
    }
}
